/**
 * 
 */
package com.sy.investment.service.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.domain.table.Sale;
import com.sy.investment.domain.table.SaleDtl;
import com.sy.investment.domain.table.SaleSettle;
import com.sy.investment.domain.table.SettleType;
import com.sy.investment.domain.vo.StoreRefundVo;
import com.sy.investment.domain.vo.StoreRefundVo.OrderDtl;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.reponsitory.CommonDao;
import com.sy.investment.service.RefundService;
import com.sy.investment.utils.MathUtil;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

/** 
* @ClassName: RefundServiceImpl 
* @Description: 
* @author albert
* @date 2018年9月28日 下午1:23:40 
*  
*/
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class RefundServiceImpl implements RefundService {
	@Resource CommonDao commonDao;
	@Override
	public Sale save(StoreRefundVo order) {
		Sale sale = commonDao.findEntityByIdForUpdate(Sale.class, order.getId());
		Assert.notNull(sale,"退货失败，未找到销售单");
		//退单
		Sale refund = Sale.builder()
					.amount(BigDecimal.ZERO)
					.changeAmount(BigDecimal.ZERO)
					.creuserId(UserThreadLocal.get().getId())
					.creDate(new Date())
					.memberId(sale.getMemberId())
					.salemanId(order.getSalemanId())
					.storeId(UserThreadLocal.get().getStoreid())
					.dtls(order.getList().size())
					.distributeStatus(0)
					.type(-1)
					.build();
		commonDao.save(refund);
		BigDecimal amount = BigDecimal.ZERO;
		for(OrderDtl dtl : order.getList()) {
			SaleDtl saleDtl = commonDao.findEntityByIdForUpdate(SaleDtl.class, dtl.getId());
			Assert.notNull(saleDtl,"退货失败，未找到销售明细数据，id："+dtl.getId());
			Assert.isTrue(dtl.getRefund().compareTo(saleDtl.getQty().subtract(saleDtl.getRefundQty()))<=0,"退货失败，退货数量超过可退货数量");
			
			BigDecimal dtlAmount = MathUtil.mul(saleDtl.getPrice(), dtl.getRefund());
			SaleDtl refundDtl =SaleDtl.builder()
							.saleId(refund.getId())
							.amount(dtlAmount)
							.goodsId(saleDtl.getGoodsId())
							.price(saleDtl.getPrice())
							.qty(dtl.getRefund())
							.saleDtlId(saleDtl.getId())
							.build();
			commonDao.save(refundDtl);
			commonDao.update(SaleDtl.class, " set refundQty=refundQty+:refundQty where id=:id ", ParamValue.build().add("refundQty", dtl.getRefund()).add("id", saleDtl.getId()));
			amount = amount.add(dtlAmount);
		}
		refund.setAmount(amount);
		commonDao.update(refund);
		if(order.getCardAmount().compareTo(BigDecimal.ZERO)==1) {  //会员卡
			saveSettle(order, refund.getId(), SettleType.MEMBERCARD);
		}
		if(order.getCashAmount().compareTo(BigDecimal.ZERO)==1) {//现金
			saveSettle(order, refund.getId(), SettleType.CASH);
		}
		if(order.getPosAmount().compareTo(BigDecimal.ZERO)==1) {//POS
			saveSettle(order, refund.getId(), SettleType.POS);
		}
		commonDao.flush();
		//存储过程
		List<ParamsEntity> pe = Arrays.asList(
				new ParamsEntity(Types.BIGINT, refund.getId())
				);
		String[] procResult = commonDao.executeProcs("p_refund", pe);
		if(procResult[0].equals("-1")) throw new ServiceException(procResult[1]);
		commonDao.clear();
		refund = commonDao.findEntityById(Sale.class, refund.getId());
		return refund;
	}
	private void saveSettle(StoreRefundVo order,Long saleId,Long typeId) {
		BigDecimal amount = BigDecimal.ZERO;
		if(typeId-SettleType.MEMBERCARD==0) 	amount = order.getCardAmount();
		if(typeId-SettleType.CASH==0) 		amount = order.getCashAmount();
		if(typeId-SettleType.POS==0) 		amount = order.getPosAmount();
		
		SaleSettle settle = SaleSettle.builder()
				.amount(amount.negate())
				.creDate(new Date())
				.saleId(saleId)
				.settleTypeId(typeId)
				.storeId(UserThreadLocal.get().getStoreid())
				.build();
		commonDao.save(settle);
	}
}

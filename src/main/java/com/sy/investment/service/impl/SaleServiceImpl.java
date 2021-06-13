/**
 * 
 */
package com.sy.investment.service.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.entity.SaleExchangeSettleEntity;
import com.sy.investment.domain.table.PackageSale;
import com.sy.investment.domain.table.ReceivableAccount;
import com.sy.investment.domain.table.ReceivableAccountDtl;
import com.sy.investment.domain.table.ReceiveOrder;
import com.sy.investment.domain.table.ReceiveOrderDtl;
import com.sy.investment.domain.table.Sale;
import com.sy.investment.domain.table.SaleDtl;
import com.sy.investment.domain.table.SaleExchangeSettle;
import com.sy.investment.domain.table.SaleExchangeSettleDtl;
import com.sy.investment.domain.table.SaleSettle;
import com.sy.investment.domain.table.SettleType;
import com.sy.investment.domain.table.WebOrder;
import com.sy.investment.domain.table.WebOrderSettle;
import com.sy.investment.domain.vo.ExchangeEggVo;
import com.sy.investment.domain.vo.PackageConvertVo;
import com.sy.investment.domain.vo.ReceivableAccountVo;
import com.sy.investment.domain.vo.StoreOrderVo;
import com.sy.investment.domain.vo.StoreOrderVo.OrderDtl;
import com.sy.investment.domain.vo.TradePayVo;
import com.sy.investment.exceptions.PayFeignException;
import com.sy.investment.exceptions.PaySettleException;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.feign.PayServiceFeign;
import com.sy.investment.reponsitory.CommonDao;
import com.sy.investment.service.SaleServie;
import com.sy.investment.utils.CommonUtils;
import com.sy.investment.utils.Constants.OrderStatus;
import com.sy.investment.utils.MathUtil;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: SaleServiceImpl 
* @Description: 
* @author albert
* @date 2018年8月28日 下午2:08:57 
*  
*/
@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class SaleServiceImpl implements SaleServie {
	@Resource CommonDao commonDao;
	@Resource PayServiceFeign payServiceFeign;
	@Override
	public RestEntity save(StoreOrderVo order) {
		//1销售保存
		Sale sale=null;
		try {
			sale = Sale.builder()
						.amount(order.getAmount())
						.changeAmount(order.getChangeAmount())
						.creuserId(UserThreadLocal.get().getId())
						.creDate(new Date())
						.memberId(order.getMemberId())
						.salemanId(order.getSalemanId())
						.storeId(UserThreadLocal.get().getStoreid())
						.dtls(order.getList().size())
						.distributeStatus(order.getDistributeStatus())
						.type(1)
						.billno(commonDao.executeFunc(String.class, "select f_get_out_trade_no() ", null))
						.cashAmount(order.getCashAmount())
						.cardAmount(order.getCardAmount())
						.posAmount(order.getPosAmount())
						.receivableAmount(order.getReceivableStatus()==1?order.getAmount():null)
						.build();
			
			commonDao.save(sale);
			for(OrderDtl dtl : order.getList()) {
				SaleDtl saleDtl =SaleDtl.builder()
								.saleId(sale.getId())
								.amount(MathUtil.mul(dtl.getPrice(), BigDecimal.valueOf(dtl.getCount())))
								.goodsId(dtl.getGoodsId())
								.price(dtl.getPrice())
								.qty(BigDecimal.valueOf(dtl.getCount()))
								.priceType(dtl.getPriceType())
								.priceId(dtl.getPriceId())
								.promotionId(dtl.getPromotionId())
								.promotionStatus(dtl.getPromotionId()>0?1:0)
								.build();
				if(dtl.getFree()>0) {
					
					saleDtl.setQty(saleDtl.getQty().subtract(BigDecimal.valueOf(dtl.getFree())));
					saleDtl.setAmount(MathUtil.mul(dtl.getPrice(), saleDtl.getQty()));
					dtl.getPackageDtlMap().forEach((key,value) -> {
						if(value==0)return;
						SaleDtl gift = new SaleDtl();
						BeanUtils.copyProperties(saleDtl, gift);
						gift.setQty(BigDecimal.valueOf(value));
						gift.setAmount(BigDecimal.ZERO);
						gift.setPrice(BigDecimal.ZERO);
						gift.setPackageDtlId(key);
						gift.setPriceId(null);
						gift.setPromotionId(null);
						gift.setPromotionStatus(0);
						commonDao.save(gift);
					});
					
				}
				if(saleDtl.getQty().compareTo(BigDecimal.ZERO)==1) {				
					commonDao.save(saleDtl);
				}
			}
		} catch (Exception e) {
			log.error("订单保存失败",e);
			throw new ServiceException("订单保存失败:"+e.getMessage());
		}
		RestEntity result = null;
		commonDao.clear();
		Sale s = commonDao.findEntityById(Sale.class, sale.getId());
		//2扫码支付金额>0，调用扫码feign
		if(order.getPosAmount().compareTo(BigDecimal.ZERO)==1) {
			TradePayVo vo = TradePayVo.buildByStoreOrder(order, s);
			try {
				result = payFeign(vo);
				Map<String,String> map = (Map<String, String>) result.getObject();
				if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {					
					paySuccess(s);
				}
			} catch (PayFeignException | PaySettleException e) {
				result = RestEntity.failed(e.getMessage());
				result.setObject(s.getBillno());
			} 
		}else {
			try {
				paySuccess(s);
				Map<String, String> map = OrderStatus.SUCCESS.toMap();
				map.put("outTradeNo", s.getBillno());
				result = RestEntity.success(map);
			} catch (PaySettleException e) {
				result = RestEntity.failed(e.getMessage());
				result.setObject(s.getBillno());
			}
		}
		return  result;
	}
	/**
	 * 请求支付服务
	 * @param vo
	 */
	private RestEntity payFeign(TradePayVo vo) {
		try {
			RestEntity result = null;
			if(CommonUtils.payType(vo.getAuthCode())==1) {
				result = payServiceFeign.alipay(vo);
			}else {
				result = payServiceFeign.micopay(vo);
			}
			checkResult(result);
			( (Map<String, String>)result.getObject()).put("outTradeNo", vo.getOutTradeNo());
			return result;
		}catch (PayFeignException e) {
			throw e;
		}catch (Exception e) {
			throw PayFeignException.from("",e.getMessage());
		}
	}
	private void checkResult(RestEntity result) {
		Assert.notNull(result, "响应结果为null，请人工确认订单状态");
		if(result.getStatus()==-1) throw new ServiceException(result.getMsg());
		Object obj = result.getObject();
		Assert.notNull(obj, "响应结果为null，请人工确认订单状态");
		Map<String,String> map;
		try {
			map = (Map<String, String>) obj;
		} catch (Exception e) {
			throw new ServiceException("响应结果异常，请人工确认订单状态");
		}
		String code = map.get("code");
		String msg = map.get("msg");
		if(StringUtils.isAnyEmpty(code,msg)) throw new ServiceException("响应结果异常，请人工确认订单状态");
		
	}
	private void paySuccess(Sale sale) {
		try {
			//结算保存
			saveSettle(sale);
			commonDao.flush();
			//存储过程
			List<ParamsEntity> pe = Arrays.asList(
					new ParamsEntity(Types.BIGINT, sale.getId())
					);
			String[] procResult = commonDao.executeProcs("p_sale", pe);
			if(procResult[0].equals("-1")) throw new ServiceException(procResult[1]);
		} catch (Exception e) {
			log.error("订单结算失败",e);
			throw new PaySettleException("付款成功，但结算失败："+e.getMessage());
		}
	}
	
	@Override
	public RestEntity syncPayResult(String outTradeNo) {
		Sale s = commonDao.findEntity(Sale.class, " where billno=:billno ", ParamValue.build().add("billno", outTradeNo));
		Assert.notNull(s, OrderStatus.ORDERNOTEXIST.msg);
		Sale sale = commonDao.findEntityByIdForUpdate(Sale.class, s.getId());
		
		List<OrderStatus> stop = Lists.newArrayList(OrderStatus.CLOSED,OrderStatus.SUCCESS,OrderStatus.REVOKED,OrderStatus.MANUAL);
		OrderStatus status = stop.stream()
				.filter(item -> item.code == s.getStatus())
                .findAny()
                .orElse(null);
		if(status!=null ) {
			return RestEntity.success(status.toMap());
		}
		
		
		RestEntity result;
		try {
			result = payServiceFeign.syncPayResult(outTradeNo);
			checkResult(result);
			Map<String,String> map = (Map<String, String>) result.getObject();
			if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {					
				paySuccess(s);
			}
			List<Integer> list = Lists.newArrayList(OrderStatus.CLOSED.code,OrderStatus.MANUAL.code,OrderStatus.REVOKED.code);
			if(list.contains(Integer.parseInt(map.get("code")))) {
				commonDao.update(Sale.class, " set status=:status,remark=:remark where id=:id ", ParamValue.build().add("id", sale.getId()).add("remark", map.get("msg")).add("status", Integer.parseInt(map.get("code"))));
			}
		} catch (Exception e) {
			return RestEntity.failed(e.getMessage());
		}
		
		return result;
	}
	
	
	private void saveSettle(Sale sale) {
		BigDecimal 	cardAmount = Optional.<BigDecimal>ofNullable(sale.getCardAmount()).orElse(BigDecimal.ZERO); 
		BigDecimal 	cashAmount = Optional.<BigDecimal>ofNullable(sale.getCashAmount()).orElse(BigDecimal.ZERO);
		BigDecimal 	posAmount = Optional.<BigDecimal>ofNullable(sale.getPosAmount()).orElse(BigDecimal.ZERO);
		BigDecimal 	receivableAmount = Optional.<BigDecimal>ofNullable(sale.getReceivableAmount()).orElse(BigDecimal.ZERO);
		if(cardAmount.compareTo(BigDecimal.ZERO)>0) {
			save(cardAmount,sale.getId(),SettleType.MEMBERCARD);
		}
		if(cashAmount.compareTo(BigDecimal.ZERO)>0) {
			save(cashAmount,sale.getId(),SettleType.CASH);
		}
		if(posAmount.compareTo(BigDecimal.ZERO)>0) {
			save(posAmount,sale.getId(),SettleType.POS);
		}
		if(receivableAmount.compareTo(BigDecimal.ZERO)>0) {
			save(receivableAmount,sale.getId(),SettleType.RECEIVABLE);
		}
		//只购买一个且享受特权
		if(MathUtil.add(cardAmount, cashAmount,posAmount,receivableAmount).compareTo(BigDecimal.ZERO)==0) {
			save(BigDecimal.ZERO,sale.getId(),SettleType.CASH);
		}
	}
	private void save(BigDecimal amount,Long saleId,Long typeId) {
		SaleSettle settle = SaleSettle.builder()
				.amount(amount)
				.creDate(new Date())
				.saleId(saleId)
				.settleTypeId(typeId)
				.storeId(UserThreadLocal.get().getStoreid())
				.build();
		commonDao.save(settle);
	}
	@Override
	public void save(List<SaleExchangeSettleEntity> list) {
		SaleExchangeSettleEntity tmp = list.get(0);
		SaleExchangeSettle settle = SaleExchangeSettle.builder()
				.amount(BigDecimal.ZERO)
				.beginDate(tmp.getBeginDate())
				.endDate(tmp.getEndDate())
				.creDate(new Date())
				.saleManId(tmp.getSaleManId())
				.storeId(UserThreadLocal.get().getStoreid())
				.build();
		commonDao.save(settle);
		BigDecimal amount = BigDecimal.ZERO;
		for(SaleExchangeSettleEntity vo : list) {
			SaleExchangeSettleDtl dtl = SaleExchangeSettleDtl.builder()
					.amount(vo.getAmount())
					.orderId(settle.getId())
					.settleTypeId(vo.getSettleTypeId())
					.build();
			commonDao.save(dtl);
			amount = amount.add(dtl.getAmount());
		}
		settle.setAmount(amount);
		commonDao.update(settle);
	}

	@Override
	public void saveReceive(List<ReceivableAccountVo> list) {
		
		List<ReceivableAccountDtl> receivables = Lists.newArrayList();
		list.forEach(item -> receivables.add(commonDao.findEntityByIdForUpdate(ReceivableAccountDtl.class, item.getId())));
		Set<Long> set = receivables.stream().map(item->item.getMemberId()).collect(Collectors.toSet());
		
		Assert.isTrue(set.size()==1,"不允许多个用户同时收款");
		
		BigDecimal amount = list.stream().map(ReceivableAccountVo::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
		ReceiveOrder order = ReceiveOrder.builder()
				.affirmDate(new Date())
				.affirmuserId(list.get(0).getSaleManId())
				.amount(amount)
				.billno(commonDao.findEntityBySql(String.class, "select f_get_out_trade_no()", ParamValue.build()))
				.creDate(new Date())
				.creuserId(list.get(0).getSaleManId())
				.memberId(receivables.get(0).getMemberId())
				.remark("门店收款")
				.settleTypeId(list.get(0).getSettleTypeId())
				.status(1)
				.storeId(UserThreadLocal.get().getStoreid())
				.build();
		commonDao.save(order);
		
		list.forEach(item -> {
			ReceivableAccountDtl dtl = receivables.stream().filter(rec -> rec.getId()-item.getId()==0).findFirst().get();
			if(dtl==null || dtl.getDebtAmount().compareTo(BigDecimal.ZERO)==0) throw new ServiceException("记录不存在或已收款");
			ReceiveOrderDtl orderDtl = ReceiveOrderDtl.builder()
					.amount(item.getAmount())
					.billDate(dtl.getCreDate())
					.orderdtlId(dtl.getId())
					.orderId(order.getId())
					.storeId(UserThreadLocal.get().getStoreid())
					.build();
			commonDao.save(orderDtl);
			this.commonDao.update(ReceivableAccountDtl.class,"  set debtAmount=debtAmount-:debtAmount where id=:id ",ParamValue.build().add("debtAmount", item.getAmount()).add("id", dtl.getId()) );
		});
		
		
		this.commonDao.update(ReceivableAccount.class," set amount=amount-:amount where storeId=:storeId and memberId=:memberId ", 
				ParamValue.build().add("amount", amount).add("storeId", UserThreadLocal.get().getStoreid()).add("memberId", receivables.get(0).getMemberId()));
		
	}

	@Override
	public void savePackageConvert(PackageConvertVo vo) {
		PackageSale sale = commonDao.findEntityByIdForUpdate(PackageSale.class, vo.getPackageSaleId());
		Assert.notNull(sale, "未找到兑换数据");
		Assert.isTrue(vo.getCount()<=sale.getUnBindQty(), "可兑换数量不足");
		WebOrder order = WebOrder.builder()
				.amount(BigDecimal.ZERO)
				.creDate(new Date())
				.memberId(vo.getMemberId())
				.salemanId(vo.getSaleManId())
				.packageId(sale.getPackageId())
				.count(vo.getCount())
				.status(3)
				.statusdesc("SUCCESS")
				.storeId(UserThreadLocal.get().getStoreid())
				.type(2)
				.build();
		commonDao.save(order);
		
		WebOrderSettle settle = WebOrderSettle.builder()
				.amount(BigDecimal.ZERO)
				.creDate(new Date())
				.webOrderId(order.getId())
				.saleManId(order.getSalemanId())
				.settleTypeId(SettleType.CASH)
				.storeId(order.getStoreId())
				.build();
		commonDao.save(settle);
		PackageSale ps = PackageSale.builder()
				.amount(BigDecimal.ZERO)
				.price(BigDecimal.ZERO)
				.creDate(new Date())
				.creuserId(UserThreadLocal.get().getId())
				.exchangeDate(new Date())
				.memberId(vo.getMemberId())
				.memberid2(sale.getMemberId())
				.packageId(sale.getPackageId())
				.qty(vo.getCount())
				.status(1)
				.storeId(UserThreadLocal.get().getStoreid())
				.unBindQty(vo.getCount())
				.webOrderId(order.getId())
				.build();
		commonDao.save(ps);
		commonDao.update(PackageSale.class, " set unBindQty=unBindQty-:unBindQty where id=:id", ParamValue.build().add("unBindQty", vo.getCount()).add("id", sale.getId()));
		commonDao.flush();
		//存储过程
		List<ParamsEntity> pe = Arrays.asList(
				new ParamsEntity(Types.BIGINT, ps.getId())
				);
		String[] procResult = commonDao.executeProcs("p_package_convert", pe);
		if(procResult[0].equals("-1")) throw new ServiceException(procResult[1]);
	}

	@Override
	public void saveExchange(ExchangeEggVo vo) {
		//销售保存
		Sale sale = Sale.builder()
					.amount(BigDecimal.ZERO)
					.changeAmount(BigDecimal.ZERO)
					.creuserId(UserThreadLocal.get().getId())
					.creDate(new Date())
					.memberId(vo.getMemberId())
					.salemanId(vo.getSaleManId())
					.storeId(UserThreadLocal.get().getStoreid())
					.dtls(1)
					.distributeStatus(1)
					.type(1)
					.exchangeCount(vo.getCardCount())
					.build();
		commonDao.save(sale);
		SaleDtl saleDtl =SaleDtl.builder()
					.saleId(sale.getId())
					.amount(BigDecimal.ZERO)
					.goodsId(vo.getGoodsId())
					.price(BigDecimal.ZERO)
					.qty(BigDecimal.valueOf(vo.getCount()))
					.priceType(1)
					.promotionStatus(0)
					.build();
		commonDao.save(saleDtl);
		commonDao.flush();
		
		StoreOrderVo so = new StoreOrderVo();
		so.setCashAmount(BigDecimal.ZERO);
		saveSettle(sale);
		//存储过程
		List<ParamsEntity> pe = Arrays.asList(
				new ParamsEntity(Types.BIGINT, sale.getId())
				);
		String[] procResult = commonDao.executeProcs("p_exchange_egg", pe);
		if(procResult[0].equals("-1")) throw new ServiceException(procResult[1]);
	}
	@Override
	public void revoke(String outTradeNo) {
		Sale s = commonDao.findEntity(Sale.class, " where billno=:billno ", ParamValue.build().add("billno", outTradeNo));
		Assert.notNull(s,"订单号有误，撤销失败");
		Sale sale = commonDao.findEntityByIdForUpdate(Sale.class, s.getId());
		
		List<OrderStatus> stop = Lists.newArrayList(OrderStatus.CLOSED,OrderStatus.SUCCESS,OrderStatus.REVOKED,OrderStatus.MANUAL);
		OrderStatus status = stop.stream()
				.filter(item -> item.code == sale.getStatus())
                .findAny()
                .orElse(null);
		if(status!=null ) {
			throw new ServiceException("订单状态成功/关闭/撤销/人工处理,不可撤销");
		}
		//1
		commonDao.update(Sale.class, " set status=:status where id=:id ", ParamValue.build().add("status", OrderStatus.REVOKED.code).add("id", sale.getId()));
		
		//2
		RestEntity result = payServiceFeign.revoke(outTradeNo);
		if(result.getStatus()==-1) throw new ServiceException(result.getMsg());
	}
	
}

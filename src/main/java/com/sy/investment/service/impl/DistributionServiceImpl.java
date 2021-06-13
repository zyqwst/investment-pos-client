/**
 * 
 */
package com.sy.investment.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sy.investment.domain.table.Distribution;
import com.sy.investment.domain.table.DistributionDtl;
import com.sy.investment.domain.table.MemberAddress;
import com.sy.investment.domain.vo.DistributionVo;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.reponsitory.CommonDao;
import com.sy.investment.service.DistributionService;
import com.sy.investment.utils.ParamValue;

/** 
* @ClassName: DistributionServiceImpl 
* @Description: 
* @author albert
* @date 2018年9月10日 下午12:55:10 
*  
*/
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class DistributionServiceImpl implements DistributionService {
	@Resource CommonDao commonDao;
	
	@Override
	public void confirm(Long id) {
		DistributionDtl dtl = commonDao.findEntityByIdForUpdate(DistributionDtl.class, id);
		Assert.isTrue(dtl.getStatus()==0, "配送申请已被确认或被取消");
		commonDao.update(DistributionDtl.class, " set status=:status where id=:id", ParamValue.build().add("status", 1).add("id", id));
		
		commonDao.update(Distribution.class, " set balance=balance-:balance,controlQty=controlQty-:controlQty where id=:id", 
				ParamValue.build().add("balance", dtl.getQty()).add("controlQty", dtl.getQty()).add("id", dtl.getOrderId()));
		
		Distribution order = commonDao.findEntityById(Distribution.class, dtl.getOrderId());
		Assert.isTrue(order.getBalance().compareTo(BigDecimal.ZERO)>-1, "可确认数量不足");
		Assert.isTrue(order.getControlQty().compareTo(BigDecimal.ZERO)>-1, "暂控数量不足");
	}

	@Override
	public void cancel(Long id) {
		DistributionDtl dtl = commonDao.findEntityByIdForUpdate(DistributionDtl.class, id);
		Assert.isTrue(dtl.getStatus()==0, "配送申请已被确认或被取消");
		commonDao.update(DistributionDtl.class, " set status=:status where id=:id", ParamValue.build().add("status", -1).add("id", id));
		
		commonDao.update(Distribution.class, " set controlQty=controlQty-:controlQty where id=:id", 
				ParamValue.build().add("controlQty", dtl.getQty()).add("id", dtl.getOrderId()));
		
		Distribution order = commonDao.findEntityById(Distribution.class, dtl.getOrderId());
		Assert.isTrue(order.getControlQty().compareTo(BigDecimal.ZERO)>-1, "暂控数量不足");
	}

	@Override
	public void save(DistributionVo vo) {
		Distribution order = commonDao.findEntityByIdForUpdate(Distribution.class, vo.getId());
		Assert.isTrue((order.getBalance().subtract(order.getControlQty())).compareTo(vo.getQty())>=0,"可配送数量不足");
		
		if(vo.getDistributionStatus()==1) {			
			commonDao.update(Distribution.class, " set balance = balance - :balance where id=:id", ParamValue.build().add("balance", vo.getQty()).add("id", vo.getId()));
		}else {
			commonDao.update(Distribution.class, " set controlQty=:controlQty where id=:id", ParamValue.build().add("controlQty", vo.getQty()).add("id", vo.getId()));
		}
		MemberAddress address = commonDao.findEntityById(MemberAddress.class, vo.getAddressId());
		Assert.notNull(address, "系统错误，配送地址不存在，ID:"+vo.getAddressId());
		DistributionDtl dtl;
		try {
			dtl = DistributionDtl.builder()
					.address(address.getAddress())
					.addressId(vo.getAddressId())
					.amount(order.getPrice().multiply(vo.getQty()))
					.distributeDate(vo.getDistributionStatus()==1?new Date():DateUtils.parseDate(vo.getDistributionDate(),new String[] {"yyyy-MM-dd"}))
					.goodsId(order.getGoodsId())
					.linkMan(address.getLinkMan())
					.memberId(order.getMemberId())
					.orderId(order.getId())
					.price(order.getPrice())
					.qty(vo.getQty())
					.status(vo.getDistributionStatus())
					.tel(address.getTel())
					.remark(vo.getRemark())
					.build();
			commonDao.save(dtl);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

}

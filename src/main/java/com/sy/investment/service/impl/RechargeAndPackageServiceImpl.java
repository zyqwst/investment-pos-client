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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.table.SettleType;
import com.sy.investment.domain.table.WebOrder;
import com.sy.investment.domain.table.WebOrderSettle;
import com.sy.investment.domain.vo.PackageVo;
import com.sy.investment.domain.vo.RechargeVo;
import com.sy.investment.domain.vo.TradePayVo;
import com.sy.investment.exceptions.PayFeignException;
import com.sy.investment.exceptions.PaySettleException;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.feign.PayServiceFeign;
import com.sy.investment.reponsitory.CommonDao;
import com.sy.investment.service.RechargeAndPackageService;
import com.sy.investment.utils.CommonUtils;
import com.sy.investment.utils.Constants.OrderStatus;
import com.sy.investment.utils.MathUtil;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: RechargeAndPackageServiceImpl 
* @Description: 
* @author albert
* @date 2018年9月7日 下午3:26:41 
*  
*/
@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class RechargeAndPackageServiceImpl implements RechargeAndPackageService {
	@Resource CommonDao commonDao;
	@Resource PayServiceFeign payServiceFeign;
	@Override
	public RestEntity save(RechargeVo vo) {
		//1
		WebOrder order;
		try {
			order = WebOrder.builder()
					.amount(vo.getCashAmount().add(vo.getPosAmount()))
					.creDate(new Date())
					.memberId(vo.getMemberId())
					.salemanId(vo.getSaleManId())
					.status(OrderStatus.NOTPAY.code)
					.statusdesc(OrderStatus.NOTPAY.name())
					.storeId(UserThreadLocal.get().getStoreid())
					.outTradeNo(commonDao.executeFunc(String.class, "select f_get_out_trade_no() ", null))
					.type(1)
					.cashAmount(vo.getCashAmount())
					.posAmount(vo.getPosAmount())
					.build();
			commonDao.save(order);
		} catch (Exception e) {
			log.error("订单保存失败",e);
			throw new ServiceException("订单保存失败:"+e.getMessage());
		}
		RestEntity result = null;
		commonDao.flush();
		commonDao.clear();
		WebOrder s = commonDao.findEntityById(WebOrder.class, order.getId());
		
		//2扫码支付
		if(order.getPosAmount().compareTo(BigDecimal.ZERO)==1) {
			TradePayVo v = TradePayVo.buildByRechage(vo, s);
			try {
				result = payFeign(v);
				Map<String,String> map = (Map<String, String>) result.getObject();
				if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {					
					paySuccess(s);
				}
			} catch (PayFeignException | PaySettleException e) {
				result = RestEntity.failed(e.getMessage());
				result.setObject(s.getOutTradeNo());
			} 
		}else {
			try {
				paySuccess(s);
				Map<String, String> map = OrderStatus.SUCCESS.toMap();
				map.put("outTradeNo", s.getOutTradeNo());
				result = RestEntity.success(map);
			} catch (PaySettleException e) {
				result = RestEntity.failed(e.getMessage());
				result.setObject(s.getOutTradeNo());
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
	
	private void paySuccess(WebOrder order) {
		try {
			//结算保存
			saveSettle(order);
			commonDao.flush();
			//存储过程
			List<ParamsEntity> pe = Arrays.asList(
					new ParamsEntity(Types.BIGINT, order.getId())
					);
			String[] procResult = commonDao.executeProcs("p_store_recharge", pe);
			if(procResult[0].equals("-1")) throw new ServiceException(procResult[1]);
		} catch (Exception e) {
			log.error("订单结算失败",e);
			throw new PaySettleException("付款成功，但结算失败："+e.getMessage());
		}
		
	}
	
	private void saveSettle(WebOrder order) {
		BigDecimal 	cashAmount = Optional.<BigDecimal>ofNullable(order.getCashAmount()).orElse(BigDecimal.ZERO);
		BigDecimal 	posAmount = Optional.<BigDecimal>ofNullable(order.getPosAmount()).orElse(BigDecimal.ZERO);
		BigDecimal 	cardAmount = Optional.<BigDecimal>ofNullable(order.getCardAmount()).orElse(BigDecimal.ZERO);
		if(cashAmount.compareTo(BigDecimal.ZERO)>0) {
			save(cashAmount,order.getId(),SettleType.CASH);
		}
		if(posAmount.compareTo(BigDecimal.ZERO)>0) {
			save(posAmount,order.getId(),SettleType.POS);
		}
		if(cardAmount.compareTo(BigDecimal.ZERO)>0) {
			save(cardAmount,order.getId(),SettleType.MEMBERCARD);
		}
	}
	private void save(BigDecimal amount,Long saleId,Long typeId) {
		WebOrderSettle settle = WebOrderSettle.builder()
				.amount(amount)
				.creDate(new Date())
				.webOrderId(saleId)
				.saleManId(UserThreadLocal.get().getId())
				.settleTypeId(typeId)
				.storeId(UserThreadLocal.get().getStoreid())
				.build();
		commonDao.save(settle);
	}
	@Override
	public RestEntity save(PackageVo vo) {
		WebOrder order;
		try {
			order = WebOrder.builder()
					.amount(MathUtil.add(vo.getCardAmount(), vo.getCashAmount(),vo.getPosAmount()))
					.creDate(new Date())
					.memberId(vo.getMemberId())
					.salemanId(vo.getSaleManId())
					.packageId(vo.getPackageId())
					.count(vo.getCount())
					.status(OrderStatus.NOTPAY.code)
					.statusdesc(OrderStatus.NOTPAY.name())
					.outTradeNo(commonDao.executeFunc(String.class, "select f_get_out_trade_no() ", null))
					.cashAmount(vo.getCashAmount())
					.posAmount(vo.getPosAmount())
					.storeId(UserThreadLocal.get().getStoreid())
					.type(2)
					.build();
			commonDao.save(order);
		} catch (Exception e) {
			log.error("订单保存失败",e);
			throw new ServiceException("订单保存失败:"+e.getMessage());
		}
		RestEntity result = null;
		commonDao.flush();
		commonDao.clear();
		WebOrder s = commonDao.findEntityById(WebOrder.class, order.getId());
		
		//2扫码支付
		if(order.getPosAmount().compareTo(BigDecimal.ZERO)==1) {
			TradePayVo v = TradePayVo.buildByPackage(vo, s);
			try {
				result = payFeign(v);
				Map<String,String> map = (Map<String, String>) result.getObject();
				if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {					
					paySuccessPackage(s);
				}
			} catch (PayFeignException | PaySettleException e) {
				result = RestEntity.failed(e.getMessage());
				result.setObject(s.getOutTradeNo());
			} 
		}else {
			try {
				paySuccessPackage(s);
				Map<String, String> map = OrderStatus.SUCCESS.toMap();
				map.put("outTradeNo", s.getOutTradeNo());
				result = RestEntity.success(map);
			} catch (PaySettleException e) {
				result = RestEntity.failed(e.getMessage());
				result.setObject(s.getOutTradeNo());
			}
		}
		return  result;
	}
	private void paySuccessPackage(WebOrder order) {
		try {
			//结算保存
			saveSettle(order);
			commonDao.flush();
			//存储过程
			List<ParamsEntity> pe = Arrays.asList(
					new ParamsEntity(Types.BIGINT, order.getId())
					);
			String[] procResult = commonDao.executeProcs("p_store_sale_package", pe);
			if(procResult[0].equals("-1")) throw new ServiceException(procResult[1]);
		} catch (Exception e) {
			log.error("订单结算失败",e);
			throw new PaySettleException("付款成功，但结算失败："+e.getMessage());
		}
		
	}
	@Override
	public RestEntity syncPayResult(String outTradeNo) {
		WebOrder s = commonDao.findEntity(WebOrder.class, " where outTradeNo=:billno ", ParamValue.build().add("billno", outTradeNo));
		Assert.notNull(s, OrderStatus.ORDERNOTEXIST.msg);
		WebOrder sale = commonDao.findEntityByIdForUpdate(WebOrder.class, s.getId());
		
		List<OrderStatus> stop = Lists.newArrayList(OrderStatus.CLOSED,OrderStatus.SUCCESS,OrderStatus.REVOKED,OrderStatus.MANUAL);
		OrderStatus status = stop.stream()
				.filter(item -> item.code == s.getStatus())
                .findAny()
                .orElse(null);
		if(status!=null ) {
//			return RestEntity.failed("订单状态成功/关闭/撤销/人工处理,无需同步");
			return RestEntity.success(status.toMap());
		}
		
		
		RestEntity result;
		try {
			result = payServiceFeign.syncPayResult(outTradeNo);
			checkResult(result);
			Map<String,String> map = (Map<String, String>) result.getObject();
			if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {	
				if(sale.getType()==1) {					
					paySuccess(sale);
				}else {
					paySuccessPackage(sale);
				}
			}
			List<Integer> list = Lists.newArrayList(OrderStatus.CLOSED.code,OrderStatus.MANUAL.code,OrderStatus.REVOKED.code);
			if(list.contains(Integer.parseInt(map.get("code")))) {
				commonDao.update(WebOrder.class, " set status=:status,statusdesc=:statusdesc,remark=:remark where id=:id ", ParamValue.build().add("id", sale.getId()).add("remark", map.get("msg")).add("status", Integer.parseInt(map.get("code"))).add("statusdesc", OrderStatus.findAny(Integer.parseInt(map.get("code"))).name()));
			}
		} catch (Exception e) {
			return RestEntity.failed(e.getMessage());
		}
		
		return result;
	}
	@Override
	public void revoke(String outTradeNo) {
		WebOrder s = commonDao.findEntity(WebOrder.class, " where outTradeNo=:billno ", ParamValue.build().add("billno", outTradeNo));
		Assert.notNull(s,"订单号有误，撤销失败");
		WebOrder sale = commonDao.findEntityByIdForUpdate(WebOrder.class, s.getId());
		
		List<OrderStatus> stop = Lists.newArrayList(OrderStatus.CLOSED,OrderStatus.SUCCESS,OrderStatus.REVOKED,OrderStatus.MANUAL);
		OrderStatus status = stop.stream()
				.filter(item -> item.code == sale.getStatus())
                .findAny()
                .orElse(null);
		if(status!=null ) {
			throw new ServiceException("订单状态成功/关闭/撤销/人工处理,不可撤销");
		}
		//1
		commonDao.update(WebOrder.class, " set status=:status,statusdesc=:statusdesc where id=:id ", ParamValue.build().add("status", OrderStatus.REVOKED.code).add("statusdesc",  OrderStatus.REVOKED.name()).add("id", sale.getId()));
		
		//2
		RestEntity result = payServiceFeign.revoke(outTradeNo);
		if(result.getStatus()==-1) throw new ServiceException(result.getMsg());
	}
}

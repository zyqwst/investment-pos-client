/**
 * 
 */
package com.sy.investment.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.entity.SaleExchangeSettleEntity;
import com.sy.investment.domain.table.MemberAmountDtl;
import com.sy.investment.domain.table.Sale;
import com.sy.investment.domain.view.MemberAmountView;
import com.sy.investment.domain.view.SaleDtlView;
import com.sy.investment.domain.view.SaleExchangeSettleDtlView;
import com.sy.investment.domain.view.SaleExchangeSettleView;
import com.sy.investment.domain.view.SaleView;
import com.sy.investment.domain.vo.ExchangeEggVo;
import com.sy.investment.domain.vo.PackageVo;
import com.sy.investment.domain.vo.RechargeVo;
import com.sy.investment.domain.vo.StoreOrderVo;
import com.sy.investment.domain.vo.TemplateMessageImpl;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.feign.WechatFeign;
import com.sy.investment.service.RechargeAndPackageService;
import com.sy.investment.service.SaleServie;
import com.sy.investment.utils.Constants.OrderStatus;
import com.sy.investment.utils.MathUtil;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.RedisUtil;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
* @ClassName: GoodsController 
* @Description: 
* @author albert
* @date 2018年8月23日 上午11:15:19 
*  
*/
@Api(tags="销售相关API")
@Validated
@RestController
@RequestMapping("/sale")
public class SaleController extends BaseController {
	
	@Resource SaleServie saleService;
	@Resource RechargeAndPackageService rechargeService;
	@Resource RedisUtil redisUtil;
	@Resource WechatFeign wechatFeign;
	
	@ApiOperation(value="门店销售")
	@PostMapping("")
	public RestEntity save(@ApiParam(name = "storeOrderVo", value = "订单信息", required =true)
				@Validated @RequestBody StoreOrderVo storeOrderVo) {
		try {
			storeOrderVo.validAmont();
			RestEntity rest = saleService.save(storeOrderVo);
			if(rest.getStatus()==1) {
				Map<String,String> map = (Map<String, String>) rest.getObject();
				if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {
					String outTradeNo = map.get("outTradeNo");
					long x= System.currentTimeMillis();
					log.info("发送消息：{}",x);
					sendWechat(outTradeNo);
					log.info("发送完成：{}",System.currentTimeMillis()-x);
				}
			}
			return rest;
		} catch (Exception e) {
			e.printStackTrace();
			return RestEntity.failed(e.getMessage());
		}
	}
	@Async
	public void sendWechat(String outTradeNo ) {
		try {
			String storeName = UserThreadLocal.get().getStoreName();
			Sale sale = commonService.findEntity(Sale.class, " where billno=:billNo", ParamValue.build().add("billNo", outTradeNo));
			if(sale.getMemberId()==null) return;
			MemberAmountView member = commonService.findEntityById(MemberAmountView.class, sale.getMemberId());
			MemberAmountDtl dtl = commonService.findEntity(MemberAmountDtl.class, " where saleId=:saleId and type=4 and addAmount>0 ", ParamValue.build().add("saleId", sale.getId()));
			
			wechatFeign.saleMessage(TemplateMessageImpl.builder()
					.first("亲爱的"+member.getName()+",您已消费成功")
					.keyword1("￥"+sale.getAmount().stripTrailingZeros().toPlainString())
					.keyword2("￥"+sale.getCardAmount().stripTrailingZeros().toPlainString())
					.keyword3("￥"+member.getAmount().add(member.getBackAmount()).stripTrailingZeros().toPlainString()
							+"（其中返现余额：￥"+member.getBackAmount().stripTrailingZeros().toPlainString()+"）")
					.keyword4(storeName)
					.openId(member.getOpenId())
					.remark("本次消费返现：￥"+(dtl==null?"0":dtl.getAddAmount().stripTrailingZeros().toPlainString()))
					.build());
			
		} catch (Exception e) {
			log.error("发送消费微信失败",e);
		}
	}
	@ApiOperation(value="扫码支付同步支付结果")
	@ApiImplicitParams({
		@ApiImplicitParam(name="outTradeNo",value="订单号",dataType="String",required=true,paramType="query"),
		@ApiImplicitParam(name="type",value="类型（1-购买商品，2-门店充值，3-购买特权）",dataType="Integer",required=true,paramType="query")
	})
	@GetMapping("/sync")
	public RestEntity syncPayResult(@RequestParam(required=true) String outTradeNo,@RequestParam(required=true) Integer type) {
		RestEntity rest ;
		if(type==1) {
			 rest = saleService.syncPayResult(outTradeNo);
		}else {
			rest = rechargeService.syncPayResult(outTradeNo);
		}
		try {
			if(rest.getStatus()==1) {
				Map<String,String> map = (Map<String, String>) rest.getObject();
				if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {
					long x= System.currentTimeMillis();
					log.info("发送消息：{}",x);
					sendWechat(outTradeNo);
					log.info("发送完成：{}",System.currentTimeMillis()-x);
				}
			}
		}catch (Exception e) {
			log.error("发送消费成功消息失败");
		}
		return rest;
	}
	@ApiOperation(value="撤销支付")
	@ApiImplicitParams({
		@ApiImplicitParam(name="outTradeNo",value="订单号",dataType="String",required=true,paramType="query"),
		@ApiImplicitParam(name="type",value="类型（1-购买商品，2-门店充值，3-购买特权）",dataType="Integer",required=true,paramType="query")
	})
	@GetMapping("/revoke")
	public RestEntity revoke(@RequestParam(required=true) String outTradeNo,@RequestParam(required=true) Integer type) {
		try {
			if(type==1) {
				saleService.revoke(outTradeNo);
			}else {
				rechargeService.revoke(outTradeNo);
			}
			return RestEntity.success();
		} catch (Exception e) {
			return RestEntity.failed(e.getMessage());
		}
	}
	@ApiOperation(value="门店交班结算数据生成")
	@GetMapping("/exchange-settle")
	@ApiImplicitParams({
		@ApiImplicitParam(name="saleManId",value="收银员ID",dataType="Long",required=true,paramType="query")
	})
	public RestEntity exchangeSettle(@RequestParam(required=true) Long saleManId) {
		List<SaleExchangeSettleEntity> list = commonService.findAllBySql(SaleExchangeSettleEntity.class, "call p_sale_exchange_settle(:storeId,:saleManId)", 
				ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()).add("saleManId", saleManId));
		if(list.size()==0) throw new ServiceException("无可结算数据");
		//缓存结算数据到Redis,30分钟
		String key = "exchange-settle"+UUID.randomUUID().toString();
		redisUtil.set(key, list, 5*60L);
		Map<String,Object> map = ImmutableMap.<String, Object>builder()
		.put("key",key)
		.put("beginDate",list.get(0).getBeginDate().getTime())
		.put("endDate",list.get(0).getEndDate().getTime())
		.put("list",list)
		.build();
		return RestEntity.success(map);
	}
	@ApiOperation(value="门店交班结算数据提交")
	@GetMapping("/exchange-settle/save")
	@ApiImplicitParams({
		@ApiImplicitParam(name="settleKey",value="交班结算key",dataType="String",required=true,paramType="query")
	})
	@SuppressWarnings("unchecked")
	public RestEntity exchangeSettleSave(@RequestParam(required=true) String settleKey) {
		Object obj = redisUtil.get(settleKey);
		if(obj==null) throw new ServiceException("结算数据已过期或不存在,请返回上一页后重新结算");
		List<SaleExchangeSettleEntity> list = (List<SaleExchangeSettleEntity>) obj;
		saleService.save(list);
		return RestEntity.success();
	}
	@ApiOperation(value="门店交班结算记录")
	@GetMapping("/exchange-settle/record")
	@ApiImplicitParams({
		@ApiImplicitParam(name="keyword",value="关键字",dataType="String",required=false,paramType="query"),
		@ApiImplicitParam(name="beginDate",value="开始日期",dataType="String",required=false,paramType="query"),
		@ApiImplicitParam(name="endDate",value="截止日期",dataType="String",required=false,paramType="query"),
		@ApiImplicitParam(name="page",value="页码",dataType="Integer",required=false,paramType="query")
	})
	public RestEntity record(@RequestParam(required=false) String keyword,
			@RequestParam(required=false) String beginDate,
			@RequestParam(required=false) String endDate, 
			@PageableDefault(page = 0,size=20)Pageable pageable) {
		Page<SaleExchangeSettleView> page;
		try {
			page = commonService.findPage(SaleExchangeSettleView.class, " where storeId=:storeId and "
					+ "(code like :code or name like :name) "
					+ " and creDate between :beginDate and :endDate "
					+ " order by id desc", 
					ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid())
					.addLike("code", keyword).add("name", keyword)
					.add("beginDate", DateUtils.parseDate(beginDate, "yyyy-MM-dd"))
					.add("endDate",DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss")),
					pageable);
			return RestEntity.success(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} 
	}
	@ApiOperation(value="门店交班结算记录")
	@GetMapping("/exchange-settle/dtl")
	@ApiImplicitParams({
		@ApiImplicitParam(name="settleId",value="结算单ID",dataType="Long",required=false,paramType="query")
	})
	public RestEntity settleDtl(Long settleId) {
		List<SaleExchangeSettleDtlView> list = commonService.findAll(SaleExchangeSettleDtlView.class, " where orderId=:orderId order by id asc", ParamValue.build().add("orderId", settleId));
		return RestEntity.success(list);
	}
	
	@ApiOperation(value="门店会员卡充值")
	@PostMapping("/recharge")
	public RestEntity recharge(@ApiParam(name = "rechargeVo", value = "充值订单信息", required =true)
			@Validated @RequestBody RechargeVo rechargeVo) {
		try {
			if(rechargeVo.getPosAmount().compareTo(BigDecimal.ZERO)==0 && rechargeVo.getCashAmount().compareTo(BigDecimal.ZERO)==0) {
				throw new ServiceException("充值金额不可为0");
			}
			RestEntity rest = rechargeService.save(rechargeVo);
			return rest;
		} catch (Exception e) {
			e.printStackTrace();
			return RestEntity.failed(e.getMessage());
		}
	}
	@ApiOperation(value="门店特权购买")
	@PostMapping("/package")
	public RestEntity packages(@ApiParam(name = "vo", value = "订单信息", required =true)
			@Validated @RequestBody PackageVo vo) {
		try {
			if(MathUtil.add(vo.getCardAmount(), vo.getCashAmount(),vo.getPosAmount()).compareTo(BigDecimal.ZERO)<=0) {
				throw new ServiceException("金额不可为0");
			}
			RestEntity rest = rechargeService.save(vo);
			return rest;
		} catch (Exception e) {
			e.printStackTrace();
			return RestEntity.failed(e.getMessage());
		}
	}
	@ApiOperation(value="销售数据查询")
	@GetMapping("")
	public RestEntity sales(@RequestParam(required=false,defaultValue="1") Integer type, 
							@RequestParam(required=false) String keyword,
							@RequestParam(required=false) String beginDate,
							@RequestParam(required=false) String endDate, 
							@PageableDefault(page = 0,size=20)Pageable pageable) {
		Page<SaleView> page;
		try {
			page = commonService.findPage(SaleView.class, " where storeId=:storeId and "
					+ "(memberCode like :memberCode or phone=:phone or billno like :billno ) "
					+ " and creDate between :beginDate and :endDate "
					+ " and status in :status and type=:type order by id desc", 
					ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid())
					.addLike("memberCode", keyword).add("phone", keyword).addLike("billno", keyword)
					.add("type", type)
					.add("beginDate", DateUtils.parseDate(beginDate, "yyyy-MM-dd"))
					.add("endDate",DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss"))
					.add("status",Lists.newArrayList(OrderStatus.SUCCESS.code,OrderStatus.NOTPAY.code,
							OrderStatus.MANUAL.code,OrderStatus.REVOKED.code)),
					pageable);
			return RestEntity.success(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} 
	}
	@ApiOperation(value="订单明细")
	@GetMapping("/dtls")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderId",value="销售单ID",dataType="Long",required=true,paramType="query")
	})
	public RestEntity dtlList(Long orderId) {
		List<SaleDtlView> list = commonService.findAll(SaleDtlView.class, " where saleId=:saleId order by name ", ParamValue.build().add("saleId", orderId));
		return RestEntity.success(list);
	}
	
	@ApiOperation(value="获取订单信息（主单和明细）")
	@GetMapping("/info")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderId",value="销售单ID",dataType="Long",required=true,paramType="query")
	})
	public RestEntity saleInfo(Long orderId) {
		List<SaleDtlView> list = commonService.findAll(SaleDtlView.class, " where saleId=:saleId order by name ", ParamValue.build().add("saleId", orderId));
		SaleView sv = commonService.findEntityById(SaleView.class, orderId);
		Assert.notNull(sv, "订单ID未找到订单信息");
		Assert.notEmpty(list, "订单ID未找到订单明细数据");
		Map<String, Object> map = ImmutableMap.<String,Object>builder()
				.put("order",sv)
				.put("list",list)
				.build();
		return RestEntity.success(map);
	}
	@ApiOperation(value="卡片兑换商品")
	@PostMapping("/exchange-egg")
	public RestEntity convert(@RequestBody @Validated
			@ApiParam(name = "body", value = "转赠信息", required =true)
			ExchangeEggVo body) {
		saleService.saveExchange(body);
		return RestEntity.success();
	}
}


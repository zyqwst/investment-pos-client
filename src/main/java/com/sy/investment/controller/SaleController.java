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
* @date 2018???8???23??? ??????11:15:19 
*  
*/
@Api(tags="????????????API")
@Validated
@RestController
@RequestMapping("/sale")
public class SaleController extends BaseController {
	
	@Resource SaleServie saleService;
	@Resource RechargeAndPackageService rechargeService;
	@Resource RedisUtil redisUtil;
	@Resource WechatFeign wechatFeign;
	
	@ApiOperation(value="????????????")
	@PostMapping("")
	public RestEntity save(@ApiParam(name = "storeOrderVo", value = "????????????", required =true)
				@Validated @RequestBody StoreOrderVo storeOrderVo) {
		try {
			storeOrderVo.validAmont();
			RestEntity rest = saleService.save(storeOrderVo);
			if(rest.getStatus()==1) {
				Map<String,String> map = (Map<String, String>) rest.getObject();
				if(OrderStatus.SUCCESS.code-Integer.parseInt(map.get("code"))==0) {
					String outTradeNo = map.get("outTradeNo");
					long x= System.currentTimeMillis();
					log.info("???????????????{}",x);
					sendWechat(outTradeNo);
					log.info("???????????????{}",System.currentTimeMillis()-x);
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
					.first("?????????"+member.getName()+",??????????????????")
					.keyword1("???"+sale.getAmount().stripTrailingZeros().toPlainString())
					.keyword2("???"+sale.getCardAmount().stripTrailingZeros().toPlainString())
					.keyword3("???"+member.getAmount().add(member.getBackAmount()).stripTrailingZeros().toPlainString()
							+"???????????????????????????"+member.getBackAmount().stripTrailingZeros().toPlainString()+"???")
					.keyword4(storeName)
					.openId(member.getOpenId())
					.remark("????????????????????????"+(dtl==null?"0":dtl.getAddAmount().stripTrailingZeros().toPlainString()))
					.build());
			
		} catch (Exception e) {
			log.error("????????????????????????",e);
		}
	}
	@ApiOperation(value="??????????????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name="outTradeNo",value="?????????",dataType="String",required=true,paramType="query"),
		@ApiImplicitParam(name="type",value="?????????1-???????????????2-???????????????3-???????????????",dataType="Integer",required=true,paramType="query")
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
					log.info("???????????????{}",x);
					sendWechat(outTradeNo);
					log.info("???????????????{}",System.currentTimeMillis()-x);
				}
			}
		}catch (Exception e) {
			log.error("??????????????????????????????");
		}
		return rest;
	}
	@ApiOperation(value="????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name="outTradeNo",value="?????????",dataType="String",required=true,paramType="query"),
		@ApiImplicitParam(name="type",value="?????????1-???????????????2-???????????????3-???????????????",dataType="Integer",required=true,paramType="query")
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
	@ApiOperation(value="??????????????????????????????")
	@GetMapping("/exchange-settle")
	@ApiImplicitParams({
		@ApiImplicitParam(name="saleManId",value="?????????ID",dataType="Long",required=true,paramType="query")
	})
	public RestEntity exchangeSettle(@RequestParam(required=true) Long saleManId) {
		List<SaleExchangeSettleEntity> list = commonService.findAllBySql(SaleExchangeSettleEntity.class, "call p_sale_exchange_settle(:storeId,:saleManId)", 
				ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()).add("saleManId", saleManId));
		if(list.size()==0) throw new ServiceException("??????????????????");
		//?????????????????????Redis,30??????
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
	@ApiOperation(value="??????????????????????????????")
	@GetMapping("/exchange-settle/save")
	@ApiImplicitParams({
		@ApiImplicitParam(name="settleKey",value="????????????key",dataType="String",required=true,paramType="query")
	})
	@SuppressWarnings("unchecked")
	public RestEntity exchangeSettleSave(@RequestParam(required=true) String settleKey) {
		Object obj = redisUtil.get(settleKey);
		if(obj==null) throw new ServiceException("?????????????????????????????????,?????????????????????????????????");
		List<SaleExchangeSettleEntity> list = (List<SaleExchangeSettleEntity>) obj;
		saleService.save(list);
		return RestEntity.success();
	}
	@ApiOperation(value="????????????????????????")
	@GetMapping("/exchange-settle/record")
	@ApiImplicitParams({
		@ApiImplicitParam(name="keyword",value="?????????",dataType="String",required=false,paramType="query"),
		@ApiImplicitParam(name="beginDate",value="????????????",dataType="String",required=false,paramType="query"),
		@ApiImplicitParam(name="endDate",value="????????????",dataType="String",required=false,paramType="query"),
		@ApiImplicitParam(name="page",value="??????",dataType="Integer",required=false,paramType="query")
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
	@ApiOperation(value="????????????????????????")
	@GetMapping("/exchange-settle/dtl")
	@ApiImplicitParams({
		@ApiImplicitParam(name="settleId",value="?????????ID",dataType="Long",required=false,paramType="query")
	})
	public RestEntity settleDtl(Long settleId) {
		List<SaleExchangeSettleDtlView> list = commonService.findAll(SaleExchangeSettleDtlView.class, " where orderId=:orderId order by id asc", ParamValue.build().add("orderId", settleId));
		return RestEntity.success(list);
	}
	
	@ApiOperation(value="?????????????????????")
	@PostMapping("/recharge")
	public RestEntity recharge(@ApiParam(name = "rechargeVo", value = "??????????????????", required =true)
			@Validated @RequestBody RechargeVo rechargeVo) {
		try {
			if(rechargeVo.getPosAmount().compareTo(BigDecimal.ZERO)==0 && rechargeVo.getCashAmount().compareTo(BigDecimal.ZERO)==0) {
				throw new ServiceException("?????????????????????0");
			}
			RestEntity rest = rechargeService.save(rechargeVo);
			return rest;
		} catch (Exception e) {
			e.printStackTrace();
			return RestEntity.failed(e.getMessage());
		}
	}
	@ApiOperation(value="??????????????????")
	@PostMapping("/package")
	public RestEntity packages(@ApiParam(name = "vo", value = "????????????", required =true)
			@Validated @RequestBody PackageVo vo) {
		try {
			if(MathUtil.add(vo.getCardAmount(), vo.getCashAmount(),vo.getPosAmount()).compareTo(BigDecimal.ZERO)<=0) {
				throw new ServiceException("???????????????0");
			}
			RestEntity rest = rechargeService.save(vo);
			return rest;
		} catch (Exception e) {
			e.printStackTrace();
			return RestEntity.failed(e.getMessage());
		}
	}
	@ApiOperation(value="??????????????????")
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
	@ApiOperation(value="????????????")
	@GetMapping("/dtls")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderId",value="?????????ID",dataType="Long",required=true,paramType="query")
	})
	public RestEntity dtlList(Long orderId) {
		List<SaleDtlView> list = commonService.findAll(SaleDtlView.class, " where saleId=:saleId order by name ", ParamValue.build().add("saleId", orderId));
		return RestEntity.success(list);
	}
	
	@ApiOperation(value="???????????????????????????????????????")
	@GetMapping("/info")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderId",value="?????????ID",dataType="Long",required=true,paramType="query")
	})
	public RestEntity saleInfo(Long orderId) {
		List<SaleDtlView> list = commonService.findAll(SaleDtlView.class, " where saleId=:saleId order by name ", ParamValue.build().add("saleId", orderId));
		SaleView sv = commonService.findEntityById(SaleView.class, orderId);
		Assert.notNull(sv, "??????ID?????????????????????");
		Assert.notEmpty(list, "??????ID???????????????????????????");
		Map<String, Object> map = ImmutableMap.<String,Object>builder()
				.put("order",sv)
				.put("list",list)
				.build();
		return RestEntity.success(map);
	}
	@ApiOperation(value="??????????????????")
	@PostMapping("/exchange-egg")
	public RestEntity convert(@RequestBody @Validated
			@ApiParam(name = "body", value = "????????????", required =true)
			ExchangeEggVo body) {
		saleService.saveExchange(body);
		return RestEntity.success();
	}
}


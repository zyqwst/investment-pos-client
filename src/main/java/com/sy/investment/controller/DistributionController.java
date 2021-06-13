/**
 * 
 */
package com.sy.investment.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.view.DistributionDtlView;
import com.sy.investment.domain.view.DistributionView;
import com.sy.investment.domain.vo.DistributionVo;
import com.sy.investment.service.DistributionService;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
* @ClassName: DistributionController 
* @Description: 
* @author albert
* @date 2018年9月10日 上午8:50:31 
*  
*/
@RestController
@RequestMapping("/distribution")
@Api(tags="门店配送API")
public class DistributionController extends BaseController {
	
	@Resource DistributionService distributionService;
	
	@GetMapping("/apply")
	@ApiOperation(value="配送申请查询（按预约日期倒序排列）")
	@ApiImplicitParams({
		@ApiImplicitParam(name="keywords",value="手机号/会员卡/销售单号",dataType="String",required=false,paramType="query")
	})
	public RestEntity query(@RequestParam(required=false) String keywords,
			@PageableDefault(page = 0,size=20)Pageable pageable) {
		Page<DistributionDtlView> page;
		if(StringUtils.isNotBlank(keywords)) {
			page = commonService.findPage(DistributionDtlView.class, " where storeId=:storeId and status=:status"
					+ " and (phone like :phone or outTradeNo like :outTradeNo or code like :code) order by distributeDate asc ",
					ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()).add("status", 0)
									  .addLike("phone", keywords).addLike("outTradeNo", keywords).add("code", keywords),
					pageable);
		}else {			
			page = commonService.findPage(DistributionDtlView.class, " where storeId=:storeId and status=:status order by distributeDate asc ",
					ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()).add("status", 0), pageable);
		}
		
		return RestEntity.success(page);
	}
	@GetMapping("/apply-count")
	@ApiOperation(value="查询当天需要配送的申请数")
	public RestEntity todayApplyCount() {
		long cnt = commonService.count(DistributionDtlView.class, " where distributeDate=curdate() and storeId=:storeId  and status=:status ", ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()).add("status", 0));
		return RestEntity.success(cnt);
	}
	
	@ApiOperation(value="配送申请确认")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="配送申请id",dataType="Long",required=true,paramType="query")
	})
	@GetMapping("/apply/confirm")
	public RestEntity confirm(@RequestParam(required=true) Long id) {
		distributionService.confirm(id);
		return RestEntity.success();
	}
	@ApiOperation(value="配送申请取消")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="配送申请id",dataType="Long",required=true,paramType="query")
	})
	@GetMapping("/apply/cancel")
	public RestEntity cancel(@RequestParam(required=true) Long id) {
		distributionService.cancel(id);
		return RestEntity.success();
	}
	@GetMapping("")
	@ApiOperation(value="待配送查询（按销售日期顺序排列）")
	@ApiImplicitParams({
		@ApiImplicitParam(name="keywords",value="手机号/会员卡/销售单号",dataType="String",required=false,paramType="query")
	})
	public RestEntity distribute(@RequestParam(required=false) String keywords,
			@PageableDefault(page = 0,size=20)Pageable pageable) {
		Page<DistributionView> page;
		if(StringUtils.isNotBlank(keywords)) {
			page = commonService.findPage(DistributionView.class, " where storeId=:storeId and balance-controlQty>0 "
					+ " and (phone like :phone or outTradeNo like :outTradeNo or code like :code) order by saleDate asc ",
					ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid())
									  .addLike("phone", keywords).addLike("outTradeNo", keywords).add("code", keywords),
					pageable);
		}else {			
			page = commonService.findPage(DistributionView.class, " where storeId=:storeId and balance-controlQty>0  order by saleDate asc ",
					ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()), pageable);
		}
		
		return RestEntity.success(page);
	}
	@GetMapping("/id")
	@ApiOperation(value="用ID查询待配送记录")
	public RestEntity queryOne(@RequestParam(required=true) Long id) {
		DistributionView dis = commonService.findEntityById(DistributionView.class, id);
		Assert.notNull(dis, "ID未找到待配送数据");
		return RestEntity.success(dis);
	}
	@PostMapping("")
	@ApiOperation(value="配送预约")
	public RestEntity send(@ApiParam(name = "distribution", value = "配送信息", required =true)
			@Validated @RequestBody DistributionVo distribution) {
		distributionService.save(distribution);
		return RestEntity.success();
	}
}

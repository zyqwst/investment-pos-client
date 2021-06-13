/**
 * 
 */
package com.sy.investment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.view.PackageSaleView;
import com.sy.investment.domain.view.PackagesView;
import com.sy.investment.domain.vo.PackageConvertVo;
import com.sy.investment.service.SaleServie;
import com.sy.investment.utils.ParamValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
* @ClassName: PackageController 
* @Description: 
* @author albert
* @date 2018年9月11日 下午12:30:48 
*  
*/
@RestController
@RequestMapping("/package")
@Api(tags="会员特权套餐API")
public class PackageController extends BaseController {
	@Resource SaleServie saleService;
	@GetMapping("")
	@ApiOperation(value="查询可购买的特权套餐")
	public RestEntity index() {
		List<PackagesView> packageList=commonService.findAll(PackagesView.class, " where status=1 and curdate() between beginDate and endDate ", null);
		return RestEntity.success(packageList);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="根据ID查询特权套餐")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="特权ID",dataType="Long",required=true,paramType="path")
	})
	public RestEntity findOne(@PathVariable(required=true) Long id) {
		PackagesView p = commonService.findEntityById(PackagesView.class, id);
		Assert.notNull(p,"ID未找到特权套餐");
		Assert.isTrue(p.getStatus()==1, "特权套餐已下线，不可销售");
		return RestEntity.success(p);
	}
	@ApiOperation(value="可兑换特权列表")
	@GetMapping("/convert/list")
	public RestEntity showUnBindPackageList() {
		List<PackageSaleView> list=this.commonService.findAll(PackageSaleView.class,
				" where status=1 and unBindQty>0 and empBuy=1 ",
				ParamValue.build());
		return RestEntity.success(list);
	}
	@ApiOperation(value="兑换特权")
	@PostMapping("/convert")
	public RestEntity convert(@RequestBody @Validated
			@ApiParam(name = "body", value = "转赠信息", required =true)
			PackageConvertVo body) {
		saleService.savePackageConvert(body);
		return RestEntity.success();
	}
}

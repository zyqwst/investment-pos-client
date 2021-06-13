package com.sy.investment.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.view.JasperIndustryView;
import com.sy.investment.utils.JsonUtils;
import com.sy.investment.utils.ParamValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/print")
@Api(tags="打印API")
public class PrintController extends BaseController {
	
	@GetMapping("/sale")
	@ApiOperation(value="门店零售打印")
	@ApiImplicitParams({
		@ApiImplicitParam(name="billno",value="销售单号",dataType="String",required=true,paramType="query")
	})
	public RestEntity print(@RequestParam(required=true) String billno) {
		JasperIndustryView v = commonService.findEntityById(JasperIndustryView.class, 1L);
		List<Map<String, Object>> list = commonService.findMapsBySql( "call p_print_sale(:billno)", ParamValue.build().add("billno", billno));
		Map<String,Object> map = ImmutableMap.<String, Object>builder()
			.put("xml", Optional.of(v).orElse(new JasperIndustryView()).getJasper())
			.put("json",JsonUtils.toJson(list))
			.build();
		return RestEntity.success(map);
	}
}

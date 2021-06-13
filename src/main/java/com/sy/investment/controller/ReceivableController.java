package com.sy.investment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.table.SettleType;
import com.sy.investment.domain.view.ReceivableAccountDtlView;
import com.sy.investment.domain.vo.ReceivableAccountVo;
import com.sy.investment.service.SaleServie;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/receivable")
@Api(tags="应收款")
@Validated
public class ReceivableController extends BaseController {
	@Resource SaleServie saleService;
	@ApiOperation(value="支付方式")
	@GetMapping("/settletype")
	public RestEntity settleType() {
		List<SettleType> list = commonService.findAll(SettleType.class,
				" where id not in (2,3,4,6) and status=1 ", ParamValue.build());
		return RestEntity.success(list);
	}
	@ApiOperation(value="查询应收账款")
	@GetMapping("")
	public RestEntity query(@RequestParam(required=true) String keyword,@PageableDefault(page = 0,size=20)Pageable pageable) {
		Page<ReceivableAccountDtlView> page = commonService.findPage(ReceivableAccountDtlView.class, 
				" where storeId=:storeId and (code like :code or name like :name or phone like :phone ) and debtAmount<>0 and billType=1 order by memberId asc ",
				ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()).addLike("code", keyword).addLike("name", keyword).addLike("phone", keyword),pageable);
		return RestEntity.success(page);
	}
	@ApiOperation(value="保存应收账款")
	@PostMapping("")
	public RestEntity save(@Validated @RequestBody List<ReceivableAccountVo> list) {
		saleService.saveReceive(list);
		return RestEntity.success(list);
	}
}

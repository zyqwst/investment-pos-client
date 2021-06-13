/**
 * 
 */
package com.sy.investment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.table.Sale;
import com.sy.investment.service.SaleServie;
import com.sy.investment.utils.Constants.OrderStatus;
import com.sy.investment.utils.ParamValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName: GoodsController 
* @Description: 
* @author albert
* @date 2018年8月23日 上午11:15:19 
*  
*/
@Api(tags="JOBAPI")
@Validated
@RestController
@RequestMapping("/job/order")
public class JobController extends BaseController {
	
	@Resource SaleServie saleService;
	
	@ApiOperation(value="扫码支付同步支付结果")
	@GetMapping("/sync")
	public RestEntity syncPayResult() {
		List<Sale> list = commonService.findAll(Sale.class, " where status=:status", ParamValue.build().add("status", OrderStatus.NOTPAY.code));
		for(Sale s : list) {
			 try {
				saleService.syncPayResult(s.getBillno());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("同步订单状态失败",e);
			}
		}
		return RestEntity.success();
	}
}


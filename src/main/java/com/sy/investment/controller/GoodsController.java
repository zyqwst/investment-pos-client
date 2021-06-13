/**
 * 
 */
package com.sy.investment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.GoodsClassEntity;
import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.view.StoreGoodsPosView;
import com.sy.investment.domain.vo.GoodsPriceVo;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName: GoodsController 
* @Description: 
* @author albert
* @date 2018年8月27日 下午12:18:51 
*  
*/
@Api(tags="商品相关API")
@Validated
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
	@ApiOperation(value="商品大类")
	@GetMapping("/goodsclass")
	public RestEntity goodsClass() {
		List<GoodsClassEntity> list = commonService.findAllBySql(GoodsClassEntity.class, 
				" select distinct typeid id,goodsclass name,typeid orderno from store_goods_v where storeid=:storeId "
				+ "and status=1 ", ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()));
		return RestEntity.success(list);
	}
	@ApiOperation(value="根据商品类型查询门店商品")
	@ApiImplicitParams({
		@ApiImplicitParam(name="goodsclassid",value="商品类型ID",dataType="Long",required=true,paramType="query"),
	})
	@GetMapping("/goodslist")
	public RestEntity goodsByClassId(@RequestParam(required=true) Long goodsclassid) {
		List<StoreGoodsPosView> list = commonService.findAll(StoreGoodsPosView.class, " where storeId=:storeId "
				+ "and goodsclassId=:goodsclassId "
				+ "and status=1 "
				+ "and (goodsStatus=1 "
				+ "or areaStatus=1 "
				+ "or storeStatus=1) ",
				ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()).add("goodsclassId", goodsclassid));
		
		List<GoodsPriceVo> prices = new ArrayList<>();
		
		list.forEach(item -> {
			
			GoodsPriceVo gv = GoodsPriceVo.buildBy(item);
			
			if(gv.getMemberPrice()!=null && gv.getPrice()!=null) prices.add(gv);
		});
		return RestEntity.success(prices);
	}
	@ApiOperation(value="根据条码/代码/帮助码/名称查询商品（限制返回50条）")
	@ApiImplicitParams({
		@ApiImplicitParam(name="keywords",value="查询参数",dataType="String",required=true,paramType="query"),
	})
	@GetMapping("")
	public RestEntity goods(@RequestParam(required=true) String keywords) {
		Pageable pageable = PageRequest.of(0, 50);
		
		Page<StoreGoodsPosView> page = commonService.findPage(StoreGoodsPosView.class, " where status=1 "
				+ "and storeId=:storeId "
				+ "and (barcode  like :barcode "
				+ "or  code     like :code "
				+ "or  helpcode like :helpcode "
				+ "or  name		like :name)"
				+ "and (goodsStatus=1 "
				+ "or areaStatus=1 "
				+ "or storeStatus=1) "
				+ "order by id",
				ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid())
								  .addLike("barcode", keywords)
								  .addLike("code", keywords)
								  .addLike("helpcode", keywords)
								  .addLike("name", keywords), pageable);
		List<GoodsPriceVo> prices = new ArrayList<>();
				
		page.getContent().forEach(item -> {
			
			GoodsPriceVo gv = GoodsPriceVo.buildBy(item);
			
			if(gv.getMemberPrice()!=null && gv.getPrice()!=null) prices.add(gv);
		});
		return RestEntity.success(prices);
	}
	@ApiOperation(value="查询可用卡片兑换的商品列表")
	@GetMapping("/exchange-list")
	public RestEntity exchangeGoods() {
		List<StoreGoodsPosView> list = commonService.findAll(StoreGoodsPosView.class, 
				" where status=1 and (goodsStatus=1 or areaStatus=1 or storeStatus=1) and exchange=:exchange", 
				ParamValue.build().add("exchange", 1));
		return RestEntity.success(list);
	}
}

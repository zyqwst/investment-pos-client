/**
 * 
 */
package com.sy.investment.controller;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.sy.investment.domain.entity.GoodsFreeEntity;
import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.vo.FreeGoodsVo;
import com.sy.investment.domain.vo.GoodsSalePriceVo;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
* @ClassName: PriceController 
* @Description: 
* @author albert
* @date 2018年8月27日 下午4:35:59 
*  
*/
@Api(tags="价格API")
@Validated
@RestController
@RequestMapping("/price")
public class PriceController extends BaseController {
	@ApiOperation(value="查询非会员商品零售价")
	@ApiImplicitParams({
		@ApiImplicitParam(name="goodsid",value="商品ID",dataType="Long",required=true,paramType="query")
	})
	@GetMapping("")
	public RestEntity price(@RequestParam(required=true) Long goodsid) {
		String price = commonService.executeFunc(String.class, "select f_sale_price(:storeid,:memberid,:goodsid) ",
					ParamValue.build().add("storeid", UserThreadLocal.get().getStoreid()).add("memberid", null).add("goodsid", goodsid));
		return RestEntity.success(price);
	}
	@ApiOperation(value="查询购物车商品价格以及特权商品")
	@PostMapping("")
	public RestEntity prices(@ApiParam(name = "GoodsSalePriceVo", value = "商品销售价格", required =true) 
							@Validated  @RequestBody GoodsSalePriceVo vo) {
		StringBuilder sb = new StringBuilder();
		vo.getGoodsList().forEach((goodsid,qty)-> sb.append("{"+goodsid+"}"));
		List<ParamsEntity> pe = Arrays.asList(
				new ParamsEntity(Types.BIGINT, vo.getMemberId()),
				new ParamsEntity(Types.BIGINT, UserThreadLocal.get().getStoreid()),
				new ParamsEntity(Types.VARCHAR, sb.toString())
				);
		
		String[] s = commonService.executeProcs("p_sale_price", pe);
		
		List<GoodsFreeEntity> freeGoods = Lists.newArrayList();
		if(vo.getMemberId()>0) {
			freeGoods = commonService.findAllBySql(GoodsFreeEntity.class,
			"select b.id packageDtlId,d.goodsId,a.cnt,if(datediff(ifnull(b.lastusedate,curdate()),curdate())=0,b.usecnt,0) usecnt " +
			"from package_t a " +
			"inner join package_dtl_t b on a.id=b.packageid and b.memberid=:memberid " +
			"inner join package_store_t c on c.Packageid=a.id and c.storeid=:storeid " +
			"inner join package_goods_t d on d.Packageid=a.id and d.status=1 and d.goodsid in :goodsid " +
			"order by a.cnt desc", 
			ParamValue.build().add("memberid",vo.getMemberId()).add("storeid", UserThreadLocal.get().getStoreid()).add("goodsid", vo.getGoodsList().keySet()));
		}
		Map<Long,Integer> packageDtlCnt = Maps.newHashMap();
		Map<Long,FreeGoodsVo> freeResult = Maps.newHashMap();
		freeGoods.forEach(item -> {
			packageDtlCnt.put(item.getPackageDtlId(), item.getCnt()-item.getUsecnt());
			FreeGoodsVo f = freeResult.get(item.getGoodsId());
			if(f==null) f = FreeGoodsVo.builder().build();
			f.getPackageDtlMap().put(item.getPackageDtlId(), 0);
			freeResult.put(item.getGoodsId(),f);
		});
		for(GoodsFreeEntity item : freeGoods) {
			Integer remain = packageDtlCnt.get(item.getPackageDtlId());
			Long goodsId = item.getGoodsId();
			Integer buyQty = vo.getGoodsList().get(item.getGoodsId());
			FreeGoodsVo oldResult = freeResult.get(goodsId);
			Map<Long,Integer> packageDtlMap = oldResult.getPackageDtlMap();
			if(remain>0 && oldResult.getCnt()<buyQty) {
				packageDtlMap.put(item.getPackageDtlId(),min(remain,buyQty)-packageDtlMap.get(item.getPackageDtlId()));//.setCnt(oldPackageDtl.getCnt()+min(remain,buyQty)-oldResult.getCnt());
				oldResult.setCnt( min(oldResult.getCnt()+remain,buyQty));
				packageDtlCnt.put(item.getPackageDtlId(), remain-min(remain,buyQty));
			}
		}
		Map<String, Object> map = ImmutableMap.<String, Object>builder()
				.put("price"	, new Gson().fromJson(s[1], List.class))
				.put("gift"		,freeResult )
				.build();
		return RestEntity.success(map);
	}
	private Integer min(Integer one,Integer two) {
		return one>two?two:one;
	}
}

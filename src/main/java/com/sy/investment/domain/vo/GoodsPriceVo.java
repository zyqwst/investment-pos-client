/**
 * 
 */
package com.sy.investment.domain.vo;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.domain.view.StoreGoodsPosView;
import com.sy.investment.utils.CommonUtils;

import lombok.Builder;
import lombok.Data;

/** 
* @ClassName: GoodsPriceVo 
* @Description: 
* @author albert
* @date 2018年8月23日 下午2:16:59 
*  
*/
@Data
@Builder
public class GoodsPriceVo implements EntityBase {
	
	private static final long serialVersionUID = 8962598254006535775L;
	private Long id;
	private Long goodsId;
	private Long storeId;
	private Long goodsclassId;
	private String code;
	private String name;
	private String format;
	private String factory;
	private String helpcode;
	private String unit;
	/**零售价*/
	private Double price;
	/**会员价*/
	private Double memberPrice;
	private String barcode;
	private String path;
	
	public static GoodsPriceVo buildBy(StoreGoodsPosView item) {
		GoodsPriceVo vo = GoodsPriceVo.builder()
		.barcode(item.getBarcode())
		.code(item.getCode())
		.factory(item.getFactory())
		.format(item.getFormat())
		.goodsclassId(item.getGoodsclassId())
		.goodsId(item.getGoodsId())
		.helpcode(item.getHelpcode())
		.id(item.getId())
		.price(CommonUtils.firstNotNull(item.getStore_retailPrice(),item.getArea_retailPrice(),item.getRetailPrice()))
		.memberPrice(CommonUtils.firstNotNull(item.getStore_memberPrice(),item.getArea_memberPrice(),item.getMemberPrice()))
		.name(item.getName())
		.path(item.getPath())
		.storeId(item.getStoreId())
		.build();
		if(vo.getMemberPrice()==null || vo.getMemberPrice()<=0) {
			vo.setMemberPrice(vo.getPrice());
		}
		return vo;
	}
}

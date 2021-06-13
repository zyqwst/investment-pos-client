/**
 * 
 */
package com.sy.investment.domain.vo;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sy.investment.domain.EntityBase;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** 
* @ClassName: GoodsSalePriceVo 
* @Description: 商品销售价
* @author albert
* @date 2018年8月28日 下午2:03:53 
*  
*/
@Data
@ApiModel("门店销售价请求")
public class GoodsSalePriceVo implements EntityBase {
	private static final long serialVersionUID = 5149820545517840607L;
	@NotNull
	private Long memberId;
	@NotEmpty(message="商品明细不可为空<ID,QTY>")
	private Map<Long,Integer> goodsList;
}

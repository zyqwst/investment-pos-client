/**
 * 
 */
package com.sy.investment.domain.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.sy.investment.domain.EntityBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: ExchangeEggVo 
* @Description: 
* @author albert
* @date 2018年10月26日 下午10:52:37 
*  
*/
@Data
@ApiModel("兑换鸡蛋请求数据")
public class ExchangeEggVo implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7263699124234701626L;
	@ApiModelProperty(value="商品ID",required = true)
	@NotNull
	private Long goodsId;
	@ApiModelProperty(value="兑换给谁")
	@NotNull
	private Long memberId;
	@ApiModelProperty(value="数量")
	@NotNull
	@Min(value=1)
	private Integer count;
	@ApiModelProperty(value="卡片数量")
	@NotNull
	@Min(value=1)
	private Integer cardCount;
	
	
	@ApiModelProperty(value="收银员")
	@NotNull
	private Long saleManId;
	
}

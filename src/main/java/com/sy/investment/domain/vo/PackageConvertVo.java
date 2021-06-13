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
* @ClassName: PackageConvertVo 
* @Description: 
* @author albert
* @date 2018年10月26日 下午10:52:37 
*  
*/
@Data
@ApiModel("特权兑换请求数据")
public class PackageConvertVo implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7263699724234701626L;
	@ApiModelProperty(value="需要转赠的packagesale",required = true)
	@NotNull
	private Long packageSaleId;
	@ApiModelProperty(value="转赠给谁")
	@NotNull
	private Long memberId;
	@ApiModelProperty(value="转赠数量")
	@NotNull
	@Min(value=1)
	private Integer count;
	@ApiModelProperty(value="收银员")
	@NotNull
	private Long saleManId;
	
}

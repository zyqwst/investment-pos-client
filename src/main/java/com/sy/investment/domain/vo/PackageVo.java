/**
 * 
 */
package com.sy.investment.domain.vo;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.sy.investment.domain.EntityBase;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** 
* @ClassName: PackageVo 
* @Description: 
* @author albert
* @date 2018年9月11日 下午3:37:42 
*  
*/
@Data
@ApiModel("门店特权购买请求数据")
public class PackageVo implements EntityBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8907253812381703207L;
	@NotNull
	private Long memberId;
	@NotNull
	private Long saleManId;
	@NotNull
	private Long packageId;
	@NotNull
	@DecimalMin(value="0",inclusive=true)
	private BigDecimal posAmount;
	@DecimalMin(value="0",inclusive=true)
	private BigDecimal cashAmount;
	@DecimalMin(value="0",inclusive=true)
	private BigDecimal cardAmount;
	@NotNull
	private Integer count;
	private String authCode;
}

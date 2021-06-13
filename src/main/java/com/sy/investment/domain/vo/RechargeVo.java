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
* @ClassName: RechargeVo 
* @Description: 
* @author albert
* @date 2018年9月7日 下午3:20:43 
*  
*/
@Data
@ApiModel("门店充值请求数据")
public class RechargeVo implements EntityBase {/**
	 * 
	 */
	private static final long serialVersionUID = -8907253874381703207L;
	@NotNull
	private Long memberId;
	@NotNull
	private Long saleManId;
	@NotNull
	@DecimalMin(value="0",inclusive=true)
	private BigDecimal posAmount;
	@DecimalMin(value="0",inclusive=true)
	private BigDecimal cashAmount;
	
	private String authCode;
}

package com.sy.investment.domain.vo;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.sy.investment.domain.EntityBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel("配送数据")
public class DistributionVo implements EntityBase {/**
	 * 
	 */
	private static final long serialVersionUID = -7444180569256292919L;
	@NotNull
	private Long id;
	@DecimalMin(inclusive=true,value="0.0001")
	private BigDecimal qty;
	@NotNull
	private Long addressId;
	
	private String remark;
	@ApiModelProperty(value="是否立即配送（1，0）",required = true)
	@NotNull
	private Integer distributionStatus;
	@ApiModelProperty(value="预约日期",required = false)
	private String  distributionDate;
}

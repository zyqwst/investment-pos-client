package com.sy.investment.domain.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.sy.investment.domain.EntityBase;

import io.swagger.annotations.ApiModel;
import lombok.Data;
@Data
@ApiModel("应收账款收款数据")
public class ReceivableAccountVo implements EntityBase {
	
	private static final long serialVersionUID = -7523545646810069109L;
	@NotNull
	private Long id;
	@NotNull
	private BigDecimal amount;
	@NotNull
	private Long saleManId;
	
	@NotNull
	private Long settleTypeId;
}

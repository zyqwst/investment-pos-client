/**
 * 
 */
package com.sy.investment.domain.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sy.investment.domain.EntityBase;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** 
* @ClassName: StoreOrderVo 
* @Description: 门店退货提交
* @author albert
* @date 2018年8月29日 上午9:08:35 
*  
*/
@Data
@ApiModel("退货订单数据")
public class StoreRefundVo implements EntityBase {

	private static final long serialVersionUID = 2111869420897023523L;
	@NotNull
	private Long id;
	@NotNull
	private Long salemanId;
	/**现金金额*/
	@NotNull
	@DecimalMin(inclusive=true,value="0")
	private BigDecimal cashAmount;
	/**POS金额*/
	@NotNull
	@DecimalMin(inclusive=true,value="0")
	private BigDecimal posAmount;
	/**会员卡金额*/
	@NotNull
	@DecimalMin(inclusive=true,value="0")
	private BigDecimal cardAmount;
	
	@NotEmpty
	@Valid
	private List<OrderDtl> list;
	/**
	* @ClassName: OrderDtl 
	* @Description: 订单明细
	* @author albert
	* @date 2018年8月29日 上午10:05:04 
	*
	 */
	@Data
	public static class OrderDtl{
		@NotNull
		private Long id;
		/**购买数量*/
		@DecimalMin(inclusive=false,value="0")
		private BigDecimal refund;
	}
}



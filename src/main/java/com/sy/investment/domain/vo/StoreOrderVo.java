/**
 * 
 */
package com.sy.investment.domain.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.utils.MathUtil;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** 
* @ClassName: StoreOrderVo 
* @Description: 门店销售订单提交
* @author albert
* @date 2018年8月29日 上午9:08:35 
*  
*/
@Data
@ApiModel("订单数据")
public class StoreOrderVo implements EntityBase {

	private static final long serialVersionUID = 2121869420897023523L;
	
	private Long memberId;
	/**当前登录员工*/
	@NotNull
	private Long creUserId;
	/**收银员ID*/
	@NotNull
	private Long salemanId;
	/**总金额*/
	@NotNull
	@DecimalMin(inclusive=true,value="0")
	private BigDecimal amount;
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
	/**收银现金*/
	@NotNull
	@DecimalMin(inclusive=true,value="0")
	private BigDecimal payCash;
	/**收银找零*/
	@NotNull
	@DecimalMin(inclusive=true,value="0")
	private BigDecimal changeAmount;
	
	@NotEmpty
	@Valid
	private List<OrderDtl> list;
	@NotNull
	private Integer distributeStatus;
	@NotNull
	private Integer receivableStatus;
	/**
	 * 扫码支付支付码
	 */
	private String authCode;
	/**
	 * 检查是否 金额
	 */
	public void validAmont() {
		BigDecimal amount = MathUtil.add(cashAmount, posAmount,cardAmount);
		BigDecimal dtlAmount = BigDecimal.ZERO;
		
		if(this.receivableStatus-1==0) {//挂账其他金额必须为0
			Assert.isTrue(amount.compareTo(dtlAmount)==0, "挂账时其他金额必须为0");
			amount = this.amount;
		}
		for(OrderDtl dtl : list) {
			dtlAmount = MathUtil.add(MathUtil.mul(dtl.getPrice(), new BigDecimal(dtl.getCount()-dtl.getFree())), dtlAmount);
		}
		Assert.isTrue(amount.compareTo(dtlAmount)==0, "合计金额与订单明细金额不符");
		
		Assert.isTrue(MathUtil.sub(payCash, cashAmount).compareTo(changeAmount)==0,"现金收银有误：实收现金-应收现金<>找零金额");
	}
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
		private Long goodsId;
		/**零售价*/
		@DecimalMin(inclusive=true,value="0")
		private BigDecimal price;
		/**购买数量*/
		@Min(1)
		private Integer count;
		/**免费数量*/
		@NotNull
		private Integer free;
		@NotNull
		private Long priceId;
		@NotNull
		private Integer priceType;
		@NotNull
		private Long promotionId;
		
		/**<特权ID，数量>*/
		@NotNull
		private Map<Long,Integer> packageDtlMap;
	}
}



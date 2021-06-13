/**
 * 
 */
package com.sy.investment.domain.vo;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.domain.table.Sale;
import com.sy.investment.domain.table.WebOrder;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/** 
* @ClassName: TradePayVo 
* @Description: 支付请求vo对象
* @author albert
* @date 2018年7月24日 下午5:18:03 
*  
*/
@Data
@Builder
@ApiModel("支付请求数据")
public class TradePayVo implements EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8935779714590505723L;
	@ApiModelProperty(value="条形码",required = false)
	@Length(max=32,min=8)
	private String authCode;
	
	@ApiModelProperty(value="商户订单号",required = true)
	@NotNull(message="商户订单号不可为空")
	@Length(max=32,min=8)
	private String outTradeNo;
	
	@ApiModelProperty(value="订单标题",required = true)
	@NotNull(message="订单主题不可为空")
	@Length(max=256)
	private String subject;
	
	@ApiModelProperty(value="订单描述",required = false)
	private String body;
	
	@ApiModelProperty(value="订单金额（元）",required = true)
	@NotNull(message="订单金额不可为空")
	@DecimalMin(value="0.01")
	@DecimalMax(value="100000000")
	private String totalAmount;
	@ApiModelProperty(value="APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP")
	@NotNull(message="ip地址不可为空")
	private String spbillCreateIp;
	@ApiModelProperty(value="公众号支付用户的openId（公众号支付必传）")
	private String openId;
	
	public static TradePayVo buildByStoreOrder(StoreOrderVo vo,Sale sale) {
		return TradePayVo.builder()
				.authCode(vo.getAuthCode())
				.body("")
				.outTradeNo(sale.getBillno())
				.spbillCreateIp("122.112.197.187")
				.subject(UserThreadLocal.get().getStoreName())
				.totalAmount(vo.getPosAmount().toPlainString())
				.build();
	}
	public static TradePayVo buildByRechage(RechargeVo vo,WebOrder sale) {
		return TradePayVo.builder()
				.authCode(vo.getAuthCode())
				.body("充值会员卡")
				.outTradeNo(sale.getOutTradeNo())
				.spbillCreateIp("122.112.197.187")
				.subject(UserThreadLocal.get().getStoreName())
				.totalAmount(vo.getPosAmount().toPlainString())
				.build();
	}
	public static TradePayVo buildByPackage(PackageVo vo,WebOrder sale) {
		return TradePayVo.builder()
				.authCode(vo.getAuthCode())
				.body("购买特权")
				.outTradeNo(sale.getOutTradeNo())
				.spbillCreateIp("122.112.197.187")
				.subject(UserThreadLocal.get().getStoreName())
				.totalAmount(vo.getPosAmount().toPlainString())
				.build();
	}
}

package com.sy.investment.domain.table;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <p>Title: WebOrder</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-07 15:29:07
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="web_order_t")
public class WebOrder implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * 1-充值 2-购买特权
	 */
	private Integer type;
	/**
	 * 
	 */
	private Long packageId;
	/**
	 * 
	 */
	private Integer count;
	/**
	 * 创建订单：NEW-0 未支付：NOTPAY-1 正在支付：USERPAING-2 支付成功：SUCCESS-3 已关闭：CLOSED-4 人工处理：MANUAL-99
	 */
	private Integer status;
	/**
	 * 
	 */
	private String statusdesc;
	/**
	 * 金额（元）
	 */
	private BigDecimal amount;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 平台订单号
	 */
	private String tradeNo;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private Integer regsetId;
	/**
	 * 门店充值，购买特权需要记入门店ID
	 */
	private Long storeId;
	/**
	 * 门店营业员，门店消费时使用
	 */
	private Long salemanId;
	
	private BigDecimal cashAmount;
	private BigDecimal posAmount;
	private BigDecimal cardAmount;
	
	private String remark;
}
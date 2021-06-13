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
 * <p>Title: Sale</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-29 11:35:43
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sale_t")
public class Sale implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private String billno;
	/**
	 * 1-销售 -1退货
	 */
	private Integer type;
	/**
	 * 
	 */
	private Long storeId;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private Long creuserId;
	/**
	 * 
	 */
	private Long salemanId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private Integer dtls;
	/**
	 * 
	 */
	private BigDecimal changeAmount;
	/**
	 * 是否需要配送  1-是 0-否
	 */
	@Builder.Default
	private Integer distributeStatus=0;
	/**
	 * 
	 */
	@Builder.Default
	private Integer status = 1 ;
	/**
	 * 
	 */
	private String remark;
	
	/**
	 * 兑换卡片数量
	 */
	private Integer exchangeCount;
	
	private BigDecimal cashAmount;
	private BigDecimal posAmount;
	private BigDecimal cardAmount;
	private BigDecimal receivableAmount;
}
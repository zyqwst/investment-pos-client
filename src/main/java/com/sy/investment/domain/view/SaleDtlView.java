package com.sy.investment.domain.view;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: SaleDtlView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-27 14:50:59
 * @version 1.0
 */
@Data
@Entity
@Table(name="sale_dtl_v")
public class SaleDtlView implements EntityBase {
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
	private Long saleId;
	/**
	 * 
	 */
	private Long goodsId;
	/**
	 * 
	 */
	private BigDecimal qty;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private BigDecimal costPrice;
	/**
	 * 
	 */
	private BigDecimal retailPrice;
	/**
	 * 
	 */
	private BigDecimal memberPrice;
	/**
	 * 
	 */
	private Integer priceType;
	/**
	 * 
	 */
	private Long priceId;
	/**
	 * 是否促销价 0-否 1-是
	 */
	private Integer promotionStatus = 0;
	/**
	 * 
	 */
	private Long promotionId;
	/**
	 * 520特权ID
	 */
	private Long packageId;
	/**
	 * 
	 */
	private Long packagedtlId;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String format;
	/**
	 * 
	 */
	private String unit;
	/**
	 * 1-520价格 2-促销价 3-会员价 4-零售价
	 */
	private Integer type;
	/**
	 * 已退货数量
	 */
	private BigDecimal refundQty;
}
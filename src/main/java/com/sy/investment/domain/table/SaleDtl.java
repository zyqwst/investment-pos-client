package com.sy.investment.domain.table;
import java.math.BigDecimal;

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
 * <p>Title: SaleDtl</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-29 13:19:32
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sale_dtl_t")
public class SaleDtl implements EntityBase {
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
	@Builder.Default
	private Integer promotionStatus = 0;
	/**
	 * 
	 */
	private Long promotionId;
	/**
	 * 520特权ID
	 */
	private Long packageId;
	
	private Long packageDtlId;
	/**
	 * 退货数量（正单用）
	 */
	@Builder.Default
	private BigDecimal refundQty = BigDecimal.ZERO;
	/**
	 * 销售单明细ID（退单用）
	 */
	private Long saleDtlId;
}
package com.sy.investment.domain.view;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sy.investment.domain.EntityBase;
import lombok.Data;
/**
 * <p>Title: DistributionView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-10 15:19:21
 * @version 1.0
 */
@Data
@Entity
@Table(name="distribution_v")
public class DistributionView implements EntityBase {
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
	private Long saledtlId;
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
	private BigDecimal balance;
	/**
	 * 
	 */
	private BigDecimal controlQty;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String outTradeNo;
	/**
	 * 
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date saleDate;
	/**
	 * 
	 */
	private String goodsCode;
	/**
	 * 
	 */
	private String goodsName;
	/**
	 * 
	 */
	private String format;
	/**
	 * 
	 */
	private String unit;
	
	private String saleMan;
}
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
 * <p>Title: SaleExchangeSettleView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-29 10:53:48
 * @version 1.0
 */
@Data
@Entity
@Table(name="sale_exchange_settle_v")
public class SaleExchangeSettleView implements EntityBase {
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
	private Long storeId;
	/**
	 * 
	 */
	private Long salemanId;
	/**
	 * 
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	/**
	 * 
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	/**
	 * 
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creDate;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String name;
}
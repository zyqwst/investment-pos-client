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
 * <p>Title: SaleExchange_settle_dtl_v</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-29 12:51:50
 * @version 1.0
 */
@Data
@Entity
@Table(name="sale_exchange_settle_dtl_v")
public class SaleExchangeSettleDtlView implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ;
	/**
	 * 
	 */
	private Long orderId;
	/**
	 * 
	 */
	private Long settletypeId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private String name;
}
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
 * <p>Title: WebOrderSettle</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-07 15:30:12
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="web_order_settle_t")
public class WebOrderSettle implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * 网单ID
	 */
	private Long webOrderId;
	/**
	 * 
	 */
	private Long settleTypeId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private Long storeId;
	/**
	 * 收银员
	 */
	private Long saleManId;
}
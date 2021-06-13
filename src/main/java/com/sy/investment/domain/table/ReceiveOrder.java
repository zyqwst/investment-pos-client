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
 * <p>Title: ReceiveOrder</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-24 22:33:24
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="	receive_order_t")
public class ReceiveOrder implements EntityBase {
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
	private Long memberId;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private String billno;
	/**
	 * 
	 */
	private Long creuserId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Long affirmuserId;
	/**
	 * 
	 */
	private Date affirmDate;
	/**
	 * 
	 */
	private Long storeId;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Long settleTypeId;
}
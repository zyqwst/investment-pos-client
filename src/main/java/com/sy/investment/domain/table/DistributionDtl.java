package com.sy.investment.domain.table;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sy.investment.domain.EntityBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <p>Title: DistributionDtl</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-10 12:57:19
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="distribution_dtl_t")
public class DistributionDtl implements EntityBase {
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
	private Long orderId;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 
	 */
	private Date distributeDate;
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
	private Long addressId;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String tel;
	/**
	 * 
	 */
	private String linkMan;
	
	private String remark;
}
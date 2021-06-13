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
 * <p>Title: SaleSettle</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-29 14:21:52
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Sale_settle_t")
public class SaleSettle implements EntityBase {
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
	private Long settleTypeId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private Date creDate;
	
	private Long storeId;
}
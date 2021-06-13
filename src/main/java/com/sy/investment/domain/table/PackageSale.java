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
 * <p>Title: PackageSale</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-10-26 23:05:53
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="package_sale_t")
public class PackageSale implements EntityBase {
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
	private Long storeId;
	/**
	 * 
	 */
	private Long packageId;
	/**
	 * 
	 */
	private Integer qty;
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
	private Integer unBindQty;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private Long creuserId;
	/**
	 * 
	 */
	private Long memberid2;
	/**
	 * （针对卡片兑换），兑换日期
	 */
	private Date exchangeDate;
	/**
	 * 公司员工内购（用于门店转赠）
	 */
	@Builder.Default
	private Integer empbuy = 0;
	/**
	 * 订单ID
	 */
	private Long webOrderId;
}
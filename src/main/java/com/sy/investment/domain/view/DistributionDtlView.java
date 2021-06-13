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
 * <p>Title: DistributionDtlView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-10 09:12:39
 * @version 1.0
 */
@Data
@Entity
@Table(name="distribution_dtl_v")
public class DistributionDtlView implements EntityBase {
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
	private Long goodsId;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private BigDecimal qty;
	/**
	 * 
	 */
	private Long addressId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date applyDate;
	/**
	 * 
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date distributeDate;
	/**
	 * 
	 */
	private Long orderId;
	/**
	 * 0-未配送 1-已配送 -1-已删除
	 */
	private Integer status;
	/**
	 * 
	 */
	private String code;
	
	private String name;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private Long storeId;
	/**
	 * 
	 */
	private Long saleId;
	/**
	 * 
	 */
	private Long saledtlId;
	/**
	 * 销售单号
	 */
	private String outTradeNo;
	/**
	 * 销售日期
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date saleDate;
	/**
	 * 
	 */
	private Long salemanId;
	/**
	 * 收银员
	 */
	private String saleManName;
	/**
	 * 
	 */
	private String saleManCode;
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
	
	private String goodsCode;
	
	private String goodsName;
	
	private String remark;
}
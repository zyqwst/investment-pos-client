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
 * <p>Title: SaleView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-25 13:43:58
 * @version 1.0
 */
@Data
@Entity
@Table(name="sale_v")
public class SaleView implements EntityBase {
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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date creDate;
	/**
	 * 
	 */
	private String billno;
	/**
	 * 1-销售 -1退货
	 */
	private Integer type;
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
	private Long creUserId;
	/**
	 * 
	 */
	private Long saleManId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private Integer dtls;
	/**
	 * 
	 */
	private BigDecimal changeAmount;
	/**
	 * 是否需要配送  1-是 0-否
	 */
	private Integer distributeStatus ;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String memberCode;
	/**
	 * 
	 */
	private String memberName;
	/**
	 * 
	 */
	private String saleManCode;
	/**
	 * 
	 */
	private String saleManName;
	
	private String qrcode;
	
	private String phone;
}
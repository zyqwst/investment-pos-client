package com.sy.investment.domain.table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: SettleType</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-29 14:22:48
 * @version 1.0
 */
@Data
@Entity
@Table(name="Settle_type_t")
public class SettleType implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	
	public static final Long CASH 	= 1L;
	public static final Long WXPAY 	= 2L;
	public static final Long ALIPAY	= 3L;
	public static final Long BANKCARD = 4L;
	public static final Long MEMBERCARD = 5L;
	public static final Long RECEIVABLE = 6L;//应收
	public static final Long POS = 7L;
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 
	 */
	private Integer singesign;
	/**
	 * 
	 */
	private Integer status;
}
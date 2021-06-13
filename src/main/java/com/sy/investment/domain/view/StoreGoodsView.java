package com.sy.investment.domain.view;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: StoreGoodsView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-23 13:33:22
 * @version 1.0
 */
@Data
@Entity
@Table(name="store_goods_v")
public class StoreGoodsView implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	/**
	 * 
	 */
	@Id
	private Long id;
	/**
	 * 
	 */
	private Long goodsId;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String format;
	/**
	 * 
	 */
	private String unit;
	/**
	 * 
	 */
	private String factory;
	/**
	 * 
	 */
	private Long typeId;
	/**
	 * 
	 */
	private String goodsclass;
	/**
	 * 
	 */
	private String storecode;
	/**
	 * 
	 */
	private String storename;
	/**
	 * 
	 */
	private Long storeId;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String statusName;
	/**
	 * 
	 */
	private Long creuserId;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private String creUserName;
}
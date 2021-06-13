package com.sy.investment.domain.view;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: 商品价格</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-23 14:14:57
 * @version 1.0
 */
@Data
@Entity
@Table(name="store_goods_pos_v")
public class StoreGoodsPosView implements EntityBase {
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
	private Long storeId;
	/**
	 * 
	 */
	private Integer status;
	private Integer goodsStatus;
	private Integer areaStatus;
	private Integer storeStatus;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private Date modDate;
	/**
	 * 
	 */
	private Long creuserId;
	/**
	 * 
	 */
	private Long moduserId;
	/**
	 * 
	 */
	private Long goodsclassId;
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
	private String factory;
	/**
	 * 
	 */
	private String helpcode;
	/**
	 * 
	 */
	private String unit;
	/**
	 * 
	 */
	private Double retailPrice;
	/**
	 * 
	 */
	private Double memberPrice;
	/**
	 * 
	 */
	private Double costPrice;
	/**
	 * 
	 */
	private Integer packsize;
	/**
	 * 
	 */
	private String barcode;
	/**
	 * 
	 */
	private String path;
	/**
	 * 
	 */
	private Double area_retailPrice;
	/**
	 * 
	 */
	private Double area_memberPrice;
	/**
	 * 
	 */
	private Double store_retailPrice;
	/**
	 * 
	 */
	private Double store_memberPrice;
	/**
	 * 
	 */
	private Long areaId;
	/**
	 * 
	 */
	private Long typeId;
	/**
	 * 是否可用卡片兑换 1-是 0-否
	 */
	private Integer exchange;
}
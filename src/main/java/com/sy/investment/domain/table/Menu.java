package com.sy.investment.domain.table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: Menu</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-23 09:52:48
 * @version 1.0
 */
@Data
@Entity
@Table(name="menu_t")
public class Menu implements EntityBase {
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
	private Long pid;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String path;
	/**
	 * 
	 */
	private Integer orderNum;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 
	 */
	private String uiName;
	/**
	 * 
	 */
	private Integer type2;
	/**
	 * 
	 */
	private String icon;
}
package com.sy.investment.domain.view;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: UserMenuView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-23 10:01:23
 * @version 1.0
 */
@Data
@Entity
@Table(name="user_menu_v")
public class UserMenuView implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	/**
	 * 
	 */
	@Id
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
	 * 5-POS机
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
	/**
	 * 
	 */
	private Long userId;
}
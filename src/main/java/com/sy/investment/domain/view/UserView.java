package com.sy.investment.domain.view;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;

/**
 * <p>Title: UserView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2019-01-03 14:28:36
 * @version 1.0
 */
@Data
@Entity
@Table(name="user_v")
public class UserView implements EntityBase {
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
	private String code;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String helpcode;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private String sex;
	/**
	 * 
	 */
	private Date birthday;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private Long depId;
	/**
	 * 
	 */
	private String depname;
	/**
	 * 
	 */
	private Long storeId;
	/**
	 * 
	 */
	private String storename;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String statusname;
	/**
	 * 
	 */
	private String remark;
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
	private Long moduserId;
	/**
	 * 
	 */
	private Date modDate;
	/**
	 * 
	 */
	private String creusername;
}
package com.sy.investment.domain.table;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sy.investment.domain.EntityBase;
import lombok.Data;
/**
 * <p>Title: User</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-22 14:41:20
 * @version 1.0
 */
@Data
@Entity
@Table(name="user_t")
public class User implements EntityBase {
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
	private Long depid;
	/**
	 * 
	 */
	private Long storeid;
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
	private Date credate;
	/**
	 * 
	 */
	private Date moddate;
	/**
	 * 
	 */
	private Long creuserid;
	/**
	 * 
	 */
	private Long moduserid;
	/**
	 * 
	 */
	private String password;
}
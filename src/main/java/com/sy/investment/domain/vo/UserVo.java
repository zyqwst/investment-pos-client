package com.sy.investment.domain.vo;
import java.util.Date;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.domain.table.User;
import com.sy.investment.domain.view.UserView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements EntityBase {
	private static final long serialVersionUID = 49611152423543243L;
	/**
	 * 
	 */
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
	
	private String storeName;
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
	
	public static UserVo buildByUser(UserView user) {
		return UserVo.builder()
				.address(user.getAddress())
				.birthday(user.getBirthday())
				.code(user.getCode())
				.depid(user.getDepId())
				.helpcode(user.getHelpcode())
				.id(user.getId())
				.name(user.getName())
				.phone(user.getPhone())
				.sex(user.getSex())
				.storeid(user.getStoreId())
				.storeName(user.getStorename())
				.build();
	}
}
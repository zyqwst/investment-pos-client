package com.sy.investment.domain.table;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;

@Data
@Entity
@Table(name="member_address_t")
public class MemberAddress implements EntityBase{



	/**
	 * 
	 */
	private static final long serialVersionUID = 6694049919759186880L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long memberId;
	
	private String address;
	
	private String tel;
	
	private String linkMan;
	
	private Integer status;
	
	private Date creDate;
	
	private Long creUserId;

	
}

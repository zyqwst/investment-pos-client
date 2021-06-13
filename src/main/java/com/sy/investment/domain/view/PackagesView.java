package com.sy.investment.domain.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="package_v")
public class PackagesView implements EntityBase{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1602053704476274607L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Date beginDate;
	private Date endDate;
	private Integer status;
	private Double amount;
	private Integer cnt;
	private Integer validDays;
	private String remark;
	
	private Long creUserId;
	
	private Long modUserId;
	
	private Date creDate;
	
	private Date modDate;
	
	private String path;

	
}

package com.sy.investment.domain.view;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sy.investment.domain.EntityBase;

import lombok.Data;

@Data
@Entity
@Table(name="package_sale_v")
public class PackageSaleView implements EntityBase{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4738428935122497097L;
	@Id
	private Long id;

	private Long storeId;
	
	private Long memberId;
	
	private Long packageId;
	
	private Integer qty;
	
	private Double price;
	
	private Double amount;
	
	private Integer unBindQty;
	
	private String remark;
	
	private Long creUserId;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date creDate;
	private Integer status;
	private String packageName;
	private String path;
	
	private Integer empBuy;
	
	
}

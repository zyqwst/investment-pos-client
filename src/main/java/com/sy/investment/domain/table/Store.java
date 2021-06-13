package com.sy.investment.domain.table;
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
@Table(name="store_t")
public class Store implements EntityBase{



	/**
	 * 
	 */
	private static final long serialVersionUID = 908059002373882538L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String code;
	
	private String name;
	
	private String helpCode;
	
	private String address;
	
	private String telephone;
	
	private String principal;
	
	private String principalphone;

	private Long industryId;
	
	private Long areaId;
	
	private Long typeId;
	
	private Integer status;
	
	private String remark;
	
}

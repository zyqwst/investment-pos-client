package com.sy.investment.domain.table;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: PackageDtl</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-26 12:11:14
 * @version 1.0
 */
@Data
@Entity
@Table(name="package_dtl_t")
public class PackageDtl implements EntityBase {
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
	private Long saleId;
	/**
	 * 
	 */
	private Long packageId;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private Date bindDate;
	/**
	 * 
	 */
	private Date endDate;
	/**
	 * 
	 */
	private Integer usecnt;
	/**
	 * 最后一次消费时间
	 */
	private Date lastuseDate;
}
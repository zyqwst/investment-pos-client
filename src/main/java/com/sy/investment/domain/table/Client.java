package com.sy.investment.domain.table;
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
/**
 * <p>Title: Client</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-19 12:10:19
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="client_t")
public class Client implements EntityBase {
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
	private String clientId;
	/**
	 * 1-总部  2-门店
	 */
	private Integer type;
	/**
	 * 
	 */
	private Long companyId;
	/**
	 * 
	 */
	private Long storeId;
	/**
	 * 
	 */
	private Integer result;
	/**
	 * 
	 */
	private Date auditDate;
	/**
	 * 
	 */
	private Date gmt_create;
	/**
	 * 
	 */
	private Date gmt_modified;
	/**
	 * 
	 */
	private String remark;
}
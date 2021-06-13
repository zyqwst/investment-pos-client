package com.sy.investment.domain.view;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: JasperIndustry_v</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-26 08:58:38
 * @version 1.0
 */
@Data
@Entity
@Table(name="jasper_industry_v")
public class JasperIndustryView implements EntityBase {
	private static final long serialVersionUID = 4961115982468162243L;
	/**
	 * 
	 */
	@Id
	private Long id;
	/**
	 * 业态ID,0-通用模板
	 */
	private Long industryId;
	/**
	 * jasper报表ID
	 */
	private Long jasperId;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 模板介绍
	 */
	private String intro;
	/**
	 * jasper报表文件内容
	 */
	private String jasper;
}
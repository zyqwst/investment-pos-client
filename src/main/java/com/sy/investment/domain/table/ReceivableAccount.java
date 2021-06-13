package com.sy.investment.domain.table;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * <p>Title: ReceivableAccount</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-24 22:31:59
 * @version 1.0
 */
@Data
@Entity
@Table(name="receivable_account_t")
public class ReceivableAccount implements EntityBase {
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
	private Long storeId;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private BigDecimal amount;
}
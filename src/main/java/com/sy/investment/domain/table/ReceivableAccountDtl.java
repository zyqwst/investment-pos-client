package com.sy.investment.domain.table;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sy.investment.domain.EntityBase;
import lombok.Data;
/**
 * <p>Title: ReceivableAccountDtl</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-09-24 22:32:32
 * @version 1.0
 */
@Data
@Entity
@Table(name="receivable_account_dtl_t")
public class ReceivableAccountDtl implements EntityBase {
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
	private Date creDate;
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
	private Integer billType;
	/**
	 * 
	 */
	private String summary;
	/**
	 * 
	 */
	private BigDecimal addAmount;
	/**
	 * 
	 */
	private BigDecimal decAmount;
	/**
	 * 
	 */
	private BigDecimal debtAmount;
	/**
	 * 
	 */
	private Long saleId;
	/**
	 * 
	 */
	private Long creuserId;
	/**
	 * 
	 */
	private String remark;
}
package com.sy.investment.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sy.investment.domain.EntityBase;

import lombok.Data;
/**
 * 门店交班结算实体 p_sale_exchange_settle
 * @author Administrator
 *
 */
@Data
@Entity
public class SaleExchangeSettleEntity implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5872051272455906495L;
	@Id
	private String id;
	/**1-充值 2-特权购买 3-门店零售*/
	private Integer channel;
	
	private String channelName;
	
	private Long settleTypeId;
	
	private String settleTypeName;
	
	private Long saleManId;
	
	private BigDecimal amount;
	/**1-销售 -1-退货*/
	private Integer type;
	
	private String typeName;
	
	private Date beginDate;
	
	private Date endDate;
}

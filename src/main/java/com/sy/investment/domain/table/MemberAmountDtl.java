package com.sy.investment.domain.table;

import java.math.BigDecimal;
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
@Table(name="member_amount_dtl_t")
public class MemberAmountDtl implements EntityBase{
	
	/** serialVersionUID*/
	private static final long serialVersionUID = 2611020878598523610L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date creDate;
	/**
	 * 1-充值，2-门店消费，3-微信消费，4-返现 5-门店消费退款
	 */
	private Integer type;
	private Long orderId;
	private Long storeId;
	private Long memberId;
	private BigDecimal addAmount;
	private BigDecimal decAmount;
	private BigDecimal balance;
	private BigDecimal backBalance;
	private BigDecimal amount;
	private BigDecimal backAmount;
	private Long creUserId;
	private String remark;
	
	private Long saleId;

	
}

package com.sy.investment.domain.view;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sy.investment.domain.EntityBase;

import lombok.Data;
@Data
@Entity
@Table(name="receivable_account_dtl_v")
public class ReceivableAccountDtlView implements EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5396776199540265904L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Date creDate;
	private Long storeId;
	private Long memberId;
	private Long billType;
	private String summary;
	private BigDecimal addAmount;
	private BigDecimal decAmount;
	private BigDecimal debtAmount;
	private Long saleId;
	private Long creUserId;
	private String remark;
	private String storeName;
	private BigDecimal amount;
	
	private String code;
	private String phone;
	private String name;
	private String qrcode;
	
	private String outTradeNo;
	private String saleManCode;
	private String saleManName;
	
}

package com.sy.investment.domain.view;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sy.investment.domain.EntityBase;
import lombok.Data;
/**
 * <p>Title: MemberAmountView</p>
 * <p>Description: auto generated </p>
 * <p>Company: 湖州双翼信息技术有限公司</p>
 * @author AlbertZhang
 * @date	2018-08-23 17:09:57
 * @version 1.0
 */
@Data
@Entity
@Table(name="member_amount_v")
public class MemberAmountView implements EntityBase {
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
	private String code;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String sex;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String openId;
	/**
	 * 
	 */
	private String wx_headimageurl;
	/**
	 * 
	 */
	private String wx_nickname;
	/**
	 * 
	 */
	private Integer attentionStatus;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date registDate;
	/**
	 * 
	 */
	private Long typeId;
	/**
	 * 
	 */
	private Long levelId;
	/**
	 * 
	 */
	private Date creDate;
	/**
	 * 
	 */
	private Date modDate;
	/**
	 * 
	 */
	private Long moduserId;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private BigDecimal backAmount;
	/**
	 * 
	 */
	private BigDecimal freezingAmount;
	/**
	 * 
	 */
	private BigDecimal freezingbackAmount;
	
	private String qrcode;
	/**
	 * 是否扫描二维码查询
	 */
	@Transient
	private Integer isScan;
}
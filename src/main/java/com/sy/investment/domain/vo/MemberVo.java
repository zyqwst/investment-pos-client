/**
 * 
 */
package com.sy.investment.domain.vo;

import com.sy.investment.domain.EntityBase;

import lombok.Data;

/** 
* @ClassName: MemberVo 
* @Description: 
* @author albert
* @date 2018年8月22日 下午4:07:22 
*  
*/
@Data
public class MemberVo implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7815429486770052970L;
	
	private Long id;
	
	private String code;
	
	private String name;
	
	private String phone;
	
	private Integer attentionStatus;

}

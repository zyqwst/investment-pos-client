/**
 * 
 */
package com.sy.investment.domain.vo;

import javax.validation.constraints.NotNull;

import com.sy.investment.domain.EntityBase;

import lombok.Data;

/** 
* @ClassName: StoreRegistVo 
* @Description: 
* @author albert
* @date 2018年9月19日 下午12:13:23 
*  
*/
@Data
public class StoreRegistVo implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2855619272687829389L;
	@NotNull
	private Long storeId;
	@NotNull
	private String computerId;
	private String intro; 
}

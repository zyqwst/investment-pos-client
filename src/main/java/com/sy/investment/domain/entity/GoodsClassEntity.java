/**
 * 
 */
package com.sy.investment.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sy.investment.domain.EntityBase;

import lombok.Data;

/** 
* @ClassName: GoodsClassEntity 
* @Description: 
* @author albert
* @date 2018年8月23日 下午1:34:52 
*  
*/
@Data
@Entity
public class GoodsClassEntity implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6323352626692287138L;
	@Id
	private Long id;
	private String name;
	private Integer orderno;
	
}

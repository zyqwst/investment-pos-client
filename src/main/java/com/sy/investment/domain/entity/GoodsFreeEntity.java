/**
 * 
 */
package com.sy.investment.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sy.investment.domain.EntityBase;

import lombok.Data;

/** 
* @ClassName: GoodsFreeEntity 
* @Description: 
* @author albert
* @date 2018年11月1日 下午2:04:19 
*  
*/
@Data
@Entity
public class GoodsFreeEntity implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7059419437388657417L;
	@Id
	private Long packageDtlId;
	@Id
	private Long goodsId;
	
	private Integer cnt;
	
	private Integer usecnt;
	
}

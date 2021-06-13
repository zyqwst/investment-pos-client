/**
 * 
 */
package com.sy.investment.domain.vo;

import java.util.Map;

import com.google.common.collect.Maps;
import com.sy.investment.domain.EntityBase;

import lombok.Builder;
import lombok.Data;

/** 
* @ClassName: FreeGoodsVo 
* @Description: 
* @author albert
* @date 2018年11月2日 上午9:51:06 
*  
*/
@Data
@Builder
public class FreeGoodsVo implements EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 57654079759419801L;
	
	@Builder.Default
	private Map<Long,Integer> packageDtlMap = Maps.newHashMap();
	@Builder.Default
	private Integer cnt = 0;
}

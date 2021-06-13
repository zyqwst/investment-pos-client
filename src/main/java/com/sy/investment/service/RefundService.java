/**
 * 
 */
package com.sy.investment.service;

import com.sy.investment.domain.table.Sale;
import com.sy.investment.domain.vo.StoreRefundVo;

/** 
* @ClassName: RefundService 
* @Description: 
* @author albert
* @date 2018年9月28日 下午1:22:59 
*  
*/
public interface RefundService {
	public Sale save(StoreRefundVo vo);
}

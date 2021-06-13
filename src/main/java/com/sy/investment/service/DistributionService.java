/**
 * 
 */
package com.sy.investment.service;

import com.sy.investment.domain.vo.DistributionVo;

/** 
* @ClassName: DistributionService 
* @Description: 
* @author albert
* @date 2018年9月10日 下午12:53:52 
*  
*/
public interface DistributionService {
	/**配送申请确认*/
	public void confirm(Long id);
	/**配送申请取消*/
	public void cancel(Long id);
	/**未申请，直接配送*/
	public void save(DistributionVo vo);
}

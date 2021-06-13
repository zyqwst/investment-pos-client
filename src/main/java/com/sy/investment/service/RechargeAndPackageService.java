/**
 * 
 */
package com.sy.investment.service;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.vo.PackageVo;
import com.sy.investment.domain.vo.RechargeVo;

/** 
* @ClassName: RechargeAndPackageService 
* @Description: 门店充值和特权销售
* @author albert
* @date 2018年9月7日 下午3:26:11 
*  
*/
public interface RechargeAndPackageService {
	/**门店充值*/
	public RestEntity save(RechargeVo rechargeVo);
	/**特权销售*/
	public RestEntity save(PackageVo packageVo);
	/**同步支付结果*/
	public RestEntity syncPayResult(String outTradeNo);
	/**撤销支付*/
	public void revoke(String outTradeNo);
}

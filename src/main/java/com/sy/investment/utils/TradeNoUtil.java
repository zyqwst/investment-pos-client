/**
 * 
 */
package com.sy.investment.utils;

import com.sy.investment.reponsitory.CommonDao;

/** 
* @ClassName: TradeNoUtil 
* @Description: 订单号工具类,只能在Service层使用
* @author albert
* @date 2018年8月6日 下午4:57:40 
*  
*/
public class TradeNoUtil {
	/**
	 * 生成out_trade_no
	 * @param dao
	 * @return
	 */
	public static String getOutTradeNo(CommonDao dao) {
		return dao.executeFunc(String.class, "select f_get_out_trade_no()", ParamValue.build());
	}
}

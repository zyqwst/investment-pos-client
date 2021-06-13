/**
 * 
 */
package com.sy.investment.utils;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

/** 
* @ClassName: Constants 
* @Description: 常量
* @author albert
* @date 2018年7月26日 下午1:57:49 
*  
*/
public interface Constants {
	/**IP白名单*/
	public final static String GLOBAL_WHITE_LIST = "gloabal_white_list";
	/**用户token*/
	public final static String USER_TOKEN = "Authorization";
	/**用户token效期(秒)*/
	public final static Long USER_TOKEN_SECONDS = 30L * 60;
	/**JWT 签名*/
	public final static String JWT_SIGN = "youaresohot";
	public enum OrderStatus{
		NOTPAY(1,"未支付，建议继续查询订单状态，超过交易时间还未支付可关闭订单"),
		USERPAING(2,"支付中，提醒用户输入支付密码，继续查询订单状态"),
		SUCCESS(3,"支付成功"),
		CLOSED(4,"订单关闭"),
		REVOKED(5,"支付撤销"),
		MANUAL(99,"订单需要人工兜底"),
		
		SYSTEMERROR(-1,"系统异常，请撤销订单后重新支付"),
		ORDERNOTEXIST(-2,"订单不存在，请核对参数后重新查询");
		
		public Integer code;
		public String msg;
		private OrderStatus(Integer code,String msg) {
			this.code = code;
			this.msg = msg;
		}
		public Map<String, String> toMap() {
			Map<String,String> map = Maps.newHashMap();
		                map.put("code", String.valueOf(code));
		                map.put("msg", msg);
		     return map;
		}
		 public static OrderStatus findAny(int code) {
	        return Arrays.stream(OrderStatus.values())
	                .filter(item -> item.code == code)
	                .findAny()
	                .orElse(null);
	    }
	}
}

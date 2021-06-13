package com.sy.investment.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.sy.investment.exceptions.ServiceException;

/** 
* @ClassName: CommonUtils 
* @Description: 
* @author albert
* @date 2018年2月2日 下午9:03:03 
*  
*/
public class CommonUtils {
	/**
	 * 返回第一个不为null且>0的数
	 * @param params
	 * @return
	 */
	public static Double firstNotNull(Double... params) {
		Assert.notEmpty(params, "参数不可以为空");
		for(Double item : params) {
			if(item!=null && item>0) {
				return item;
			}
		}
		return null;
	}
	/**
	 * 手机号码脱敏
	 * @param phone
	 * @return
	 */
	public static String desensitization(String phone) {
		if(StringUtils.isBlank(phone) || phone.trim().length()!=11) return phone;
		return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}
	/**
	 * 判断是微信支付还是支付宝支付
	 * @param authCode
	 * @return 1-支付宝 2-微信支付
	 */
	public static Integer payType(String authCode) {
		Assert.hasText(authCode, "支付码不可为空");
		Assert.isTrue(authCode.length()>2, "支付码长度有误");
		String[] alipayPrefix = new String[]{"25","26","27","28","29","30"};
		String[] wxpayPrefix = new String[] {"10","11","12","13","14","15"};
		
		if(StringUtils.startsWithAny(authCode, alipayPrefix)) {
			return 1;
		}else if(StringUtils.startsWithAny(authCode, wxpayPrefix)) {
			return 2;
		}
		throw new UnsupportedOperationException("不合法的条码信息");
	}
}

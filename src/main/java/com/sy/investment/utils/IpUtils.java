/**
 * 
 */
package com.sy.investment.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.sy.investment.exceptions.UnAuthorizedException;
/** 
* @ClassName: IpUtils 
* @Description: 
* @author albert
* @date 2018年9月14日 上午9:11:31 
*  
*/
public class IpUtils {

	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for"); 
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            System.out.println("Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
            System.out.println("X-Real-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            System.out.println("getRemoteAddr ip: " + ip);
        } 
        System.out.println("获取客户端ip: " + ip);
        return ip;  
    }


	private static String getFirst(List<String> list) {
		if(list==null || list.size()==0) return null;
		return list.get(0);
	}
	/**
	 * IP地址正则校验
	 * @param ipAddress
	 * @return
	 */
	public static boolean isIp(String ipAddress) {
		if (ipAddress.length() < 7 || ipAddress.length() > 15) {
			return false;
		}
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(ipAddress);
		boolean flag = mat.find();
		return flag;
		}
	/**
	 * 检查IP地址是否在白名单
	 * @param whiteList
	 * @param ip
	 */
	public static void inWhitelist(List<String> whiteList,String ip) {
		Assert.notNull(ip, "ip地址不可为空");
		Assert.isTrue(isIp(ip), "不是有效的IP地址");
		Assert.notEmpty(whiteList,"白名单不可为空");
		Assert.noNullElements(whiteList.toArray(), "白名单不可包含null值");
		for(String item : whiteList) {
			if(StringUtils.contains(item, "/")) {
				SubnetUtils sub = new SubnetUtils(item);
				sub.setInclusiveHostCount(true);//包含网络地址和广播地址
				if(sub.getInfo().isInRange(ip)) return;
			}else {
				if(item.equals(ip)) return;
			}
		}
		throw new UnAuthorizedException("IP未通过白名单验证");
	}
	public static void main(String[] args) {
		List<String> list = Lists.asList("183.3.234.0/25", new String[]{});
		inWhitelist(list, "183.3.234.48");
	}
}

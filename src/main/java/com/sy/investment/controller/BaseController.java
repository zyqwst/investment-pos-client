/**
 * 
 */
package com.sy.investment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.sy.investment.service.CommonService;

/** 
* @ClassName: BaseController 
* @Description: 
* @author albert
* @date 2018年7月26日 上午9:39:55 
*  
*/
public abstract class BaseController {
	public Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	public HttpServletRequest request;
	@Autowired
	protected Environment env;
	@Resource
	public CommonService commonService;
	
	public final static String DEV 	=	"dev",
							   PROD	=	"prod",
							   TEST	=	"test";
	/**
	 * 当前环境
	 * @return
	 */
	public String getProfile() {
		return env.getActiveProfiles()[0];
	}
	public Boolean isDev() {
		return DEV.equals(getProfile());
	}
	public String getIpAddress() {  
		 String Xip = request.getHeader("X-Real-IP");
	        String XFor = request.getHeader("X-Forwarded-For");
	        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
	            //多次反向代理后会有多个ip值，第一个ip才是真实ip
	            int index = XFor.indexOf(",");
	            if(index != -1){
	                return XFor.substring(0,index);
	            }else{
	                return XFor;
	            }
	        }
	        XFor = Xip;
	        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
	            return XFor;
	        }
	        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
	            XFor = request.getHeader("Proxy-Client-IP");
	        }
	        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
	            XFor = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
	            XFor = request.getHeader("HTTP_CLIENT_IP");
	        }
	        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
	            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
	        }
	        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
	            XFor = request.getRemoteAddr();
	        }
	        return XFor;
  } 
}

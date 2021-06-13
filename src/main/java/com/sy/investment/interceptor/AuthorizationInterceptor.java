package com.sy.investment.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sy.investment.controller.BaseController;
import com.sy.investment.domain.view.UserView;
import com.sy.investment.domain.vo.UserVo;
import com.sy.investment.exceptions.UnAuthorizedException;
import com.sy.investment.service.CommonService;
import com.sy.investment.token.TokenManager;
import com.sy.investment.utils.Constants;
import com.sy.investment.utils.RedisUtil;
import com.sy.investment.utils.UserThreadLocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	@Resource
	Environment env;
	
	@Resource
	private RedisUtil redisUtil;
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private TokenManager tokenManager;
	
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
//        测试环境直接返回
        if(request.getRequestURI().startsWith("/") 
        		&& StringUtils.contains(env.getActiveProfiles()[0], BaseController.DEV)) {
        		
        		UserView user = commonService.findEntityById(UserView.class, 12l);
        		log.debug("当前用户：{}",user);
        		UserThreadLocal.set(UserVo.buildByUser(user));
        		return true;
        }
//        if(white(request)) {
//        		return true;
//        }
        if(!request.getRequestURI().startsWith("/job/order")) {
        		
        		UserThreadLocal.set(null);
        	
        		String token = request.getHeader(Constants.USER_TOKEN);
        		
        		if(StringUtils.isBlank(token)) {
        			throw new UnAuthorizedException("无访问权限");
        		}
        		
        		Object obj = redisUtil.get(token);
        		
        		if(null==obj) throw new UnAuthorizedException("无访问权限");
        		
        		tokenManager.refreshToken(token);
        		
        		UserThreadLocal.set((UserVo) obj);
        }
        return true;
    }
}

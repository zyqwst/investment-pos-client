/**
 * 
 */
package com.sy.investment.token.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import com.sy.investment.domain.vo.UserVo;
import com.sy.investment.token.TokenManager;
import com.sy.investment.utils.Constants;
import com.sy.investment.utils.RedisUtil;

/** 
* @ClassName: RedisTokenManager 
* @Description: 
* @author albert
* @date 2018年7月26日 下午1:54:31 
*/
@Component
public class RedisTokenManager implements TokenManager {
	
	@Autowired
    private RedisUtil redisUtil;
	
	@Override
	public String getToken(UserVo userInfo) {
        String token = DigestUtils.md5DigestAsHex((String.join(".", userInfo.getCode(),System.currentTimeMillis()+"")).getBytes()).toUpperCase();
        		//JwtUtil.createJWT(String.valueOf(userInfo.getId()),JsonUtils.toJson(userInfo) , -1, Constants.JWT_SIGN);
        redisUtil.set(token,userInfo,Constants.USER_TOKEN_SECONDS);
        return token;
	}

	@Override
	public void refreshToken(String token) {
		Assert.notNull(redisUtil.get(token), "redis中未找到key对应的数据");
        redisUtil.expire(token, Constants.USER_TOKEN_SECONDS);
	}
	@Override
	public void logout(String token) {
		redisUtil.del(token);
	}

	@Override
	public UserVo getUserInfoByToken(String token) {
		Assert.notNull(token,"参数不能为空");
		return (UserVo) redisUtil.get(token);
	}

}

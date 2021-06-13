package com.sy.investment.token;

import com.sy.investment.domain.vo.UserVo;
/**
* @ClassName: TokenManager 
* @Description: Token管理器
* @author albert
* @date 2018年7月26日 下午1:53:50 
*
 */
public interface TokenManager {

    /**
     * 创建token
     * @param userInfo
     * @return
     */
    String getToken(UserVo userInfo);

    /**
     * 刷新Token过期时间
     * @param token
     */
    void refreshToken(String token);

    /**
     * 用户退出登陆
     * @param token
     */
    void logout(String token);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    UserVo getUserInfoByToken(String token);

}
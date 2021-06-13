package com.sy.investment.utils;

import com.sy.investment.domain.vo.UserVo;

public class UserThreadLocal {
	private static ThreadLocal<UserVo> USER_LOCAL = new ThreadLocal<UserVo>();

	public static void set(UserVo user) {
		USER_LOCAL.set(user);
	}

	public static UserVo get() {
		return USER_LOCAL.get();
	}
}
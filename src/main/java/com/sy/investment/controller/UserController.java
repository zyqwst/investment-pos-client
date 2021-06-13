/**
 * 
 */
package com.sy.investment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.table.Client;
import com.sy.investment.domain.table.Store;
import com.sy.investment.domain.table.User;
import com.sy.investment.domain.view.UserMenuView;
import com.sy.investment.domain.view.UserView;
import com.sy.investment.domain.vo.LoginVo;
import com.sy.investment.domain.vo.StoreRegistVo;
import com.sy.investment.domain.vo.SwitchCaisherVo;
import com.sy.investment.domain.vo.UserVo;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.service.CommonService;
import com.sy.investment.token.TokenManager;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
* @ClassName: UserController 
* @Description: 
* @author albert
* @date 2018年8月22日 下午4:33:06 
*  
*/
@Api(tags="员工权限相关API")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Resource CommonService commonService;
	@Resource TokenManager tokenManager;
	
	@ApiOperation(value="员工登录")
	@PostMapping("/login")
	public RestEntity login(
			@ApiParam(name = "loginVo", value = "登录信息", required =true)
			@RequestBody @Validated LoginVo loginVo) {
		long cnt = commonService.count(Client.class, " where clientId=:clientId and result=1 ", ParamValue.build().add("clientId",loginVo.getComputerId() ));
		//if(!isDev() && cnt==0) throw new ServiceException("POS机未注册，请注册后使用");
		UserView user = commonService.findEntity(UserView.class, " where code=:code and password=:password and status=:status ", 
				ParamValue.build().add("code", loginVo.getUsername()).add("password", loginVo.getPassword()).add("status", 1));
		if(user==null) throw new ServiceException("用户名或密码错误");
		cnt = commonService.count(Client.class, " where storeId=:storeId and clientId=:clientId and result=1 ", ParamValue.build().add("storeId", user.getStoreId()).add("clientId",loginVo.getComputerId() ));
		//if(!isDev() && cnt==0) throw new ServiceException("登录信息与注册门店不符，不允许登录");
		String token = tokenManager.getToken(UserVo.buildByUser(user));
		return RestEntity.success(token);
	}
	@ApiOperation(value="切换收银员")
	@PostMapping("/switch-cashier")
	public RestEntity switchCashier(
			@ApiParam(name = "switchCaisherVo", value = "收银员信息", required =true)
			@RequestBody @Validated SwitchCaisherVo vo) {
		User user = commonService.findEntity(User.class, " where id=:id and password=:password and status=:status ", 
				ParamValue.build().add("id", vo.getId()).add("password", vo.getPassword()).add("status", 1));
		if(user==null) throw new ServiceException("收银员不存在或密码错误");
		return RestEntity.success();
	}
	@ApiOperation(value="员工信息")
	@GetMapping("/info")
	public RestEntity userInfo() {
		List<UserView> users = commonService.findAll(UserView.class, " where storeId=:storeId and status=1 ", ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()));
		List<UserVo> list = Lists.newArrayList();
		users.forEach(item -> list.add(UserVo.buildByUser(item)));
		Map<String, Object> map = ImmutableMap.<String, Object>builder()
				.put("saleMans", list)
				.put("curSaleMan",UserThreadLocal.get())
				.build();
		return RestEntity.success(map);
	}
	@ApiOperation(value="查询门店所有收银员")
	@GetMapping("/all-cashier")
	public RestEntity allCashier() {
		List<User> list = commonService.findAll(User.class, " where storeId=:storeId and status=1 ", ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()));
		return RestEntity.success(list);
	}
	@ApiOperation(value="员工菜单")
	@GetMapping("/menu")
	public RestEntity menus() {
		UserVo u = UserThreadLocal.get();
		List<UserMenuView> list = commonService.findAll(UserMenuView.class, " where type=:type and userId=:userId and uiName is not null ", 
				ParamValue.build().add("type",5).add("userId", u.getId()));
		List<Map<String,Object>> menus = new ArrayList<>();
		list.forEach(item ->{
			Map<String, Object> map = ImmutableMap.<String, Object>builder()
			.put("orderNo"	, item.getOrderNum())
			.put("code"		, item.getUiName())
			.put("name"		, item.getName())
			.put("icon"		, Optional.ofNullable(item.getIcon()).orElse(""))
			.build();
			menus.add(map);
		});
		return RestEntity.success(menus);
	}
	@ApiOperation(value="门店注册")
	@PostMapping("/store-regist")
	public RestEntity storeRegist(@Validated @RequestBody StoreRegistVo vo) {
		commonService.save(Client.builder()
				.companyId(1l)
				.clientId(vo.getComputerId())
				.gmt_create(new Date())
				.remark(vo.getIntro())
				.storeId(vo.getStoreId())
				.type(2)
				.result(0)
				.build());
		return RestEntity.success();
	}
	@ApiOperation(value="查找门店")
	@GetMapping("/store")
	public RestEntity queryStore(@RequestParam(required=true) @NotNull String code) {
		List<Store> list = commonService.findAll(Store.class, " where code=:code and status=1 ", ParamValue.build().add("code", code));
		Assert.isTrue(list.size()==1, "未找到当前门店，请确认门店代码输入是否有误");
		return RestEntity.success(list.get(0));
	}
}

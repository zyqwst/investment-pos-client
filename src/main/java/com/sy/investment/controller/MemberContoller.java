/**
 * 
 */
package com.sy.investment.controller;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.table.Member;
import com.sy.investment.domain.table.MemberAddress;
import com.sy.investment.domain.view.MemberAmountView;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.utils.CommonUtils;
import com.sy.investment.utils.Constants;
import com.sy.investment.utils.JwtUtil;
import com.sy.investment.utils.ParamValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName: MemberContoller 
* @Description: 
* @author albert
* @date 2018年8月22日 下午3:43:12 
*  
*/
@RestController
@RequestMapping("/member")
@Api(tags="会员API")
@Validated
public class MemberContoller extends BaseController {
	
	@GetMapping("")
	@ApiOperation(value="根据手机号/会员卡/姓名查询会员")
	@ApiImplicitParams({
		@ApiImplicitParam(name="keywords",value="参数",dataType="String",required=true,paramType="query")
	})
	public RestEntity member(@RequestParam(required=true) @NotNull String keywords) {
		List<MemberAmountView> list = commonService.findAll(MemberAmountView.class, " where status=1 and code like :code or phone like :phone or name like :name  or qrcode=:qrcode", 
				ParamValue.build().addLike("code", keywords).addLike("phone", keywords).addLike("name", keywords).add("qrcode", keywords));
		Member m = commonService.findEntity(Member.class, " where qrcode=:qrcode and status=1 ", ParamValue.build().add("qrcode", keywords));
		list.forEach(item -> {
			item.setOpenId("");
			item.setIsScan(0);
			item.setPhone(CommonUtils.desensitization(item.getPhone()));
			if(m!=null) {
				try {
					JwtUtil.parseJWT(keywords, Constants.JWT_SIGN);
					item.setIsScan(1);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("二维码过期或无效，提醒会员刷新二维码");
				}
			}
		});
		return RestEntity.success(list);
	}
	@GetMapping("id")
	@ApiOperation(value="根据ID 查找会员")
	@ApiImplicitParams({
		@ApiImplicitParam(name="keywords",value="参数",dataType="String",required=true,paramType="query")
	})
	public RestEntity memberById(Long id) {
		MemberAmountView member = commonService.findEntityById(MemberAmountView.class,  id);
		member.setOpenId("");
		member.setIsScan(0);
		return RestEntity.success(member);
	}
	@GetMapping("qrcode")
	@ApiOperation(value="根据会员二维码查找会员")
	@ApiImplicitParams({
		@ApiImplicitParam(name="qrcode",value="会员码",dataType="String",required=true,paramType="query")
	})
	public RestEntity memberByQrcode(String qrcode) {
		MemberAmountView member = commonService.findEntity(MemberAmountView.class,  " where qrcode=:qrcode and status=1 ",ParamValue.build().add("qrcode", qrcode));
		Assert.notNull(member,"会员码未找到会员信息，或会员被冻结");
		try {
			if(!isDev())JwtUtil.parseJWT(qrcode, Constants.JWT_SIGN);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("会员码过期或无效，提醒会员刷新二维码");
		}
		member.setOpenId("");
		member.setIsScan(1);
		return RestEntity.success(member);
	}
	@ApiOperation(value = "送货地址列表", notes = "/member/address")
	@ApiImplicitParams({
		@ApiImplicitParam(name="memberId",value="获取会员送货地址",dataType="Long",required=true,paramType="query")
	})
	@GetMapping("/address")
	public RestEntity list(@RequestParam(required=true) Long memberId) {
		List<MemberAddress> list=this.commonService.findAll(MemberAddress.class, " where memberId=:memberId and status=1 ", ParamValue.build().add("memberId",memberId));
		return RestEntity.success(list);
	}
}

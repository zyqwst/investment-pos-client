/**
 * 
 */
package com.sy.investment.domain.vo;

import org.hibernate.validator.constraints.Length;

import com.sy.investment.domain.EntityBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: LoginVo 
* @Description: 
* @author albert
* @date 2018年8月22日 下午5:00:47 
*  
*/
@Data
@ApiModel("登录请求数据")
public class LoginVo implements EntityBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5571783600078718455L;
	@ApiModelProperty(value="用户名",required = true)
	@Length(max=32,min=2)
	private String username;
	@ApiModelProperty(value="密码",required = false)
	@Length(max=32,min=1)
	private String password;
	@ApiModelProperty(value="客户机ID",required = true)
	@Length(max=32,min=1)
	private String computerId;
}

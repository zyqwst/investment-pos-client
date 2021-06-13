/**
 * 
 */
package com.sy.investment.domain.vo;

import javax.validation.constraints.NotNull;

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
@ApiModel("切换收银员请求数据")
public class SwitchCaisherVo implements EntityBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5571783600078718455L;
	@ApiModelProperty(value="ID",required = true)
	@NotNull
	private Long id;
	@ApiModelProperty(value="密码",required = false)
	@Length(max=32,min=1)
	private String password;
}

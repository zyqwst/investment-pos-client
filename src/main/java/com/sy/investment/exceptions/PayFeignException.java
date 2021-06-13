/**
 * 
 */
package com.sy.investment.exceptions;

import com.google.common.base.Joiner;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 
* @ClassName: DaoException 
* @Description: dao层异常
* @author albert
* @date 2018年2月2日 上午9:43:07 
*  
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class PayFeignException extends RuntimeException {

	private static final long serialVersionUID = -3860176566418183180L;

	  /**
	   * 自定义错误讯息.
	   */
	  private String customErrorMsg;
	  /**
	   * 返回状态码.
	   */
	  private String code;
	  /**
	   * 返回信息.
	   */
	  private String msg;


	  public PayFeignException(String customErrorMsg) {
	    super(customErrorMsg);
	    this.customErrorMsg = customErrorMsg;
	  }

	  public PayFeignException(String customErrorMsg, Throwable tr) {
	    super(customErrorMsg, tr);
	    this.customErrorMsg = customErrorMsg;
	  }

	  private PayFeignException(Builder builder) {
	    super(builder.buildErrorMsg());
	    code = builder.code;
	    msg = builder.msg;
	  }

	  public static PayFeignException from(String code,String msg) {
	    return PayFeignException.newBuilder()
	      .code(code)
	      .msg(msg)
	      .build();
	  }

	  public static Builder newBuilder() {
	    return new Builder();
	  }

	  public static final class Builder {
	    private String code;
	    private String msg;

	    private Builder() {
	    }

	    public Builder code(String code) {
	      this.code = code;
	      return this;
	    }

	    public Builder msg(String msg) {
	      this.msg = msg;
	      return this;
	    }


	    public PayFeignException build() {
	      return new PayFeignException(this);
	    }

	    public String buildErrorMsg() {
	      return Joiner.on("，").skipNulls().join(
	        code == null ? null : String.format("返回代码：[%s]", code),
	        msg == null ? null : String.format("返回信息：[%s]", msg)
	      );
	    }
	  }
}

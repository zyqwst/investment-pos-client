/**
 * 
 */
package com.sy.investment.exceptions;

import java.util.Optional;

/** 
* @ClassName: ServiceException 
* @Description: 业务层异常
* @author albert
* @date 2018年2月2日 上午9:43:07 
*  
*/
public class ServiceException extends DaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6174745763250180374L;
	private String            errCode;
    private String            errMsg;
    private String			  advise;
	public ServiceException() {
		super();
	}
	public ServiceException(String message) {
        super(message);
    }
	public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
	public ServiceException(Throwable cause) {
	        super(cause);
	}
	protected ServiceException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	 public ServiceException(String errCode, String errMsg,String advise) {
	        super("状态码："+Optional.ofNullable(errCode).orElse("") + "；状态信息：" + Optional.ofNullable(errMsg).orElse("")+"；"+Optional.ofNullable(advise).orElse(""));
	        this.errCode = errCode;
	        this.errMsg = errMsg;
	        this.advise = advise;
	    }

	    public String getErrCode() {
	        return this.errCode;
	    }

	    public String getErrMsg() {
	        return this.errMsg;
	    }

		public String getAdvise() {
			return advise;
		}
}

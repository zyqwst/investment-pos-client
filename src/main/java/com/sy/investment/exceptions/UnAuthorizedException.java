/**
 * 
 */
package com.sy.investment.exceptions;

/** 
* @ClassName: DaoException 
* @Description: 鉴权异常
* @author albert
* @date 2018年2月2日 上午9:43:07 
*  
*/
public class UnAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -3860176566418183180L;
	/**
	 * 
	 */
	public UnAuthorizedException() {
		super();
	}
	public UnAuthorizedException(String message) {
        super(message);
    }
	public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
	public UnAuthorizedException(Throwable cause) {
	        super(cause);
	}
	protected UnAuthorizedException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

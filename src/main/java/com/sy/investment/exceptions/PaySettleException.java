/**
 * 
 */
package com.sy.investment.exceptions;

/** 
* @ClassName: DaoException 
* @Description: dao层异常
* @author albert
* @date 2018年2月2日 上午9:43:07 
*  
*/
public class PaySettleException extends RuntimeException {

	private static final long serialVersionUID = -3860176566418183180L;
	/**
	 * 
	 */
	public PaySettleException() {
		super();
	}
	public PaySettleException(String message) {
        super(message);
    }
	public PaySettleException(String message, Throwable cause) {
        super(message, cause);
    }
	public PaySettleException(Throwable cause) {
	        super(cause);
	}
	protected PaySettleException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

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
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -3860176566418183180L;
	/**
	 * 
	 */
	public DaoException() {
		super();
	}
	public DaoException(String message) {
        super(message);
    }
	public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
	public DaoException(Throwable cause) {
	        super(cause);
	}
	protected DaoException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

/**
 * 
 */
package com.sy.investment.exceptions;

/** 
* @ClassName: RedisException 
* @Description: 
* @author albert
* @date 2018年8月22日 下午2:30:12 
*  
*/
public class RedisException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4379981569052540435L;
	public RedisException() {
		super();
	}
	public RedisException(String message) {
        super(message);
    }
	public RedisException(String message, Throwable cause) {
        super(message, cause);
    }
	public RedisException(Throwable cause) {
	        super(cause);
	}
	protected RedisException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

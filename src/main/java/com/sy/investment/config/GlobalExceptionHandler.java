package com.sy.investment.config;

import java.util.Enumeration;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.exceptions.UnAuthorizedException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
 public class GlobalExceptionHandler {
	 @Resource
	 HttpServletRequest request;
	 @Resource
	 HttpServletResponse response;
	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(MissingServletRequestParameterException.class)
	  public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
	    log.error("缺少请求参数", e);
	    return responseCustomized(HttpStatus.BAD_REQUEST, "缺少请求参数");
	  }

	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(HttpMessageNotReadableException.class)
	  public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
	    log.error("参数解析失败", e);
	    return responseCustomized(HttpStatus.BAD_REQUEST, "参数解析失败");
	  }

	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(MethodArgumentNotValidException.class)
	  public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
	    log.error("参数验证失败", e.getMessage());
	    BindingResult result = e.getBindingResult();
	    FieldError error = result.getFieldError();
	    String field = error.getField();
	    String code = error.getDefaultMessage();
	    String message = String.format("%s:%s", field, code);
	    return responseCustomized(HttpStatus.BAD_REQUEST, message);
	  }

	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(BindException.class)
	  public Object handleBindException(BindException e) {
	    log.error("参数绑定失败", e);
	    BindingResult result = e.getBindingResult();
	    FieldError error = result.getFieldError();
	    String field = error.getField();
	    String code = error.getDefaultMessage();
	    String message = String.format("%s:%s", field, code);
	    return responseCustomized(HttpStatus.BAD_REQUEST, message);
	  }

	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(ConstraintViolationException.class)
	  public Object handleServiceException(ConstraintViolationException e) {
	    log.error("参数验证失败", e);
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    ConstraintViolation<?> violation = violations.iterator().next();
	    String message = violation.getMessage();
	    return responseCustomized(HttpStatus.BAD_REQUEST, message);
	  }

	  /**
	   * 400 - Bad Request
	   */
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(ValidationException.class)
	  public Object handleValidationException(ValidationException e) {
	    log.error("参数验证失败", e);
	    return responseCustomized(HttpStatus.BAD_REQUEST, "validation_exception");
	  }

	  /**
	   * 405 - Method Not Allowed
	   */
	  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	  public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
	    log.error("不支持当前请求方法", e);
	    return responseCustomized(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	  }

	  /**
	   * 415 - Unsupported Media Type
	   */
	  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	  public Object handleHttpMediaTypeNotSupportedException(Exception e) {
	    log.error("不支持当前媒体类型", e);
	    return responseCustomized(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型");
	  }


	  /**
	   * 操作数据库出现异常:名称重复，外键关联
	   */
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(DataIntegrityViolationException.class)
	  public Object handleException(DataIntegrityViolationException e) {
	    log.error("操作数据库出现异常:", e);
	    return responseCustomized(HttpStatus.INTERNAL_SERVER_ERROR, "操作数据库出现异常：字段重复、有外键关联等");
	  }
	  
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(ServiceException.class)
	  public Object handleServiceException(ServiceException e) {
	    log.error("业务层异常:", e);
	    return responseCustomized(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	  }
	  @ResponseStatus(HttpStatus.UNAUTHORIZED)
	  @ExceptionHandler(UnAuthorizedException.class)
	  public Object handleServiceException(UnAuthorizedException e) {
	    log.error("鉴权异常:", e);
	    return responseCustomized(HttpStatus.UNAUTHORIZED, e.getMessage());
	  }
	  /**
	   * 500 - Internal Server Error
	   */
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(IllegalArgumentException.class)
	  public Object handleServiceException(IllegalArgumentException e) {
	    log.error("参数异常:", e);
	    return responseCustomized(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	  }
	  /**
	   * 500 - Internal Server Error
	   */
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(Exception.class)
	  public Object handleException(Exception e) {
	    log.error("系统异常", e);
	    return responseCustomized(HttpStatus.INTERNAL_SERVER_ERROR,"系统异常："+ e.getMessage());
	  }
	  
	  /**
	   * 根据请求确定响应方式
	   * @param uri
	   * @param httpStatus
	   * @param msg
	   * @return
	   */
	  private Object responseCustomized(HttpStatus httpStatus,String msg) {
		  Enumeration<?> enu=request.getHeaderNames();
		   while(enu.hasMoreElements()){
		       String headerName=(String)enu.nextElement();
		       String headerValue=request.getHeader(headerName);
		       log.info("{}===={}",headerName,headerValue);
		   }
		  response.setStatus(httpStatus.value());
		  response.setContentType("application/json;charset=utf-8");
		  return RestEntity.failed(msg, String.valueOf(httpStatus.value()));
	  }
	  
 }
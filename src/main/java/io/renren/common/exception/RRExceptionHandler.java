/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.exception;

import io.renren.common.constant.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@RestControllerAdvice
public class RRExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public CommonResult<?> handleRRException(RRException e){
		return CommonResult.fail(e.getCode(), e.getMsg());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public CommonResult<?> handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return CommonResult.fail(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public CommonResult<?> handleDuplicateKeyException(DuplicateKeyException e){
		log.error(e.getMessage(), e);
		return CommonResult.fail("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public CommonResult<?> handleAuthorizationException(AuthorizationException e){
		log.error(e.getMessage(), e);
		return CommonResult.fail("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public CommonResult<?> handleException(Exception e){
		log.error(e.getMessage(), e);
		return CommonResult.fail();
	}
}

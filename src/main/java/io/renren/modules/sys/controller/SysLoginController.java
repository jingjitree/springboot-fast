/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.renren.common.constant.CommonResult;
import io.renren.common.constant.JwtConstant;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.form.SysLoginForm;
import io.renren.modules.sys.oauth2.TokenGenerator;
import io.renren.modules.sys.service.SysCaptchaService;
import io.renren.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@Api(tags = "登录授权控制器")
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysCaptchaService sysCaptchaService;


	@Autowired
	private JwtConstant jwtConstant;
	@Autowired
	private RedisUtils redisUtils;


	private final Gson gson = new GsonBuilder().create();
	/**
	 * 验证码
	 */
	@ApiOperation(value = "获取图片验证码")
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@ApiOperation(value = "登录接口")
	@PostMapping("/sys/login")
	public CommonResult<Map<String, Object>> login(@RequestBody SysLoginForm form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return CommonResult.fail("验证码不正确");
		}

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return CommonResult.fail("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return CommonResult.fail("账号已被锁定,请联系管理员");
		}

		//存入redis
		String token = TokenGenerator.generateValue();
		String tokenKey = String.format(jwtConstant.getUserTokenKey(), token);
		log.info("用户tokenKey: {},token：{}", tokenKey, token);
		redisUtils.setValueTimeout(tokenKey, gson.toJson(user), jwtConstant.getExpire(), TimeUnit.SECONDS);

		Map<String, Object> result = MapUtil.builder(new HashMap<String, Object>())
				.put("token", token)
				.put("expire", jwtConstant.getExpire())
				.build();
		return CommonResult.success(result);
	}


	/**
	 * 退出
	 */
	@ApiOperation(value = "退出登录")
	@PostMapping("/sys/logout")
	public CommonResult<?> logout(HttpServletRequest request) {
		String token = request.getHeader(jwtConstant.getHeader());
		if (StrUtil.isNotBlank(token)){
			String tokenKey = String.format(jwtConstant.getUserTokenKey(), token);
			log.info("退出的tokenKey：" + tokenKey);
			if (redisUtils.hasKey(tokenKey))
				redisUtils.delKey(tokenKey);
		}
		return CommonResult.success();
	}
	
}

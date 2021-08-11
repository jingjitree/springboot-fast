/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.annotation.SysLog;
import io.renren.common.constant.CommonPageResult;
import io.renren.common.constant.CommonResult;
import io.renren.common.constant.Constant;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.vo.UserListVo;
import io.renren.modules.sys.form.PasswordForm;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:user:list")
	public CommonPageResult<List<SysUserEntity>> list(@RequestBody UserListVo vo){
		SysUserEntity user = getUser();
		Integer userId = user.getId();
		//只有超级管理员，才能查看所有管理员列表
		if(userId != Constant.SUPER_ADMIN){
			vo.setCreateUserId(userId);
		}
		IPage<SysUserEntity> pageDatas = sysUserService.queryPage(vo);

		return CommonPageResult.success(pageDatas.getRecords(), pageDatas.getTotal(), pageDatas.getPages());
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public CommonResult<Map<String, Object>> info(){
		SysUserEntity user = getUser();
		Map<String, Object> result = MapUtil.of("user", user);
		return CommonResult.success(result);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public CommonResult<?> password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		SysUserEntity user = getUser();
		//sha256加密
		String password = new Sha256Hash(form.getPassword(), user.getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), user.getSalt()).toHex();
				
		//更新密码
		boolean flag = sysUserService.updatePassword(user.getId(), password, newPassword);
		if(!flag){
			return CommonResult.fail("原密码不正确");
		}
		
		return CommonResult.success();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public CommonResult<Map<String, Object>> info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Integer> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return CommonResult.success(MapUtil.of("user", user));
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public CommonResult<?> save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.saveUser(user);
		
		return CommonResult.success();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public CommonResult<?> update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return CommonResult.success();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public CommonResult<?> delete(@RequestBody Integer[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return CommonResult.fail("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return CommonResult.fail("当前用户不能删除");
		}
		
		sysUserService.removeByIds(Arrays.asList(userIds));
		
		return CommonResult.success();
	}
}

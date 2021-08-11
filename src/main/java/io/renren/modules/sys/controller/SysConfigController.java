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
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.entity.vo.ConfigListVo;
import io.renren.modules.sys.service.SysConfigService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@ApiOperation(value = "配置列表查询")
	@PostMapping("/list")
	@RequiresPermissions("sys:config:list")
	public CommonPageResult<List<SysConfigEntity>> list(@RequestBody ConfigListVo vo){
		IPage<SysConfigEntity> pageDatas = sysConfigService.queryPage(vo);

		return CommonPageResult.success(pageDatas.getRecords(), pageDatas.getTotal(), pageDatas.getPages());
	}
	
	
	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public CommonResult<Map<String, Object>> info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.getById(id);
		return CommonResult.success(MapUtil.of("config", config));
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public CommonResult<?> save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);
		return CommonResult.success();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public CommonResult<?> update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.updateById(config);
		return CommonResult.success();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public CommonResult<?> delete(@RequestBody Integer[] ids){
		log.info("删除配置ids：{}", ids);
		sysConfigService.removeByIds(Arrays.asList(ids));
		
		return CommonResult.success();
	}

}

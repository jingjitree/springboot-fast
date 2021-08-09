/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.constant.CommonPageResult;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.entity.vo.LogListVo;
import io.renren.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@PostMapping("/list")
	@RequiresPermissions("sys:log:list")
	public CommonPageResult<List<SysLogEntity>> list(@RequestBody LogListVo vo){
		IPage<SysLogEntity> pageDatas = sysLogService.queryPage(vo);

		return CommonPageResult.success(pageDatas.getRecords(), pageDatas.getTotal(), pageDatas.getPages());
	}
	
}

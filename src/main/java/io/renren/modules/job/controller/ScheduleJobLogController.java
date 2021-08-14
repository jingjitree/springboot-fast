/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.constant.CommonPageResult;
import io.renren.common.constant.CommonResult;
import io.renren.modules.job.entity.ScheduleJobLogEntity;
import io.renren.modules.job.entity.vo.ScheduleJobLogListVo;
import io.renren.modules.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public CommonPageResult<List<ScheduleJobLogEntity>> list(@RequestBody ScheduleJobLogListVo vo){
		IPage<ScheduleJobLogEntity> pageDatas = scheduleJobLogService.queryPage(vo);
		return CommonPageResult.success(pageDatas.getRecords(), pageDatas.getTotal(), pageDatas.getPages());
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public CommonResult<Map<String, Object>> info(@PathVariable("logId") Integer logId){
		ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
		
		return CommonResult.success(MapUtil.of("log", log));
	}
}

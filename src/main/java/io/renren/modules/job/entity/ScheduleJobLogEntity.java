/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.BaseEntity;
import lombok.Data;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("schedule_job_log")
public class ScheduleJobLogEntity extends BaseEntity<ScheduleJobLogEntity> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务id
	 */
	private Integer jobId;
	
	/**
	 * spring bean名称
	 */
	private String beanName;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 任务状态    0：成功    1：失败
	 */
	private Integer status;
	
	/**
	 * 失败信息
	 */
	private String error;
	
	/**
	 * 耗时(单位：毫秒)
	 */
	private Integer times;
	
}

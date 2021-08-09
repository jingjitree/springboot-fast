/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.entity.vo.ScheduleJobVo;

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

	IPage<ScheduleJobEntity> queryPage(ScheduleJobVo vo);

	/**
	 * 保存定时任务
	 */
	void saveJob(ScheduleJobEntity scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Integer[] jobIds);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Integer[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Integer[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Integer[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Integer[] jobIds);
}

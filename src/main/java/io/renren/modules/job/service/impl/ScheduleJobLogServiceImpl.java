/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.job.dao.ScheduleJobLogDao;
import io.renren.modules.job.entity.ScheduleJobLogEntity;
import io.renren.modules.job.entity.vo.ScheduleJobLogListVo;
import io.renren.modules.job.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public IPage<ScheduleJobLogEntity> queryPage(ScheduleJobLogListVo vo) {
		String jobId = vo.getJobId();
		Page<ScheduleJobLogEntity> pageParam = new Page<>(vo.getPage(), vo.getPageSize());
		return page(pageParam,
			new QueryWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId),"job_id", jobId)
		);
	}

}

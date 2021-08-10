/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.utils;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Stopwatch;
import io.renren.common.utils.SpringContextUtils;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.entity.ScheduleJobLogEntity;
import io.renren.modules.job.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap()
        		.get(ScheduleJobEntity.JOB_PARAM_KEY);
        //数据库保存执行记录
        ScheduleJobLogEntity jobLog = new ScheduleJobLogEntity()
				.setJobId(scheduleJob.getId())
				.setBeanName(scheduleJob.getBeanName())
				.setParams(scheduleJob.getParams());

        //任务开始时间
		Stopwatch stopwatch = Stopwatch.createStarted();
        
        try {
            //执行任务
        	log.debug("任务准备执行，任务ID：" + scheduleJob.getId());

			Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
			Method method = target.getClass().getDeclaredMethod("run", String.class);
			method.invoke(target, scheduleJob.getParams());

			stopwatch.stop();
			long times = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			jobLog.setTimes((int)times)
					.setStatus(0);
			
			log.debug("任务执行完毕，任务ID：" + scheduleJob.getId() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			log.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
			if (stopwatch.isRunning())
				stopwatch.stop();

			jobLog.setTimes((int)stopwatch.elapsed(TimeUnit.MILLISECONDS))
					.setStatus(1)
					.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
        	jobLog.insert();
		}
    }
}

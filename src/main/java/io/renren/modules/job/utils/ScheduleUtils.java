/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.utils;

import io.renren.common.exception.RRException;
import io.renren.common.constant.Constant;
import io.renren.modules.job.entity.ScheduleJobEntity;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class ScheduleUtils {
    private final static String JOB_NAME = "TASK_";
    
    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Integer jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }
    
    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Integer jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Integer jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
        	//构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob);

            scheduler.scheduleJob(jobDetail, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()){
            	pauseJob(scheduler, scheduleJob.getId());
            }
        } catch (SchedulerException e) {
            throw new RRException("创建定时任务失败", e);
        }
    }
    
    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getId());
            
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            
            //参数
            trigger.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob);
            
            scheduler.rescheduleJob(triggerKey, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()){
            	pauseJob(scheduler, scheduleJob.getId());
            }
            
        } catch (SchedulerException e) {
            throw new RRException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
        	//参数
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob);
        	
            scheduler.triggerJob(getJobKey(scheduleJob.getId()), dataMap);
        } catch (SchedulerException e) {
            throw new RRException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("暂停定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("删除定时任务失败", e);
        }
    }
}

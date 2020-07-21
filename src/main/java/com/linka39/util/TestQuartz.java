package com.linka39.util;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class TestQuartz {
    public static void main(String[] args) throws Exception{
        //jobDataMap("iloveYou@aini.com");
//        databaseCurrentJob();
//        exceptionHandle2();
        //exceptionHandle1();
        stop();
    }
    private static void stop() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(StoppableJob.class)
                .withIdentity("exceptionJob1", "someJobGroup")
                .build();

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();

        Thread.sleep(5000);
        System.out.println("过5秒，调度停止 job");

        //key 就相当于这个Job的主键
        scheduler.interrupt(job.getKey());

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(20000);
        scheduler.shutdown(true);
    }
    private static void exceptionHandle1() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(3))
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(ExceptionJob1.class)
                .withIdentity("backupjob", "databasegroup")
                .usingJobData("database", "how2java")
                .build();

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(20000);
        scheduler.shutdown(true);
    }
    private static void exceptionHandle2() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(3))
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(ExceptionJob2.class)
                .withIdentity("backupjob", "databasegroup")
                .usingJobData("database", "how2java")
                .build();

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(20000);
        scheduler.shutdown(true);
    }
    private static void databaseCurrentJob() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .withRepeatCount(10))
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(DatabaseBackupJob.class)
                .withIdentity("backupjob", "databasegroup")
                .usingJobData("database", "how2java")
                .build();

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(20000);
        scheduler.shutdown(true);
    }

    private static void jobDataMap(String mail) throws SchedulerException, InterruptedException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .withRepeatCount(10))
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(MailJob.class)
                .withIdentity("mailjob1", "mailgroup")
                .usingJobData("email", "admin@10086.com")
                .build();

        //用JobDataMap 修改email
        job.getJobDataMap().put("email", mail);

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(20000);
        scheduler.shutdown(true);
    }
}

package com.linka39.util;

import com.linka39.util.joblistener.MailJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public class TestQuartz {
    public static void main(String[] args) throws Exception{
//        jobDataMap("iloveYou@aini.com");
//        databaseCurrentJob();
//        exceptionHandle2();
//        exceptionHandle1();
//        stop();
        try {
            assginNewJob();
        } catch (ObjectAlreadyExistsException e) {
            System.err.println("发现任务已经在数据库存在了，直接从数据库里运行:"+ e.getMessage());
            // TODO Auto-generated catch block
            resumeJobFromDatabase();
        }
    }
    private static void resumeJobFromDatabase() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        System.out.println("当前调度器的id是："+scheduler.getSchedulerInstanceId());
        scheduler.start();
        // 等待200秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(200000);
        scheduler.shutdown(true);
    }

    private static void assginNewJob() throws SchedulerException, InterruptedException {
        // 创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // 定义一个触发器
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1") // 定义名称和所属的租
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(10) // 每隔10秒执行一次
                        .withRepeatCount(10)) // 总共执行11次(第一次执行不基数)
                .build();

        // 定义一个JobDetail
        JobDetail job = newJob(MailJob.class) // 指定干活的类MailJob
                .withIdentity("mailjob1", "mailgroup") // 定义任务名称和分组
                .usingJobData("email", "admin@10086.com") // 定义属性
                .build();

        // 调度加入这个job
        scheduler.scheduleJob(job, trigger);

        // 启动
        scheduler.start();

        // 等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(20000);
        scheduler.shutdown(true);
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

 /*       Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .withRepeatCount(10))
                .build();*/
        //下一秒的5倍
        //Date startTime = DateBuilder.nextGivenSecondDate(null, 5);
        //获取在10秒后 定时运行
        Date startTime = DateBuilder.futureDate(4, DateBuilder.IntervalUnit.SECOND);
/*        SimpleTrigger trigger = (SimpleTrigger)newTrigger().withIdentity("trigger1", "group1")
                .startAt(startTime)
                //无限重复，间隔1
                .withSchedule(simpleSchedule()
                        .repeatForever()
                        .withIntervalInSeconds(1))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .withRepeatCount(3))
                .build();*/

        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").
                withSchedule(cronSchedule("0/2 * * * * ?"))
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(MailJob.class)
                .withIdentity("mailjob1", "mailgroup")
                .usingJobData("email", "admin@10086.com")
                .build();

        //增加Job监听
        MailJobListener mailJobListener = new MailJobListener();
        KeyMatcher<JobKey> keyMatcher = KeyMatcher.keyEquals(job.getKey());
        scheduler.getListenerManager().addJobListener(mailJobListener,keyMatcher);

        //用JobDataMap 修改email
        job.getJobDataMap().put("email", mail);

       /*
       //调度加入这个job
        scheduler.scheduleJob(job, trigger);*/

        // schedule it to run!
        Date ft = scheduler.scheduleJob(job, trigger);

        System.out.println("当前时间是：" + new Date().toLocaleString());
        System.out.println("使用的Cron表达式是："+trigger.getCronExpression());
     /*   System.out.printf("%s 这个任务会在 %s 准时开始运行，累计运行%d次，间隔时间是%d毫秒%n",
                job.getKey(), ft.toLocaleString(), trigger.getRepeatCount()+1, trigger.getRepeatInterval());*/
        //启动
        scheduler.start();

        //等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(20000);
        scheduler.shutdown(true);
    }
}

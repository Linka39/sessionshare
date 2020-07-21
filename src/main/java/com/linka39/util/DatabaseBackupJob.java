package com.linka39.util;

import org.quartz.*;

@DisallowConcurrentExecution
public class DatabaseBackupJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail detail = context.getJobDetail();
        String database = detail.getJobDataMap().getString("database");

        System.out.printf("给数据库 %s 备份, 耗时4秒 %n" ,database);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

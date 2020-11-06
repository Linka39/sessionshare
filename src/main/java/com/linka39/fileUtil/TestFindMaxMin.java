package com.linka39.fileUtil;

import java.io.File;

/**
 * 遍历一个文件下所有文件夹，包括子文件夹，寻找最大和最小的文件
 */
public class TestFindMaxMin {
    private static long minlen = 1000;
    private static long maxlen = 1000;
    private static File minFile;
    private static File maxFile;

    public static void main(String[] args) {
        exercise2();
    }

    public static void exercise2() {
        System.out.println("================ 寻找一文件夹下的最大，最小文件(文件夹) ==================");
        File choose = new File("C:\\DRMsoft");
        System.out.println(choose.exists());
//        traverse(choose);
        if(choose.isFile()){
            System.out.println("文件为：" + choose);
            System.out.println("长度为：" + choose.length());
        }else{
            File[] files = choose.listFiles();
            int m = files.length;
            long[] sumlen = new long[m];
            for(int i=0;i<m;++i){
                sumlen[i] = traverse2(files[i]);
            }
            for(int i=0;i<m;++i){
                if(sumlen[i]<minlen){
                    minFile = files[i];
                    minlen = sumlen[i];
                }
                if(sumlen[i]>maxlen){
                    maxFile = files[i];
                    maxlen = sumlen[i];
                }
            }
            System.out.println("长度最短文件为：" + minFile);
            System.out.println("长度为：" + minlen);
            System.out.println("长度最长文件为：" + maxFile);
            System.out.println("长度为：" + maxlen);
        }
    }

    private static long traverse2(File file) {
            if(file.isFile())
                return file.length();
            int sum = 0;
            File[] files = file.listFiles();
            if(file.exists())
                for (File a : files)
                    sum+= traverse2(a);
            return sum;
    }

    public static void exercise1() {
        System.out.println("================ 寻找一文件夹下的最大，最小文件 ==================");
        File choose = new File("f:/filetest");
        System.out.println(choose.exists());
        traverse(choose);
        System.out.println("长度最短文件为：" + minFile);
        System.out.println("长度为：" + minlen);
        System.out.println("长度最长文件为：" + maxFile);
        System.out.println("长度为：" + maxlen);
    }

    private static void traverse(File file) {
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            for (File a : files) {
                if (a.exists() && !a.isFile()) {
                    traverse(a);
                }
                if (a.isFile() && a.length() > maxlen) {
                    maxFile = a;
                    maxlen = a.length();
                }
                if (a.isFile() && a.length() < minlen) {
                    minFile = a;
                    minlen = a.length();
                }
            }
        }
    }
}

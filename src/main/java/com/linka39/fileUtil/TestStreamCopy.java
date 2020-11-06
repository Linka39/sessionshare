package com.linka39.fileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestStreamCopy {
    public  static void copyFile(String srcPath,String destPath){
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        //如果源文件不存在
        if(!srcFile.exists()){
            throw new RuntimeException("找不到源文件");
        }
        //如果目标文件不存在
        if(!destFile.exists()){
            destFile.mkdirs();
        }
        //如果源文件是文件
        if (srcFile.isFile()) {
            //如果目标文件是文件
            if (destFile.isFile()) {
                copy(srcFile.getAbsolutePath(),destFile.getAbsolutePath());
            }
            //如果目标文件是文件夹
            else if (destFile.isDirectory()) {
                File newFile = new File(destFile, srcFile.getName());
                copy(srcFile.getAbsolutePath(),newFile.getAbsolutePath());
            }
        }
        //如果源文件是文件夹
        else if (srcFile.isDirectory()) {
            //如果目标文件是文件
            if (destFile.isFile()) {
                throw new RuntimeException("源文件是文件夹，目标文件是文件，无法进行复制操作");
            }
            //如果目标文件是文件夹
            else if (destFile.isDirectory()) {
                File[] fs = srcFile.listFiles();
                for (File f : fs) {
                    File newFile = new File(destFile, f.getName());
                    //如果子级文件是文件夹，则递归
                    if (f.isDirectory()) {
                        copyFile(f.getAbsolutePath(), newFile.getAbsolutePath());
                    }
                    //如果子级文件是文件，则复制
                    if (f.isFile()) {
                        copy(f.getAbsolutePath(),newFile.getAbsolutePath());
                    }
                }
            }
        }
    }

    public static void copy(String srcPath,String destPath){
        byte[] bytes = new byte[1024];
        try (FileInputStream fis = new FileInputStream(srcPath);
             FileOutputStream fos = new FileOutputStream(destPath)) {
            while (true) {
                int len = fis.read(bytes);
                if (len == -1)
                    break;
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        copyFile("F:\\filetest\\skin","F:\\filetest\\abc");
    }
}

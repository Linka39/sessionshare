package com.linka39.fileUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileFind {
    public static void main(String[] args) throws IOException   {
        File f = new File("E:\\my_project\\sessionshare\\src\\main\\java\\com\\linka39");
        String sc = "融资授信申请额度";
        search(f,sc);
    }
    public static void search(File folder, String search) throws IOException {
        if(folder.isDirectory()) {
            if(folder.getName().contains(search)){
                System.out.println(folder.getAbsolutePath()+"\t【Dir】");
            }
            File[] fs = folder.listFiles();
            if(fs != null) {
                for(File f : fs) {
                    search(f,search);
                }
            }
        }else {
            int count = 0;
            if(folder.getName().contains(search))
                System.out.println(folder.getAbsolutePath()+"\t【File】");
            BufferedReader bfr = new BufferedReader(new FileReader(folder));
            while(true) {
                String line = bfr.readLine();
                ++count;
                if(line == null) {
                    break;
                }
                if(line.contains(search)) {
                    System.out.println(folder.getAbsolutePath()+"\tline:"+count);
                }
            }
        }
    }
}

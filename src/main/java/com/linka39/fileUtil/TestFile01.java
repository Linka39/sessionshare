package com.linka39.fileUtil;

import java.io.*;

public class TestFile01 {
    public static void main(String[] args) {
        // method1();
        // splitFile();
        // mergeFile("F:\\filetest\\filedemo","pie.jpg");

//        encodeFile("F:\\filetest\\encode\\cy.txt", "F:\\filetest\\encode\\CY1.txt");
//        decryptFile("F:\\filetest\\encode\\CY1.txt", "F:\\filetest\\encode\\cy2.txt");
        readChinese("F:\\filetest\\encode\\demo.txt");
    }

    public static void method1() {
        try {
            File f = new File("F:\\filetest\\newFile\\1\\2\\abc.txt");
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            // 准备长度是2的字节数组，用88,89初始化，其对应的字符分别是X,Y
            byte data[] = {88, 89, 90, 91};
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void splitFile() {
        try {
            File fu = new File("F:\\filetest\\pie.jpg");
            int p = (int) (1 + fu.length() / 1024);
            byte[][] all = new byte[p][1024];
            String tempName = fu.getName();
            FileInputStream x = new FileInputStream(fu);
            for (int i = 0; i < p; i++) {
                x.read(all[i]);
                File q = new File("F:\\filetest\\filedemo\\" + tempName + "-" + i);
                FileOutputStream ff = new FileOutputStream(q);
                ff.write(all[i]);
                ff.close();
            }
            x.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeFile(String folder, String fileName) {
        try {
            // 合并的目标文件
            File destFile = new File(folder, fileName);
            FileOutputStream fos = new FileOutputStream(destFile);
            int index = 0;
            while (true) {
                //子文件
                File eachFile = new File(folder, fileName + "-" + index++);
                //如果子文件不存在了就结束
                if (!eachFile.exists())
                    break;
                //读取子文件的内容
                FileInputStream fis = new FileInputStream(eachFile);
                byte[] eachContent = new byte[(int) eachFile.length()];
                fis.read(eachContent);
                fis.close();

                //把子文件的内容写出去
                fos.write(eachContent);
                fos.flush();
                System.out.printf("把子文件 %s写出到目标文件中%n", eachFile);
            }
            fos.close();
            System.out.printf("最后目标文件的大小：%,d字节", destFile.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void encodeFile(String encodingFileStr, String encodedFileStr) {
        File encodingFile = new File(encodingFileStr);
        File encodedFile = new File(encodedFileStr);
        try (FileReader fis = new FileReader(encodingFile)) {
            char a[] = new char[(int) encodingFile.length()];
            fis.read(a);
            System.out.println("加密前的内容：");
            System.out.print(new String(a));
            System.out.println();
            for (int i = 0; i < a.length; i++) {
                if ((a[i] >= '0' && a[i] <= '9') || (a[i] >= 'a' && a[i] <= 'z') || (a[i] >= 'A' && a[i] <= 'Z')) {
                    if (a[i] == 'Z') {
                        a[i] = 'A';
                    } else if (a[i] == '9') {
                        a[i] = '0';
                    } else if (a[i] == 'z') {
                        a[i] = 'a';
                    } else {
                        a[i]++;
                    }
                }
            }
            try (FileWriter fr = new FileWriter(encodedFile)) {
                char aa[] = a;
                fr.write(aa);
                System.out.println("加密后的内容：");
                for (char c : aa) {
                    System.out.print(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(String decryptingFileStr, String decryptedFileStr) {
        File decryptingFile = new File(decryptingFileStr);
        File decryptedFile = new File(decryptedFileStr);
        try (FileReader fis = new FileReader(decryptingFile)) {
            char a[] = new char[(int) decryptingFile.length()];
            fis.read(a);
            System.out.println("加密后的内容：");
            System.out.print(new String(a));
            System.out.println();
            for (int i = 0; i < a.length; i++) {
                if ((a[i] >= '0' && a[i] <= '9') || (a[i] >= 'a' && a[i] <= 'z') || (a[i] >= 'A' && a[i] <= 'Z')) {
                    if (a[i] == 'A') {
                        a[i] = 'Z';
                    } else if (a[i] == '0') {
                        a[i] = '9';
                    } else if (a[i] == 'a') {
                        a[i] = 'z';
                    } else {
                        a[i]--;
                    }
                }
            }
            try (FileWriter fr = new FileWriter(decryptedFile)) {
                char aa[] = a;
                fr.write(aa);
                System.out.println("加密前的内容：");
                for (char c : aa) {
                    System.out.print(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readChinese(String FileStr) {
        File f = new File(FileStr);
        try (FileInputStream fis = new FileInputStream(f);) {
            byte[] all = new byte[(int) f.length()];
            fis.read(all);
            //文件中读出来的数据是
            System.out.println("文件中读出来的数据是：");
            for (byte b : all)
            {
                //int i = b&0x000000ff;  //只取16进制的后两位
                System.out.println(Integer.toHexString(b));
            }
            String str = new String(all,"UTF-8");
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

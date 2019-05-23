package com.study.remotedata.remote;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ZipUtil {

    public static String pathOf7Z = "C:/7-Zip/7z.exe";

    public static String remoteUrl = "//192.168.1.232/recipe/";
    public static void main(String[] args) throws Exception {
        String command = "C:/7-Zip/7z.exe a -t7z E:/DriverTest_1.7z E:/swift/* -mx=9 -ms=200m -mf -mhc -mhcf -m0=LZMA:a=2:d=25:mf=bt4b:fb=64 -mmt -r";
        String testCommand = "C:/7-Zip/7z.exe a E:/L.7z E:/FSAPBH1";
        String delCommand = "del  E://F.zip";
        long start = Instant.now().toEpochMilli();
//        ZipUtil.zipFileBy7Z("E:/swift", "E:/lalala.7z");
        RemoteAccessData.smbRead();
        long end = Instant.now().toEpochMilli();
        System.out.println(end - start);

    }


    public static boolean zipFileBy7Z(String originalFile, String targetFile) throws IOException, InterruptedException {
        //先删除目标文件,确定为7z文件,防止删除其他文件
        if (targetFile.endsWith(".7z")) {
            new File(targetFile).delete();
        } else {
            //todo  其他类型文件，或文件夹处理（错误路径处理）
            return false;
        }
        String command = pathOf7Z + " a " + targetFile + " " + originalFile;
        Process exec = Runtime.getRuntime().exec(command);

        return  exec.waitFor(20, TimeUnit.MINUTES);
    }

    public static boolean unzipBy7Z(String zipName) throws InterruptedException, IOException {

        String command = pathOf7Z+ " x "+remoteUrl+zipName+"  -o"+remoteUrl+" -aoa ";
        Process exec = Runtime.getRuntime().exec(command);
        return  exec.waitFor(20, TimeUnit.MINUTES);
    }




}

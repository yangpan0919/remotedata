package com.study.remotedata.remote;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class FileUtil {

    public static String pathOf7Z = "C:/7-Zip/7z.exe";

    public static String remoteUrl = "//192.168.1.232/recipe/";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(delFile(remoteUrl + "2018.7z"));
        copyFile("E://F.7z",remoteUrl+"temp.7z");
    }

    /**
     *  复制文件/文件夹
     * @param localPath
     * @param targetPath
     * @throws IOException
     */
    public static void copyFile(String localPath, String targetPath) throws IOException {
        File file = new File(localPath);
        File outFile = new File(targetPath);
        if(file.isFile()){
            FileUtils.copyFile(file,outFile);
        }else{
            FileUtils.copyDirectory(file,outFile);
        }
//
//        if (file.isDirectory()) {
//            new File(targetPath).mkdir();
//            File[] files = file.listFiles();
//            for (File f : files) {
//                copyFile(localPath + File.separator + f.getName(), targetPath + File.separator + f.getName());
//            }
//        } else {
////            pool.submit(new CopyFileThread(file,targetPath));
//            FileInputStream input = null;
//            BufferedInputStream inBuff = null;
//
//            FileOutputStream output = null;
//            BufferedOutputStream outBuff = null;
//            try {
//                // 新建文件输入流并对它进行缓冲
//                input = new FileInputStream(file);
//                inBuff = new BufferedInputStream(input);
//
//                // 新建文件输出流并对它进行缓冲
//                output = new FileOutputStream(targetPath);
//                outBuff = new BufferedOutputStream(output);
//
////                avary88#
//                // 缓冲数组
//                byte[] b = new byte[1024 * 1024];
//                int len;
//                int i=0;
//                while ((len = inBuff.read(b)) != -1) {
//                    System.out.println(i++);
//                    outBuff.write(b, 0, len);
//                }
//                // 刷新此缓冲的输出流
//                outBuff.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if(input!=null){
//                    try {
//                        input.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(inBuff!=null){
//                    try {
//                        inBuff.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(output!=null){
//                    try {
//                        output.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(outBuff!=null){
//                    try {
//                        outBuff.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }

//        }

    }

    /**
     * 删除文件
     * @param filePath
     * @return
     */
    public static boolean delFile(String filePath) throws IOException, InterruptedException {
        File file = new File(filePath);
        if(file.exists()&&file.isDirectory()){
            FileUtils.deleteDirectory(file);
            return true;
        }else if(file.exists()&& file.isFile()){
            FileUtils.forceDelete(file);
            return true;
        }
        return false;
    }

    /**
     * 使用7z压缩文件
     * @param originalFile
     * @param targetFile
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * 解压文件
     * @param zipName
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public static boolean unzipBy7Z(String zipName) throws InterruptedException, IOException {

        String command = pathOf7Z+ " x "+remoteUrl+zipName+"  -o"+remoteUrl+" -aoa ";
        Process exec = Runtime.getRuntime().exec(command);
        return  exec.waitFor(20, TimeUnit.MINUTES);
    }
//
//    static class CopyFileThread implements Runnable{
//
//        private File file;
//        private String targetPath;
//
////        protected CopyFile(){
////
////        }
//        public CopyFileThread(File file, String targetPath){
//            this.file = file;
//            this.targetPath = targetPath;
//        }
//
//        @Override
//        public void run() {
//            FileInputStream input = null;
//            BufferedInputStream inBuff = null;
//
//            FileOutputStream output = null;
//            BufferedOutputStream outBuff = null;
//            try {
//                // 新建文件输入流并对它进行缓冲
//                input = new FileInputStream(file);
//                inBuff = new BufferedInputStream(input);
//
//                // 新建文件输出流并对它进行缓冲
//                output = new FileOutputStream(targetPath);
//                outBuff = new BufferedOutputStream(output);
//
////                avary88#
//                // 缓冲数组
//                byte[] b = new byte[1024 * 1024];
//                int len;
//                while ((len = inBuff.read(b)) != -1) {
//                    outBuff.write(b, 0, len);
//                }
//                // 刷新此缓冲的输出流
//                outBuff.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                countDownLatch.countDown();
//                if(input!=null){
//                    try {
//                        input.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(inBuff!=null){
//                    try {
//                        inBuff.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(output!=null){
//                    try {
//                        output.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(outBuff!=null){
//                    try {
//                        outBuff.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }
//    }
}

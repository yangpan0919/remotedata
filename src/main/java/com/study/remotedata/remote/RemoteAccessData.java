package com.study.remotedata.remote;

import jcifs.smb.*;

import java.io.*;
import java.net.MalformedURLException;
import java.time.Instant;


public class RemoteAccessData {

    public static String remoteUrl = "smb://luosy:luosy123@192.168.43.95/recipe";
//    public static String remoteUrl2 = "smb://192.168.43.95/recipe";  //用于auth登陆

    public static void main(String[] args) throws Exception {
// 远程url smb://192.168.100.86/test
// 如果需要用户名密码就这样：
// smb://username:password@192.168.100.86/test
        /* Java利用SMB上传、下载、读取（内容或文件名）、复制、删除远程文件
         * smbGet1("smb://192.168.100.86/test/新建 文本文档.txt" ); smbGet(
         * "smb://192.168.100.86/test/新建 文本文档.txt" , "e:/" );
         * 需要jar包：jcifs-1.3.15.jar
         */
// 得到共享目录下文件的大小 （测试成功）
//        smbGet1("smb://10086:10086@192.168.100.86/ybweatherweb/2-各类服务产品/23-专业服务产品/移动部门/2017/移动部门（20170411）.doc");
// 从共享目录下载指定文件到本地指定路径  （测试成功）
//        smbGet("smb://10086:10086@192.168.100.86/ybweatherweb/2-各类服务产品/23-专业服务产品/移动部门/2017/移动部门（20170411）.doc", "F:/");
//上传文件到共享目录 （测试成功）
//        smbPut("smb://10086:10086@192.168.100.86/ybweatherweb/2-各类服务产品/23-专业服务产品/移动部门/2017", "F:/4/");
//        smbPut("smb://sy_l@outlook.com:luosy123@192.168.1.157/recipe", "E:/swift.rar");
//把共享目录指定文件夹下的文件拷贝到另外一个共享目录指定文件夹下（目录文件夹不存在，就创建） （测试成功）
//        smbCopy("192.168.100.86/ybweatherweb/2-各类服务产品/1-气象信息快报/每日天气报告/2017/草稿/", "192.168.100.86/ybweatherweb/2-各类服务产品/1-气象信息快报/每日天气报告/2017/审核后/");
//读取指定文件夹下的所有文件名（测试成功）
//        smbRead();
//删除共享目录下的指定文件（测试成功）
//        smbDelete();
// 远程url smb://192.168.100.86/test
// 如果需要用户名密码就这样：
// smb://username:password@192.168.100.86/test
    }

    // 向共享目录上传文件

    /**
     * @param remoteFileName
     * @param localFilePath
     * @param type           1  文件存在覆盖该文件；2   文件存在直接返回
     */
    public static void smbPut(String remoteFileName, String localFilePath, int type) {
//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("domain", "luosy", "luosy123");  //用户登陆处理
        File localfile = null;
        InputStream ins = null;
        SmbFile smbfile = null;
        OutputStream outs = null;
        try {
            String URL_remote = remoteUrl + "/" + remoteFileName;
            localfile = new File(localFilePath);
            ins = new FileInputStream(localfile);
            smbfile = new SmbFile(URL_remote);
//            smbfile = new SmbFile(URL_remote,auth);
            if (smbfile.exists()) {
                //todo  远端文件已存在处理
                switch (type) {
                    case 1:
                        smbDelete(smbfile);
                        break;
                    case 2:
                        return;
                    default:
                        return;

                }
            }

            smbfile.connect();
            outs = new SmbFileOutputStream(smbfile);
            byte[] buffer = new byte[4096];
            int len = 0; //读取长度
            while ((len = ins.read(buffer, 0, buffer.length)) != -1) {
                outs.write(buffer, 0, len);
            }
            outs.flush(); //刷新缓冲的输出流

            System.out.println("写入成功");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//注意SmbFile本来就不稳定，一定要注意把所有的流关闭啊。try-catch挺烦的，用IDE自己生成吧！
    }

    //删除共享目录中指定文件
    public static void smbDelete(String targetName) throws SmbException, MalformedURLException {
        String url = remoteUrl + "/" + targetName;  //文件以/ 结尾
//        String url = "smb://luosy:luosy123@192.168.43.95/recipe/testRecipe/";  //文件以/ 结尾
        SmbFile file = new SmbFile(url);
        smbDelete(file);
    }

    //删除共享目录中指定文件
    public static void smbDelete(SmbFile file) throws SmbException {
        if (file.exists()) {
            long l = Instant.now().toEpochMilli();
            file.delete();
            long l1 = Instant.now().toEpochMilli();
            System.out.println(l1 - l);
            System.out.println("删除文件成功！");
        } else {
            System.out.println("没有文件");
        }
    }

    /**
     * @return
     * @throws MalformedURLException
     */
    public static String[] smbRead() throws MalformedURLException {
        String[] arr = new String[3];
        //主要利用类 SmbFile 去实现读取共享文件夹 shareFile 下的所有文件(文件夹)的名称
        // String URL="smb://xxx:xxx@192.168.0.100/shareFile/";
        SmbFile smbfile = new SmbFile(remoteUrl + "/");
        try {
            if (!smbfile.exists()) {
                System.out.println("no such folder");
            } else {
                SmbFile[] files = smbfile.listFiles();
                for (SmbFile f1 : files) {
                    if (f1.isDirectory()) {
                        String name = f1.getName();
                        arr[0] = name.substring(0, name.length() - 1);
                        SmbFile[] imgs = f1.listFiles("Img");
                        for (SmbFile f2 : imgs) {
                            String name1 = f2.getName();
                            arr[1] = name1.substring(0, name1.length() - 1);
                            SmbFile[] smbFiles = f2.listFiles();
                            for (SmbFile f3 : smbFiles) {
                                String name2 = f3.getName();
                                arr[2] = name2.substring(0, name2.length() - 1);
                                break;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        } catch (SmbException e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 方法四：把共享目录指定文件夹下的文件拷贝到另外一个共享目录指定文件夹下 （测试成功）
     *
     * @param rpath  指定共享目录文件夹的路径一
     * @param rpath1 指定共享目录文件夹的路径二
     * @throws IOException
     */
    private static void smbCopy(String rpath, String rpath1) throws IOException {
//rpath = "192.168.100.86/ybweatherweb/2-各类服务产品/1-气象信息快报/每日天气报告/"+yyyy+"/草稿/";
//rpath1 = "192.168.100.86/ybweatherweb/2-各类服务产品/1-气象信息快报/每日天气报告/"+yyyy+"/审核后/";
        String filename = "测试.docx";
        String username = "10086";
        String pwd = "10086";
        SmbFile f = new SmbFile("smb://" + username + ":" + pwd + "@" + rpath + filename);
        SmbFile f1 = new SmbFile("smb://" + username + ":" + pwd + "@" + rpath1);
        if (!f1.exists()) { // 目录文件夹不存在，就创建
            f1.mkdirs();
        }
        if (f.exists() && f.isFile()) {
            SmbFile fa1 = new SmbFile("smb://" + username + ":" + pwd + "@" + rpath1 + f.getName());
            f.copyTo(fa1);
        }
    }

    /**
     * 方法一： \\192.168.100.86\ybweatherweb\2-各类服务产品\23-专业服务产品\虎门部队\2017
     *
     * @param remoteUrl 远程路径 smb://192.168.100.86/test/新建 文本文档.txt
     * @throws IOException
     */
    public static void smbGet1(String remoteUrl) throws IOException {
        SmbFile smbFile = new SmbFile(remoteUrl);
        int length = smbFile.getContentLength(); // 得到文件的大小
        byte buffer[] = new byte[length];
        SmbFileInputStream in = new SmbFileInputStream(smbFile);
// 建立smb文件输入流
        while ((in.read(buffer)) != -1) {
            System.out.write(buffer); //这个打印到控制台乱码，未解决；
            System.out.println(buffer.length);
        }
        in.close();
    }


// 从共享目录下载文件

    /**
     * 方法二： 路径格式：smb://192.168.100.86/test/新建 文本文档.txt
     * smb://username:password@192.168.100.86/test
     *
     * @param remoteUrl 远程路径
     * @param localDir  要写入的本地路径
     */
    @SuppressWarnings("unused")
    public static void smbGet(String remoteUrl, String localDir) {
        InputStream in = null;
        OutputStream out = null;
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl);
            if (remoteFile == null) {
                System.out.println("共享文件不存在");
                return;
            }
            String fileName = remoteFile.getName();
            File localFile = new File(localDir + File.separator + fileName);
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

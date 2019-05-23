package com.study.remotedata.remote;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.util.StringUtils;

import java.io.*;

public class Remote {

    private static String smburl = "smb://luosy:luosy123@192.168.1.157/recipe";

    //删除共享目录中指定文件
    private static void smbDelete() throws Exception {
        String url = "smb://luosy:luosy123@192.168.43.95/recipe/testRecipe/";  //文件以/ 结尾
        SmbFile file = new SmbFile(url);
        if (file.exists()) {
            file.delete();
            System.out.println("删除文件成功！");
        }else{
            System.out.println("没有文件");
        }
    }
    //zhoushun ----------------------------
    public static void createDir(String dir) throws Exception{
        SmbFile fp = new SmbFile(smburl+"//"+dir);
        System.out.println("fieldir+++++++++++++++++++++="+smburl+"//"+dir);
        //File fp = new File("Z://"+dir);
        // 目录已存在创建文件夹
        if (fp.exists() && fp.isDirectory()) {

        } else{
            // 目录不存在的情况下，会抛出异常
            fp.mkdir();
        }
    }

    public static void main(String[] args) throws Exception {
//        Remote.createDir("test");
//        Remote.copyDir("E://swift.rar","");
        Remote.smbDelete();
    }


    public static void copyDir(String fileName, String target) throws Exception{
        InputStream in = null;
        OutputStream out = null;
        try{
            File fp = new File(fileName);

            SmbFile remoteFile = new SmbFile(smburl+"//"+fp.getName());
            System.out.println("remoteFile+++++++++++++++++++++="+remoteFile);
            in = new BufferedInputStream(new FileInputStream(fp));
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            // 刷新此缓冲的输出流
            out.flush();
        }catch (Exception e) {
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

    public static void fileUpload(String fileName, String newFileName, String filePath)
    {
        InputStream in = null;
        OutputStream out = null;
        try{
            String newname = "";
            fileName = StringUtils.replace(fileName, "\\", "/");
            if (fileName.indexOf("/") > -1)
                newname = fileName.substring(fileName.lastIndexOf("/") + 1);
            else {
                newname = fileName;
            }
            SmbFileOutputStream file_out = null;
            if ((newFileName != null) && (!(newFileName.equals("")))){
                file_out = new SmbFileOutputStream(smburl+"//"+filePath+"//"+newFileName);
                System.out.println("filename+++++++++++++++++++++="+smburl+"//"+filePath+"//"+newFileName);
            }else {
                file_out = new SmbFileOutputStream(smburl+"//"+filePath+"//"+newname);
                System.out.println("filename+++++++++++++++++++++="+smburl+"//"+filePath+"//"+newFileName);
            }

            File file_in = new File(fileName);



            in = new BufferedInputStream(new FileInputStream(file_in));
            out = new BufferedOutputStream(file_out);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            // 刷新此缓冲的输出流
            out.flush();
            out.flush();
        }catch (Exception e) {
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

package com.study.remotedata.remote;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFileInputStream;

import java.io.File;


public class RemoteTest {
    public static String  localPath = "C:/Users/86180/AppData/Roaming/Microsoft/Windows/Network Shortcuts/recipe (Desktop-ger1kd5 (Luosy))";
    public static String  localPath1 = "//Desktop-ger1kd5/recipe/test";
    public static String  localPath2 = "//192.168.43.95/recipe";
    public static void main(String[] args) throws Exception {
        File file = new File(localPath2);
        File[] files = file.listFiles();
        for(File f : files){
            System.out.println(f.getName());
        }

//        boolean b = file.exists();
//        System.out.println(b);
//        boolean delete = file.delete();
//        System.out.println(delete);
//        jcifs.Config.setProperty( "jcifs.netbios.wins", "192.168.1.157" );
//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("domain", "sy_l@outlook.com", "luosy123");
//        SmbFileInputStream in = new SmbFileInputStream("smb://host/c/My Documents/somefile.txt", auth);
//        byte[] b = new byte[8192];
//        int n;
//        while(( n = in.read( b )) > 0 ) {
//            System.out.write( b, 0, n );
//        }
    }
}

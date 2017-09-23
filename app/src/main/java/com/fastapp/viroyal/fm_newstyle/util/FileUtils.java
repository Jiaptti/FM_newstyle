package com.fastapp.viroyal.fm_newstyle.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * Created by hanjiaqi on 2017/9/15.
 */

public class FileUtils {
    private static File CacheRoot;

    public static void saveData(String json) {
        Context c = AppContext.getAppContext();
        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ?
                c.getExternalCacheDir() : c.getCacheDir();
        FileOutputStream outStream = null;
        try {
            File file = new File(CacheRoot.toString()
                    +File.separator+ "fm"
                    +File.separator+ AppConstant.DATA_FILE);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            outStream = new FileOutputStream(file);
            outStream.write(json.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeData(){
        Context c = AppContext.getAppContext();
        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        File file=new File(CacheRoot.toString()
                +File.separator+ "fm"
                +File.separator+ AppConstant.DATA_FILE);
        if(file.exists()){
            file.delete();
        }
    }


    public static String readData() {
        Context c = AppContext.getAppContext();
        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileInputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        byte[] buffer = new byte[1024];
        String str = null;
        File file=new File(CacheRoot.toString()
                +File.separator+ "fm"
                +File.separator+ AppConstant.DATA_FILE);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            if(file.exists()){
                inStream = new FileInputStream(file);
                outStream = new ByteArrayOutputStream();
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                byte[] data = outStream.toByteArray();
                str = new String(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

}

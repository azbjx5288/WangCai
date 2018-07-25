package com.wangcai.lottery.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ACE-PC on 2017/6/28.
 */

public class DownPicUtil
{
    
    /**
     * 下载图片，返回图片的地址
     *
     * @param url
     */
    public static void downPic(String url, DownFinishListener downFinishListener)
    {
        // 获取存储卡的目录
        File file = new File(Environment.getExternalStorageDirectory(), "JYZ_Pay");
        if (!file.exists())
        {
            file.mkdir();
        }
        loadPic(file.getPath(), url, downFinishListener);
    }
    
    private static void loadPic(final String filePath, final String url, final DownFinishListener downFinishListener)
    {
        new AsyncTask<Void, Void, Bitmap>()
        {
            String fileName;
            @Override
            protected Bitmap doInBackground(Void... voids)
            {
                Pattern p = Pattern.compile("(\\s+?http://)?(.*\\.(jpg|gif|png|jpeg|bmp))", Pattern.CASE_INSENSITIVE);
                Matcher matcher = p.matcher(url);
                
                if (matcher.find())
                {
                    // 下载文件的名称
                    String[] split = url.split("/");
                    fileName = split[split.length - 1];
                    // 创建目标文件,不是文件夹
                    File picFile = new File(filePath + File.separator + fileName);
                    if (picFile.exists())
                    {
                        return getHttpBitmap(picFile.getPath());
                    }
                    return getHttpBitmap(url);
                } else
                {
                    return stringToBitmap(url);
                }
            }
            
            @Override
            protected void onPostExecute(Bitmap bitmap)
            {
                super.onPostExecute(bitmap);
                if (bitmap != null)
                {
                    downFinishListener.getDownPath(bitmap);
                }
            }
        }.execute();
    }
    
    //下载完成回调的接口
    public interface DownFinishListener
    {
        void getDownPath(Bitmap bitmap);
    }
    
    /**
     * 获取网络图片
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url)
    {
        Bitmap bitmap = null;
        try
        {
            URL pictureUrl = new URL(url);
            InputStream in = pictureUrl.openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
    
    /**
     * Base64 格式图片
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string)
    {
        Bitmap bitmap = null;
        try
        {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
}

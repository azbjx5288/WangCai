package com.wangcai.lottery.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.util.SharedPreferencesUtils;

import java.io.File;

import static android.R.attr.id;


/**
 * 检测安装更新文件的助手类
 * Created by Sakura on 2017/1/11.
 */

public class UpdateService extends Service
{
    private long downloadId;
    private String url;

    public UpdateService()
    {
    }

    /**
     * 安卓系统下载类
     **/
    private DownloadManager downloadManager;

    /**
     * 接收下载完的广播
     **/
    private DownloadCompleteReceiver receiver;

    /**
     * 初始化下载器
     **/
    private void initDownManager()
    {
        receiver = new DownloadCompleteReceiver();

        //设置下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        // 下载时，通知栏显示途中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        // 显示下载界面
        request.setVisibleInDownloadsUi(true);
        // 设置下载后文件存放的位置
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "jyz.apk");

        request.setTitle(getApplicationContext().getResources().getString(R.string.app_name));
        request.setDescription("下载新版本");

        // 将下载请求放入队列
        downloadId = downloadManager.enqueue(request);

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private boolean canInstall()
    {
        downloadId = SharedPreferencesUtils.getLong(getApplicationContext(), "download", "downloadID");
        if (downloadId != -1L)
        {
            installAPK(Uri.parse(""));
            UpdateService.this.stopSelf();
            return true;
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //if(!canInstall())
        url = intent.getExtras().getString("updateUrl");
        if (url != null && !"".equals(url))
        {
            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            if (isNeedDownloadAgain())
            {
                Toast.makeText(getApplicationContext(), "开始下载...", Toast.LENGTH_SHORT).show();
                // 调用下载
                initDownManager();
            }
        }

        //flags=START_STICKY_COMPATIBILITY;
        return super.onStartCommand(intent, flags, startId);
        //return Service.START_REDELIVER_INTENT;
        /*// 调用下载
        initDownManager();

        return super.onStartCommand(intent, flags, startId);*/
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);

        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            //判断是否下载完成的广播
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            {
                //获取下载的文件id
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                SharedPreferencesUtils.putLong(getApplicationContext(), "download", "downloadID", downloadId);

                //自动安装apk
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    Uri uriForDownloadedFile = downloadManager.getUriForDownloadedFile(downloadId);
                    Log.d("下载路径", "uri=" + uriForDownloadedFile);

                    installAPK(uriForDownloadedFile);
                }

                //停止服务并关闭广播
                UpdateService.this.stopSelf();
            } /*else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction()))
            {
                long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                //点击通知栏取消下载
                downloadManager.remove(ids);
                Toast.makeText(getApplicationContext(), "已经取消下载", Toast.LENGTH_SHORT).show();
            }*/
        }

        /*//安装apk
        protected void installApkNew(Uri uri)
        {
            if (Build.VERSION.SDK_INT < 23)
            {
                Intent intent = new Intent();
                //执行动作
                intent.setAction(Intent.ACTION_VIEW);
                //执行的数据类型
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                //不加下面这句话是可以的，查考的里面说如果不加上这句的话在apk安装完成之后点击单开会崩溃
                // android.os.Process.killProcess(android.os.Process.myPid());
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), "下载完成，请手动安装", Toast.LENGTH_SHORT).show();
        }*/
    }

    /**
     * 安装apk文件
     */
    private void installAPK(Uri apk)
    {
        //通过Intent安装APK文件
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DEFAULT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        File apkFile = queryDownloadedApk();
        //String path = getRealFilePath(getApplicationContext(), apk);
        //File apkFile = new File(path);
        if (apkFile.exists())
        {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else
        {
            Toast.makeText(getApplicationContext(), "安装文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    //解析uri,，获取下载文件真实路径
    private String getRealFilePath(final Context context, final Uri uri)
    {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme))
        {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
        {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns
                    .DATA}, null, null, null);
            if (null != cursor)
            {
                if (cursor.moveToFirst())
                {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1)
                    {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    //下面代码是一个简单的防止重复下载判断：
    private boolean isNeedDownloadAgain()
    {
        boolean isNeedDownloadAgain = true;
        DownloadManager.Query query = new DownloadManager.Query();
        long downloadID = SharedPreferencesUtils.getLong(getApplicationContext(), "download", "downloadID");
        //ServiceProviderApplication.getValueByKey("serviceProviderDownloadManagerId", -1L);
        // Toast.makeText(getApplicationContext(), "id..."+id, Toast.LENGTH_SHORT).show();
        if (downloadID != -1L)
        {
            query.setFilterById(id);
            Cursor cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst())
            {
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);
                int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                int reason = cursor.getInt(columnReason);
                switch (status)
                {
                    case DownloadManager.STATUS_FAILED:
                        switch (reason)
                        {
                            case DownloadManager.ERROR_CANNOT_RESUME:
                                //some possibly transient error occurred but we can't resume the download
                                break;
                            case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                                //no external storage device was found. Typically, this is because the SD card is not
                                // mounted
                                break;
                            case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                                //the requested destination file already exists (the download downloadManager will
                                // not overwrite an existing file)
                                break;
                            case DownloadManager.ERROR_FILE_ERROR:
                                //a storage issue arises which doesn't fit under any other error code
                                break;
                            case DownloadManager.ERROR_HTTP_DATA_ERROR:
                                //an error receiving or processing data occurred at the HTTP level
                                break;
                            case DownloadManager.ERROR_INSUFFICIENT_SPACE://sd卡满了
                                //here was insufficient storage space. Typically, this is because the SD card is full
                                break;
                            case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                                //there were too many redirects
                                break;
                            case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                                //an HTTP code was received that download downloadManager can't handle
                                break;
                            case DownloadManager.ERROR_UNKNOWN:
                                //he download has completed with an error that doesn't fit under any other error code
                                break;
                        }
                        isNeedDownloadAgain = true;
                        break;
                    case DownloadManager.STATUS_PAUSED:
                        switch (reason)
                        {
                            case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                                //the download exceeds a size limit for downloads over the mobile network and the
                                // download downloadManager is waiting for a Wi-Fi connection to proceed

                                break;
                            case DownloadManager.PAUSED_UNKNOWN:
                                //the download is paused for some other reason
                                break;
                            case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                                //the download is waiting for network connectivity to proceed
                                break;
                            case DownloadManager.PAUSED_WAITING_TO_RETRY:
                                //the download is paused because some network error occurred and the download
                                // downloadManager is waiting before retrying the request
                                break;
                        }
                        isNeedDownloadAgain = false;
                        break;
                    case DownloadManager.STATUS_PENDING:
                        isNeedDownloadAgain = false;
                        //  Toast.makeText(getApplicationContext(), "STATUS_PENDING...", Toast.LENGTH_SHORT).show();
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        Toast.makeText(getApplicationContext(), "正在下载...", Toast.LENGTH_SHORT).show();
                        isNeedDownloadAgain = false;
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        //the download has successfully completed
                        isNeedDownloadAgain = true;
                        // isNeedDownloadAgain = false;
                        //  installApk(id, downloadManager, mContext);
                        break;
                }
            }

        }

        return isNeedDownloadAgain;
    }

    //获取下载文件
    private File queryDownloadedApk()
    {
        File targetApkFile = null;
        if (downloadId != -1)
        {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloadManager.query(query);
            if (cur != null)
            {
                if (cur.moveToFirst())
                {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString))
                    {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }
}

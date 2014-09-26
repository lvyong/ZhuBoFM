package com.andy.commonlibrary.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Created by Vinchaos api on 14-1-2.
 * App信息
 */
public class AppUtil {

    private static Context context;

    private AppUtil(Context context) {
        this.context = context;
    }

    private static AppUtil appUtil;

    public static synchronized AppUtil getInstance(Context context) {
        if (appUtil == null) appUtil = new AppUtil(context);
        return appUtil;
    }

    /**
     * 获取目前软件版本code
     *
     * @return
     */
    public String getAppVersionCode() {
        String versionCode = "";
        try {
            PackageManager pm = context.getPackageManager();

            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode + "";
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 获取目前软件版本name
     *
     * @return
     */
    public String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();

            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName + "";
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 从AndroidManifest中获取channel值
     */
    public String getAppChannel(){
        int channel = 0;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(),pm.GET_META_DATA);
            channel = info.metaData.getInt("CHANNEL");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return channel+"";
    }

    /**
     * 从AndroidManifest中获取crashVersion
     */
    public String getCrashVersion(){
        String crashVersion = "get version error";
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(),pm.GET_META_DATA);
            crashVersion = info.metaData.getString("CRASH");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return crashVersion;
    }

    /**
     * app是否为前台可见
     */
    public boolean isAppRunningForeground(){
        String packageName = context.getPackageName();
        String topActivityPkgName = getTopActivityPkgName(context);
        if (packageName!=null && topActivityPkgName!=null
                && topActivityPkgName.equals(packageName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取Device栈顶程序包名
     */
    public String getTopActivityPkgName(Context context){
        String topActivityPkgName=null;
        ActivityManager activityManager =
                (ActivityManager)(context.getSystemService(Context.ACTIVITY_SERVICE )) ;
        //android.app.ActivityManager.getRunningTasks(int maxNum)
        //int maxNum--->The maximum number of entries to return in the list
        //即最多取得的运行中的任务信息(RunningTaskInfo)数量
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
        if(runningTaskInfos != null){
            ComponentName f=runningTaskInfos.get(0).topActivity;
            topActivityPkgName = f.getPackageName();

        }
        //按下Home键盘后 topActivityClassName=com.android.launcher2.Launcher
        return topActivityPkgName;
    }

    /**
     * 获取IMEI
     * @return
     */
    public String getIMEI(){
       String imei = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
       return imei;
    }



    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        int x = bitmap.getWidth();
        Bitmap output = Bitmap.createBitmap(x,
                x, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xffffffff;
        final Paint paint = new Paint();
    	/*paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
    	paint.setColor(color);
     	// 画出一个圆
     	canvas.drawCircle(x/2, x/2, x/2, paint);*/
        // 根据原来图片大小画一个矩形
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setColor(color);
        // 画出一个圆
        canvas.drawCircle(x/2, x/2, x/2-1, paint);
        //canvas.translate(-25, -6);
        // 取两层绘制交集,显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 将图片画上去
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // 返回Bitmap对象
        final Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(2);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        //paint.setAntiAlias(true);
        color = 0xffffffff;
        paint1.setColor(color);
        // 画出一个圆
        canvas.drawCircle(x/2, x/2, x/2, paint1);
        return output;
    }

    /**
     * 显示软键盘
     * @param context
     * @param view
     */
    public static void showSoftKeyboard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     * @param context
     * @param view
     */
    public static void hideSoftKeyBoard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(),0);
    }

}

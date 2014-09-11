package com.zhubo.fm;

import android.support.v4.app.FragmentActivity;

import com.andy.corelibray.CoreApplication;
import com.andy.corelibray.ui.common.DialogController;
import com.andy.ui.libray.dialog.AlertDialog;
import com.imageloader.cache.disc.naming.Md5FileNameGenerator;
import com.imageloader.core.ImageLoader;
import com.imageloader.core.ImageLoaderConfiguration;

/**
 *
 * Created by andy_lv on 2014/9/1.
 */
public class ZhuBoApplication extends CoreApplication {

    private static ZhuBoApplication zhuBoApplication;

    public static ZhuBoApplication getInstance(){
        return zhuBoApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        zhuBoApplication = this;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .discCacheSize(50000000)
                .memoryCacheSize(5000000)
                .discCacheFileCount(1000)
                .defaultDisplayImageOptions(new com.imageloader.core.DisplayImageOptions.Builder()
                        .cacheOnDisc()
                        .cacheInMemory()
                        .build())
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .enableLogging() // Not necessary in common
                        //.offOutOfMemoryHandling()
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

    }

    /**
     * exitAcivity
     */
    public static void exit(final FragmentActivity activity){
        DialogController.getInstance().showAlertDialog(
                 activity,
                "提示",
                "您确定要退出吗?",
                "取消",
                "确定",
                new AlertDialog.Builder.AlertDialogClickListener(){

                    @Override
                    public void clickCallBack(AlertDialog.Builder.ButtonTypeEnum typeEnum, AlertDialog alertDialog) {
                            if(typeEnum == AlertDialog.Builder.ButtonTypeEnum.LEFT_BUTTON){
                                alertDialog.dismiss();
                            }else if(typeEnum == AlertDialog.Builder.ButtonTypeEnum.RIGHT_BUTTON){
                                activity.finish();
                                alertDialog.dismiss();
                            }
                    }
                });
        }

}

package com.example.mythinkerdemo;

import android.app.Application;

import android.app.Activity;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;


import java.util.LinkedList;
import java.util.List;


public class BaseApplication extends MyApplication {


    //运用list来保存们每一个activity是关键
    private List<Activity> mActivities = new LinkedList<Activity>();

    private static BaseApplication instance = null;

    //实例化一次
    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (null == instance) {
                    instance = new BaseApplication();
                }
            }
        }
        return instance;
    }

    //====================系统环境的设置==========================
    public void onCreate() {
        super.onCreate();


    }

    /**
     * 当后台程序已经终止资源还匮乏时会调用这个方法。
     * 好的应用程序一般会在这个方法里面释放一些不必要的资源来应付当后台程序已经终止，
     * 前台应用程序内存还不够时的情况。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 当终止应用程序对象时调用，不保证一定被调用，
     * 当程序是被内核终止以便为其他应用程序释放资源，那么将不会提醒，
     * 并且不调用应用程序的对象的onTerminate方法而直接终止进 程
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onTerminate() {//在更多中清理了
        super.onTerminate();
    }


    /**
     * 清除WebView缓存
     */
    private void removeCookie() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieManager.getInstance().removeSessionCookie();
        CookieSyncManager.getInstance().sync();
        CookieSyncManager.getInstance().startSync();
    }

    // add Activity
    public void addActivity(Activity activity) {
//    	if(Main_FragmentActivity.class.getSimpleName()
//    			.equals(activity.getClass().getSimpleName())){
//    		closeAllActivity();//进入首页清除其他所有节目
//    	}
        mActivities.add(activity);
    }

    // removes Activity
    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }

    //关闭每一个list内的activity
    public void closeAllActivity() {
        try {
            for (Activity activity : mActivities) {
                if (activity != null)
                    activity.finish();
            }
            mActivities.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

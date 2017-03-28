package com.gean.businessassistant.base;


import android.app.Activity;
import android.content.Context;

import java.util.Stack;

import javax.inject.Singleton;

/**
 *Created by cj on 2015/11/22.
 */
@Singleton
public class ActivityManager {
	
	private Stack<Activity> activityStack = new Stack<Activity>();

	/**
	 * 添加Activity到堆栈中
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
//		Log.d("ActivityManager -->>","已添加...");
	}
	/**
	 * 获取当前Activity（堆栈中最后压入的）
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 * 结束当前Activity（堆栈中最后压入的）
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	/**
	 * 彻底退出应用程序，安全退出
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			android.app.ActivityManager activityMgr= (android.app.ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {	}
	}
}
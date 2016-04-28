package com.example.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pgyersdk.crash.PgyCrashManager;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

public class BaseApplication extends Application {
	// private List<Activity> activities = new ArrayList<Activity>();
	public static List<Activity> activityList = new LinkedList();

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
		x.Ext.init(this);

		// 新浪微博
		PlatformConfig.setSinaWeibo("3353377827", "a09d88b15a453d56b1476e3f60d2bbc5");
		// qq qq空间
		PlatformConfig.setQQZone("1105302848", "t4jsyGyArSeNrmIa");
		// 微信
		PlatformConfig.setWeixin("wx638f1ae0c59d8040", "7bb08e721cfe8a9ca6c1da3ffa09369d");

		Log.LOG = true;

		Config.IsToastTip = true;

		// 蒲公英crash上传
		PgyCrashManager.register(this);

		//DBFlow
		FlowManager.init(this);
	}

	/**
	 * 添加到Activity容器中
	 */
	public static void addActivity(Activity activity) {
		if (!activityList.contains(activity)) {
			activityList.add(activity);
		}
	}

	/**
	 * 便利所有Activigty并finish
	 */
	public static void finishActivity() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	/**
	 * 结束指定的Activity
	 */
	@SuppressWarnings("static-access")
	public static void finishSingleActivity(Activity activity) {
		if (activity != null) {
			if (activityList.contains(activity)) {
				activityList.remove(activity);
			}
			activity.setResult(activity.RESULT_CANCELED);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作， 所有我们先记住要删除的对象，遍历之后才去删除。
	 */
	public static void finishSingleActivityByClass(Class<?> cls) {
		Activity tempActivity = null;
		for (Activity activity : activityList) {
			if (activity.getClass().equals(cls)) {
				tempActivity = activity;
			}
		}
		finishSingleActivity(tempActivity);
	}

	/**
	 * 结束 除 指定类名的Activity 在遍历一个列表的时候不能执行删除操作， 所有我们先记住要删除的对象，遍历之后才去删除。
	 */
	public static void finishAllActivityByClass(Class<?> cls) {
		Activity tempActivity = null;
		for (Activity activity : activityList) {
			if (!activity.getClass().equals(cls)) {
				finishSingleActivity(activity);
			}
		}
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}

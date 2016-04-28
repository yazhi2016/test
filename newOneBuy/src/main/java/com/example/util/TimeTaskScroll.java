package com.example.util;

import java.util.List;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

public class TimeTaskScroll extends TimerTask {
	
	private ListView listView;
	int num;
	
	public TimeTaskScroll(Context context, ListView listView, List<String> list, int num){
		this.listView = listView;
		this.num = num;
		listView.setAdapter(new ListAdapter(context, list, num));
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			listView.smoothScrollBy(13, 0); //移动的距离
		};
	};

	@Override
	public void run() {
		Message msg = handler.obtainMessage();
		handler.sendMessage(msg);
	}

}

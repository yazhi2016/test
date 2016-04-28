package com.example.util;

import java.util.List;

import com.example.newonebuy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * * 实现对listView的循环滚动 
 *
 */
public class ListAdapter extends BaseAdapter {
	
	private List<String> list;
	private LayoutInflater mInflater;
	int num;
	
	public ListAdapter(Context context, List<String> list, int num){
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
		this.num = num;
	}
	
	@Override
	public int getCount() {
		return num;  //return 6，则只滚动到数字5 
	}

	@Override
	public Object getItem(int arg0) {
		
		return list.get(arg0 % list.size());
	}

	@Override
	public long getItemId(int arg0) {
		return arg0 % list.size();
	}
	@Override
	public View getView(int postition, View convertView, ViewGroup arg2) {
		ViewHoler viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHoler();
			convertView = mInflater.inflate(R.layout.adapter_list_layout, null);
			viewHolder.tvText = (TextView) convertView.findViewById(R.id.adapter_list_layout_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHoler) convertView.getTag();
		}
		viewHolder.tvText.setText(list.get(postition % list.size()));//取余展示数据
		return convertView;
	}
	
	static class ViewHoler{
		TextView tvText;
	}

}

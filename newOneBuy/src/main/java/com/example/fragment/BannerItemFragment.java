package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newonebuy.R;
import com.example.onebuy.BannerDetailActivity;
import com.example.util.GlobalVariable;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@SuppressLint("ValidFragment")
public class BannerItemFragment extends Fragment {
	private int position;
	private ImageView image;
	private String imageUrl = "";
	String url;
	private TextView title;

	public BannerItemFragment() {
	}

	public BannerItemFragment(int position, String imageUrl, String url) {
		this.position = position;
		this.imageUrl = imageUrl;
		this.url = url;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_banner_item, container, false);
		image = (ImageView) layout.findViewById(R.id.imageView1);
		title = (TextView) layout.findViewById(R.id.title);
		if (imageUrl.isEmpty()) {
			image.setImageResource(R.drawable.white);
		} else {
			GlobalVariable.displayImageNoAnim(imageUrl, image);
		}
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!url.isEmpty()) {
					Intent intent = new Intent(getActivity(), BannerDetailActivity.class);
					intent.putExtra("url", url);
					startActivity(intent);
				}
			}
		});
		return layout;
	}

}

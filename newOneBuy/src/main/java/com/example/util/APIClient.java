package com.example.util;

import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Context;

public class APIClient {

	public static void requestCities(Context context, VolleyListener listener) {
		HTTPUtils.get(context, Content.URL.CITIES, listener);
	}
}

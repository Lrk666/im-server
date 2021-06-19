package com.lrk.im.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ApiUtil {

	private static final String API_KEY="95b4deda08039ea83edb99054809dce2";
	
	public static String getName() {
		String name="";
		int a=(int)(2+Math.random()*(3-2+1));
		String resp=HttpUtil.doGet("http://api.tianapi.com/txapi/cname/index?key="+API_KEY+"&wordnum="+a);
		JSONObject jsonObject=JSON.parseObject(resp);
		String code=jsonObject.getString("code");
		if ("200".equals(code)) {
			JSONArray newsList=jsonObject.getJSONArray("newslist");
			JSONObject nameData=newsList.getJSONObject(0);
			name=nameData.getString("naming");
		}else {
			name="用户"+StringRandomUtil.getUUID().substring(0, 4);
		}
		return name;
	};
	
	
	
}

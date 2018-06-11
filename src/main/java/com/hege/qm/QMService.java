package com.hege.qm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class QMService {

	CloseableHttpClient httpClient = null;
	CloseableHttpResponse response = null;
	HttpPost httpPost = null;
	String url = "http://www.uustv.com/";

	public void create(String name, String style) {

		httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
				.setConnectionRequestTimeout(15000).build();
		httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		sent(name,style);
	}

	private void sent(String name, String style) {

		Map<String, String> maps = new HashMap<String, String>();
		maps.put("word", name);
		maps.put("sizes", "60");
		maps.put("fonts", style);
		maps.put("fontcolor", "#000000");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : maps.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity, "utf-8");

			String p = "<div class=\"tu\">﻿<img src=";

			int start = html.indexOf(p) + p.length();
			String imageUrl = "http://www.uustv.com/".concat(html.substring(start + 1, start + 24));
//			System.out.println(imageUrl);

			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\"+ name + ".gif"));
			URL url = new URL(imageUrl);
			InputStream in = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(in);
			byte[] buff = new byte[1024];
			int len = 0;
			while((len = bis.read(buff)) != -1) {
				bos.write(buff, 0, len);
				bos.flush();
			}
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}

				if (httpClient != null) {
					httpClient.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}

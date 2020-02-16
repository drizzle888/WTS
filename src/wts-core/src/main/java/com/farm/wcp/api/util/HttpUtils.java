package com.farm.wcp.api.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtils {

	public static JSONObject httpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpGet.setConfig(requestConfig);
		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				resultStr = resultStr.replace("callback(", "").replace(")", "").replace(";", "");
				JSONObject result = new JSONObject(resultStr);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					System.out.println("httpGet:" + e + e.getMessage());
				}
		}

		return null;
	}

	public static JSONObject httpPost(String url, Map<String, String> data) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		httpPost.setConfig(requestConfig);
		// httpPost.addHeader("Content-Type", "application/json");
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String key : data.keySet()) {
				params.add(new BasicNameValuePair(key, data.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
			response = httpClient.execute(httpPost, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = new JSONObject(resultStr);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					System.out.println("httpPost:" + e + e.getMessage());
				}
		}
		return null;
	}
}

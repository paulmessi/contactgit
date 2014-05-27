package com.contact.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utility {

	public static void showDialog(Context ctx, String title, String msg) {
		AlertDialog.Builder alertbuilder = new AlertDialog.Builder(ctx);
		alertbuilder.setTitle(title);
		alertbuilder.setMessage(msg);
		alertbuilder.setPositiveButton("确定", null);
		alertbuilder.create().show();
	}

	public static void showDialog(Context ctx, String title, String msg,
			DialogInterface.OnClickListener yesBtnClickListener) {
		AlertDialog.Builder alertbuilder = new AlertDialog.Builder(ctx);
		alertbuilder.setTitle(title);
		alertbuilder.setMessage(msg);
		alertbuilder.setPositiveButton("确定", yesBtnClickListener);
		alertbuilder.create().show();
	}

	public static void showDialog(Context ctx, String title, String msg,
			DialogInterface.OnClickListener yesBtnClickListener,
			DialogInterface.OnClickListener noBtnClickListener) {
		AlertDialog.Builder alertbuilder = new AlertDialog.Builder(ctx);
		alertbuilder.setTitle(title);
		alertbuilder.setMessage(msg);
		alertbuilder.setPositiveButton("确定", yesBtnClickListener);
		alertbuilder.setNegativeButton("取消", noBtnClickListener);
		alertbuilder.create().show();
	}

	public static void showDialog(Context ctx, String title, String msg,
			DialogInterface.OnClickListener yesBtnClickListener,
			DialogInterface.OnClickListener noBtnClickListener,
			DialogInterface.OnClickListener ignBtnClickListener) {
		AlertDialog.Builder alertbuilder = new AlertDialog.Builder(ctx);
		alertbuilder.setTitle(title);
		alertbuilder.setMessage(msg);
		alertbuilder.setPositiveButton("确定", yesBtnClickListener);
		alertbuilder.setNegativeButton("取消", noBtnClickListener);
		alertbuilder.setNeutralButton("忽略", ignBtnClickListener);
	}

	public static String getData(String url) {
		HttpGet getMethod = new HttpGet(url);

		HttpClient httpClient = new DefaultHttpClient();
		String result = "";
		try {
			HttpResponse response = httpClient.execute(getMethod);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = "Error:network disconnected";
			e.printStackTrace();
		}
		return result;
	}

	public static String postData(String url, ArrayList<NameValuePair> params) {
		String result = null;
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpPost request = new HttpPost(url);
		try {
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = new DefaultHttpClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			result = "Error:network disconnected";
			e.printStackTrace();
		}
		return result;
	}

	public static String urlEncode(String str, String charset) {
		String tmp;
		try {
			tmp = URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			tmp = "Error:urlencode unsuccessful!";
			e.printStackTrace();
		}
		return tmp;
	}

	public static String urlDecode(String str, String charset) {
		String tmp;
		try {
			tmp = URLDecoder.decode(str, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			tmp = "Error:urldecode unsuccessful!";
			e.printStackTrace();
		}
		return tmp;
	}

}

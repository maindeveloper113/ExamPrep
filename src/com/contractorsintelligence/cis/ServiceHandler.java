package com.contractorsintelligence.cis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceHandler {

	static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;

	public ServiceHandler() {

	}

	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * */
//	public String makeServiceCall(String url, int method) {
//		return this.makeServiceCall(url, method, null);
//	}

	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */
//	public String makeServiceCall(String url, int method,
//			List<NameValuePair> params) {
//		try {
//			// http client
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpEntity httpEntity = null;
//			HttpResponse httpResponse = null;
//
//			// Checking http request method type
//			if (method == POST) {
//				HttpPost httpPost = new HttpPost(url);
//				// adding post params
//				if (params != null) {
//					httpPost.setEntity(new UrlEncodedFormEntity(params));
//				}
//				httpResponse = httpClient.execute(httpPost);
//			} else if (method == GET) {
//				// appending params to url
//				if (params != null) {
//					String paramString = URLEncodedUtils.format(params, "UTF-8");
//					url += "?" + paramString;
//				}
//
//				HttpGet httpGet = new HttpGet(url);
//
//				httpResponse = httpClient.execute(httpGet);
//
//			}
//			httpEntity = httpResponse.getEntity();
//			response = EntityUtils.toString(httpEntity);
//
//		} catch (UnsupportedEncodingException e) {
//		//	e.printStackTrace();
//		} catch (ClientProtocolException e) {
//		//	e.printStackTrace();
//		} catch (IOException e) {
//		//	e.printStackTrace();
//		}
//
//		return response;
//
//	}

	//////////////////////////////////////////////////////
	/*
 * Making request by using HttpURLConnection
 * @url - url to make request
 * @method - http request method
 * */
	public String makeRequest(String url, int method) {
		return this.makeRequest(url, method, null);
	}

	/*
 * Making request by using HttpURLConnection
 * @url - url to make request
 * @method - http request method
 * @params - http request params in jsonString
 * */
	public String makeRequest(String urlString, int method,
								  String jsonString) {
		if (method == POST){
			try {
				BufferedReader bufferedReader = null;

				URL url = new URL(urlString);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("Accept", "application/json");
				if (jsonString != null){
					Writer writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
					writer.write(jsonString);
					writer.close();
				}

				StringBuilder stringBuilder = new StringBuilder();
				bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String jsonStr;
				while ((jsonStr = bufferedReader.readLine())!= null){
					stringBuilder.append(jsonStr+"\n");
				}
				jsonStr = stringBuilder.toString().trim();
				response = jsonStr;
				bufferedReader.close();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (method == GET){
			URL url;
			HttpURLConnection urlConnection = null;
			try {
				url = new URL(urlString);
				urlConnection = (HttpURLConnection) url.openConnection();

				BufferedReader bufferedReader = null;
				StringBuilder stringBuilder = new StringBuilder();
				bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

				String jsonStr;
				while ((jsonStr = bufferedReader.readLine())!= null){
					stringBuilder.append(jsonStr);
				}
				jsonStr = stringBuilder.toString().trim();
				response = jsonStr;
				bufferedReader.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
			}
		}

		return response;

	}
	////////////////////////////////////////////////////////////////
}

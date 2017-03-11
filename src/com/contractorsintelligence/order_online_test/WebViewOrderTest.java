package com.contractorsintelligence.order_online_test;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.SessionManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewOrderTest extends Activity {
	private WebView webView;
	SessionManager session;
	String Url, data, result, email, select_order_test;
	HashMap<String, String> user;
	private static final String URL_STRING = "https://www.contractorsischool.com/mobile/checkoutpage.php";

	// private static final String URL_STRING =
	// "http://iblinfotech.com/testing.php";

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_order_test);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		select_order_test = getIntent().getExtras().getString("SelectTest");
		Log.e("selected", "orderTestName:-" + select_order_test);
		email = user.get(SessionManager.KEY_EMAIL);
		webView = (WebView) findViewById(R.id.webView_correction);
		Getdata();

		new WebViewLoad().execute();

	}

	private void Getdata() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				OrderOnlineTest.class);

		intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private class WebViewLoad extends AsyncTask<String, Void, Void> {
		private Dialog pDialog = new Dialog(WebViewOrderTest.this);

		protected void onPreExecute() {
			pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			pDialog.setContentView(R.layout.custom_image_dialog);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				//
				try {
					postData();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (Exception e) {
			//	e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Void unused) {
			pDialog.dismiss();
			// webView.loadUrl("http://docs.google.com/gview?embedded=true&url="
			// + Url);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setPluginState(PluginState.ON);
			webView.loadData(data, "text/html", "utf-8");

		}
	}

	public void postData() throws IOException, ClientProtocolException,
			JSONException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("emailtemp", email));
		nameValuePairs.add(new BasicNameValuePair("order_test_name",
				select_order_test));
		// nameValuePairs.add(new BasicNameValuePair("password", "bili56"));
		// nameValuePairs.add(new BasicNameValuePair("billingCity", "surat"));
		// nameValuePairs.add(new BasicNameValuePair("firstname", "a"));
		// nameValuePairs.add(new BasicNameValuePair("lastname", "23456"));
		// nameValuePairs.add(new BasicNameValuePair("companyName", "23456"));
		// nameValuePairs.add(new BasicNameValuePair("foo", "12345"));
		// nameValuePairs.add(new BasicNameValuePair("bar", "23456"));

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL_STRING);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpResponse response = httpclient.execute(httppost);
		data = new BasicResponseHandler().handleResponse(response);
		// HttpEntity entity = response.getEntity();
		// // // If the response does not enclose an entity, there is
		// // no need
		//
		// if (entity != null) {
		//
		// InputStream instream = entity.getContent();
		// result = RestClient.convertStreamToString(instream);
		// Log.i("Read from server", "Login" + result);
		// // Toast.makeText(this, result,
		// // Toast.LENGTH_LONG).show();
		// }
		// Log.w("Reponse webview....", "Reponse webview:-" + result);
	}

}

package com.contractorsintelligence.cis;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;

import org.json.JSONObject;

import java.util.HashMap;

public class Agreement extends Activity {
	private static final String TAG = Agreement.class.getSimpleName();
	Button btnAccept;
	CheckBox ckBox;
	static String email;
	String view_count;

	SessionManager session;

	HashMap<String, String> user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.agreement);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		// get data via the key
		String body = extras.getString("body");
		Log.e("body", "=>" + body);

		btnAccept = (Button) findViewById(R.id.btnAccept);
		// if button is clicked, close the custom dialog
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				//ApplicationhomeActivity.acceptAgreement();
				//postAgreement();
				new updateFirstTime().execute();

				finish();
			}
		});

		ckBox = (CheckBox) findViewById(R.id.checkBox1);
		ckBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ckBox.isChecked()) {
					btnAccept.setEnabled(true);
				} else {
					btnAccept.setEnabled(false);
				}
			}
		});

		WebView mWebView = (WebView) findViewById(R.id.webview);
		String page="<html><body>"+body+"</body></html>";

		mWebView.loadData(page,"text/html", "UTF-8");
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);

				return true;
			}
		});
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class updateFirstTime extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			session = new SessionManager(getApplicationContext());
			user = session.getUserDetails();
			email = user.get(SessionManager.KEY_EMAIL);

			try {

				JSONObject json = new JSONObject();
				json.put("Customer_email", email);

				String uri = StaticData.link+"updateFirsttime.php";

				ServiceHandler sh = new ServiceHandler();
				// Making a request to url and getting response
				String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());
				session.changePassword(false);
				Log.d("Response: ", "> " + jsonStr);

			} catch (Exception e) {
				// Toast.makeText(this, "Request failed: " + t.toString(),
				// Toast.LENGTH_LONG).show();
				//e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

		}
	}

}

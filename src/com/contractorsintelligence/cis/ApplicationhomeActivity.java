package com.contractorsintelligence.cis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.application.ApplicationActivity;
import com.contractorsintelligence.online_test_subscription.YourTestSubscriptionApp;
import com.contractorsintelligence.order_online_test.OrderOnlineTest;
import com.contractorsintelligence.settings.SettingsActivity;
import com.crittercism.app.Crittercism;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ApplicationhomeActivity extends Activity implements OnClickListener {
	Button btn_application, btn_your_test_subscription, btn_order_online_test,btn_settings, btn_logout;
	Controller aController;
	AsyncTask<Void, Void, Void> mRegisterTask;
	JSONArray Review_CountJson = null;
	Typeface Set_font;
	TextView txt_title;
	String url;
	static String email;
	String view_count;

	SessionManager session;

	HashMap<String, String> user;

	private static final String TAG_View = "applicationview";
	private static final String TAG_number_count = "application_view";

	String isFirstTime;
	String result;
	String Response_code;
	static AlertDialog alertDialog;

	static String  new_pass;
	static String confirm_new_pass;
	String  current_email, student_current_pass,
			student_id;
	String account_type, fname, lname;
	static Context context;

	private static final String TAG = ApplicationhomeActivity.class.getSimpleName();
	String id;
	String revValue;
	String revDate;
	String revBody;
	String revFirsttime;

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "On Start .....");

		new getFirstTime().execute();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.applicationhome);

			/*if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			}*/

			getAndroidVersion();

			//TextView tvVersionName = (TextView) findViewById(R.id.tv_versionName);
			//tvVersionName.setText(myVersionName);

			setContent();
			SetFont();
			clickeEvent();
			//new Document_Review().execute(); //?????????????
			Log.e("textview W", "W=" + txt_title.getWidth());
			Log.e("textview H", "H=" + txt_title.getHeight());


		} catch (Exception e) {
			//e.printStackTrace();
		}

		Crittercism.initialize(getApplicationContext(), "547b6c510729df1a15000005");



	}
	// Gcm Complete

	public void showAgreementDialog(){
		final Button btnAccept;
		final CheckBox ckBox;
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		btnAccept = (Button) dialog.findViewById(R.id.btnAccept);
		// if button is clicked, close the custom dialog
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				acceptAgreement();

			}
		});

		ckBox = (CheckBox) dialog.findViewById(R.id.checkBox1);
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

		WebView mWebView = (WebView) dialog.findViewById(R.id.webview);
		mWebView.loadUrl("file:///android_asset/agreement.html");
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);

				return true;
			}
		});

		dialog.show();
	}

	@SuppressLint("NewApi")
	public static void acceptAgreement(){
		postAgreement();
	}


	public static void postAgreement() throws NullPointerException {
		// TODO Auto-generated method stub

//		try {
//			Log.d("data", "sent");
//			JSONObject json = new JSONObject();
//			json.put("email", email);
//			// json.put("password", pass);
//
//			HttpParams httpParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
//			HttpConnectionParams.setSoTimeout(httpParams, 10000);
//			HttpClient client = new DefaultHttpClient(httpParams);
//
//			String url = StaticData.link + "getFirsttimeIOS.php";
//
//			HttpPost request = new HttpPost(url);
//			request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
//			request.setHeader("json", json.toString());
//			HttpResponse response = client.execute(request);
//			HttpEntity entity = response.getEntity();
//			// If the response does not enclose an entity, there is no need
//
//			if (entity != null) {
//
//				InputStream instream = entity.getContent();
//				String result = MainActivity.RestClient.convertStreamToString(instream);
//				Log.i("Read from server", "Login" + result);
//				// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//			}
//
//		} catch (Throwable t) {
//			// Toast.makeText(this, "Request failed: " + t.toString(),
//			// Toast.LENGTH_LONG).show();
//		}
//		try {
//			//Response_code = result;
//		} catch (Exception e) {
//			//e.printStackTrace();
//		}

	}

	public void changePassword(){

		if (StaticData.isNetworkConnected(getApplicationContext())) {

			LayoutInflater li = LayoutInflater.from(this);
			View promptsView = li.inflate(R.layout.update_password, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			//final EditText et_current_pass = (EditText) promptsView.findViewById(R.id.et_old_password);
			final EditText et_new_pass = (EditText) promptsView.findViewById(R.id.et_new_password);
			final EditText et_new_confirm_pass = (EditText) promptsView.findViewById(R.id.et_new_conform_pass);
			final Button btn_change_pass_ok = (Button) promptsView.findViewById(R.id.btn_ok);
			final Button btn_change_pass_cancel = (Button) promptsView.findViewById(R.id.btn_cancel);
			btn_change_pass_ok
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							//current_pass = et_current_pass.getText().toString();
							new_pass = et_new_pass.getText().toString();
							confirm_new_pass = et_new_confirm_pass.getText().toString();
							//Log.d("Old", "Student pass:-"+ student_current_pass);
							//Log.d("current", "pass:-" + current_pass);
							Log.d("new", "pass:-" + new_pass);
							Log.d("new conform", "pass:-" + confirm_new_pass);
							/*if (current_pass.length() <= 0|| current_pass.equals("")) {
								et_current_pass.setError("Enter Current Password");
							} else */

							if (new_pass.length() <= 0
									|| new_pass.equals("")) {
								et_new_pass
										.setError("Enter New Password");
							} else if (confirm_new_pass.length() <= 0
									|| confirm_new_pass.equals("")) {
								et_new_confirm_pass.setError("Enter Confirm New Password");
							} else {

								if (StaticData.isNetworkConnected(ApplicationhomeActivity.this)) {

									if (new_pass.equals(confirm_new_pass)) {

										new ChangePass().execute();

									} else {
										et_new_confirm_pass.setError("Password Is Not match");
									}

								} else {
									Toast.makeText(
											context,
											"Not connected to Internet",
											Toast.LENGTH_SHORT).show();
								}
							}

						}
					});

			// create alert dialog
			alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
			btn_change_pass_cancel
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							alertDialog.cancel();

						}
					});
		} else {
			Toast.makeText(context,
					"Not connected to Internet", Toast.LENGTH_SHORT)
					.show();
		}

	}


	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
		// finish();
	};

	private void setContent() {

		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		email = user.get(SessionManager.KEY_EMAIL);
		txt_title = (TextView) findViewById(R.id.txt_title);
		url = StaticData.link + "countapplicationview.php?email=" + email;
		btn_application = (Button) findViewById(R.id.btn_application);
		btn_your_test_subscription = (Button) findViewById(R.id.btn_your_test_subscription);
		btn_order_online_test = (Button) findViewById(R.id.btn_order_online_test);
		btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_logout = (Button) findViewById(R.id.btn_logout);
	}

	private void SetFont() {
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_title.setTypeface(Set_font);
	}

	private void clickeEvent() {
		btn_application.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_your_test_subscription.setOnClickListener(this);
		btn_order_online_test.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.btn_application:
				// btn_demo_practice.setBackgroundResource(R.drawable.btn_btn1_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {
					ApplicationActivity.i0_select = 0;
					ApplicationActivity.i1_unselect = 1;
					ApplicationActivity.i2_unselect = 2;
					ApplicationActivity.i3_unselect = 3;
					Intent demo_i = new Intent(this, ApplicationActivity.class);
					// demo_i.setFlags(demo_i.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(demo_i);

					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.btn_your_test_subscription:
				// btn_your_test_subscription.setBackgroundResource(R.drawable.btn_your_test_subscription_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent i = new Intent(this, YourTestSubscriptionApp.class);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.btn_order_online_test:
				// btn_order_online_test.setBackgroundResource(R.drawable.btn_order_online_test_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent order_i = new Intent(this, OrderOnlineTest.class);
					startActivity(order_i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.btn_settings:
				// btn_settings.setBackgroundResource(R.drawable.btn_settings_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent settings_i = new Intent(this, SettingsActivity.class);
					startActivity(settings_i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT).show();
				}

				break;

			case R.id.btn_logout:
				// btn_settings.setBackgroundResource(R.drawable.btn_settings_hover);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Logout")
						.setMessage("Are you sure you want to log out?")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
														int which) {
										// Yes button clicked, do something
										session.logoutUser();
										finish();
									}
								}).setNegativeButton("No", null) // Do nothing on no
						.show();

				break;
		}
	}

	private class Document_Review extends AsyncTask<String, Void, Void> {
		Dialog pDialog = new Dialog(ApplicationhomeActivity.this);

		protected void onPreExecute() {
			try {

				pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				pDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));

				pDialog.setContentView(R.layout.custom_image_dialog);
				pDialog.setCancelable(false);
				pDialog.show();

			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(String... params)
				throws NullPointerException {
			// TODO Auto-generated method stub
			try {
				ServiceHandler sh = new ServiceHandler();

				// Making a request to url and getting response
				//String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
				String jsonStr = sh.makeRequest(url, ServiceHandler.GET);
				//String jsonStr = sh.makeServiceCall(url.replaceAll(" ","%20"), ServiceHandler.GET);

				Log.d("Response: ", "> " + jsonStr);

				JSONObject jsonObj = new JSONObject(jsonStr);

				// Getting JSON Array node
				Review_CountJson = jsonObj.getJSONArray(TAG_View);

				// looping through All Contacts

				JSONObject c = Review_CountJson.getJSONObject(0);

				view_count = c.getString(TAG_number_count);

			} catch (Exception e) {
				//e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Void unused) throws NullPointerException {
			try {

				if (pDialog.isShowing())
					pDialog.dismiss();
				/**
				 * Updating parsed JSON data into ListView
				 * */
				if (view_count == null) {
					btn_application.setText("");
				}
				else {
					//BRX
					//btn_application.setText("(" + view_count + ")");
				}


			} catch (Exception e) {
				//e.printStackTrace();
			}

		}
	}

	private class ChangePass extends AsyncTask<String, Void, Void> {

		private Dialog Dialog = new Dialog(ApplicationhomeActivity.this);
		String update_pass_result;

		protected void onPreExecute() {
			// Dialog.setMessage("Please wait.....");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			Dialog.setContentView(R.layout.custom_image_dialog);
			Dialog.show();
			Dialog.setCancelable(false);
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {
			postData();
			Log.w("....", "...." + Response_code);
			try {
				JSONObject jobj = new JSONObject(Response_code);
				update_pass_result = jobj.getString("ResponseCode");

				Log.e("Result", "result...." + update_pass_result);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {

			try {
				if (Dialog.isShowing())
					Dialog.dismiss();

				updatePass_result();
			} catch (Exception e) {
				//e.printStackTrace();
			}

		}

		public void updatePass_result() {
			// TODO Auto-generated method stub
			if (update_pass_result.endsWith("1")) {

				// session.createLoginSession(et_email.getText().toString());

				Toast.makeText(getApplicationContext(),
						"Password Change Successful...", Toast.LENGTH_SHORT)
						.show();

				session.createLoginSession(current_email, account_type,student_id, new_pass,fname,lname);
				session.ChangedPassword();
				alertDialog.cancel();

				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();


			} else if (update_pass_result.endsWith("2")) {

				Toast.makeText(getApplicationContext(),
						"Could not change pass...", Toast.LENGTH_SHORT).show();

			}
		}
	}

	public void postData() {

		try {

			JSONObject json = new JSONObject();
			json.put("email", email);
			json.put("new_password", new_pass);

			String uri = StaticData.link + "passwordChange.php";

			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());
			result = jsonStr;

		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
			Log.d("Request failed", "fail:-" + t.toString());
		}

		// Show response on activity
		Log.w("Reponse....", "Reponse:-" + result);
		Response_code = result;
	}


	public String getAndroidVersion() {
		String release = Build.VERSION.RELEASE;
		int sdkVersion = Build.VERSION.SDK_INT;
		return "Android SDK: " + sdkVersion + " (" + release +")";
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class getFirstTime extends AsyncTask<Void, Void, Void> {

//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
		Dialog pDialog = new Dialog(ApplicationhomeActivity.this);
		@Override
		protected void onPreExecute() {
			try {

				pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				pDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));

				pDialog.setContentView(R.layout.custom_image_dialog);
				pDialog.setCancelable(false);
				pDialog.show();

			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			session = new SessionManager(getApplicationContext());
			user = session.getUserDetails();
			email = user.get(SessionManager.KEY_EMAIL);



			try {

//				BufferedReader bufferedReader = null;
//				JSONObject json = new JSONObject();
//			    json.put("Customer_email", email);
//				String uri = StaticData.link+"getFirsttimedb.php";
//				URL url = new URL(uri);
//				HttpURLConnection con = (HttpURLConnection)url.openConnection();
//				con.setDoOutput(true);
//				con.setRequestMethod("POST");
//				con.setRequestProperty("Content-Type", "application/json");
//				con.setRequestProperty("Accept", "application/json");
//				Writer writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
//				writer.write(json.toString());
//				writer.close();
//
//				StringBuilder stringBuilder = new StringBuilder();
//				bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
////				HttpParams httpParams = new BasicHttpParams();
////				HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
////				HttpConnectionParams.setSoTimeout(httpParams, 10000);
////				HttpClient client = new DefaultHttpClient(httpParams);
////
////				//String url = "http://contractorsischool.com/mobile-services/getFirsttimedb.php?Customer_email="+email;
////
////				HttpPost request = new HttpPost(url);
////				request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF-8")));
////				request.setHeader("json", json.toString());
////				HttpResponse response = client.execute(request);
//
//
//
////				HttpEntity entity = response.getEntity();
//
//				String jsonStr;
//				while ((jsonStr = bufferedReader.readLine())!= null){
//					stringBuilder.append(jsonStr+"\n");
//				}
//				jsonStr = stringBuilder.toString().trim();

				JSONObject json = new JSONObject();
			    json.put("Customer_email", email);
				String uri = StaticData.link+"getFirsttimedb.php";

				ServiceHandler sh = new ServiceHandler();
				// Making a request to url and getting response
				String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());

				Log.d("Response: ", "> " + jsonStr);

				try {
					JSONObject jobj = new JSONObject(jsonStr);
					id = jobj.getString("id");
					revValue = jobj.getString("Firsttime_value_of_student_value");
					revDate = jobj.getString("Agreement_rev_date");
					revBody = jobj.getString("Agreement_body");
					revFirsttime = jobj.getString("Firsttime");


					Log.v(TAG, "=> "+id);
					Log.d(TAG, "=> "+revValue);
					Log.i(TAG, "=> "+revDate);
					Log.w(TAG, "=> "+revBody);
					Log.e(TAG, "=> "+revFirsttime);

					// if (response != null) Log.i(TAG, "response " + response.getStatusLine().toString());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (Exception e) {
				// Toast.makeText(this, "Request failed: " + t.toString(),
				// Toast.LENGTH_LONG).show();
				//e.printStackTrace();
			}

//            HttpClient client = new DefaultHttpClient();
//            //HttpPost post = new HttpPost("http://contractorsischool.com/mobile-services/getFirsttimedb.php?Customer_email=leomanxp@yahoo.com");
//			HttpPost post = new HttpPost("http://contractorsischool.com/mobile-services/getFirsttimedb.php?Customer_email="+email);
//
//            try {
//                HttpResponse response = client.execute(post);
//                String responseStr = EntityUtils.toString(response.getEntity());
//                try {
//                    JSONObject jobj = new JSONObject(responseStr);
//                    id = jobj.getString("id");
//                    revValue = jobj.getString("Firsttime_value_of_student_value");
//                    revDate = jobj.getString("Agreement_rev_date");
//                    revBody = jobj.getString("Agreement_body");
//                    revFirsttime = jobj.getString("Firsttime");
//
//                    /**
//                     Log.v(); // Verbose
//                     Log.d(); // Debug
//                     Log.i(); // Info
//                     Log.w(); // Warning
//                     Log.e(); // Error
//                     */
//
//                    Log.v(TAG, "=> "+id);
//                    Log.d(TAG, "=> "+revValue);
//                    Log.i(TAG, "=> "+revDate);
//                    Log.w(TAG, "=> "+revBody);
//                    Log.e(TAG, "=> "+revFirsttime);
//
//                    if (response != null) Log.i(TAG, "response " + response.getStatusLine().toString());
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            } catch (ClientProtocolException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//
//            } catch (Exception e) {}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);


			try {

				if (pDialog.isShowing())
					pDialog.dismiss();
			} catch (Exception e) {
				//e.printStackTrace();
			}

			if (revValue.equalsIgnoreCase("0")){
				if (!session.isChangedPassword())
				{
					session.changePassword(true);
					changePassword();
				}
			}else {

				Intent agreement = new Intent(getApplicationContext(), Agreement.class);
				agreement.putExtra("body",revBody);
				startActivity(agreement);
			}
		}
	}


}

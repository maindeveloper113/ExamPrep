package com.contractorsintelligence.cis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends Activity implements OnClickListener {

	EditText et_email, et_pass;
	Button btn_login, btn_register;
	TextView txt_welcome, txt_forgotpass, txt_title;
	public static String email, pass;
	String Response_code;
	String login_result, result;
	Typeface Set_font;
	SessionManager session;
	String account_type, student_id, student_pass, f_name, l_name;
	JSONArray get_accounttype = null;
	JSONObject jobj;
	JSONObject jobj_userdetails;
	AlertDialog alertDialog1, alertDialog2;
	Controller aController;
	AsyncTask<Void, Void, Void> mRegisterTask;
    String account_isFirstTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.userlogin);

			setContent();
			SetFont();
			clickeEvent();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
		finish();
	}

	private void setContent() {
		// TODO Auto-generated method stub
		session = new SessionManager(getApplicationContext());
		et_email = (EditText) findViewById(R.id.et_email);
		txt_welcome = (TextView) findViewById(R.id.txt_welcome);
		txt_forgotpass = (TextView) findViewById(R.id.txt_forgotpass);
		txt_title = (TextView) findViewById(R.id.txt_title);
		et_pass = (EditText) findViewById(R.id.et_pass);
		btn_register = (Button) findViewById(R.id.btn_signup);
		btn_login = (Button) findViewById(R.id.btn_login);
		
		// et_email.setText("demo@demo.com");
		// et_email.setInputType(InputType.TYPE_CLASS_TEXT |
		// InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
		et_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
		//et_email.setText("leomanxp@yahoo.com");
// 		et_pass.setText("demo");
		//et_pass.setText("1");

	}

	private void SetFont() {
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		Typeface Set_font_welcome = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYB.TTF");
		btn_register.setTypeface(Set_font);
		btn_login.setTypeface(Set_font);
		txt_forgotpass.setTypeface(Set_font);
		txt_welcome.setTypeface(Set_font_welcome);
		txt_title.setTypeface(Set_font);
		et_email.setTypeface(Set_font);
		et_pass.setTypeface(Set_font);
	}

	private void clickeEvent() {
		// TODO Auto-generated method stub
		btn_register.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		txt_forgotpass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
		switch (v.getId()) {

		case R.id.btn_login:
			try {
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                email = et_email.getText().toString().toLowerCase();
				pass = et_pass.getText().toString();

				if (email.length() <= 0 || email.equals("")) {
					et_email.setError("Invalid Email");
				} else if (pass.length() <= 0 || pass.equals("")) {
					et_pass.setError("Invalid Password");
				} else {
					if (StaticData.isNetworkConnected(getApplicationContext())) {
						try {
							new LoginOperation().execute();
						} catch (Exception e) {
							//e.printStackTrace();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Not connected to Internet", Toast.LENGTH_SHORT)
								.show();
					}
				}

			} catch (Exception e) {
				//e.printStackTrace();
			}
			break;
		case R.id.btn_signup:

			Intent regi_i = new Intent(this, RegisterActivity.class);
			regi_i.setFlags(regi_i.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(regi_i);
			finish();
			break;
		case R.id.txt_forgotpass:

			Intent forgotpass_i = new Intent(this, ForgotPasswordActivity.class);
			forgotpass_i.setFlags(forgotpass_i.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(forgotpass_i);
			finish();
			break;
		}
	}

	private class LoginOperation extends AsyncTask<String, Void, Void> {

		Dialog pDialog = new Dialog(LoginActivity.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Authanticating...");
			pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));
			pDialog.setContentView(R.layout.custom_image_dialog);
			pDialog.show();
			pDialog.setCancelable(false);
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			try {

				postData();

				Log.d("....", "...." + Response_code);

				jobj = new JSONObject(Response_code);
				login_result = jobj.getString("Result");

				Log.d("Result", "...." + login_result);
				jobj_userdetails = new JSONObject(Response_code);
				Log.d("UserDetails", "Details:--" + jobj_userdetails);
				get_accounttype = jobj_userdetails.getJSONArray("userdetail");

				Log.d("Only userTrue", "GetUserdetails" + get_accounttype);

				JSONObject jsnaccount_type = get_accounttype.getJSONObject(0);
				Log.d("JSONObject Accountytpe", "type:--" + jsnaccount_type);
				account_type = jsnaccount_type.getString("account_type");
				student_id = jsnaccount_type.getString("customerid");
				student_pass = jsnaccount_type.getString("password");
				f_name = jsnaccount_type.getString("name");
				l_name = jsnaccount_type.getString("lname");

                account_isFirstTime = jsnaccount_type.getString("firsttime");
                Log.d("account_isFirstTime", ":--" + account_isFirstTime);
				// {"userdetail":[{"customerid":"3275","account_type":"1","Result":"True"}
				Log.d("StudentId", "Id:--" + student_id);
				Log.d("account_type", "type:--" + account_type);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Void unused) {

			try {
				if (pDialog.isShowing())
					pDialog.dismiss();
				Login_result();
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
			}

		}
	}

	@SuppressWarnings("deprecation")
	private void Login_result() {
		// TODO Auto-generated method stub

		if (login_result.equals("True")) {
			Log.w("Login Result..", "In:--" + login_result);
			try {
				// alertDialog1 = new AlertDialog.Builder(LoginActivity.this)
				// .create();
				// // Setting Dialog Title
				// alertDialog1.setTitle(" Login Successfully...");
				// // Setting Dialog Message
				// // alertDialog.setMessage("Login Successfully...");
				// // Setting Icon to Dialog
				// alertDialog1.setIcon(R.drawable.img_green);
				// // Showing Alert Message
				// alertDialog1.show();

				// Setting OK Button
				// alertDialog1.setButton("OK",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int which) {
				// //
				// Toast.makeText(getApplicationContext(),"Login Successfully...",
				// // Toast.LENGTH_SHORT).show();
				// LoginCheck();
				// }
				//
				// });

				// Thread splashThread = new Thread() {
				// public void run() {
				//
				// synchronized (this) {
				// try {
				// wait(3000);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// }
				//
				// alertDialog1.cancel();
				// }
				// };
				//
				// splashThread.start();
				Toast.makeText(getApplicationContext(),
						"Login Successfully...", Toast.LENGTH_SHORT).show();
				LoginCheck();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		} else if (login_result.equals("2")) {

			Toast.makeText(getApplicationContext(),
					"Incurrect Username and Password...", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(getApplicationContext(),
					"your login failed try again...", Toast.LENGTH_SHORT)
					.show();
			// alertDialog2 = new
			// AlertDialog.Builder(LoginActivity.this).create();
			//
			// // Setting Dialog Title
			// alertDialog2.setTitle(" your login failed try again...");
			//
			// // Setting Dialog Message
			// // alertDialog.setMessage("Login Successfully...");
			//
			// // Setting Icon to Dialog
			// alertDialog2.setIcon(R.drawable.img_red);

			// Setting OK Button
			// alertDialog2.setButton("OK", new
			// DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog, int which) {
			// dialog.cancel();
			//
			// //
			// Toast.makeText(getApplicationContext(),"Incurrect Username and Password.. .. . ",Toast.LENGTH_SHORT).show();
			//
			// }
			// });

			// Showing Alert Message
			// alertDialog2.show();
			// Thread splashThread = new Thread() {
			// public void run() {
			//
			// synchronized (this) {
			// try {
			// wait(5000);
			// } catch (InterruptedException e) {
			//
			// e.printStackTrace();
			// }
			// }
			//
			// alertDialog2.cancel();
			// }
			// };
			//
			// splashThread.start();
		}
	}

	private void LoginCheck() {

        if (account_type.equals("1")) {

            Log.d("account_isFirstTime", ":--" + account_isFirstTime);

            Log.d("In", "login");
            session.createLoginSession(et_email.getText().toString().toLowerCase(),account_type, student_id, student_pass, f_name, l_name);
            // email_id =
            // user.get(SessionManager.KEY_EMAIL);
            Intent i = new Intent(LoginActivity.this,ApplicationhomeActivity.class);
            i.putExtra("keyName",account_isFirstTime);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

            et_email.setText("");
            et_pass.setText("");
            gcmregister();

        } else if (account_type.equals("0")) {
            session.createLoginSession(et_email.getText().toString().toLowerCase(),account_type, student_id, student_pass, f_name, l_name);
            Intent i = new Intent(LoginActivity.this, homeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            et_email.setText("");
            et_pass.setText("");
            finish();
            //gcmregister();

        }

    }

	private void gcmregister() {
		// TODO Auto-generated method stub
		// AndroidManifest.xml)
		aController = (Controller) getApplicationContext();

		// Check if Internet present
		if (!aController.isConnectingToInternet()) {

			// Internet Connection is not present
			aController.showAlertDialog(LoginActivity.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			return;
		}
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest permissions was properly set
		GCMRegistrar.checkManifest(this);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(Config.DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);
        GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
		// Check if regid already presents
		if (regId.equals("")) {

			// Register with GCM
			GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
			// GCMRegistrar.unregister(this);

		} else {

			// Device is already registered on GCM Server
			 if (GCMRegistrar.isRegisteredOnServer(this)) {

			// // Skips registration.
			 Toast.makeText(getApplicationContext(),
			 "Already registered with GCM Server", Toast.LENGTH_LONG).show();

			 } else {

			// Try to register again, but not in the UI thread.
			// It's also necessary to cancel the thread onDestroy(),
			// hence the use of AsyncTask instead of a raw thread.

			final Context context = this;
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {

					// Register on our server
					// On server creates a new user
					aController.register(context, email, regId);

					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}



			};

			// execute AsyncTask
			mRegisterTask.execute(null, null, null);
		    }
		 }
	}

	// Create a broadcast receiver to get message and show on screen
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String newMessage = intent.getExtras().getString(
					Config.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());

			// Display message on the screen
			// lblMessage.append(newMessage + "\n");

			// Toast.makeText(getApplicationContext(),
			// "Got Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};

	@Override
	protected void onDestroy() {
		// Cancel AsyncTask
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
//		try {
//			// Unregister Broadcast Receiver
//			//unregisterReceiver(mHandleMessageReceiver);
//
//			// Clear internal resources.
//			//GCMRegistrar.onDestroy(this);
//
//		} catch (Exception e) {
//			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
//		}
		super.onDestroy();
	}

	// Gcm Complete
	public void postData() throws NullPointerException {
		// TODO Auto-generated method stub

		try {
			Log.d("data", "sent");
			JSONObject json = new JSONObject();
			json.put("email", email);
			json.put("password", pass);

			String url = StaticData.link + "login.php";

			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeRequest(url, ServiceHandler.POST, json.toString());
			result = jsonStr;
		} catch (Throwable t) {
			//Toast.makeText(this, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
		}
		try {
			Response_code = result;
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}


    public JSONObject getJSONFromUrl(String url) {
        // Making HTTP request
        InputStream is = null;
        JSONObject jObj = null;
        String json = null;
        HttpResponse httpResponse = null;

        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpProtocolParams.setUseExpectContinue(params, true);
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient(params);
            HttpGet httpPost = new HttpGet( url);
            httpResponse = httpClient.execute( httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
//        } catch (UnsupportedEncodingException ee) {
//            Log.i("UnsupportedEncodingException", is.toString());
        } catch (ClientProtocolException e) {
            Log.i("ClientProtocolException", is.toString());
        } catch (IOException e) {
            Log.i("IOException...", is.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "utf-8"), 8); //old charset iso-8859-1
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            reader.close();
            json = sb.toString();
            Log.i("StringBuilder...", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (Exception e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            try {
                jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
            } catch (Exception e0) {
                Log.e("JSON Parser0", "Error parsing data [" + e0.getMessage()+"] "+json);
                Log.e("JSON Parser0", "Error parsing data " + e0.toString());
                try {
                    jObj = new JSONObject(json.substring(1));
                } catch (Exception e1) {
                    Log.e("JSON Parser1", "Error parsing data [" + e1.getMessage()+"] "+json);
                    Log.e("JSON Parser1", "Error parsing data " + e1.toString());
                    try {
                        jObj = new JSONObject(json.substring(2));
                    } catch (Exception e2) {
                        Log.e("JSON Parser2", "Error parsing data [" + e2.getMessage()+"] "+json);
                        Log.e("JSON Parser2", "Error parsing data " + e2.toString());
                        try {
                            jObj = new JSONObject(json.substring(3));
                        } catch (Exception e3) {
                            Log.e("JSON Parser3", "Error parsing data [" + e3.getMessage()+"] "+json);
                            Log.e("JSON Parser3", "Error parsing data " + e3.toString());
                        }
                    }
                }
            }
        }

        // return JSON String
        return jObj;

    }


}

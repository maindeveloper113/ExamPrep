package com.contractorsintelligence.cis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends Activity implements OnClickListener {

	Button btn_submit;
	String Response_code;
	TextView txt_msg, txt_title;
	String forgotpasswprd_result, email;
	EditText et_email;
	Typeface Set_font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpassword);

		setContent();
		clickeEvent();

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void clickeEvent() {
		// TODO Auto-generated method stub
		btn_submit.setOnClickListener(this);
	}

	private void setContent() {
		// TODO Auto-generated method stub
		txt_msg = (TextView) findViewById(R.id.txt_msg);
		txt_title = (TextView) findViewById(R.id.txt_title);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		et_email = (EditText) findViewById(R.id.et_email);
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_msg.setTypeface(Set_font);
		txt_title.setTypeface(Set_font);
		btn_submit.setTypeface(Set_font);
		et_email.setTypeface(Set_font);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_submit:
			try {
				email = et_email.getText().toString();

                /*if(emailValidator(et_email.getText().toString())){
                    Toast.makeText(this, "valid email address",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this, "invalid email address",
                            Toast.LENGTH_SHORT).show();

                }
                */

				if (email.length() <= 0 || email.equals("")) {
					et_email.setError("Invalid Email");
				} else {
					if (StaticData.isNetworkConnected(getApplicationContext())) {
						forgotpassword();
					} else {
						Toast.makeText(getApplicationContext(),
								"Not connected to Internet", Toast.LENGTH_SHORT)
								.show();
					}
				}

				// i = new Intent(ForgotPassword_Activity.this,
				// ForgotPasswordThankYou_Activity.class);
				// i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(i);
				break;
			} catch (Exception e) {
				//e.printStackTrace();
			}

		}
	}

	private void forgotpassword() {
		// TODO Auto-generated method stub

		new ForgotpasswordOperation().execute();
	}

	private class ForgotpasswordOperation extends AsyncTask<String, Void, Void> {

		//private ProgressDialog pDialog = new ProgressDialog(
			//	ForgotPasswordActivity.this);
        Dialog pDialog = new Dialog(ForgotPasswordActivity.this);
		protected void onPreExecute() {
			// Dialog.setMessage("Please Wait...");
           // Dialog.getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
			//Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			//Dialog.setContentView(R.layout.custom_image_dialog);
           // Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			//Dialog.show();
			//Dialog.setCancelable(false);

            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));

            pDialog.setContentView(R.layout.custom_image_dialog);
            pDialog.show();
            pDialog.setCancelable(false);
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {
			postData();
			Log.w("....", "...." + Response_code);
			try {

				JSONObject jobj = new JSONObject(Response_code);
				forgotpasswprd_result = jobj.getString("ResponseCode");
				Log.e("Forgotpasswprd Result", "...." + forgotpasswprd_result);

			} catch (JSONException e) {
				//e.printStackTrace();
			} catch (NullPointerException n) {
				//n.printStackTrace();
			}

			return null;
		}

		private void postData() {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			email = et_email.getText().toString().toLowerCase();

			String result = "";
			try {
				JSONObject json = new JSONObject();
				json.put("email", email);

				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
				HttpConnectionParams.setSoTimeout(httpParams, 10000);
				HttpClient client = new DefaultHttpClient(httpParams);

				String url = StaticData.link + "forgot_password.php";

				HttpPost request = new HttpPost(url);
				request.setEntity(new ByteArrayEntity(json.toString().getBytes(
						"UTF8")));
				request.setHeader("json", json.toString());
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();
				// If the response does not enclose an entity, there is no need

				if (entity != null) {
					InputStream instream = entity.getContent();

					result = MainActivity.RestClient.convertStreamToString(instream);
					Log.i("Read from server", result);
					// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				}
			} catch (Throwable t) {
				// Toast.makeText(this, "Request failed: " + t.toString(),
				// Toast.LENGTH_LONG).show();
			}

			Response_code = result;

		}

		protected void onPostExecute(Void unused) {
            pDialog.dismiss();
			Forgotpassword_result();

		}

		private void Forgotpassword_result() {
			// TODO Auto-generated method stub
			Log.w("Login Result..", "l" + forgotpasswprd_result);

			if (forgotpasswprd_result.equals("1")) {

				Toast.makeText(getApplicationContext(),
						"Your Password Email Address Send Successfully...",
						Toast.LENGTH_SHORT).show();
				txt_msg.setText("Your password has been sent to the email address that you entered in the form.");
				txt_msg.setTextColor(Color.parseColor("#008017"));
				txt_msg.setTextSize(15);
				// String android_id = Secure.getString(getApplicationContext()
				// .getContentResolver(), Secure.ANDROID_ID);
				//
				// Log.w("Device ID:-", ".." + android_id);
			} else {
				Toast.makeText(getApplicationContext(),
						"Email does Not exists.... . ", Toast.LENGTH_SHORT)
						.show();
				txt_msg.setText("Email Does Not exists  Currect Your Email.");
				txt_msg.setTextColor(Color.parseColor("#cc3333"));
				txt_msg.setTextSize(15);
			}

		}

	}

    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();

    }
}

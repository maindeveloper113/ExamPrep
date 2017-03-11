package com.contractorsintelligence.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.ApplicationhomeActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;
import com.contractorsintelligence.cis.homeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SettingsActivity extends Activity implements OnClickListener {
	Button btn_change_customer_info, btn_update_password,
			btn_cancel_your_subscription, btn_logout;

	Typeface Set_font;
	TextView txt_title;
	SessionManager session;
	String account_type, fname, lname;
	HashMap<String, String> user;
	String current_pass, new_pass, confo_new_pass;
	String Response_code, result, current_email, student_current_pass,
			student_id;
	AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.settings_activity);
			setContent();
			SetFont();
			clickeEvent();
		} catch (Exception e) {
		//	e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {
		try {

			if (account_type.equals("1")) {

				Intent intent = new Intent(getApplicationContext(),
						ApplicationhomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();

			} else if (account_type.equals("0")) {

				Intent intent = new Intent(getApplicationContext(),
						homeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();

			}

		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private void setContent() {
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		account_type = user.get(SessionManager.KEY_AccountType);
		fname = user.get(SessionManager.KEY_F_Name);
		lname = user.get(SessionManager.KEY_L_Name);
		current_email = user.get(SessionManager.KEY_EMAIL);
		student_current_pass = user.get(SessionManager.KEY_StudentPassword);
		student_id = user.get(SessionManager.KEY_StudentId);
		txt_title = (TextView) findViewById(R.id.txt_title);

		btn_change_customer_info = (Button) findViewById(R.id.btn_change_customer_info);
		btn_update_password = (Button) findViewById(R.id.btn_update_password);
		btn_cancel_your_subscription = (Button) findViewById(R.id.btn_cancel_your_subscription);

		btn_logout = (Button) findViewById(R.id.btn_logout);
	}

	private void SetFont() {
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_title.setTypeface(Set_font);
	}

	private void clickeEvent() {
		btn_change_customer_info.setOnClickListener(this);
		btn_update_password.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_cancel_your_subscription.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		try {

			switch (v.getId()) {

			case R.id.btn_change_customer_info:
				// btn_demo_practice.setBackgroundResource(R.drawable.btn_btn1_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {

					Intent demo_i = new Intent(this, ChangeCustomerInfo.class);
					startActivity(demo_i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.btn_update_password:
				// btn_demo_practice.setBackgroundResource(R.drawable.btn_btn1_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {

					LayoutInflater li = LayoutInflater.from(this);
					View promptsView = li.inflate(R.layout.changepassword, null);

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							this);

					// set prompts.xml to alertdialog builder
					alertDialogBuilder.setView(promptsView);

					final EditText et_current_pass = (EditText) promptsView
							.findViewById(R.id.et_old_password);
					final EditText et_new_pass = (EditText) promptsView
							.findViewById(R.id.et_new_password);
					final EditText et_new_confo_pass = (EditText) promptsView
							.findViewById(R.id.et_new_conform_pass);
					final Button btn_change_pass_ok = (Button) promptsView
							.findViewById(R.id.btn_ok);
					final Button btn_change_pass_cancel = (Button) promptsView
							.findViewById(R.id.btn_cancel);
					btn_change_pass_ok
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {

									current_pass = et_current_pass.getText()
											.toString();
									new_pass = et_new_pass.getText().toString();
									confo_new_pass = et_new_confo_pass
											.getText().toString();
									Log.d("Old", "Student pass:-"
											+ student_current_pass);
									Log.d("current", "pass:-" + current_pass);
									Log.d("new", "pass:-" + new_pass);
									Log.d("new conform", "pass:-"
											+ confo_new_pass);
									if (current_pass.length() <= 0
											|| current_pass.equals("")) {
										et_current_pass
												.setError("Enter Current Password");
									} else if (new_pass.length() <= 0
											|| new_pass.equals("")) {
										et_new_pass
												.setError("Enter New Password");
									} else if (confo_new_pass.length() <= 0
											|| confo_new_pass.equals("")) {
										et_new_confo_pass
												.setError("Enter Conform New Password");
									} else {

										if (StaticData
												.isNetworkConnected(getApplicationContext())) {
											if (student_current_pass
													.equals(current_pass)) {
												if (new_pass
														.equals(confo_new_pass)) {
													new ChangePass().execute();

												} else {
													et_new_confo_pass
															.setError("Password Is Not match");
												}

											} else {
												et_current_pass
														.setError("Old pass Is InCorrect");
											}

										} else {
											Toast.makeText(
													getApplicationContext(),
													"Not connected to Internet",
													Toast.LENGTH_SHORT).show();
										}
									}

								}
							});
					// set dialog message
					// alertDialogBuilder
					// .setCancelable(false)
					// .setPositiveButton("OK",
					// new DialogInterface.OnClickListener() {
					// public void onClick(DialogInterface dialog,int id) {
					// // get user input and set it to result
					// // edit text
					// String current_pass,new_pass,confo_new_pass;
					// current_pass = et_current_pass.getText().toString();
					// }
					// })
					// .setNegativeButton("Cancel",
					// new DialogInterface.OnClickListener() {
					// public void onClick(DialogInterface dialog,int id) {
					// dialog.cancel();
					// }
					// });

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
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;

			case R.id.btn_cancel_your_subscription:
				// btn_cancel_your_subscription.setBackgroundResource(R.drawable.btn_your_test_subscription_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent i = new Intent(this, CancelSubscribtionTest.class);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;

			case R.id.btn_logout:
				// btn_settings.setBackgroundResource(R.drawable.btn_settings_hover);
				if (StaticData.isNetworkConnected(getApplicationContext())) {

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Logout")
							.setMessage("Are you sure you want to log out?")
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// Yes button clicked, do something
											session.logoutUser();
											finish();
										}
									}).setNegativeButton("No", null) // Do
																		// nothing
																		// on no
							.show();

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			}

		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private class ChangePass extends AsyncTask<String, Void, Void> {

		private Dialog Dialog = new Dialog(SettingsActivity.this);
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
			//	e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {

			try {
				if (Dialog.isShowing())
					Dialog.dismiss();

				updatePass_result();
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		public void updatePass_result() {
			// TODO Auto-generated method stub
			if (update_pass_result.endsWith("1")) {

				// session.createLoginSession(et_email.getText().toString());

				Toast.makeText(getApplicationContext(),
						"Password Change Successful...", Toast.LENGTH_SHORT)
						.show();

				session.createLoginSession(current_email, account_type,
						student_id, new_pass,fname,lname);
				alertDialog.cancel();
				Intent intent = new Intent(getApplicationContext(),
						SettingsActivity.class);
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

			Log.d("e", "email:-" + current_email);

			Log.d("current", "pass sent:-" + current_pass);
			Log.d("new", "pass sent:-" + new_pass);
			Log.d("new conform", "pass sent:-" + confo_new_pass);
			JSONObject json = new JSONObject();
			json.put("email", current_email);
			json.put("new_password", new_pass);

			String uri = StaticData.link + "changepassword.php";

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

}

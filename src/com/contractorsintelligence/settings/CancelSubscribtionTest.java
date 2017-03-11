package com.contractorsintelligence.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CancelSubscribtionTest extends Activity implements OnClickListener {
	List<String> documnet_list;
	Button btn_logout, btn_can_sub;
	private static final String TAG_View = "Acceslevel";
	private static final String TAG_accessLev = "accessLev";
	private static final String TAG_accessLev2 = "accessLev2";
	private static final String TAG_accessLev3 = "accessLev3";
	private static final String TAG_accessLev4 = "accessLev4";
	private static final String TAG_accessLev5 = "accessLev5";

	private static final String TAG_cancel_accessLev = "Canceled_accesslev1";
	private static final String TAG_cancel_accessLev2 = "Canceled_accesslev2";
	private static final String TAG_cancel_accessLev3 = "Canceled_accesslev3";
	private static final String TAG_cancel_accessLev4 = "Canceled_accesslev4";
	private static final String TAG_cancel_accessLev5 = "Canceled_accesslev5";

	private static final String TAG_Purge_date_accessLev = "Purge_date_accesslev1";
	private static final String TAG_Purge_date_accessLev2 = "Purge_date_accesslev2";
	private static final String TAG_Purge_date_accessLev3 = "Purge_date_accesslev3";
	private static final String TAG_Purge_date_accessLev4 = "Purge_date_accesslev4";
	private static final String TAG_Purge_date_accessLev5 = "Purge_date_accesslev5";
	String flag1, flag2, flag3, flag4, flag5;
	TextView txt_title, txt_accesslev1, txt_accesslev2, txt_accesslev3,
			txt_accesslev4, txt_accesslev5, txt_expires_accesslev1,
			txt_expires_accesslev2, txt_expires_accesslev3,
			txt_expires_accesslev4, txt_expires_accesslev5;
	CheckBox chk_accesslev1, chk_accesslev2, chk_accesslev3, chk_accesslev4,
			chk_accesslev5;
	String accessLev, accessLev2, accessLev3, accessLev4, accessLev5;
	String cancel_accessLev, cancel_accessLev2, cancel_accessLev3,
			cancel_accessLev4, cancel_accessLev5;

	String Purge_date_accessLev, Purge_date_accessLev2, Purge_date_accessLev3,
			Purge_date_accessLev4, Purge_date_accessLev5;
	String url, CurrentDate;
	Typeface set_font;

	String Response_code, result, email, canSub_result;
	String accesslevel1 = "", accesslevel2 = "", accesslevel3 = "",
			accesslevel4 = "", accesslevel5 = "";
	String expiresdate_accesslevel1 = "", expiresdate_accesslevel2 = "",
			expiresdate_accesslevel3 = "", expiresdate_accesslevel4 = "",
			expiresdate_accesslevel5 = "";
	SessionManager session;
	HashMap<String, String> user;
	JSONArray CancelSubscriptionJson = null;
	ArrayList<HashMap<String, String>> CancelSubList;
	private Dialog pDialog;
	String expire = "expires ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.cancel_subscription);

			setContent();
			SetFont();
			clickeEvent();
			CurrentDateGet();
		} catch (Exception e) {
		//	e.printStackTrace();
		}
		try {
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				new GetCancelSubscription().execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
		//	e.printStackTrace();
		}

	}

	private void setContent() {
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		email = user.get(SessionManager.KEY_EMAIL);
		url = StaticData.link + "displayexpireinfo.php?email=" + email;
		btn_can_sub = (Button) findViewById(R.id.btn_can_sub);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_accesslev1 = (TextView) findViewById(R.id.txt_accesslev1);
		txt_accesslev2 = (TextView) findViewById(R.id.txt_accesslev2);
		txt_accesslev3 = (TextView) findViewById(R.id.txt_accesslev3);
		txt_accesslev4 = (TextView) findViewById(R.id.txt_accesslev4);
		txt_accesslev5 = (TextView) findViewById(R.id.txt_accesslev5);

		txt_expires_accesslev1 = (TextView) findViewById(R.id.txt_expires_accesslev1);
		txt_expires_accesslev2 = (TextView) findViewById(R.id.txt_expires_accesslev2);
		txt_expires_accesslev3 = (TextView) findViewById(R.id.txt_expires_accesslev3);
		txt_expires_accesslev4 = (TextView) findViewById(R.id.txt_expires_accesslev4);
		txt_expires_accesslev5 = (TextView) findViewById(R.id.txt_expires_accesslev5);

		chk_accesslev1 = (CheckBox) findViewById(R.id.chk_accesslev1);
		chk_accesslev2 = (CheckBox) findViewById(R.id.chk_accesslev2);
		chk_accesslev3 = (CheckBox) findViewById(R.id.chk_accesslev3);
		chk_accesslev4 = (CheckBox) findViewById(R.id.chk_accesslev4);
		chk_accesslev5 = (CheckBox) findViewById(R.id.chk_accesslev5);

		CancelSubList = new ArrayList<HashMap<String, String>>();

		documnet_list = new ArrayList<String>();
	}

	private void SetFont() {
		set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");

		txt_title.setTypeface(set_font);
		txt_accesslev1.setTypeface(set_font);
		txt_accesslev2.setTypeface(set_font);
		txt_accesslev3.setTypeface(set_font);
		txt_accesslev4.setTypeface(set_font);
		txt_accesslev5.setTypeface(set_font);

		txt_expires_accesslev1.setTypeface(set_font);
		txt_expires_accesslev2.setTypeface(set_font);
		txt_expires_accesslev3.setTypeface(set_font);
		txt_expires_accesslev4.setTypeface(set_font);
		txt_expires_accesslev5.setTypeface(set_font);
		btn_can_sub.setTypeface(set_font);
	}

	private void clickeEvent() {
		btn_logout.setOnClickListener(this);
		btn_can_sub.setOnClickListener(this);

	}

	private void CurrentDateGet() {
		Calendar cal = Calendar.getInstance();

		System.out.println("Current time => " + cal.getTime());

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		CurrentDate = df.format(cal.getTime());
		int day = cal.get(Calendar.DAY_OF_WEEK);
		Log.e("CurrentDate", "displaydate:-" + CurrentDate);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_can_sub:
			try {

				String flag_accesslevel1 = "", flag_accesslevel2 = "", flag_accesslevel3 = "", flag_accesslevel4 = "", flag_accesslevel5 = "";
				if (chk_accesslev1.isChecked()) {
					accesslevel1 = txt_accesslev1.getText().toString();
					flag_accesslevel1 = "1";
					expiresdate_accesslevel1 = CurrentDate;
				}
				if (chk_accesslev2.isChecked()) {
					accesslevel2 = txt_accesslev2.getText().toString();
					flag_accesslevel2 = "2";
					expiresdate_accesslevel2 = CurrentDate;
				}
				if (chk_accesslev3.isChecked()) {
					accesslevel3 = txt_accesslev3.getText().toString();
					flag_accesslevel3 = "3";
					expiresdate_accesslevel3 = CurrentDate;
				}
				if (chk_accesslev4.isChecked()) {
					accesslevel4 = txt_accesslev4.getText().toString();
					flag_accesslevel4 = "4";
					expiresdate_accesslevel4 = CurrentDate;
				}
				if (chk_accesslev5.isChecked()) {
					accesslevel5 = "," + txt_accesslev5.getText().toString();
					flag_accesslevel5 = "5";
					expiresdate_accesslevel5 = CurrentDate;
				}
				Log.e("selected check", "test:-" + accesslevel1 + accesslevel2
						+ accesslevel3 + accesslevel4 + accesslevel5);
				Log.e("selected flag", "test:-" + flag_accesslevel1
						+ flag_accesslevel2 + flag_accesslevel3
						+ flag_accesslevel4 + flag_accesslevel5);
				try {
					if (accesslevel1.equals("") && accesslevel2.equals("")
							&& accesslevel3.equals("")
							&& accesslevel4.equals("")
							&& accesslevel5.equals("")) {
						finish();
					} else {
						new CancelSubscriptionOperation().execute();
					}

				} catch (Exception e) {
				//	e.printStackTrace();
				}

				break;
			} catch (Exception e) {
			//	e.printStackTrace();
			}
		case R.id.btn_logout:
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

		}

	}

	private class CancelSubscriptionOperation extends
			AsyncTask<String, Void, Void> {

		Dialog Dialog = new Dialog(CancelSubscribtionTest.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Register Now.....");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

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
				canSub_result = jobj.getString("ResponseCode");

				Log.e("Result", "result...." + canSub_result);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (NullPointerException n) {
			//	n.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			try {
				canSub_result();
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}
	}

	@SuppressWarnings("deprecation")
	public void canSub_result() {
		// TODO Auto-generated method stub
		accesslevel1 = "";
		accesslevel2 = "";
		accesslevel3 = "";
		accesslevel4 = "";
		accesslevel5 = "";
		if (canSub_result.endsWith("1")) {

			Toast.makeText(getApplicationContext(), "Successful...",
					Toast.LENGTH_SHORT).show();
			// Intent i = new Intent(getApplicationContext(),
			// CancelSubscribtionTest.class);
			// i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(i);
			// finish();
			new GetCancelSubscription().execute();

		} else {
			Toast.makeText(getApplicationContext(), "Could not enter data.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void postData() {

		try {

			Log.e("selected acc1", "accesslevl1:-" + accesslevel1);
			Log.e("selected acc2", "accesslevl2:-" + accesslevel2);
			Log.e("selected acc3", "accesslevl3:-" + accesslevel3);
			Log.e("selected acc4", "accesslevl4:-" + accesslevel4);
			Log.e("selected acc5", "accesslevl5:-" + accesslevel5);

			Log.e("selected exDate_1", "expiresdate_accesslevl1:-"
					+ expiresdate_accesslevel1);
			Log.e("selected exDate_2", "expiresdate_accesslevl2:-"
					+ expiresdate_accesslevel2);
			Log.e("selected exDate_3", "expiresdate_accesslevl3:-"
					+ expiresdate_accesslevel3);
			Log.e("selected exDate_4", "expiresdate_accesslevl4:-"
					+ expiresdate_accesslevel4);
			Log.e("selected exDate_5", "expiresdate_accesslevl5:-"
					+ expiresdate_accesslevel5);

			JSONObject json = new JSONObject();
			json.put("email", email);

			json.put("accessLev", accesslevel1);
			json.put("accessLev2", accesslevel2);
			json.put("accessLev3", accesslevel3);
			json.put("accessLev4", accesslevel4);
			json.put("accessLev5", accesslevel5);

			String uri = StaticData.link + "cancel_subsciption.php";
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());
			result = jsonStr;

		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
		}

		// Show response on activity
		Log.w("Reponse....", "Reponse:-" + result);
		Response_code = result;
	}

	private class GetCancelSubscription extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {
				// Showing progress dialog
				// StaticData.Pdialog(getApplicationContext());
				pDialog = new Dialog(CancelSubscribtionTest.this);
				pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				pDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));

				pDialog.setContentView(R.layout.custom_image_dialog);
				// pDialog.setMessage("Please wait...");
				pDialog.setCancelable(false);
				pDialog.show();
				// pd = new TransparentProgressDialog(ImageUpload.this,
				// R.drawable.ic_app);

			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			try {
				ServiceHandler sh = new ServiceHandler();

				// Making a request to url and getting response
				//String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
				String jsonStr = sh.makeRequest(url, ServiceHandler.GET);
				Log.d("Response: ", "> " + jsonStr);

				JSONObject jsonObj = new JSONObject(jsonStr);

				// Getting JSON Array node
				CancelSubscriptionJson = jsonObj.getJSONArray(TAG_View);
				CancelSubList.clear();
				documnet_list.clear();
				// looping through All Contacts

				for (int i = 0; i < CancelSubscriptionJson.length(); i++) {
					JSONObject c = CancelSubscriptionJson.getJSONObject(i);

					accessLev = c.getString(TAG_accessLev);
					accessLev2 = c.getString(TAG_accessLev2);
					accessLev3 = c.getString(TAG_accessLev3);
					accessLev4 = c.getString(TAG_accessLev4);
					accessLev5 = c.getString(TAG_accessLev5);

					cancel_accessLev = c.getString(TAG_cancel_accessLev);
					cancel_accessLev2 = c.getString(TAG_cancel_accessLev2);
					cancel_accessLev3 = c.getString(TAG_cancel_accessLev3);
					cancel_accessLev4 = c.getString(TAG_cancel_accessLev4);
					cancel_accessLev5 = c.getString(TAG_cancel_accessLev5);

					Purge_date_accessLev = c
							.getString(TAG_Purge_date_accessLev);
					Purge_date_accessLev2 = c
							.getString(TAG_Purge_date_accessLev2);
					Purge_date_accessLev3 = c
							.getString(TAG_Purge_date_accessLev3);
					Purge_date_accessLev4 = c
							.getString(TAG_Purge_date_accessLev4);
					Purge_date_accessLev5 = c
							.getString(TAG_Purge_date_accessLev5);

					Log.w("accesslevel", "1:-" + accessLev);
					Log.w("accesslevel", "2:-" + accessLev2);
					Log.w("accesslevel", "3:-" + accessLev3);
					Log.w("accesslevel", "4:-" + accessLev4);
					Log.w("accesslevel", "5:-" + accessLev5);

					Log.e("cancel_accesslevel", "1:-" + cancel_accessLev);
					Log.e("cancel_accesslevel", "2:-" + cancel_accessLev2);
					Log.e("cancel_accesslevel", "3:-" + cancel_accessLev3);
					Log.e("cancel_accesslevel", "4:-" + cancel_accessLev4);
					Log.e("cancel_accesslevel", "5:-" + cancel_accessLev5);

					Log.e("Purge_date_accesslevel", "1:-"
							+ Purge_date_accessLev);
					Log.e("Purge_date_accesslevel", "2:-"
							+ Purge_date_accessLev2);
					Log.e("Purge_date_accesslevel", "3:-"
							+ Purge_date_accessLev3);
					Log.e("Purge_date_accesslevel", "4:-"
							+ Purge_date_accessLev4);
					Log.e("Purge_date_accesslevel", "5:-"
							+ Purge_date_accessLev5);
					//pDialog.dismiss();

				}
			} catch (JSONException e) {
			//	e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			try {
				if (pDialog.isShowing())
					pDialog.dismiss();
				flag1 = "0";
				flag2 = "0";
				flag3 = "0";
				flag4 = "0";
				flag5 = "0";
				chk_accesslev1.setVisibility(View.VISIBLE);
				chk_accesslev2.setVisibility(View.VISIBLE);
				chk_accesslev3.setVisibility(View.VISIBLE);
				chk_accesslev4.setVisibility(View.VISIBLE);
				chk_accesslev5.setVisibility(View.VISIBLE);
				if (cancel_accessLev.equals("1")) {
					chk_accesslev1.setVisibility(View.GONE);
					txt_expires_accesslev1.setVisibility(View.VISIBLE);
					txt_expires_accesslev1.setText(expire
							+ Purge_date_accessLev);
					flag1 = "1";
				}
				if (cancel_accessLev2.equals("1")) {
					chk_accesslev2.setVisibility(View.GONE);
					txt_expires_accesslev2.setVisibility(View.VISIBLE);
					txt_expires_accesslev2.setText(expire
							+ Purge_date_accessLev2);
					flag2 = "1";
				}
				if (cancel_accessLev3.equals("1")) {
					chk_accesslev3.setVisibility(View.GONE);
					txt_expires_accesslev3.setVisibility(View.VISIBLE);
					txt_expires_accesslev3.setText(expire
							+ Purge_date_accessLev3);
					flag3 = "1";
				}
				if (cancel_accessLev4.equals("1")) {
					chk_accesslev4.setVisibility(View.GONE);
					txt_expires_accesslev4.setVisibility(View.VISIBLE);
					txt_expires_accesslev4.setText(expire
							+ Purge_date_accessLev4);
					flag4 = "1";
				}
				if (cancel_accessLev5.equals("1")) {
					chk_accesslev5.setVisibility(View.GONE);
					txt_expires_accesslev5.setVisibility(View.VISIBLE);
					txt_expires_accesslev5.setText(expire
							+ Purge_date_accessLev5);
					flag5 = "1";
				}
				if (accessLev.equals("null") || accessLev.equals("")
						|| accessLev.equals(null)) {
					Log.e("true", "null1");
					txt_accesslev1.setVisibility(View.GONE);
					chk_accesslev1.setVisibility(View.GONE);

					flag1 = "1";
				}
				if (accessLev2.equals("null") || accessLev2.equals("")
						|| accessLev2.equals(null)) {
					Log.e("true", "null2");
					txt_accesslev2.setVisibility(View.GONE);
					chk_accesslev2.setVisibility(View.GONE);
					flag2 = "1";
				}
				if (accessLev3.equals("null") || accessLev3.equals("")
						|| accessLev3.equals(null)) {
					Log.e("true", "null3");
					txt_accesslev3.setVisibility(View.GONE);
					chk_accesslev3.setVisibility(View.GONE);
					flag3 = "1";
				}
				if (accessLev4.equals("null") || accessLev4.equals("")
						|| accessLev4.equals(null)) {
					Log.e("true", "null4");
					txt_accesslev4.setVisibility(View.GONE);
					chk_accesslev4.setVisibility(View.GONE);
					flag4 = "1";
				}
				if (accessLev5.equals("null") || accessLev5.equals("")
						|| accessLev5.equals(null)) {
					Log.e("true", "null5");
					txt_accesslev5.setVisibility(View.GONE);
					chk_accesslev5.setVisibility(View.GONE);
					flag5 = "1";
				}
				txt_accesslev1.setText(accessLev);
				txt_accesslev2.setText(accessLev2);
				txt_accesslev3.setText(accessLev3);
				txt_accesslev4.setText(accessLev4);
				txt_accesslev5.setText(accessLev5);
				if (flag1.equals("1") && flag2.equals("1") && flag3.equals("1")
						&& flag4.equals("1") && flag5.equals("1")) {
					btn_can_sub.setVisibility(View.GONE);
				} else {
					btn_can_sub.setVisibility(View.VISIBLE);
				}
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}
	}

}

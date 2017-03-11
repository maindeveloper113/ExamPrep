package com.contractorsintelligence.order_online_test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOnlineTest extends Activity implements OnClickListener {

	private static String desiredquestion_url = StaticData.link
			+ "getClassification.php";
	private static final String array_desiredquestion = "Classes";
	private static final String TAG_desiredquestion = "classDescription";
	private static final String TAG_access_levels = "access_levels";
	private static final String TAG_class_id = "classId";
	Button btn_subscribe, btn_logout;
	Spinner Spinner_desiredquestion;
	TextView txt_title;
	String Response_code;
	String register_result, result, email;
	List<String> desiredquestion_list, access_levels_list, class_listId;
	String user_type;
	String desiredquestion = null, state = null;
	HashMap<String, String> user;
	JSONArray jsnarr_desiredquestion = null;
	SessionManager session;
	private static final String URL_STRING = "https://www.contractorsischool.com/mobile/checkoutpage.php";

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.order_online_test);
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			setContent();
			clickeEvent();
			new Getdesiredquestion().execute();

		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	// @Override
	// public void onBackPressed() {
	// Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
	// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// startActivity(intent);
	// finish();
	// }

	private void clickeEvent() {
		// TODO Auto-generated method stub
		btn_subscribe.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}

	private void setContent() {
		// TODO Auto-generated method stub

		session = new SessionManager(getApplicationContext());
		txt_title = (TextView) findViewById(R.id.txt_title);

		user = session.getUserDetails();
		email = user.get(SessionManager.KEY_EMAIL);
		Spinner_desiredquestion = (Spinner) findViewById(R.id.Spinner_desiredquestion);

		btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		Typeface tf = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "fonts/AGENCYR.TTF");
		btn_subscribe.setTypeface(tf);

		desiredquestion_list = new ArrayList<String>();
		desiredquestion_list.add("Select a practice test");
		access_levels_list = new ArrayList<String>();
		class_listId = new ArrayList<String>();

		txt_title.setTypeface(tf);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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

			break;
		case R.id.btn_subscribe:
			try {

				desiredquestion = Spinner_desiredquestion.getSelectedItem()
						.toString();
				Log.e("selected", "orderTestName:-" + desiredquestion);
				if (desiredquestion.equals(desiredquestion_list.get(0))) {
					Toast.makeText(getApplicationContext(),
							"Select a practice test", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (StaticData.isNetworkConnected(getApplicationContext())) {

						Intent browserIntent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("https://www.contractorsischool.com/mobile/checkoutpage.php?emailtemp="
										+ email
										+ "&&order_test_name="
										+ desiredquestion));
						browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(browserIntent);

						// Intent i = new Intent(OrderOnlineTest.this,
						// WebViewOrderTest.class);
						// i.putExtra("SelectTest", desiredquestion);
						// i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						// startActivity(i);
						// finish();

					} else {
						Toast.makeText(getApplicationContext(),
								"Not connected to Internet", Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;

			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}
	}

	private class Getdesiredquestion extends AsyncTask<Void, Void, Void> {
		private Dialog Dialog = new Dialog(OrderOnlineTest.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// Dialog.setMessage("Please Wait.....");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			Dialog.setContentView(R.layout.custom_image_dialog);

			Dialog.show();
			Dialog.setCancelable(false);
		}

		@Override
		protected Void doInBackground(Void... arg0) throws NullPointerException {

			ServiceHandler sh = new ServiceHandler();

			//String jsonStr = sh.makeServiceCall(desiredquestion_url, ServiceHandler.GET);
			String jsonStr = sh.makeRequest(desiredquestion_url, ServiceHandler.GET);

			Log.d("Desi Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					jsnarr_desiredquestion = jsonObj.getJSONArray(array_desiredquestion);
					Log.d("jsnarr_state: ", "> " + jsnarr_desiredquestion);
					for (int i = 0; i < jsnarr_desiredquestion.length(); i++) {
						try {

							// Log.d("jsnarr desiredquestion length: ", "> "
							// + jsnarr_desiredquestion.length());
							JSONObject c = jsnarr_desiredquestion
									.getJSONObject(i);

							String desiredquestion = c
									.getString(TAG_desiredquestion);
							String access_level = c
									.getString(TAG_access_levels);
							String id = c.getString(TAG_class_id);

							String[] separated = desiredquestion.split("\\(");

							Log.d("custome separated", "custome pos 0:- "
									+ separated[0]);

							Log.d("jsnarr_desir : ", "> " + desiredquestion);
							access_levels_list.add(access_level);
							desiredquestion_list.add(desiredquestion);
							class_listId.add(id);
						} catch (Exception e) {
						//	e.printStackTrace();
						}
					}

				} catch (JSONException e) {
				//	e.printStackTrace();
				}
			} else {

				Log.e("ServiceHandler",
						"Couldn't get any data from the jsnarr_state_url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				if (Dialog.isShowing())
					Dialog.dismiss();

				Log.d("Desiredquestion List: ", "> " + desiredquestion_list);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						OrderOnlineTest.this, R.layout.order_test_spinner_list,
						desiredquestion_list) {

					public View getView(int position, View convertView,
							ViewGroup parent) {
						View v = super.getView(position, convertView, parent);

						Typeface externalFont = Typeface.createFromAsset(
								getAssets(), "fonts/AGENCYR.TTF");
						((TextView) v).setSingleLine(false);
						((TextView) v).setGravity(android.view.Gravity.CENTER);

						((TextView) v).setTypeface(externalFont);
						((TextView) v).setText(desiredquestion_list
								.get(position));

						return v;
					}

					public View getDropDownView(int position, View convertView,
							ViewGroup parent) {
						View v = super.getDropDownView(position, convertView,
								parent);

						Typeface Sp_Font = Typeface.createFromAsset(
								getAssets(), "fonts/AGENCYR.TTF");
						((TextView) v).setTypeface(Sp_Font);
						((TextView) v).setSingleLine(false);
						((TextView) v).setGravity(android.view.Gravity.CENTER);

						((TextView) v).setText(desiredquestion_list
								.get(position));
						return v;
					}
				};

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				Spinner_desiredquestion
//						.setPrompt("Select Desired Trade Questions");
				Spinner_desiredquestion.setAdapter(adapter);

			} catch (Exception e) {
			//	e.printStackTrace();
			}
		}

	}

}

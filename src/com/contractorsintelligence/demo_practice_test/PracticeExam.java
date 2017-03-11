package com.contractorsintelligence.demo_practice_test;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class PracticeExam extends Activity implements OnClickListener {

	Button btn_logout;
	TextView txt_title, txt_class_test, txt_demo_test;
	Typeface set_font;
	SessionManager session;
	ListView lst_demo_practice_exam;
	public static String CategoryName;
	HashMap<String, String> user;
	String Response_code, result, email, student_id;
	JSONArray demonameJson = null;
	JSONArray classificationnameJson = null,
			classification_test_nameJson = null;
	List<String> categoryList;
	ArrayList<HashMap<String, String>> CategoryNameList;
	public static String categoryName, TestName;
	String test_id, class_categoryName, class_category_test_id,
			class_test_name, class_categoryId;
	public static String endTestcate_testname_flag ="0";
	public static String explanation_flag ="0";
	private static final String TAG_category = "DemoTest";
	private static final String TAG_classification = "classification";
	private static final String TAG_classification_test = "Test_tital";
	private static final String TAG_category_test_name = "tital";
	private static final String TAG_test_id = "id";
	private static final String TAG_classification_cat_test_name = "name";
	private static final String TAG_classification_cat_id = "id";
	private static final String TAG_classification_test_cat_id = "id";
	private static final String TAG_classification_test_name = "tital";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.practice_exam);
		setContent();
		clickeEvent();
		SetFont();
		try {
			new ViewdemoList().execute();
		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private void setContent() {

		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_class_test = (TextView) findViewById(R.id.txt_class_test);
		txt_demo_test = (TextView) findViewById(R.id.txt_demo_test);
		session = new SessionManager(getApplicationContext());
		btn_logout = (Button) findViewById(R.id.btn_logout);
		lst_demo_practice_exam = (ListView) findViewById(R.id.lst_demo_practice_exam);
		categoryList = new ArrayList<String>();
		CategoryNameList = new ArrayList<HashMap<String, String>>();
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		email = user.get(SessionManager.KEY_EMAIL);
		student_id = user.get(SessionManager.KEY_StudentId);
		// btn_Law_and_Business = (Button)
		// findViewById(R.id.btn_Law_and_Business);
		// btn_B_General_Building_Contractor = (Button)
		// findViewById(R.id.btn_B_General_Building_Contractor);
		// btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
		// btn_settings = (Button) findViewById(R.id.btn_settings);
	}

	private void clickeEvent() {
		btn_logout.setOnClickListener(this);
		txt_demo_test.setOnClickListener(this);
		txt_class_test.setOnClickListener(this);
		// btn_Law_and_Business.setOnClickListener(this);
		// btn_B_General_Building_Contractor.setOnClickListener(this);
		// btn_subscribe.setOnClickListener(this);
		// btn_settings.setOnClickListener(this);
	}

	private void SetFont() {
		set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");

		txt_title.setTypeface(set_font);
		txt_demo_test.setTypeface(set_font);
		txt_class_test.setTypeface(set_font);
	}

	@Override
	public void onClick(View v) {

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
		case R.id.txt_demo_test:
			endTestcate_testname_flag ="1";
			explanation_flag ="1";
			Intent intent = new Intent(PracticeExam.this,
					DemostartpageTest.class);

			intent.putExtra("Test_name", categoryName);
			intent.putExtra("CategoryName", TestName);

			intent.putExtra("Test_id", test_id);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.txt_class_test:
			endTestcate_testname_flag ="1";
			explanation_flag ="1";
			Intent intent_class = new Intent(PracticeExam.this,
					DemostartpageTest.class);

			intent_class.putExtra("Test_name", class_categoryName);
			intent_class.putExtra("CategoryName", class_test_name);
			intent_class.putExtra("Test_id", class_category_test_id);
			intent_class.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_class);
			finish();
			break;
		// case R.id.btn_Law_and_Business:
		// //
		// btn_Law_and_Business.setBackgroundResource(R.drawable.btn_btn1_hover);
		// if (StaticData.isNetworkConnected(getApplicationContext())) {
		// // Intent demo_i = new Intent(this, DemoPractice.class);
		// // startActivity(demo_i);
		// } else {
		// Toast.makeText(getApplicationContext(),
		// "Not connected to Internet", Toast.LENGTH_SHORT).show();
		// }
		//
		// break;
		// case R.id.btn_B_General_Building_Contractor:
		// //
		// btn_your_test_subscription.setBackgroundResource(R.drawable.btn_your_test_subscription_hover);
		// if (StaticData.isNetworkConnected(getApplicationContext())) {
		// // Intent yourtest_i = new Intent(this,
		// // YourTestSubscription.class);
		// // startActivity(yourtest_i);
		// } else {
		// Toast.makeText(getApplicationContext(),
		// "Not connected to Internet", Toast.LENGTH_SHORT).show();
		// }
		//
		// break;
		// case R.id.btn_subscribe:
		// //
		// btn_order_online_test.setBackgroundResource(R.drawable.btn_order_online_test_hover);
		// if (StaticData.isNetworkConnected(getApplicationContext())) {
		// // Intent order_i = new Intent(this, OrderOnlineTest.class);
		// // startActivity(order_i);
		// } else {
		// Toast.makeText(getApplicationContext(),
		// "Not connected to Internet", Toast.LENGTH_SHORT).show();
		// }
		//
		// break;
		// case R.id.btn_settings:
		// // btn_settings.setBackgroundResource(R.drawable.btn_settings_hover);
		// if (StaticData.isNetworkConnected(getApplicationContext())) {
		// // Intent settings_i = new Intent(this, Settings.class);
		// // startActivity(settings_i);
		// } else {
		// Toast.makeText(getApplicationContext(),
		// "Not connected to Internet", Toast.LENGTH_SHORT).show();
		// }
		//
		// break;
		}
	}

	private class ViewdemoList extends AsyncTask<Void, Void, Void> {
		private Dialog pDialog;

		@Override
		protected void onPreExecute() {

			try {
				super.onPreExecute();




				// Showing progress dialog
				pDialog = new Dialog(PracticeExam.this);
				// pDialog.setMessage("Please wait...");
				pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				pDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));

				pDialog.setContentView(R.layout.custom_image_dialog);
				pDialog.setCancelable(false);
				pDialog.show();

			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				sendEmail();
				JSONObject jsonObj = new JSONObject(Response_code);

				// Getting JSON Array node
				demonameJson = jsonObj.getJSONArray(TAG_category);
				if (jsonObj.getJSONArray(TAG_classification) != null) {
					classificationnameJson = jsonObj
							.getJSONArray(TAG_classification);
				}

				classification_test_nameJson = jsonObj
						.getJSONArray(TAG_classification_test);
				Log.d("Demotest", "demo" + demonameJson);
				Log.d("classtest", "class" + classificationnameJson);
				Log.d("classtestId", "Id" + classification_test_nameJson);
				// looping through All Contacts
				// for (int i = 0; i < demonameJson.length(); i++) {
				JSONObject c = demonameJson.getJSONObject(0);

				TestName = c.getString(TAG_category_test_name);

				String remove_str = TestName.replace(" Test 1", "");
				categoryName = remove_str + "(Demo Practice Test)";
				Log.d("custome separated", "custome pos 0:- " + remove_str);

				test_id = c.getString(TAG_test_id);

				// categoryList.add(categoryName);
				// tmp hashmap for single contact
				// HashMap<String, String> categoryMap = new HashMap<String,
				// String>();

				// adding each child node to HashMap key => value
				// categoryMap.put(TAG_category_test_name, categoryName);
				// adding contact to contact list
				// CategoryNameList.add(categoryMap);
				// }

				// for (int i = 0; i < demonameJson.length(); i++) {
				JSONObject c1 = classificationnameJson.getJSONObject(0);

				class_categoryName = c1
						.getString(TAG_classification_cat_test_name)
						+ "(Demo Practice Test)";

				class_categoryId = c1.getString(TAG_classification_cat_id);
				JSONObject c2 = classification_test_nameJson.getJSONObject(0);
				class_category_test_id = c2
						.getString(TAG_classification_test_cat_id);
				class_test_name = c2.getString(TAG_classification_test_name);
				Log.d("Id", "Start testId" + class_category_test_id);
				// categoryList.add(class_categoryName);
				// tmp hashmap for single contact

				// adding each child node to HashMap key => value
				// categoryMap.put(TAG_category_test_name, class_categoryName);
				// adding contact to contact list
				// CategoryNameList.add(categoryMap);
				// }
			} catch (JSONException e) {
				//e.printStackTrace();
			} catch (NullPointerException n) {
				//n.printStackTrace();
			}

			return null;
		}

		private void sendEmail() {

			try {

				Log.e("customerid", "demo email:-" + student_id);
				Log.d("data", "sent");
				JSONObject json = new JSONObject();
				// json.put("email", email);
				json.put("customerid", student_id);

				String uri = StaticData.link + "level0_demotest.php";
				ServiceHandler sh = new ServiceHandler();
				// Making a request to url and getting response
				String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());

				result = jsonStr;

			} catch (Exception e) {
				// Toast.makeText(this, "Request failed: " + t.toString(),
				// Toast.LENGTH_LONG).show();
				//e.printStackTrace();
			}
			// Show response on activity
			Log.w("Reponse....", "Reponse:-" + result);
			Response_code = result;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			try {
				if (pDialog.isShowing())
					pDialog.dismiss();
				/**
				 * Updating parsed JSON data into ListView
				 * */

				txt_demo_test.setText(categoryName);
				txt_class_test.setText(class_categoryName);
				// SpecialAdapter adapter = new
				// SpecialAdapter(PracticeExam.this,
				// CategoryNameList, R.layout.category_name_list,
				// new String[] { TAG_category_test_name },
				// new int[] { R.id.txt_category_name });
				//
				// lst_demo_practice_exam.setAdapter(adapter);
				// setListAdapter(adapter);
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		// public class SpecialAdapter extends SimpleAdapter {
		//
		// public SpecialAdapter(Context context,
		// List<? extends Map<String, ?>> data, int resource,
		// String[] from, int[] to) {
		// super(context, data, resource, from, to);
		// // TODO Auto-generated constructor stub
		// }
		//
		// @Override
		// public View getView(final int position, View convertView,
		// ViewGroup parent) {
		// View view = super.getView(position, convertView, parent);
		// Log.d("POSITION", "count" + position);
		// Log.d("POSITION", "list" + CategoryNameList.size());
		//
		// TextView txt_categoryname = (TextView) view
		// .findViewById(R.id.txt_category_name);
		// Typeface Set_font = Typeface.createFromAsset(
		// getApplicationContext().getAssets(),
		// "fonts/AGENCYR.TTF");
		//
		// txt_categoryname.setTypeface(Set_font);
		//
		// txt_categoryname.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.d("POSITION",
		// "cate name" + categoryList.get(position));
		// CategoryName = categoryList.get(position);
		// String[] split_categoryname = categoryList
		// .get(position).split(" - ");
		// Log.d("POSITION", "split cate name"
		// + split_categoryname[0]);
		//
		// Intent intent = new Intent(PracticeExam.this,
		// OnlinePracticeExamApp.class);
		// intent.putExtra("categoryname", split_categoryname[0]);
		// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		//
		// }
		// });
		// return view;
		// }
		// }
	}
}

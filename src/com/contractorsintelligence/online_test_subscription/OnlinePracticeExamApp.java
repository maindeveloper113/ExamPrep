package com.contractorsintelligence.online_test_subscription;

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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;
import com.contractorsintelligence.demo_practice_test.LawandBusinessFirstPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlinePracticeExamApp extends Activity implements OnClickListener {
	Button btn_logout;
	Typeface Set_font;
	TextView txt_title, txt_exam_newsystem_title, txt_exam_oldsystem_title,
			txt_genral_health_safety_test, txt_genral_health_safety_test_title;
	ListView lst_newsystem_test, lst_oldsystem_test;
	SessionManager session;
	HashMap<String, String> user;
	String email, test_categoryId, Response_code, result, CategoryName;
	public static String test_title_id;
	JSONArray TestnameJson = null;
	ArrayList<HashMap<String, String>> TestNewSystemNameList;
	private static final String TAG_Test = "ExamTitle";
	private static final String TAG_Test_id = "id";
	private static final String TAG_Test_name = "tital";
	List<String> TestNameList, TestIdList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.onlineexampractice_app);
			setContent();
			SetFont();
			clickeEvent();
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				new ViewTestNewSystemList().execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private void setContent() {
		test_categoryId = getIntent().getExtras().getString("TestCategoryId");
		Log.d("cate name", "split send :--" + test_categoryId);
		CategoryName = getIntent().getExtras().getString("CategoryName");
		Log.d("Category Display", "Category:--" + CategoryName);
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		email = user.get(SessionManager.KEY_EMAIL);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_genral_health_safety_test_title = (TextView) findViewById(R.id.txt_genral_health_safety_test_title);
		txt_genral_health_safety_test = (TextView) findViewById(R.id.txt_genral_health_safety_test);
		txt_exam_newsystem_title = (TextView) findViewById(R.id.txt_exam_newsystem_title);
		txt_exam_oldsystem_title = (TextView) findViewById(R.id.txt_exam_oldsystem_title);
		lst_newsystem_test = (ListView) findViewById(R.id.lst_newsystem_test);
		lst_oldsystem_test = (ListView) findViewById(R.id.lst_oldsystem_test);
		TestNameList = new ArrayList<String>();
		TestIdList = new ArrayList<String>();
		TestNewSystemNameList = new ArrayList<HashMap<String, String>>();
	}

	private void SetFont() {
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");

		txt_title.setTypeface(Set_font);

		txt_exam_newsystem_title.setTypeface(Set_font);
		txt_exam_oldsystem_title.setTypeface(Set_font);
		txt_genral_health_safety_test_title.setTypeface(Set_font);
		txt_genral_health_safety_test.setTypeface(Set_font);
	}

	private void clickeEvent() {
		btn_logout.setOnClickListener(this);
		txt_genral_health_safety_test.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		try {

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
								}).setNegativeButton("No", null) // Do nothing
																	// on no
						.show();

				break;
			case R.id.txt_genral_health_safety_test:

				String testname = txt_genral_health_safety_test.getText()
						.toString();
				Intent intent = new Intent(OnlinePracticeExamApp.this,
						LawandBusinessFirstPage.class);
				intent.putExtra("CategoryName", testname);
				intent.putExtra("Test_id", "223");
//				intent.putExtra("Test_id", "223");
				intent.putExtra("Test_name", testname);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;

			}

		} catch (Exception e) {
		//	e.printStackTrace();
		}

	}

	private class ViewTestNewSystemList extends AsyncTask<Void, Void, Void> {
		private Dialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {
				// Showing progress dialog
				pDialog = new Dialog(OnlinePracticeExamApp.this);
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
				sendCategoryName();

				JSONObject jsonObj = new JSONObject(Response_code);

				// Getting JSON Array node
				TestnameJson = jsonObj.getJSONArray(TAG_Test);
				Log.d("TestJson", "data" + TestnameJson);
				// looping through All Contacts
				for (int i = 0; i < TestnameJson.length(); i++) {
					JSONObject c = TestnameJson.getJSONObject(i);

					String testName = c.getString(TAG_Test_name);
					String testId = c.getString(TAG_Test_id);
					Log.d("Test", "Name" + testName);
					TestNameList.add(testName);
					TestIdList.add(testId);
					HashMap<String, String> testMap = new HashMap<String, String>();

					testMap.put(TAG_Test_name, testName);

					TestNewSystemNameList.add(testMap);
				}
			} catch (JSONException e) {
			//	e.printStackTrace();
			}catch (NullPointerException n) {
			//	n.printStackTrace();
			}

			return null;
		}

		private void sendCategoryName() {

			try {

				Log.d("Selected", "TestName" + test_categoryId);
				Log.d("data", "sent");
				JSONObject json = new JSONObject();
				// json.put("email", email);
				json.put("test_id", test_categoryId);

				String uri = StaticData.link + "get_test_subtests.php";

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
				SpecialAdapter adapter = new SpecialAdapter(
						OnlinePracticeExamApp.this, TestNewSystemNameList,
						R.layout.category_name_list,
						new String[] { TAG_Test_name },
						new int[] { R.id.txt_category_name });

				lst_newsystem_test.setAdapter(adapter);

				// setListAdapter(adapter);
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		public class SpecialAdapter extends SimpleAdapter {

			public SpecialAdapter(Context context,
					List<? extends Map<String, ?>> data, int resource,
					String[] from, int[] to) {
				super(context, data, resource, from, to);
				// TODO Auto-generated constructor stub
			}

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {

				View view = super.getView(position, convertView, parent);
				try {
					Log.d("POSITION",
							"count" + TestNewSystemNameList.get(position));
					Log.d("POSITION", "list" + TestNewSystemNameList.size());

					TextView txt_Testname = (TextView) view
							.findViewById(R.id.txt_category_name);
					Typeface Set_font = Typeface.createFromAsset(
							getApplicationContext().getAssets(),
							"fonts/AGENCYR.TTF");

					txt_Testname.setTypeface(Set_font);

					txt_Testname.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Log.d("POSITION",
									"Test name" + TestNameList.get(position));
							Log.d("POSITION",
									"Test id" + TestIdList.get(position));
							test_title_id = TestIdList.get(position);
							Intent intent = new Intent(
									OnlinePracticeExamApp.this,
									LawandBusinessFirstPage.class);
							intent.putExtra("CategoryName", CategoryName);
							intent.putExtra("Test_id", TestIdList.get(position));
							intent.putExtra("Test_name",
									TestNameList.get(position));
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);

						}
					});

				} catch (Exception e) {
				//	e.printStackTrace();
				}
				return view;
			}
		}
	}

}

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YourTestSubscriptionApp extends Activity implements
		OnClickListener {
	Button btn_logout;
	TextView txt_title;

	JSONArray all_questions = null;
	String Response_code, result, email, Test_category_id;

	public static String CategoryName, CategoryId;
	SessionManager session;
	HashMap<String, String> user;
	Typeface set_font;
	List<String> categoryList, categoryIdList, TestcategoryIdist;

	ArrayList<HashMap<String, String>> CategoryNameList;

	List<String> QuestionsList, Ans1List, Ans2List, Ans3List, Ans4List,
			RightAnsList;
	ListView lst_categoryname;
	JSONArray CategorynameJson = null;

	private static final String TAG_category = "Tests";
	private static final String TAG_category_name = "classDescription";
	private static final String TAG_category_Id = "classId";
	private static final String TAG_test_category_id = "test_category_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.yourtestsubscription_app);

			setContent();
			clickeEvent();
			setFont();
			new ViewCategoryList().execute();
		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private void setContent() {
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		email = user.get(SessionManager.KEY_EMAIL);

		txt_title = (TextView) findViewById(R.id.txt_title);
		// txt_msg= (TextView) findViewById(R.id.txt_msg);
		lst_categoryname = (ListView) findViewById(R.id.lst_categoryname);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		CategoryNameList = new ArrayList<HashMap<String, String>>();
		categoryList = new ArrayList<String>();
		categoryIdList = new ArrayList<String>();
		TestcategoryIdist = new ArrayList<String>();
		QuestionsList = new ArrayList<String>();
		Ans1List = new ArrayList<String>();
		Ans2List = new ArrayList<String>();
		Ans3List = new ArrayList<String>();
		Ans4List = new ArrayList<String>();
		RightAnsList = new ArrayList<String>();
	}

	private void clickeEvent() {

		btn_logout.setOnClickListener(this);
	}

	private void setFont() {
		set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_title.setTypeface(set_font);
		// txt_msg.setTypeface(set_font);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btn_logout:
			if (StaticData.isNetworkConnected(getApplicationContext())) {
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

			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

			break;

		}
	}

	private class ViewCategoryList extends AsyncTask<Void, Void, Void> {
		private Dialog pDialog;

		@Override
		protected void onPreExecute() {

			try {
				super.onPreExecute();
				// Showing progress dialog
				pDialog = new Dialog(YourTestSubscriptionApp.this);
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
				CategorynameJson = jsonObj.getJSONArray(TAG_category);

				// looping through All Contacts
				for (int i = 0; i < CategorynameJson.length(); i++) {
					JSONObject c = CategorynameJson.getJSONObject(i);

					String categoryName = c.getString(TAG_category_name);
					String categoryId = c.getString(TAG_category_Id);
					String test_category_id = c.getString(TAG_test_category_id);

					String[] separated = categoryName.split("\\(");

					Log.d("custome separated", "custome pos 0:- "+ separated[0]);
					categoryList.add(separated[0]);
					categoryIdList.add(categoryId);
					TestcategoryIdist.add(test_category_id);
					// tmp hashmap for single contact
					HashMap<String, String> categoryMap = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					categoryMap.put(TAG_category_name, separated[0]);
					// adding contact to contact list
					CategoryNameList.add(categoryMap);
				}
			} catch (JSONException e) {
			//	e.printStackTrace();
			} catch (NullPointerException n) {
			//	n.printStackTrace();
			}

			return null;
		}

		private void sendEmail() {

			try {

				Log.d("email", "email:-" + email);
				Log.d("data", "sent");
				JSONObject json = new JSONObject();
				// json.put("email", email);
				json.put("email", email);
				String uri = StaticData.link + "get_test_subscription.php";
				ServiceHandler sh = new ServiceHandler();
				// Making a request to url and getting response
				String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());
				result = jsonStr;

			} catch (Exception e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
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
				//pDialog = null;
				/**
				 * Updating parsed JSON data into ListView
				 * */
				SpecialAdapter adapter = new SpecialAdapter(
						YourTestSubscriptionApp.this, CategoryNameList,
						R.layout.category_name_list,
						new String[] { TAG_category_name },
						new int[] { R.id.txt_category_name });

				lst_categoryname.setAdapter(adapter);
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

					Log.d("POSITION", "count" + position);
					Log.d("POSITION", "list" + CategoryNameList.size());

					TextView txt_categoryname = (TextView) view
							.findViewById(R.id.txt_category_name);
					Typeface Set_font = Typeface.createFromAsset(
							getApplicationContext().getAssets(),
							"fonts/AGENCYR.TTF");

					txt_categoryname.setTypeface(Set_font);

					txt_categoryname.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							try {

								Log.d("POSITION",
										"cate name"
												+ categoryList.get(position));
								CategoryName = categoryList.get(position);
								CategoryId = categoryIdList.get(position);
								Test_category_id = TestcategoryIdist
										.get(position);
								String[] split_categoryname = categoryList.get(
										position).split(" - ");
								Log.d("POSITION", "split cate name"
										+ split_categoryname[0]);

								Intent intent = new Intent(
										YourTestSubscriptionApp.this,
										OnlinePracticeExamApp.class);
								intent.putExtra("CategoryName", CategoryName);
								intent.putExtra("TestCategoryId",
										Test_category_id);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);

							} catch (Exception e) {
							//	e.printStackTrace();
							}
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

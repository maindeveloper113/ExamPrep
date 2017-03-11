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
import android.widget.TextView;

import com.contractorsintelligence.cis.MainActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class GeneralHealthSafetyTest extends Activity implements
		OnClickListener {

	Button btn_start, btn_logout;
	SessionManager session;
	TextView txt_test_name, txt_title_full_score, txt_full_score, txt_title,
			txt_total_que, txt_title_total_que, txt_passing_rate,
			txt_title_passing_rate, txt_title_passing_score, txt_passing_score,
			txt_time_limit, txt_title_time_limit;
	private static final String TAG_All_Questions = "question";
	private static final String TAG_quotations = "ques";
	private static final String TAG_id = "id";
	private static final String TAG_right_ans = "correct";
	private static final String TAG_ans1 = "ans1";
	private static final String TAG_ans2 = "ans2";
	private static final String TAG_ans3 = "ans3";
	private static final String TAG_ans4 = "ans4";
	private static final String TAG_explanation = "explanation";
	Typeface Set_font;
	JSONArray all_questions;
	String Response_code, result, TestName, TestId, CategoryName;
	public static String Category_Name, Test_Name;
	ArrayList<String> QuestionsList, Ans1List, Ans2List, Ans3List, Ans4List,
			IdList, RightAnsList, ExplanationList;
	int passing_score = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lawandbusiness_firstpage);
		setContent();
		clickeEvent();
		SetFont();
		try {
			new GetData().execute();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private void clickeEvent() {
		// TODO Auto-generated method stub
		btn_start.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}

	private void setContent() {
		CategoryName = getIntent().getExtras().getString("CategoryName");
		TestName = getIntent().getExtras().getString("Test_name");
		Category_Name = TestName.replace("(Demo Practice Test)", "");
		Test_Name = CategoryName;
		Log.d("Category name", "cat:-" + Category_Name);
		Log.d("Test name", "test:-" + Test_Name);
		TestId = getIntent().getExtras().getString("Test_id");
		Log.d("Test name", "name:--" + TestName);
		Log.d("test id", "id:--" + TestId);
		session = new SessionManager(getApplicationContext());
		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_start = (Button) findViewById(R.id.btn_start);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_test_name = (TextView) findViewById(R.id.txt_test_name);
		txt_test_name.setText(TestName);
		txt_total_que = (TextView) findViewById(R.id.txt_total_que);
		txt_passing_rate = (TextView) findViewById(R.id.txt_passing_rate);
		txt_passing_score = (TextView) findViewById(R.id.txt_passing_score);
		txt_title_passing_score = (TextView) findViewById(R.id.txt_title_passing_score);
		txt_time_limit = (TextView) findViewById(R.id.txt_time_limit);
		txt_title_total_que = (TextView) findViewById(R.id.txt_title_total_que);
		txt_title_time_limit = (TextView) findViewById(R.id.txt_title_time_limit);
		txt_title_passing_rate = (TextView) findViewById(R.id.txt_title_passing_rate);
		txt_title_full_score = (TextView) findViewById(R.id.txt_title_full_score);
		txt_full_score = (TextView) findViewById(R.id.txt_full_score);
		QuestionsList = new ArrayList<String>();
		IdList = new ArrayList<String>();
		Ans1List = new ArrayList<String>();
		Ans2List = new ArrayList<String>();
		Ans3List = new ArrayList<String>();
		Ans4List = new ArrayList<String>();
		RightAnsList = new ArrayList<String>();
		ExplanationList = new ArrayList<String>();
	}

	private void SetFont() {
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_test_name.setTypeface(Set_font);
		txt_title_full_score.setTypeface(Set_font);
		txt_total_que.setTypeface(Set_font);
		txt_title_passing_rate.setTypeface(Set_font);
		txt_passing_score.setTypeface(Set_font);
		txt_title_total_que.setTypeface(Set_font);
		txt_time_limit.setTypeface(Set_font);
		txt_title_time_limit.setTypeface(Set_font);
		txt_passing_rate.setTypeface(Set_font);
		txt_title_passing_score.setTypeface(Set_font);
		txt_title.setTypeface(Set_font);
		btn_start.setTypeface(Set_font);
		txt_full_score.setTypeface(Set_font);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_start:
			try {

				Intent intent = new Intent(GeneralHealthSafetyTest.this,
						StartLABtest.class);
				Bundle b = new Bundle();
				b.putStringArrayList("QuestionsList", QuestionsList);
				b.putStringArrayList("IdList", IdList);
				b.putStringArrayList("Ans1List", Ans1List);
				b.putStringArrayList("Ans2List", Ans2List);
				b.putStringArrayList("Ans3List", Ans3List);
				b.putStringArrayList("Ans4List", Ans4List);
				b.putStringArrayList("RightAnsList", RightAnsList);
				b.putStringArrayList("ExplanationList", ExplanationList);
				b.putString("TestName", TestName);
				b.putString("CategoryName", "");
				intent.putExtras(b);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				break;
			} catch (Exception e) {
				//e.printStackTrace();
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

	private class GetData extends AsyncTask<String, Void, Void> {
		private Dialog Dialog = new Dialog(GeneralHealthSafetyTest.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Please wait...");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			Dialog.setContentView(R.layout.custom_image_dialog);
			Dialog.setCancelable(false);
			Dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {

			try {
				SendTestName();
				JSONObject jsonObj = new JSONObject(Response_code);

				// Getting JSON Array node
				all_questions = jsonObj.getJSONArray(TAG_All_Questions);

				// looping through All Contacts

				for (int i = 0; i < all_questions.length(); i++) {
					JSONObject c = all_questions.getJSONObject(i);
					// String result = java.net.URLDecoder.decode(url,
					// "UTF-8");
					try {

						String quotations = c.getString(TAG_quotations);
						String id = c.getString(TAG_id);
						String right_ans = c.getString(TAG_right_ans);
						String ans1 = c.getString(TAG_ans1);
						String ans2 = c.getString(TAG_ans2);
						String ans3 = c.getString(TAG_ans3);
						String ans4 = c.getString(TAG_ans4);
						String explanation = c.getString(TAG_explanation);

						QuestionsList.add(quotations);
						IdList.add(id);
						Ans1List.add(ans1);
						Ans2List.add(ans2);
						Ans3List.add(ans3);
						Ans4List.add(ans4);
						RightAnsList.add(right_ans);
						ExplanationList.add(explanation);
						passing_score = Math
								.round(80 * QuestionsList.size() / 100);
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}

				Log.d("DoInbackground", "QuestionsSize" + QuestionsList.size());

			} catch (JSONException e) {
				//e.printStackTrace();
			} catch (NullPointerException n) {
				//n.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(Void unused) {

			try {
				if (Dialog.isShowing())
					Dialog.dismiss();
				txt_total_que.setText(String.valueOf(QuestionsList.size()));
				txt_full_score.setText(String.valueOf(QuestionsList.size()));
				txt_passing_score.setText(String.valueOf(passing_score));

				if (QuestionsList.size() == 0) {
					btn_start.setClickable(false);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}

		}
	}

	public void SendTestName() {
		try {

			Log.d("Selected", "TestName" + TestName);
			Log.d("get", "Test Id" + TestId);
			Log.d("data", "sent");
			JSONObject json = new JSONObject();
			// json.put("email", email);
			json.put("test_title_id", TestId);

			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
			HttpConnectionParams.setSoTimeout(httpParams, 10000);
			HttpClient client = new DefaultHttpClient(httpParams);

			String url = StaticData.link + "get_demo_testdetail.php";

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
				Log.i("Read from server", "ExamDetails" + result);
				// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			}

		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
		}

		// Show response on activity
		Log.w("Reponse....", "Reponse:-" + result);
		Response_code = result;

	}

}
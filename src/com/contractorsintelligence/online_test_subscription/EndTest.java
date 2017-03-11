package com.contractorsintelligence.online_test_subscription;

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
import android.widget.Toast;

import com.contractorsintelligence.cis.ApplicationhomeActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;
import com.contractorsintelligence.cis.homeActivity;
import com.contractorsintelligence.demo_practice_test.DemostartpageTest;
import com.contractorsintelligence.demo_practice_test.PracticeExam;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EndTest extends Activity implements OnClickListener {
	Typeface Set_font, Set_font_bold;
	TextView txt_your_result, txt_category, txt_testname, txt_totalque,
			txt_attemptedque, txt_rightque, txt_wrongque, txt_percentage,
			txt_title_your_result, txt_title_category;

	TextView txt_title, txt_title_testname, txt_title_totalque,
			txt_title_attemptedque, txt_title_rightque, txt_title_wrongque,
			txt_title_percentage, txt_back_dashboard;
	Button btn_logout;
	SessionManager session;
	String test_result, Response_code, result;
	String categoryname, testname, totalQue, attemptquestions, rightans,
			wrongans, currentscore, your_result, account_type;

	public static String student_id, AccountType;
	HashMap<String, String> user;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.end_test);
			setContent();
			SetFont();
			clickeEvent();
			PracticeExam.explanation_flag = "0";
			getDataAndsetTextview();
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				new ResultSend().execute();
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
		student_id = user.get(SessionManager.KEY_StudentId);
		AccountType = user.get(SessionManager.KEY_AccountType);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_back_dashboard = (TextView) findViewById(R.id.txt_back_dashboard);
		txt_your_result = (TextView) findViewById(R.id.txt_your_result);
		txt_category = (TextView) findViewById(R.id.txt_category);
		txt_testname = (TextView) findViewById(R.id.txt_testname);
		txt_totalque = (TextView) findViewById(R.id.txt_totalque);
		txt_attemptedque = (TextView) findViewById(R.id.txt_attemptedque);
		txt_rightque = (TextView) findViewById(R.id.txt_rightque);
		txt_wrongque = (TextView) findViewById(R.id.txt_wrongque);
		txt_percentage = (TextView) findViewById(R.id.txt_percentage);
		txt_title_your_result = (TextView) findViewById(R.id.txt_title_your_result);
		txt_title_category = (TextView) findViewById(R.id.txt_title_category);
		txt_title_testname = (TextView) findViewById(R.id.txt_title_testname);
		txt_title_totalque = (TextView) findViewById(R.id.txt_title_totalque);
		txt_title_attemptedque = (TextView) findViewById(R.id.txt_title_attemptedque);
		txt_title_rightque = (TextView) findViewById(R.id.txt_title_rightque);
		txt_title_wrongque = (TextView) findViewById(R.id.txt_title_wrongque);
		txt_title_percentage = (TextView) findViewById(R.id.txt_title_percentage);
		btn_logout = (Button) findViewById(R.id.btn_logout);
	}

	private void SetFont() {
		session = new SessionManager(getApplicationContext());
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		Set_font_bold = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "fonts/AGENCYB.TTF");
		txt_your_result.setTypeface(Set_font_bold);
		txt_title.setTypeface(Set_font);
		txt_category.setTypeface(Set_font);
		txt_testname.setTypeface(Set_font);
		txt_totalque.setTypeface(Set_font);
		txt_attemptedque.setTypeface(Set_font);
		txt_rightque.setTypeface(Set_font);
		txt_wrongque.setTypeface(Set_font);
		txt_percentage.setTypeface(Set_font);
		txt_title_your_result.setTypeface(Set_font);
		txt_title_category.setTypeface(Set_font);
		txt_title_testname.setTypeface(Set_font);
		txt_title_totalque.setTypeface(Set_font);
		txt_title_attemptedque.setTypeface(Set_font);
		txt_title_rightque.setTypeface(Set_font);
		txt_title_wrongque.setTypeface(Set_font);
		txt_title_percentage.setTypeface(Set_font);
		txt_back_dashboard.setTypeface(Set_font);
	}

	private void clickeEvent() {
		btn_logout.setOnClickListener(this);
		txt_back_dashboard.setOnClickListener(this);

	}

	private void getDataAndsetTextview() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		categoryname = extras.getString("CategoryName");
		testname = extras.getString("TestName");
		totalQue = extras.getString("TotalQuestions");
		attemptquestions = extras.getString("AttemptedQuestions");
		rightans = extras.getString("RightAns");
		wrongans = extras.getString("WrongAnd");
		currentscore = extras.getString("CurrentScore");

		if (Integer.valueOf(currentscore) <= 80) {
			your_result = "Failed";
			txt_your_result.setTextColor(Color.parseColor("#ed0e08"));
		} else {
			your_result = "Pass";
		}
		txt_your_result.setText(your_result);
		if (PracticeExam.endTestcate_testname_flag.equals("1")) {
			if (AccountType.equals("0")) {
				txt_category.setText(DemostartpageTest.Category_Name);
				txt_testname.setText(DemostartpageTest.Test_Name);
				PracticeExam.endTestcate_testname_flag = "0";
			}
		} else {
			txt_category.setText(categoryname);
			txt_testname.setText(testname);
		}

		txt_totalque.setText(totalQue);
		txt_attemptedque.setText(attemptquestions);
		txt_rightque.setText(rightans);
		txt_wrongque.setText(wrongans);
		txt_percentage.setText(currentscore + " %");
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

		case R.id.txt_back_dashboard:

			account_type = user.get(SessionManager.KEY_AccountType);
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
			break;

		}

	}

	private class ResultSend extends AsyncTask<String, Void, Void> {

		private Dialog Dialog = new Dialog(EndTest.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Please wait.....");
			try {

				Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				Dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));

				Dialog.setContentView(R.layout.custom_image_dialog);
				Dialog.show();
				Dialog.setCancelable(false);

			} catch (Exception e) {
			//	e.printStackTrace();
			}
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			try {
				postData();
				Log.w("....", "...." + Response_code);
				JSONObject jobj = new JSONObject(Response_code);
				test_result = jobj.getString("ResponseCode");

				Log.e("Result", "result...." + test_result);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			//Dialog.dismiss();
			if (Dialog.isShowing()) {
				Dialog.dismiss(); // or .cancel()
			}
			try {
				register_result();
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}
	}

	public void register_result() {
		// TODO Auto-generated method stub
		if (test_result.endsWith("1")) {

			Log.e("Result", "Sucess send....");

		} else {
			Log.e("Result", "Error....");

		}
	}

	public void postData() {

		try {

			Log.d("Student_id", "Id" + student_id);
			JSONObject json = new JSONObject();
			json.put("student_id", student_id);
			json.put("test_title_id", OnlinePracticeExamApp.test_title_id);
			json.put("cat_id", YourTestSubscriptionApp.CategoryId);
			json.put("total_question", totalQue);
			json.put("attempt", attemptquestions);
			json.put("correct", rightans);
			json.put("wrong", wrongans);
			json.put("persentage", currentscore + " %");

			String uri = StaticData.link + "insert_result.php";

			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());

			result = jsonStr;

//			HttpParams httpParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
//			HttpConnectionParams.setSoTimeout(httpParams, 10000);
//			HttpClient client = new DefaultHttpClient(httpParams);
//
//
//
//			HttpPost request = new HttpPost(url);
//			request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
//			request.setHeader("json", json.toString());
//			HttpResponse response = client.execute(request);
//			HttpEntity entity = response.getEntity();
//			// If the response does not enclose an entity, there is no need
//			if (entity != null) {
//
//				InputStream instream = entity.getContent();
//				result = MainActivity.RestClient.convertStreamToString(instream);
//				Log.i("Read from server", "Response" + result);
//				// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//			}

		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
		}

		// Show response on activity
		Log.w("Reponse....", "Reponse:-" + result);
		Response_code = result;
	}
}

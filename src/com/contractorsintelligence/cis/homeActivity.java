package com.contractorsintelligence.cis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.demo_practice_test.PracticeExam;
import com.contractorsintelligence.online_test_subscription.YourTestSubscriptionApp;
import com.contractorsintelligence.order_online_test.OrderOnlineTest;
import com.contractorsintelligence.settings.SettingsActivity;

public class homeActivity extends Activity implements OnClickListener {
	TextView txt_title;
	Button btn_demo_practice, btn_your_test_subscription,
			btn_order_online_test, btn_settings, btn_logout;
	SessionManager session;
	Typeface set_font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		setContent();
		clickeEvent();
		SetFont();
		
	}
	
	

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
//		finish();
	};

	private void setContent() {
		session = new SessionManager(getApplicationContext());
		txt_title = (TextView) findViewById(R.id.txt_title);
		btn_demo_practice = (Button) findViewById(R.id.btn_demo_practice);
		btn_your_test_subscription = (Button) findViewById(R.id.btn_your_test_subscription);
		btn_order_online_test = (Button) findViewById(R.id.btn_order_online_test);
		btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_logout = (Button) findViewById(R.id.btn_logout);
	}

	private void clickeEvent() {
		btn_demo_practice.setOnClickListener(this);
		btn_your_test_subscription.setOnClickListener(this);
		btn_order_online_test.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}

	private void SetFont() {
		set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_title.setTypeface(set_font);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btn_demo_practice:
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				Intent demo_i = new Intent(this, PracticeExam.class);
				startActivity(demo_i);
			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btn_your_test_subscription:
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				PracticeExam.explanation_flag = "0";
				//PracticeExam.explanation_flag = "1";
				Intent demoTest_i = new Intent(this, YourTestSubscriptionApp.class);
				startActivity(demoTest_i);

			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btn_order_online_test:
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				Intent order_i = new Intent(this, OrderOnlineTest.class);
				startActivity(order_i);
			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btn_settings:
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				Intent settings_i = new Intent(this, SettingsActivity.class);
				startActivity(settings_i);
			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

			break;
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
		}
	}
}

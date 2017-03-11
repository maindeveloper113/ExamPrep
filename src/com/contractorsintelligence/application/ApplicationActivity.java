package com.contractorsintelligence.application;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.LoginActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import java.util.HashMap;

@SuppressWarnings("deprecation")
public class ApplicationActivity extends TabActivity implements
		OnTabChangeListener, OnClickListener {
	TabHost tHost;
	TextView txt_title;
	Button btn_logout;
	SessionManager session;
	int width, height;
	public static int i0_select = 0, i1_unselect = 1, i2_unselect = 2,
			i3_unselect = 3;
	String email;
	HashMap<String, String> user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.application_activity);
			setContent();
			user = session.getUserDetails();

			// Log.e("true or false", "loggin:-" + session.isLoggedIn());
			email = user.get(SessionManager.KEY_EMAIL);
			Log.e("Email", "user E:-" + email);
			if (session.isLoggedIn() == false) {
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}

			clickevent();
			Display display = getWindowManager().getDefaultDisplay();
			width = display.getWidth();
			height = display.getHeight();
			Log.e("display W", "width:-" + width);
			Log.e("display H", "height:-" + height);

			if (width > 1023 || height > 1023) {
				// code for big screen (like tablet)
			} else {
				// code for small screen (like smartphone)
			}
			txt_title = (TextView) findViewById(R.id.txt_title);
			Typeface set_font = Typeface.createFromAsset(
					getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
			txt_title.setTypeface(set_font);

			customeTabDisplay();
		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private void setContent() {
		session = new SessionManager(getApplicationContext());
		btn_logout = (Button) findViewById(R.id.btn_logout);

	}

	private void clickevent() {
		btn_logout.setOnClickListener(this);
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
		}

	}

	private void customeTabDisplay() {
		try {

			Resources resources = getResources();
			tHost = getTabHost();
			TabHost.TabSpec tSpec;
			Intent intent;
			intent = new Intent().setClass(this, VideoApplication.class);
			tSpec = tHost.newTabSpec("first").setIndicator("Videos For\nApplication").setContent(intent);
			tHost.addTab(tSpec);

			intent = new Intent().setClass(this, ImageUpload.class);
			tSpec = tHost.newTabSpec("second")
					.setIndicator("    Upload \nApplication")
					.setContent(intent);
			tHost.addTab(tSpec);

			if (StaticData.isNetworkConnected(getApplicationContext())) {
				intent = new Intent().setClass(this, ViewCorrections.class);
			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

			tSpec = tHost.newTabSpec("third")
					.setIndicator("     View\nCorrections").setContent(intent);
			tHost.addTab(tSpec);

			intent = new Intent().setClass(this, DownloadForms.class);
			tSpec = tHost.newTabSpec("forth")
					.setIndicator("Download \n  Forms").setContent(intent);
			tHost.addTab(tSpec);
			tHost.setCurrentTab(i0_select); // Default Selected Tab

			tHost.setOnTabChangedListener(ApplicationActivity.this);
			// tHost.getTabWidget().getChildAt(0).getLayoutParams().height = 60;
			tHost.getTabWidget().getChildAt(i0_select)
					.setBackgroundResource(R.drawable.tab_selected);
			// tHost.getTabWidget().getChildAt(1).getLayoutParams().height = 60;
			tHost.getTabWidget().getChildAt(i1_unselect)
					.setBackgroundResource(R.drawable.tab_unselected);
			// tHost.getTabWidget().getChildAt(2).getLayoutParams().height = 60;
			tHost.getTabWidget().getChildAt(i2_unselect)
					.setBackgroundResource(R.drawable.tab_unselected);
			// tHost.getTabWidget().getChildAt(3).getLayoutParams().height = 60;
			tHost.getTabWidget().getChildAt(i3_unselect)
					.setBackgroundResource(R.drawable.tab_unselected);
			// tHost.getTabWidget().getChildAt(0)
			// .setBackgroundColor(Color.parseColor("#df8c21"));
			tHost.getTabWidget().getChildAt(i0_select)
					.setBackgroundResource(R.drawable.tab_selected);
			Typeface set_font = Typeface.createFromAsset(
					getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
			for (int i = 0; i < tHost.getTabWidget().getChildCount(); i++) {
				TextView tv = (TextView) tHost.getTabWidget().getChildAt(i)
						.findViewById(android.R.id.title);

				if (width >= 590 || height >= 900) {
					Log.e("true", "if");
					tv.setTextSize(15);
					// tHost.getTabWidget().getChildAt(i).getLayoutParams().height
					// = 100;
				} else {
					Log.e("false", "else");
					tv.setTextSize(11);
				}
				// tv.setTextSize(14);
				tv.setTextColor(Color.WHITE);
				tv.setSingleLine(false);
				tv.setTypeface(set_font);
			}

		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	@Override
	public void onTabChanged(String tabId) {
		try {

			if (tabId.equals("first")) {

				tHost.getTabWidget().getChildAt(0)
						.setBackgroundResource(R.drawable.tab_selected);
				tHost.getTabWidget().getChildAt(1)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(2)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(3)
						.setBackgroundResource(R.drawable.tab_unselected);
			} else if (tabId.equals("second")) {

				tHost.getTabWidget().getChildAt(0)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(1)
						.setBackgroundResource(R.drawable.tab_selected);
				tHost.getTabWidget().getChildAt(2)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(3)
						.setBackgroundResource(R.drawable.tab_unselected);
			} else if (tabId.equals("third")) {

				tHost.getTabWidget().getChildAt(0)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(1)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(2)
						.setBackgroundResource(R.drawable.tab_selected);
				tHost.getTabWidget().getChildAt(3)
						.setBackgroundResource(R.drawable.tab_unselected);

			} else if (tabId.equals("forth")) {
				tHost.getTabWidget().getChildAt(0)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(1)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(2)
						.setBackgroundResource(R.drawable.tab_unselected);
				tHost.getTabWidget().getChildAt(3)
						.setBackgroundResource(R.drawable.tab_selected);
			}

		} catch (Exception e) {
	//		e.printStackTrace();
		}
	}

}

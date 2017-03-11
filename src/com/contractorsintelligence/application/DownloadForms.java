package com.contractorsintelligence.application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.ApplicationhomeActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.StaticData;

public class DownloadForms extends Activity implements OnClickListener {
	Typeface Set_font, Set_font_bold;
	TextView txt_most_form_title, txt_most_forms1, txt_most_forms2,
			txt_most_forms3, txt_other_form_title, txt_other_forms1,
			txt_other_forms2, txt_other_forms3, txt_other_forms4,
			txt_other_forms5;
	public static String selected_txt;

	// Hashmap for ListView

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.downloadforms);
			setContent();
			SetFont();
			clickeEvent();
			selected_txt = "0";
		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private void setContent() {

		txt_most_form_title = (TextView) findViewById(R.id.txt_most_form_title);
		txt_most_forms1 = (TextView) findViewById(R.id.txt_most_forms1);
		txt_most_forms2 = (TextView) findViewById(R.id.txt_most_forms2);
		txt_most_forms3 = (TextView) findViewById(R.id.txt_most_forms3);
		txt_other_form_title = (TextView) findViewById(R.id.txt_other_form_title);
		txt_other_forms1 = (TextView) findViewById(R.id.txt_other_forms1);
		txt_other_forms2 = (TextView) findViewById(R.id.txt_other_forms2);
		txt_other_forms3 = (TextView) findViewById(R.id.txt_other_forms3);
		txt_other_forms4 = (TextView) findViewById(R.id.txt_other_forms4);
		txt_other_forms5 = (TextView) findViewById(R.id.txt_other_forms5);

	}

	private void SetFont() {
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		Set_font_bold = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "fonts/AGENCYB.TTF");
		txt_most_form_title.setTypeface(Set_font_bold);

		txt_most_forms1.setTypeface(Set_font);
		txt_most_forms2.setTypeface(Set_font);
		txt_most_forms3.setTypeface(Set_font);
		txt_other_form_title.setTypeface(Set_font_bold);
		txt_other_forms1.setTypeface(Set_font);
		txt_other_forms2.setTypeface(Set_font);
		txt_other_forms3.setTypeface(Set_font);
		txt_other_forms4.setTypeface(Set_font);
		txt_other_forms5.setTypeface(Set_font);
		// btn_order_online_test.setTypeface(Set_font);
		// btn_settings.setTypeface(Set_font);
	}

	private void clickeEvent() {
		txt_most_forms1.setOnClickListener(this);
		txt_most_forms2.setOnClickListener(this);
		txt_most_forms3.setOnClickListener(this);
		txt_other_forms1.setOnClickListener(this);
		txt_other_forms2.setOnClickListener(this);
		txt_other_forms3.setOnClickListener(this);
		txt_other_forms4.setOnClickListener(this);
		txt_other_forms5.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
	
		try {
			Intent intent = new Intent(getApplicationContext(),
					ApplicationhomeActivity.class);

			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();

		} catch (Exception e) {
		//	e.printStackTrace();
		}
		// }

	}

	@Override
	public void onClick(View v) {
		try {

			switch (v.getId()) {

			case R.id.txt_most_forms1:

				if (StaticData.isNetworkConnected(getApplicationContext())) {

					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/ApplicationForOriginalContractorsLicense.pdf"));
					// selected_txt = "1";

					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.txt_most_forms2:

				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/DisclosureStatementRegardingCriminalPleaConviction.pdf"));

					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.txt_most_forms3:

				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/CertificationOfWorkExperience.pdf"));

					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case R.id.txt_other_forms1:

				if (StaticData.isNetworkConnected(getApplicationContext())) {

					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/ApplicationForOriginalContractorsLicenseLLC.pdf"));

					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.txt_other_forms2:

				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/ApplicationForAdditionalClassification.pdf"));
					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.txt_other_forms3:

				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/ApplicationForReplacingTheQualifyingIndividual.pdf"));
					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.txt_other_forms4:

				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/ApplicationForAsbestosCertification.pdf"));
					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

				} else {
					Toast.makeText(getApplicationContext(),
							"Not connected to Internet", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.txt_other_forms5:

				if (StaticData.isNetworkConnected(getApplicationContext())) {
					Intent browserIntent = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://www.cslb.ca.gov/Resources/FormsAndApplications/ApplicationForHazardousSubstanceRemovalCertification.pdf"));
					browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(browserIntent);

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

}

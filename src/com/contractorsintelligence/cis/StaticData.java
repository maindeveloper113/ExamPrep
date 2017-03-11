package com.contractorsintelligence.cis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;

public class StaticData extends Activity {

	//public static String link = "https://www.contractorsintelligence.com/Services/";

	public static String link= "http://www.contractorsischool.com/mobile-services/";

    //public static String link = "http://192.168.1.2/ExamPrep/Services/";
	//public static String link ="http://moimagazine.iblinfotech.com/SteamGreenClean/";
	private static Dialog pDialog;
	public static boolean isChangedPassword;

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isNetworkConnected(Context context) {
		
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static void Pdialog(Context context) {
		pDialog = new Dialog(context);
		pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		pDialog.setContentView(R.layout.custom_image_dialog);
		// pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		pDialog.show();

	}

	public static void Pdialog_dismiss() {
		pDialog.dismiss();

	}

}

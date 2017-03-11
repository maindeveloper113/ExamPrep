package com.contractorsintelligence.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.contractorsintelligence.cis.R;

@SuppressLint("SetJavaScriptEnabled")
public class ImageDisplay extends Activity {
	private ImageView img_display;

	String Url = null;
	Bitmap bitmap;
	Dialog pDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.imagedisplay);
			Url = getIntent().getExtras().getString("imageurl");
			// Url =
			// "http://www.contractorsintelligence.com/staff/review_document/image_061114094239am.jpg";
			// Url
			// ="http://www.contractorsintelligence.com/staff/review_document/image_211014041844am.jpeg";
			Log.d("Webview Activity", "URL" + Url);

//			img_display = (ImageView) findViewById(R.id.img_display);
//			pDialog = new Dialog(ImageDisplay.this);
//			// pDialog.setMessage("Loading Image ....");
//			pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			pDialog.getWindow().setBackgroundDrawable(
//					new ColorDrawable(Color.TRANSPARENT));
//
//			pDialog.setContentView(R.layout.custom_image_dialog);
//			pDialog.show();
			AQuery aq = new AQuery(ImageDisplay.this);

			aq.id(R.id.img_display).progress(R.id.process).image(Url, true, true);
		} catch (Exception e) {
		//	e.printStackTrace();
		}
		// Picasso.with(getApplicationContext()).load(Url)
		// .into(img_display);

		try {
			// new LoadImage().execute(Url);
		} catch (Exception e) {
		//	e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				ApplicationActivity.class);
		ApplicationActivity.i0_select = 1;
		ApplicationActivity.i1_unselect = 0;
		ApplicationActivity.i2_unselect = 2;
		ApplicationActivity.i3_unselect = 3;
		intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		// finish();
	}

	// http://www.contractorsintelligence.com/staff/review_document/image_211014041844am.jpeg
	// https://www.contractorsintelligence.com/Services/Images/image_041014085203am.jpg

	// private class LoadImage extends AsyncTask<String, String, Bitmap> {
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// pDialog = new Dialog(ImageDisplay.this);
	// // pDialog.setMessage("Loading Image ....");
	// pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// pDialog.getWindow().setBackgroundDrawable(
	// new ColorDrawable(Color.TRANSPARENT));
	//
	// pDialog.setContentView(R.layout.custom_image_dialog);
	// pDialog.show();
	// }
	//
	// protected Bitmap doInBackground(String... args) {
	// String urldisplay = args[0];
	//
	// try {
	// InputStream in = new java.net.URL(urldisplay).openStream();
	// bitmap = BitmapFactory.decodeStream(in);
	// } catch (Exception e) {
	// Log.e("Error", e.getMessage());
	// e.printStackTrace();
	// } catch (OutOfMemoryError O) {
	// O.printStackTrace();
	// }
	//
	// // try {
	// //
	// // bitmap = BitmapFactory.decodeStream((InputStream) new URL(
	// // args[0]).getContent());
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // }
	//
	// return bitmap;
	// }
	//
	// //
	// http://www.contractorsintelligence.com/staff/review_document/image_061114094239am.jpg
	//
	// protected void onPostExecute(Bitmap image) {
	//
	// if (image != null) {
	// img_display.setImageBitmap(bitmap);
	// pDialog.dismiss();
	// } else {
	// pDialog.dismiss();
	// Toast.makeText(ImageDisplay.this,
	// "Image Does Not exist or Network Error",
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	// }

}

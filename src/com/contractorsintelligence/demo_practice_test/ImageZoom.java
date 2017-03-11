package com.contractorsintelligence.demo_practice_test;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.contractorsintelligence.cis.R;
import com.polites.android.GestureImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageZoom extends Activity {
	Button btn_back;
	ImageView img;
	Bitmap bitmap;
	Dialog pDialog;
	String Url;
	protected GestureImageView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_zoom);
		btn_back = (Button) findViewById(R.id.btn_back);
		// img = (ImageView)findViewById(R.id.img);
		Url = getIntent().getExtras().getString("imageurl");
		Url.replace("contractorsintelligence","contractorsischool");
		Log.d("ImageZoom Activity", "URL=" + Url);
		new LoadImage().execute(Url);
		btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
//				Intent intent = new Intent(getApplicationContext(),
//						StartLABtest.class);
//				startActivity(intent);

			}
		});
	}

	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new Dialog(ImageZoom.this);
//			pDialog.setMessage("Loading Image ....");
			pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));
			pDialog.setCancelable(false);
			pDialog.setContentView(R.layout.custom_image_dialog);
			pDialog.show();
		}

		protected Bitmap doInBackground(String... args) {
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(
						args[0]).getContent());
			} catch (Exception e) {
				//e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap image) {
			if (image != null) {
				// img.setImageBitmap(image);
				LayoutParams params = new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				view = new GestureImageView(ImageZoom.this);
				view.setImageBitmap(image);
				view.setLayoutParams(params);

				ViewGroup layout = (ViewGroup) findViewById(R.id.layout);

				layout.addView(view);
				pDialog.dismiss();
			} else {
				pDialog.dismiss();
				Toast.makeText(ImageZoom.this,
						"Image Does Not exist or Network Error",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
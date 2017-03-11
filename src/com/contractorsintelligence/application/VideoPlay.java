package com.contractorsintelligence.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.contractorsintelligence.cis.R;

public class VideoPlay extends Activity {

	ProgressDialog pDialog;
	VideoView videoview;
	String url, video_title;
	TextView txt_video_title;
	Typeface Set_font;

	// Insert your Video URL
	// String VideoURL =
	// "http://www.contractorsischool.com/videos/Android/application-video-1Android.mp4";
	//String VideoURL =
	//"http://www.contractorsischool.com/tutorial/AndroidCommercial.3gp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.video_play);
			url = getIntent().getExtras().getString("VideoUrl");
//			url="http://www.contractorsischool.com/tutorial/AndroidCommercial.3gp";
			video_title = getIntent().getExtras().getString("VideoTitle");

			videoview = (VideoView) findViewById(R.id.play_video);
//			txt_video_title = (TextView) findViewById(R.id.txt_video_title);
			Set_font = Typeface.createFromAsset(getApplicationContext()
					.getAssets(), "fonts/AGENCYR.TTF");
//			txt_video_title.setTypeface(Set_font);
//			txt_video_title.setText(video_title);
			pDialog = new ProgressDialog(VideoPlay.this);

			pDialog.setMessage("please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);

			pDialog.show();

			// Start the MediaController
			MediaController mediacontroller = new MediaController(
					VideoPlay.this);
			mediacontroller.setAnchorView(videoview);


			videoview.setVideoURI(Uri.parse(url));
			videoview.setMediaController(mediacontroller);
			videoview.requestFocus();
			videoview.start();
			videoview.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					Log.e("Video", "can't video play");
					if (pDialog.isShowing())
						pDialog.dismiss();
					return false;
				}
			});
			videoview.setOnPreparedListener(new OnPreparedListener() {
				// Close the progress bar and play the video
				public void onPrepared(MediaPlayer mp) {
					pDialog.dismiss();
					videoview.start();
				}
			});
			videoview.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					Log.e("Complete video", "Complete");
					Intent intent = new Intent(getApplicationContext(),ApplicationActivity.class);
					ApplicationActivity.i0_select = 0;
					ApplicationActivity.i1_unselect = 1;
					ApplicationActivity.i2_unselect = 2;
					ApplicationActivity.i3_unselect = 3;
					intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			//e.printStackTrace();
		}
	}
}

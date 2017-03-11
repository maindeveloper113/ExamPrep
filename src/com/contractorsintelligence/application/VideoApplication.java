package com.contractorsintelligence.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.contractorsintelligence.cis.ApplicationhomeActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.StaticData;
import com.contractorsintelligence.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoApplication extends Activity  {

    // Log tag
    private static final String TAG = VideoApplication.class.getSimpleName();
    // Video json url
    private static final String url = StaticData.link+"getVideo.php";

    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;

    // JSON Node names
    private static final String TAG_VIDEO = "videoApplication";
    private static final String TAG_SN = "SN";
    private static final String TAG_NAME = "Name";
    private static final String TAG_THUMBNAIL_URL = "ThumbnailURL";
    private static final String TAG_ANDROID_URL = "AndroidURL";

    JSONArray videoAppliationJson = null;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        listView = (ListView) findViewById(R.id.listView);
//        adapter = new CustomListAdapter(this, movieList);
//        listView.setAdapter(adapter);

        new getVideos().execute();

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),ApplicationhomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

    /**
     * Async task class to get json by making HTTP call
     * */
    private class getVideos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(VideoApplication.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            //String jsonStr = sh.makeServiceCall(url,ServiceHandler.GET);
            String jsonStr = sh.makeRequest(url,ServiceHandler.GET);
            Log.d("Response: ", "=> " + jsonStr);
            if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        videoAppliationJson = jsonObj.getJSONArray(TAG_VIDEO);
                        Log.d("jsnarr_state: ", "> " + videoAppliationJson);
                        for (int i = 0; i < videoAppliationJson.length(); i++) {
                            try {
                                JSONObject c = videoAppliationJson .getJSONObject(i);
                                String SN = c.getString(TAG_SN);
                                String Name = c.getString(TAG_NAME);
                                String ThumbURL = c.getString(TAG_THUMBNAIL_URL);
                                String AndroidURL = c.getString(TAG_ANDROID_URL);
                                //String androidURLNew = c.getString("androidURLNew");

                                Log.e(TAG, "SN: "+SN);
                                Log.e(TAG, "Name: "+Name);
                                Log.e(TAG, "ThumbURL: "+ThumbURL);
                                Log.e(TAG, "AndroidURL: "+AndroidURL);
                                //Log.e(TAG, "androidURLNew: "+androidURLNew);

                                Movie movie = new Movie();
                                movie.setSN(SN);
                                movie.setTitle(Name);
                                movie.setThumbnailUrl(ThumbURL);
                                movie.setAndroidURL(AndroidURL);
                                //movie.setAndroidURL(iOSURL);
                                //movie.setAndroidURL(androidURLNew);
                                movieList.add(movie);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    Log.e("ServiceHandler",
                            "Couldn't get any data from the jsnarr_state_url");
                }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            /*ListAdapter adapter = new SimpleAdapter(
                    VideoApplication.this, contactList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
                    TAG_PHONE_MOBILE }, new int[] { R.id.name,
                    R.id.email, R.id.mobile });
            */

         //   setListAdapter(adapter);

            adapter = new CustomListAdapter(VideoApplication.this, movieList);
            listView.setAdapter(adapter);
        }

    }
}

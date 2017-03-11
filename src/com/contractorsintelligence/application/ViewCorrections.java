package com.contractorsintelligence.application;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.ApplicationhomeActivity;
import com.contractorsintelligence.cis.MainActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCorrections extends Activity implements OnClickListener {
	Typeface Set_font;
	TextView txt_title, txt_title_document_name, txt_title_review_date,
			txt_title_view;
	Button btn_application;
	ListView lst_view_corrections;
	String Response_code, result, email;
	SessionManager session;
	HashMap<String, String> user;
	List<String> documnet_list, documnet_id_list;
	ArrayList<HashMap<String, String>> ViewCorrectionList;
	String viewText_display;
	private Dialog pDialog;

	private static String url;

	// JSON Node names
	private static final String TAG_View = "Applicationreview";
	private static final String TAG_document_name = "doc_name";
	private static final String TAG_id = "id";
	private static final String TAG_student_view = "student_view";
	private static final String TAG_review_date = "date";

	// contacts JSONArray
	JSONArray ViewCorrectionJson = null;

    String oAuth;

    // Response
    String responseServer;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.viewcorrections);
			setContent();
			SetFont();
			clickeEvent();
		} catch (Exception e) {
		//	e.printStackTrace();
		}

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (StaticData.isNetworkConnected(getApplicationContext())) {
			try {
				new GetViewCorrection().execute();
			} catch (Exception e) {
			//	e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Not connected to Internet", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				ApplicationhomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void setContent() {
		try {

			session = new SessionManager(getApplicationContext());
			user = session.getUserDetails();
			email = user.get(SessionManager.KEY_EMAIL);

            //url = StaticData.link + "dispalyapplicationreview.php?email="+email;
			url = StaticData.link + "dispalyapplicationreview.php";

			txt_title_document_name = (TextView) findViewById(R.id.txt_title_document_name);
			txt_title_review_date = (TextView) findViewById(R.id.txt_title_review_date);
			txt_title_view = (TextView) findViewById(R.id.txt_title_view);
			ViewCorrectionList = new ArrayList<HashMap<String, String>>();
			lst_view_corrections = (ListView) findViewById(R.id.lst_view_corrections);
			documnet_list = new ArrayList<String>();
			documnet_id_list = new ArrayList<String>();
		} catch (Exception e) {
		//	e.printStackTrace();
		}
	}

	private void SetFont() {
		Set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");

		// txt_title.setTypeface(Set_font);
		// txt_application.setTypeface(Set_font);
		txt_title_document_name.setTypeface(Set_font);
		txt_title_review_date.setTypeface(Set_font);
		txt_title_view.setTypeface(Set_font);
		// btn_your_test_subscription.setTypeface(Set_font);
		// btn_order_online_test.setTypeface(Set_font);
		// btn_settings.setTypeface(Set_font);
	}

	private void clickeEvent() {
		// btn_application.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private class GetViewCorrection extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {
				// Showing progress dialog
				pDialog = new Dialog(ViewCorrections.this);
				// pDialog.setMessage("Please wait...");
				pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				pDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));

				pDialog.setContentView(R.layout.custom_image_dialog);
				pDialog.setCancelable(false);
				pDialog.show();

			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... arg0) {

           // postData();
            //CHANGE GET TO POST for //ServiceHandler.GET
			try {
				JSONObject json = new JSONObject();
				json.put("email", email);
				String uri = StaticData.link+"dispalyapplicationreview.php";

				ServiceHandler sh = new ServiceHandler();
				// Making a request to url and getting response
				String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());

                // String jsonStr = responseServer;
                Log.d("Response:", " - " + jsonStr);

				if (jsonStr != null  || jsonStr.contains("Applicationreview")) {

					try {
						JSONObject jsonObj = new JSONObject(jsonStr);

						// Getting JSON Array node
						ViewCorrectionJson = jsonObj.getJSONArray(TAG_View);
						// looping through All Contacts
						for (int i = 0; i < ViewCorrectionJson.length(); i++) {
							JSONObject c = ViewCorrectionJson.getJSONObject(i);
							String documentName = c.getString(TAG_document_name);
							String date = c.getString(TAG_review_date);
							String id = c.getString(TAG_id);
							String student_view = c.getString(TAG_student_view);
							if (student_view.equals("400")) {
								viewText_display = "View (Old)";
							} else if (student_view.equals("401")) {
								viewText_display = "View (New)";
							}
							documnet_list.add(documentName);
							documnet_id_list.add(id);
							// tmp hashmap for single contact
							HashMap<String, String> ViewMap = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							ViewMap.put(TAG_document_name, documentName);
							ViewMap.put(TAG_review_date, date);
							ViewMap.put(TAG_student_view, viewText_display);
							// adding contact to contact list
							ViewCorrectionList.add(ViewMap);
						}

					} catch (JSONException e) {
						//	e.printStackTrace();
						Log.e("ServiceHandler", "Couldn't get any data from the url");
					}
				} else {
					Log.e("ServiceHandler", "Couldn't get any data from the url");
				}
			} catch (JSONException e) {
				//	e.printStackTrace();
			} catch (NullPointerException n) {
				//		n.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			try {
				if ((ViewCorrections.this.pDialog != null) && ViewCorrections.this.pDialog.isShowing()) {
					ViewCorrections.this.pDialog.dismiss();
				}
				// if (pDialog.isShowing())
				// pDialog.dismiss();
				/**
				 * Updating parsed JSON data into ListView
				 * */
				SpecialAdapter adapter = new SpecialAdapter(ViewCorrections.this, ViewCorrectionList,
						R.layout.view_corrections_list, new String[] {
								TAG_document_name, TAG_review_date,
								TAG_student_view }, new int[] {
								R.id.txt_document_name, R.id.txt_review_date,
								R.id.btn_view });

				lst_view_corrections.setAdapter(adapter);
				// setListAdapter(adapter);
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		public class SpecialAdapter extends SimpleAdapter {

			public SpecialAdapter(Context context,
					List<? extends Map<String, ?>> data, int resource,
					String[] from, int[] to) {
				super(context, data, resource, from, to);
				// TODO Auto-generated constructor stub
			}

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {

				View view = super.getView(position, convertView, parent);
				try {

					Log.d("POSITION", "count" + position);
					Log.d("POSITION", "list" + ViewCorrectionList.size());
					// if (position == 0) {
					// view.setBackgroundResource(R.drawable.list_view_main);
					// } else if (ViewCorrectionList.size() == position + 1) {
					// view.setBackgroundResource(R.drawable.list_view_bottom);
					// } else {
					// view.setBackgroundResource(R.drawable.list_view_center);
					// }
					// if (ViewCorrectionList.size() == position) {
					// view.setBackgroundResource(R.drawable.list_view_bottom);
					// }
					TextView txt_document_name = (TextView) view
							.findViewById(R.id.txt_document_name);
					TextView txt_review_date = (TextView) view
							.findViewById(R.id.txt_review_date);
					Typeface Set_font = Typeface.createFromAsset(
							getApplicationContext().getAssets(),
							"fonts/AGENCYR.TTF");
					Button btn_view = (Button) view.findViewById(R.id.btn_view);
					txt_document_name.setTypeface(Set_font);
					txt_review_date.setTypeface(Set_font);
					btn_view.setTypeface(Set_font);

					btn_view.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Log.d("pdf id",
								 "id" + documnet_id_list.get(position));
							Log.d("pdfUrl",
									"http://www.contractorsischool.com/staff/review_document/"
											+ documnet_list.get(position));
							Intent intent = new Intent(ViewCorrections.this,
									WebViewViewcorrections.class);

							intent.putExtra("Document_id",
									documnet_id_list.get(position));
							intent.putExtra("ViewCorrectionUrl",
									"http://www.contractorsischool.com/staff/review_document/"
											+ documnet_list.get(position));
//							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);

							// Toast.makeText(getApplicationContext(),"http://www.contractorsischool.com/staff/review_document/"+
							// documnet_list.get(position),Toast.LENGTH_SHORT).show();

						}
					});

				} catch (Exception e) {
				//	e.printStackTrace();
				}
				return view;
			}

		}
	}
	///////////////////////////////////////////
    public void postData() throws NullPointerException {
        // TODO Auto-generated method stub

        try {
            Log.d("data", "sent");
            JSONObject json = new JSONObject();
            json.put("oAuth", "oAuthExamPrep");
            //json.put("password", pass);

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
            request.setHeader("json", json.toString());
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need

            if (entity != null) {

                InputStream instream = entity.getContent();
                result = MainActivity.RestClient.convertStreamToString(instream);
                Log.i("Read from server", "Login " + result);

              //  Toast.makeText(this, result, Toast.LENGTH_LONG).show();

                Response_code = result;
            }

        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
            Log.i("Request failed:"," "+ t.toString());
        }

    }

    public void postoAuth() throws NullPointerException {
        // TODO Auto-generated method stub

        try {
            Log.d("data", "sent");
            JSONObject json = new JSONObject();
            json.put("oAuth", "oAuthExamPrep");
            //json.put("password", pass);

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            HttpClient client = new DefaultHttpClient(httpParams);

            String url = StaticData.link + "dispalyapplicationreview.php";

            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
            request.setHeader("json", json.toString());
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need

            if (entity != null) {

                InputStream instream = entity.getContent();
                result = MainActivity.RestClient.convertStreamToString(instream);
				Log.i("Read from server", "Login" + result);
               // Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            }

        } catch (Throwable t) {
            //Toast.makeText(this, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
        }
        try {
            Response_code = result;
        } catch (Exception e) {
        //    e.printStackTrace();
        }
    }


    /* Inner class to get response */
    private class AsyncT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = StaticData.link + "displayApplicationReview.php";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try {

                JSONObject jsonobj = new JSONObject();

                jsonobj.put("oAuth", "oAuthExamPrep");


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

                Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

                // Use UrlEncodedFormEntity to send in proper format which we need
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                responseServer = str.getStringFromInputStream(inputStream);
                Log.e("response", "response -----" + responseServer);


            } catch (Exception e) {
             //   e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //txt.setText(responseServer);
            Log.e("BRX", "=>" + responseServer);

        }
    }
///////////////////////////////////////////////////////
    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is = new ByteArrayInputStream("file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);

			System.out.println(result);
            System.out.println("Done");

        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
            //    e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                     //   e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }
}

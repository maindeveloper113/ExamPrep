package com.contractorsintelligence.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewViewcorrections extends Activity {
	private WebView webView;
	TextView txt_final_review_title, txt_final_review_text, txt_final_review,
			txt_id3_text_heading, txt_id3_text_email;
	LinearLayout ll_review;
	String Url, url;
	String document_id, heading, headingid4, data_dear_id4, headingid3,
			email_textid3, final_text, id, id1 = "", id3 = "", id4 = "";
	private static final String TAG_View = "Applicationreview";
	private static final String TAG_View2 = "finalview";
	private static final String TAG_number = "number";
	private static final String TAG_text = "text";
	private static final String TAG_section = "section";
	private static final String TAG_heading = "heading";
	private static final String TAG_emailid3 = "email";
	private static final String TAG_f_text = "text";
	private static final String TAG_data_dear = "data";
	private static final String TAG_id = "id";

	private static final String TAG_Comment_View3 = "comments";
	private static final String TAG_1_6 = "comment_1_6";
	private static final String TAG_2_1 = "comment_2_1";
	private static final String TAG_9_4 = "comment_9_4";
	private static final String TAG_14_4 = "comment_14_4";
	private static final String TAG_16_3 = "comment_16_3";
	private static final String TAG_C4_1 = "comment_C4_1";
	private static final String TAG_C5_3 = "comment_C5_3";
	private static final String TAG_C6_1 = "comment_C6_1";
	private static final String TAG_C8 = "comment_C8";

	JSONArray Doc_ReviewJson = null;
	JSONArray Doc_ReviewJson2 = null;
	JSONArray Doc_ReviewJson3_comment = null;
	ListView lst_review;
	List<String> marginArray, sectionHeightArray, NulldataNumberList,
			NullCommentTextList;
	ArrayList<HashMap<String, String>> ViewsubName, ViewFirstreview;
	Typeface set_font;
	ImageView img_review;
	SessionManager session;
	String fname, lname, flag;
	String First_heading, Text;
	HashMap<String, String> user;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.webview_correction);
			setcontent();
			setFont();


			WebView mWebView = (WebView) findViewById(R.id.webView_correction);
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + getIntent().getExtras().getString("ViewCorrectionUrl"));
			mWebView.setWebViewClient(new WebViewClient() {
			    @Override
			    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			        view.loadUrl(url);

			        return true;
			    }
			});

			new WebViewLoad().execute();
			new Document_Review().execute();

		} catch (Exception e) {
		//	e.printStackTrace();
		}

	}

	private void setcontent() {
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		fname = user.get(SessionManager.KEY_F_Name);
		lname = user.get(SessionManager.KEY_L_Name);
		webView = (WebView) findViewById(R.id.webView_correction);
		webView.setBackgroundColor(0x00000000);
		document_id = getIntent().getExtras().getString("Document_id");
		ViewsubName = new ArrayList<HashMap<String, String>>();
		ViewFirstreview = new ArrayList<HashMap<String, String>>();
		marginArray = new ArrayList<String>();
		NulldataNumberList = new ArrayList<String>();
		NullCommentTextList = new ArrayList<String>();
		// NullCommentTextList.add("aammdjhgdfsfgnbd f hds hfk");
		// NullCommentTextList.add("bbbvd dufuo dfoidfoi aaaa");

		NulldataNumberList.add("1.6");
		NulldataNumberList.add("2.1");
		NulldataNumberList.add("9.4");
		NulldataNumberList.add("14.4");
		NulldataNumberList.add("16.3");
		NulldataNumberList.add("C4.1");
		NulldataNumberList.add("C5.3");
		NulldataNumberList.add("C6.1");
		NulldataNumberList.add("C8");
		sectionHeightArray = new ArrayList<String>();
		lst_review = (ListView) findViewById(R.id.lst_review);
		txt_final_review = (TextView) findViewById(R.id.txt_final_review);
		txt_final_review_title = (TextView) findViewById(R.id.txt_final_review_title);
		txt_final_review_text = (TextView) findViewById(R.id.txt_final_review_text);
		txt_id3_text_heading = (TextView) findViewById(R.id.txt_id3_text_heading);
		txt_id3_text_email = (TextView) findViewById(R.id.txt_id3_text_email);
		img_review = (ImageView) findViewById(R.id.img_review);
		ll_review = (LinearLayout) findViewById(R.id.ll_review);
		Log.d("Doc id", "Id:-" + document_id);
		url = StaticData.link + "documentreview.php?id=" + document_id;
		
	}

	private void setFont() {
		set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_final_review_title.setTypeface(set_font);
		txt_final_review_text.setTypeface(set_font);
		txt_final_review.setTypeface(set_font);
		txt_id3_text_heading.setTypeface(set_font);
		txt_id3_text_email.setTypeface(set_font);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				ApplicationActivity.class);
		ApplicationActivity.i0_select = 2;
		ApplicationActivity.i1_unselect = 0;
		ApplicationActivity.i2_unselect = 1;
		ApplicationActivity.i3_unselect = 3;
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private class WebViewLoad extends AsyncTask<String, Void, Void> {
		private Dialog Dialog = new Dialog(WebViewViewcorrections.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Please wait...");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(
			new ColorDrawable(Color.TRANSPARENT));

			Dialog.setContentView(R.layout.custom_image_dialog);
			Dialog.setCancelable(false);
			Dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				//LoadUrlWebiew();
			} catch (Exception e) {
			//	e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Void unused) {

			//webView.loadUrl(Url);
			Thread splashThread = new Thread() {
				public void run() {

					synchronized (this) {
						try {
							wait(7000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						//	e.printStackTrace();
						}
					}
					try {
						Dialog.dismiss();

					} catch (Exception e) {
					//	e.printStackTrace();
					}

				}
			};

			splashThread.start();

		}
	}

	private void LoadUrlWebiew() {
		

		WebView mWebView = (WebView) findViewById(R.id.webView_correction);
		mWebView.getSettings().setJavaScriptEnabled(true);
		//mWebView.loadUrl("file:///android_asset/agreement.html");
		mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + getIntent().getExtras().getString("ViewCorrectionUrl"));
		mWebView.setWebViewClient(new WebViewClient() {
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);

		        return true;
		    }
		});

		
		 //"http://docs.google.com/gview?embedded=true&url="+
		//Url = "http://docs.google.com/gview?embedded=true&url="
		//Url = "https://docs.google.com/viewer?url="
		//		+ getIntent().getExtras().getString("ViewCorrectionUrl");
	//	Log.d("Webview Activity", "URL" + Url);
	
	   // webView.getSettings().setJavaScriptEnabled(true);
	  //  webView.getSettings().setPluginState(PluginState.ON);
	   // webView.setWebViewClient(new Callback());
	    
	    //webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + getIntent().getExtras().getString("ViewCorrectionUrl"));
	   // webView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://docs.google.com/viewer?url=http%3A%2F%2Fwww.cranialtech.com%2Fimages%2Fstories%2FFiles%2Fpositioningprotocol.pdf");
	  //  webView.loadUrl("http://www.google.com");
	    //  setContentView(webView);
	    
		// String pdfURL =
		// "http://dl.dropboxusercontent.com/u/37098169/Course%20Brochures/AND101.pdf";
	
	// http://www.contractorsintelligence.com/staff/review_document/Aaron_Gonzales.pdf
	// http://www.contractorsintelligence.com/staff/review_document/90573_20140922.pdf
	

	}

	private class Document_Review extends AsyncTask<String, Void, Void> {
		Dialog pDialog = new Dialog(WebViewViewcorrections.this);

		protected void onPreExecute() {
			try {

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
		protected Void doInBackground(String... params)
				throws NullPointerException {
			// TODO Auto-generated method stub
			try {
				ServiceHandler sh = new ServiceHandler();

				// Making a request to url and getting response
				//String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
				String jsonStr = sh.makeRequest(url, ServiceHandler.GET);
				Log.d("Response: ", "> " + jsonStr);

				JSONObject jsonObj = new JSONObject(jsonStr);
				JSONObject jsonObj2 = new JSONObject(jsonStr);

				// Getting JSON Array node
				Doc_ReviewJson = jsonObj.getJSONArray(TAG_View);
				Doc_ReviewJson2 = jsonObj2.getJSONArray(TAG_View2);
				Doc_ReviewJson3_comment = jsonObj2
						.getJSONArray(TAG_Comment_View3);

				for (int i = 0; i < Doc_ReviewJson2.length(); i++) {
					JSONObject c = Doc_ReviewJson2.getJSONObject(i);

					id = c.getString(TAG_id);
					Log.e("main id", "id:-" + id);
					if (id.equals("1")) {
						id1 = id;
						heading = c.getString(TAG_heading);
						final_text = c.getString(TAG_f_text);
						HashMap<String, String> sub_review = new HashMap<String, String>();

						// adding each child node to HashMap key => value

						sub_review.put(TAG_section, heading);
						sub_review.put(TAG_number, "");
						sub_review.put(TAG_text, final_text);

						ViewsubName.add(sub_review);
					}

					if (id.equals("4")) {
						id4 = id;
						headingid4 = c.getString(TAG_heading);
						data_dear_id4 = c.getString(TAG_data_dear) + " "
								+ fname + " " + lname;
						HashMap<String, String> sub_review = new HashMap<String, String>();
						flag = "1";
						// adding each child node to HashMap key => value

						sub_review.put(TAG_section, data_dear_id4);
						sub_review.put(TAG_number, "");
						sub_review.put(TAG_text, headingid4);

						ViewsubName.add(sub_review);
					}
				}

				for (int i = 0; i < Doc_ReviewJson3_comment.length(); i++) {
					JSONObject c = Doc_ReviewJson3_comment.getJSONObject(i);
					String C_1_6 = c.getString(TAG_1_6);
					String C_2_1 = c.getString(TAG_2_1);
					String C_9_4 = c.getString(TAG_9_4);
					String C_14_4 = c.getString(TAG_14_4);
					String C_16_3 = c.getString(TAG_16_3);
					String C_C4_1 = c.getString(TAG_C4_1);
					String C_C5_3 = c.getString(TAG_C5_3);
					String C_C61 = c.getString(TAG_C6_1);
					String C_C8 = c.getString(TAG_C8);
					NullCommentTextList.add(C_1_6);
					NullCommentTextList.add(C_2_1);
					NullCommentTextList.add(C_9_4);
					NullCommentTextList.add(C_14_4);
					NullCommentTextList.add(C_16_3);
					NullCommentTextList.add(C_C4_1);
					NullCommentTextList.add(C_C5_3);
					NullCommentTextList.add(C_C61);
					NullCommentTextList.add(C_C8);

				}
				// looping through All Contacts
				for (int i = 0; i < Doc_ReviewJson.length(); i++) {
					JSONObject c = Doc_ReviewJson.getJSONObject(i);

					String checkbox_number = c.getString(TAG_number);
					String section = c.getString(TAG_section);
					String text = c.getString(TAG_text);
					String id = c.getString(TAG_id);

					if (text.equals("")) {
						for (int j = 0; j < NulldataNumberList.size(); j++) {
							if (checkbox_number.equals(NulldataNumberList
									.get(j))) {
								text = NullCommentTextList.get(j);
							}
						}

						// text = "test";

					}
					int count = checkbox_number.replace(".", "").length();
					Log.d("count", "count count count:-" + count);
					String check_no[] = checkbox_number.split("\\.");
					Log.d("size", "check no size:-" + check_no.length);
					if (check_no.length == 2) {
						if (check_no[1].length() == 1
								|| check_no[1].length() == 2) {
							marginArray.add(String.valueOf(i + 1));
						}

					}
					for (int l = 0; l < ViewsubName.size(); l++) {
						String temp = section;

						if (temp.equals(ViewsubName.get(l).get(TAG_section))) {
							section = "";
							sectionHeightArray.add(String.valueOf(i + 1));
						}
					}
					// documnet_list.add(documentName);
					// documnet_id_list.add(id);
					// tmp hashmap for single contact
					HashMap<String, String> sub_review = new HashMap<String, String>();

					// adding each child node to HashMap key => value

					sub_review.put(TAG_section, section);
					sub_review.put(TAG_number, checkbox_number);
					sub_review.put(TAG_text, text);

					// String nextValue = sub_review.get(TAG_section);
					// if (section.equals(nextValue)) {
					// sub_review.remove(TAG_section);
					// }
					// for (int l = 0; l < ViewsubName.size(); l++) {
					// String temp = ViewsubName.get(l).get(TAG_section);
					// for (int k = l + 1; k < ViewsubName.size(); k++) {
					// if (temp.equals(ViewsubName.get(k).get(TAG_section))) {
					// ViewsubName.remove(TAG_section);
					// }
					// }
					//
					// }
					// adding contact to contact list

					ViewsubName.add(sub_review);

					// sub_review.containsKey(TAG_section);
				}

				for (int i = 0; i < Doc_ReviewJson2.length(); i++) {
					JSONObject c = Doc_ReviewJson2.getJSONObject(i);

					id = c.getString(TAG_id);
					Log.e("main id", "id:-" + id);
					// if (id.equals("1")) {
					// id1 = id;
					// heading = c.getString(TAG_heading);
					// final_text = c.getString(TAG_f_text);
					// }
					// if (id.equals("4")) {
					// id4 = id;
					// headingid4 = c.getString(TAG_heading);
					// data_dear_id4 = c.getString(TAG_data_dear);
					// }
					if (id.equals("3")) {
						id3 = id;
						headingid3 = c.getString(TAG_heading);
						email_textid3 = c.getString(TAG_emailid3);
						// HashMap<String, String> sub_review = new
						// HashMap<String, String>();
						// sub_review.put(TAG_section, headingid3);
						// sub_review.put(TAG_number, "");
						// sub_review.put(TAG_text, email_textid3);
						//
						// ViewsubName.add(sub_review);
					}

				}

				Log.d("array", "margin list:-" + marginArray);

			} catch (Exception e) {
			//	e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Void unused) throws NullPointerException {
			try {

				if (pDialog.isShowing())
					pDialog.dismiss();
				/**
				 * Updating parsed JSON data into ListView
				 * */

				SpecialAdapter adapter = new SpecialAdapter(
						WebViewViewcorrections.this, ViewsubName,
						R.layout.review_list, new String[] { TAG_section,
								TAG_number, TAG_text }, new int[] {
								R.id.txt_section, R.id.txt_sub_no,
								R.id.txt_sub_text });
				// SpecialAdapter2 adapter2 = new SpecialAdapter2(
				// WebViewViewcorrections.this, ViewFirstreview,
				// R.layout.review_first_list, new String[] {
				// First_heading, Text },
				// new int[] { R.id.txt_first_section,
				// R.id.txt_first_sub_text });
				lst_review.setAdapter(adapter);

				// lst_review.setAdapter(adapter);
				// DynamicScroolviewInTextview();
				// if (heading.equals(null) || heading.equals("")
				// || heading.equals("null")) {
				// img_review.setVisibility(View.GONE);
				// }
				Log.e("id1", "id1:-" + id1);
				Log.e("id3", "id3:-" + id3);
				Log.e("id4", "id4:-" + id4);
				if (id.equals("1")) {
					img_review.setVisibility(View.GONE);
					txt_final_review.setText(heading);
					txt_final_review_text.setText(final_text);
					txt_final_review_title.setText(heading);
				} else if (id4.equals("4")) {
					if (id4.equals("4")) {
						img_review.setVisibility(View.GONE);
						txt_final_review.setText(headingid4);
						txt_final_review_title.setText(data_dear_id4);
						txt_final_review_title
								.setGravity(android.view.Gravity.LEFT);
						// txt_final_review_title.setVisibility(View.GONE);
						txt_final_review_text.setVisibility(View.GONE);
					}
					if (id3.equals("3")) {
						txt_id3_text_heading.setVisibility(View.GONE);
						txt_id3_text_email.setVisibility(View.GONE);
						txt_id3_text_heading.setText(Html.fromHtml(headingid3));
						txt_id3_text_email
								.setText(Html.fromHtml(email_textid3));
					}
				}
				// CustomListHeight.setListViewHeightBasedOnChildren(lst_review);
				// setListAdapter(adapter);
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}

		private void DynamicScroolviewInTextview() {
			final int N = 10; // total number of textviews to add

			final TextView[] myTextViews = new TextView[N]; // create an empty
															// array;

			for (int i = 0; i < N; i++) {
				// create a new textview
				final TextView rowTextView = new TextView(
						WebViewViewcorrections.this);

				// set some properties of rowTextView or something
				rowTextView.setText("This is row #" + i);

				// add the textview to the linearlayout
				ll_review.addView(rowTextView);

				// save a reference to the textview for later
				myTextViews[i] = rowTextView;
			}

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

				Log.d("POSITION" + position, "count" + position);
				Log.d("size", "viewsubname" + ViewsubName.size());

				// if (position == 0) {
				// view.setBackgroundResource(R.drawable.list_view_bottom);
				// } else if (ViewCorrectionList.size() == position + 1) {
				// view.setBackgroundResource(R.drawable.list_view_bottom);
				// } else {
				// view.setBackgroundResource(R.drawable.list_view_center);
				// }
				// if (ViewCorrectionList.size() == position) {
				// view.setBackgroundResource(R.drawable.list_view_bottom);
				// }
				TextView txt_section = (TextView) view
						.findViewById(R.id.txt_section);
				TextView txt_sub_no = (TextView) view
						.findViewById(R.id.txt_sub_no);
				TextView txt_sub_text = (TextView) view
						.findViewById(R.id.txt_sub_text);
				TextView txt_headingid3 = (TextView) view
						.findViewById(R.id.txt_headingid3);
				TextView txt_emailid3 = (TextView) view
						.findViewById(R.id.txt_emailid3);

				Typeface Set_font = Typeface.createFromAsset(
						getApplicationContext().getAssets(),
						"fonts/AGENCYR.TTF");
				txt_section.setVisibility(View.VISIBLE);
				ImageView img_check = (ImageView) view
						.findViewById(R.id.img_check);
				if (position == 0) {
					// Dear Leonid Vorontsov

					if (txt_section.getText().toString().equals(data_dear_id4)
							|| txt_section.getText().toString().equals(heading)) {
						Log.e("In", "1");
						img_check.setVisibility(View.GONE);
						flag = "0";
					}
					// view.setVisibility(View.GONE);

				} else {
					img_check.setVisibility(View.VISIBLE);
				}
				// img_check.setVisibility(View.VISIBLE);
				for (int i = 0; i < marginArray.size(); i++) {
					if (String.valueOf(position).equals(marginArray.get(i))) {
						Log.e("pos", "m:-" + position);
						Log.e("pos", "margin:-" + marginArray.get(i));
						Log.e("Array pos", "margin A:-" + marginArray);

						txt_sub_no.setPadding(10, 0, 0, 0);
					}
				}
				for (int i = 0; i < sectionHeightArray.size(); i++) {
					if (String.valueOf(position).equals(
							sectionHeightArray.get(i))) {
						Log.d("sec pos", "section" + sectionHeightArray.get(i));
						// txt_section.setHeight(1);
						txt_section.setVisibility(View.GONE);
					}
				}
				txt_headingid3.setTypeface(Set_font);
				txt_emailid3.setTypeface(Set_font);
				txt_headingid3.setVisibility(View.GONE);
				txt_emailid3.setVisibility(View.GONE);
				if (id3.equals("3") || id4.equals("4")) {
					if (position == ViewsubName.size() - 1) {
						txt_headingid3.setVisibility(View.VISIBLE);
						txt_emailid3.setVisibility(View.VISIBLE);
						txt_emailid3.setText(Html.fromHtml(email_textid3));
						txt_headingid3.setText(Html.fromHtml(headingid3));
					}
				}

				txt_section.setTypeface(Set_font);
				txt_sub_text.setTypeface(Set_font);
				txt_sub_no.setTypeface(Set_font);
				Log.d("section:" + position, "s:-"
						+ txt_section.getText().toString());
			} catch (Exception e) {
			//	e.printStackTrace();
			}
			return view;
		}
	}

	public class SpecialAdapter2 extends SimpleAdapter {

		public SpecialAdapter2(Context context,
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
				Log.d("size", "viewsubname" + ViewsubName.size());

				TextView txt_section = (TextView) view
						.findViewById(R.id.txt_first_section);

				TextView txt_sub_text = (TextView) view
						.findViewById(R.id.txt_first_sub_text);

				Typeface Set_font = Typeface.createFromAsset(
						getApplicationContext().getAssets(),
						"fonts/AGENCYR.TTF");

				txt_section.setTypeface(Set_font);
				txt_sub_text.setTypeface(Set_font);

			} catch (Exception e) {
			//	e.printStackTrace();
			}
			return view;
		}

	}
}

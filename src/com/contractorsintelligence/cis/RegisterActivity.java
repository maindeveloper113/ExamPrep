package com.contractorsintelligence.cis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity implements OnClickListener {

	EditText et_phone_no, et_firstname, et_lastname, et_address_Line, et_city,
			et_email, et_password, et_zip_code, et_conform_password,
			et_conform_email;
	private static String State_url = StaticData.link + "statedisplay.php";
	private static final String array_state = "state";
	private static final String TAG_statename = "state_name";

	private static String desiredquestion_url = StaticData.link
			+ "displaycategory.php";
	private static final String array_desiredquestion = "category";
	private static final String TAG_desiredquestion = "name";
	private static final String TAG_access_levels = "access_levels";
	private static final String TAG_class_id = "id";
	Button btn_register;
	Spinner Spinner_state, Spinner_desiredquestion;
	TextView txt_title;
	String Response_code;
	String register_result, result;
	List<String> state_list, desiredquestion_list, access_levels_list,
			class_listId, test;
	String user_type;
	String email, conform_email, desiredquestion = null, state = null;
	String phone_no, firstname, lastname, address_Line, city, zip_code,
			password, conform_password;
	JSONArray jsnarr_state = null;
	JSONArray jsnarr_desiredquestion = null;
	SessionManager session;
	String[] fiilliste;
	ScrollView regiscrollview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		setContent();
		clickeEvent();
		try {
			new Getdesiredquestion().execute();
			new GetState().execute();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void clickeEvent() {
		// TODO Auto-generated method stub
		btn_register.setOnClickListener(this);
	}

	private void setContent() {
		// TODO Auto-generated method stub
		regiscrollview = (ScrollView) findViewById(R.id.regi_scrollview);
		session = new SessionManager(getApplicationContext());
		txt_title = (TextView) findViewById(R.id.txt_title);
		Spinner_state = (Spinner) findViewById(R.id.spinner_state);
		Spinner_desiredquestion = (Spinner) findViewById(R.id.Spinner_desiredquestion);
		et_phone_no = (EditText) findViewById(R.id.et_phone_no);
		et_firstname = (EditText) findViewById(R.id.et_firstname);
		et_lastname = (EditText) findViewById(R.id.et_lastname);
		et_address_Line = (EditText) findViewById(R.id.et_address_Line);
		et_city = (EditText) findViewById(R.id.et_city);
		et_email = (EditText) findViewById(R.id.et_email);
		et_conform_email = (EditText) findViewById(R.id.et_conform_email);
		et_zip_code = (EditText) findViewById(R.id.et_zip_code);
		et_password = (EditText) findViewById(R.id.et_password);
		et_conform_password = (EditText) findViewById(R.id.et_conform_password);
		btn_register = (Button) findViewById(R.id.btn_register);
		Typeface tf = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "fonts/AGENCYR.TTF");
		btn_register.setTypeface(tf);

		state_list = new ArrayList<String>();
		desiredquestion_list = new ArrayList<String>();
		// desiredquestion_list.add("--Classification (required)--");
		desiredquestion_list.add("Desired Trade Questions.");

		access_levels_list = new ArrayList<String>();
		class_listId = new ArrayList<String>();
		test = new ArrayList<String>();

		// state_list.add("State");

		test.add("txt_title");
		test.add("et_firstname");
		test.add("DisplayDescription");
		txt_title.setTypeface(tf);
		et_firstname.setTypeface(tf);
		et_lastname.setTypeface(tf);
		et_phone_no.setTypeface(tf);
		et_address_Line.setTypeface(tf);
		et_zip_code.setTypeface(tf);
		et_city.setTypeface(tf);
		et_email.setTypeface(tf);
		et_conform_email.setTypeface(tf);
		et_conform_password.setTypeface(tf);
		et_password.setTypeface(tf);

		// addDesiredquestions();
		addState();
		// DisplayDescription();
	}

	private void addState() {
		// TODO Auto-generated method stub
		state_list.add("California");
		state_list.add("Alabama");
		state_list.add("Alaska");
		state_list.add("Arizona");
		state_list.add("Arkansas");

		state_list.add("Colorado");
		state_list.add("Connecticut");
		state_list.add("Delaware");
		state_list.add("District Of Columbia");
		state_list.add("Florida");

		state_list.add("Georgia");
		state_list.add("Guam");
		state_list.add("Hawaii");
		state_list.add("Idaho");
		state_list.add("Illinois");

		state_list.add("Indiana");
		state_list.add("Iowa");
		state_list.add("Kansas");
		state_list.add("Kentucky");
		state_list.add("Louisiana");

		state_list.add("Maine");
		state_list.add("Maryland");
		state_list.add("Massachusetts");
		state_list.add("Michigan");
		state_list.add("Minnesota");

		state_list.add("Mississippi");
		state_list.add("Missourioption");
		state_list.add("Montana");
		state_list.add("Nebraska");
		state_list.add("Nevada");

		state_list.add("New Hampshire");
		state_list.add("New Jersey");
		state_list.add("New Mexico");
		state_list.add("New York");
		state_list.add("North Carolina");

		state_list.add("North Dakota");
		state_list.add("Ohio");
		state_list.add("Oklahoma");
		state_list.add("Oregon");
		state_list.add("Pennsylvania");

		state_list.add("Puerto Rico");
		state_list.add("Rhode Island");
		state_list.add("South Carolina");
		state_list.add("South Dakota");
		state_list.add("Tennessee");

		state_list.add("Texas");
		state_list.add("Utah");
		state_list.add("Vermont");
		state_list.add("Virginia");
		state_list.add("Washington");

		state_list.add("West Virginia");
		state_list.add("Wisconsin");
		state_list.add("Wyoming");
	}

	private void addDesiredquestions() {
		// TODO Auto-generated method stub
		desiredquestion_list.add("None or Not Sure.");
		desiredquestion_list.add("A-General Engineering");
		desiredquestion_list.add("B-General Building");
		desiredquestion_list.add("C-2 Insulation and Acoustical");
		desiredquestion_list
				.add("C-4 Boiler, Hot Water Heating and Steam Fitting");

		desiredquestion_list.add("C-5 Framing and Rough Carpentry");
		desiredquestion_list.add("C-6 Cabinet, Millwork and Finish Carpentry ");
		desiredquestion_list.add("C-7 Low Voltage Systems ");
		desiredquestion_list.add("C-8 Concrete");
		desiredquestion_list.add("C-9 Drywall");

		desiredquestion_list.add("C-10 Electrical");
		desiredquestion_list.add("C-11 Elevator");
		desiredquestion_list.add("C-12 Earthwork and Paving");
		desiredquestion_list.add("C-13 Fencing");
		desiredquestion_list.add("C-15 Flooring and Floor Covering");

		desiredquestion_list.add("C-16 Fire Protection");
		desiredquestion_list.add("C-17 Glazing");
		desiredquestion_list
				.add("C-20 Warm-Air Heating, Ventilating and Air-Conditioning");
		desiredquestion_list.add("C-21 Building Moving and Demolition");
		desiredquestion_list.add("C-23 Ornamental Metal");

		desiredquestion_list.add("C-27 Landscaping");
		desiredquestion_list.add("C-28 Lock and Security Equipment");
		desiredquestion_list.add("C-29 Masonry");
		desiredquestion_list.add("C-31 Construction Zone Traffic Control");
		desiredquestion_list.add("C-32 Parking and Highway Improvement");

		desiredquestion_list.add("C-33 Painting and Decorating	");
		desiredquestion_list.add("C-34 Pipeline");
		desiredquestion_list.add("C-35 Lathing and Plastering");
		desiredquestion_list.add("C-36 Plumbing");
		desiredquestion_list.add("C-38 Refrigeration");

		desiredquestion_list.add("C-39 Roofing");
		desiredquestion_list.add("C-42 Sanitation System");
		desiredquestion_list.add("C-43 Sheet Metal");
		desiredquestion_list.add("C-45 Electrical Sign");
		desiredquestion_list.add("C-46 Solar");

		desiredquestion_list.add("C-47 General Manufactured Housing");
		desiredquestion_list.add("C-50 Reinforcing Steel");
		desiredquestion_list.add("C-51 Structural Steel");
		desiredquestion_list.add("C-53 Swimming Pool");
		desiredquestion_list.add("C-54 Ceramic and Mosaic Tile");

		desiredquestion_list.add("C-55 Water Conditioning");
		desiredquestion_list.add("C-57 Water Well Drilling");
		desiredquestion_list.add("C-60 Welding");
		desiredquestion_list.add("C-61 Limited Speciality");
		desiredquestion_list.add("ASB Asbestos Certification");
		desiredquestion_list
				.add("HAZ Hazardous Substance Removal Certification");

	}

	// private void DisplayDescription() {
	//
	//
	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	// RegisterActivity.this, R.layout.spinner_list, test) {
	//
	// public View getView(int position, View convertView,
	// ViewGroup parent) {
	// View v = super.getView(position, convertView, parent);
	//
	//
	// Typeface externalFont = Typeface.createFromAsset(
	// getAssets(), "fonts/AGENCYR.TTF");
	// ((TextView) v).setTypeface(externalFont);
	// ((TextView) v).setText(test.get(position));
	// // ((TextView) v)
	// // .setTextColor(Color.parseColor("#999999"));
	// // ((TextView) v)
	// // .setTextSize(getResources().getDimension(R.dimen.edittext_size));
	// return v;
	// }
	//
	// public View getDropDownView(int position, View convertView,
	// ViewGroup parent) {
	// View v = super.getDropDownView(position, convertView,
	// parent);
	//
	// Typeface Sp_Font = Typeface.createFromAsset(
	// getAssets(), "fonts/AGENCYR.TTF");
	// ((TextView) v).setTypeface(Sp_Font);
	// ((TextView) v).setText(test.get(position));
	// // ((TextView) v)
	// // .setTextColor(Color.parseColor("#999999"));
	// // ((TextView) v)
	// // .setTextSize(getResources().getDimension(R.dimen.edittext_size));
	// return v;
	// }
	// };
	//
	// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// Spinner_desiredquestion
	// .setPrompt("Select Desired Trade Questions");
	// Spinner_desiredquestion.setAdapter(adapter);
	//
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_register:
			try {
				Log.d("in ", ">1 ");
				phone_no = et_phone_no.getText().toString();
				firstname = et_firstname.getText().toString();
				lastname = et_lastname.getText().toString();
				desiredquestion = Spinner_desiredquestion.getSelectedItem()
						.toString();
				state = Spinner_state.getSelectedItem().toString();

				address_Line = et_address_Line.getText().toString();
				zip_code = et_zip_code.getText().toString();
				city = et_city.getText().toString();
				email = et_email.getText().toString();
				conform_email = et_conform_email.getText().toString();
				password = et_password.getText().toString();
				conform_password = et_conform_password.getText().toString();
				if (firstname.length() <= 0 || firstname.equals("")) {
					regiscrollview.scrollTo(0, 0);
					Log.d("in ", ">1.1 ");
					et_firstname.setError("Invalid First Name");
				} else if (lastname.length() <= 0 || lastname.equals("")) {
					regiscrollview.scrollTo(0, 0);
					et_lastname.setError("Invalid Last Name");
				} else if (phone_no.length() <= 9 || phone_no.equals("")) {
					regiscrollview.scrollTo(0, 0);
					et_phone_no.setError("Invalid Phone Number");
				} else if (desiredquestion_list.get(0).equals(desiredquestion)) {
					regiscrollview.scrollTo(0, 0);
					Toast.makeText(getApplicationContext(),
							"Please Select Desired Trade Questions",
							Toast.LENGTH_SHORT).show();
				}

				else if (!isValidEmail(email)) {
					regiscrollview.scrollTo(0, 0);
					et_email.setError("Invalid Email");
				} else if (!email.equals(conform_email)) {
					regiscrollview.scrollTo(0, 0);
					et_conform_email.setError("Email Is Not match");
				} else if (address_Line.length() <= 0
						|| address_Line.equals("")) {
					regiscrollview.scrollBy(0, +20);
					et_address_Line.setError("Invalid Driving Licence");
				} else if (city.length() <= 0 || city.equals("")) {
					regiscrollview.scrollBy(0, +20);
					et_city.setError("Invalid city Name");
				} else if (zip_code.length() <= 0 || zip_code.equals("")) {
					regiscrollview.scrollBy(0, -20);
					et_zip_code.setError("Invalid Zip Code");
				} else if (password.length() <= 0 || password.equals("")) {
					regiscrollview.scrollBy(0, -20);
					et_email.setError("Invalid Password");
				} else if (!password.equals(conform_password)) {
					regiscrollview.scrollBy(0, -20);
					et_conform_password.setError("Password Is Not match");
				} else {
					if (StaticData.isNetworkConnected(getApplicationContext())) {
						RegisterNow();

					} else {
						Toast.makeText(getApplicationContext(),
								"Not connected to Internet", Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;

			} catch (Exception e) {
				//e.printStackTrace();
			}

		}
	}

	private boolean isValidEmail(String email) {

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	private void RegisterNow() {
		// TODO Auto-generated method stub
		// String serverURL = StaticData.link + "register.php";
		new RegisterOperation().execute();
	}

	private class Getdesiredquestion extends AsyncTask<Void, Void, Void> {

		Dialog pDialog = new Dialog(RegisterActivity.this);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			// Dialog.setMessage("Authanticating...");
			pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));
			pDialog.setContentView(R.layout.custom_image_dialog);
			pDialog.show();
			pDialog.setCancelable(false);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to jsnarr_state_url and getting response
			//String jsonStr = sh.makeServiceCall(desiredquestion_url,ServiceHandler.GET);
			String jsonStr = sh.makeRequest(desiredquestion_url, ServiceHandler.GET);

			Log.d("D Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					jsnarr_desiredquestion = jsonObj
							.getJSONArray(array_desiredquestion);
					Log.d("jsnarr_state: ", "> " + jsnarr_desiredquestion);
					for (int i = 0; i < jsnarr_desiredquestion.length(); i++) {
						try {

							// Log.d("jsnarr desiredquestion length: ", "> "
							// + jsnarr_desiredquestion.length());
							JSONObject c = jsnarr_desiredquestion
									.getJSONObject(i);

							String desiredquestion = c
									.getString(TAG_desiredquestion);
							// String access_level =
							// c.getString(TAG_access_levels);
							String id = c.getString(TAG_class_id);

							// String[] separated =
							// desiredquestion.split("\\(");
							//
							// Log.d("custome separated", "custome pos 0:- "+
							// separated[0]);

							Log.d("desiredquestion ", "> "	+ desiredquestion);
							// access_levels_list.add(access_level);
							desiredquestion_list.add(desiredquestion);
							class_listId.add(id);
						} catch (Exception e) {
							//e.printStackTrace();
						}
					}

				} catch (JSONException e) {
					//e.printStackTrace();
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
			try {
				if (pDialog.isShowing())
					pDialog.dismiss();

				Log.d("Desiredquestion List: ", "> " + desiredquestion_list);

				// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				// RegisterActivity.this,
				// android.R.layout.simple_spinner_item,
				// desiredquestion_list);
				// dataAdapter
				// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Spinner_desiredquestion.setAdapter(dataAdapter);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						RegisterActivity.this, R.layout.spinner_list,
						desiredquestion_list) {

					public View getView(int position, View convertView,
							ViewGroup parent) {
						View v = super.getView(position, convertView, parent);

						Typeface externalFont = Typeface.createFromAsset(
								getAssets(), "fonts/AGENCYR.TTF");
						((TextView) v).setTypeface(externalFont);
						((TextView) v).setText(desiredquestion_list
								.get(position));
						// ((TextView) v)
						// .setTextColor(Color.parseColor("#999999"));
						// ((TextView) v)
						// .setTextSize(getResources().getDimension(R.dimen.edittext_size));
						return v;
					}

					public View getDropDownView(int position, View convertView,
							ViewGroup parent) {
						View v = super.getDropDownView(position, convertView,
								parent);

						Typeface Sp_Font = Typeface.createFromAsset(
								getAssets(), "fonts/AGENCYR.TTF");
						((TextView) v).setTypeface(Sp_Font);
						((TextView) v).setText(desiredquestion_list
								.get(position));
						// ((TextView) v)
						// .setTextColor(Color.parseColor("#999999"));
						// ((TextView) v)
						// .setTextSize(getResources().getDimension(R.dimen.edittext_size));
						return v;
					}
				};

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				Spinner_desiredquestion
						.setPrompt("Select Desired Trade Questions");
				Spinner_desiredquestion.setAdapter(adapter);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private class GetState extends AsyncTask<Void, Void, Void> {
		Dialog pDialog = new Dialog(RegisterActivity.this);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			// Dialog.setMessage("Authanticating...");
			pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));
			pDialog.setContentView(R.layout.custom_image_dialog);
			pDialog.show();
			pDialog.setCancelable(false);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to jsnarr_state_url and getting response
			//String jsonStr = sh.makeServiceCall(State_url, ServiceHandler.GET);
			String jsonStr = sh.makeRequest(State_url, ServiceHandler.GET);
			Log.d("State Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					jsnarr_state = jsonObj.getJSONArray(array_state);
					Log.d("jsnarr_state: ", "> " + jsnarr_state);
					for (int i = 0; i < jsnarr_state.length(); i++) {
						Log.d("jsnarr_state length: ",
								"> " + jsnarr_state.length());
						JSONObject c = jsnarr_state.getJSONObject(i);

						String state = c.getString(TAG_statename);
						Log.d("jsnarr_state : ", "> " + state);

						state_list.add(state);

					}

				} catch (JSONException e) {
					//e.printStackTrace();
				} catch (NullPointerException n) {
					//n.printStackTrace();
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

			try {
				if (pDialog.isShowing())
					pDialog.dismiss();
				// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				// RegisterActivity.this,
				// android.R.layout.simple_spinner_item, state_list);
				// dataAdapter
				// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Spinner_state.setAdapter(dataAdapter);
				Log.d("State list: ", "> " + state_list);

				ArrayAdapter<String> state_adapter = new ArrayAdapter<String>(
						RegisterActivity.this, R.layout.spinner_list,
						state_list) {

					public View getView(int position, View convertView,
							ViewGroup parent) {
						View v = super.getView(position, convertView, parent);

						Typeface externalFont = Typeface.createFromAsset(
								getAssets(), "fonts/AGENCYR.TTF");
						((TextView) v).setTypeface(externalFont);
						((TextView) v).setText(state_list.get(position));
						// ((TextView) v)
						// .setTextColor(Color.parseColor("#999999"));
						// ((TextView) v)
						// .setTextSize(getResources().getDimension(R.dimen.edittext_size));
						return v;
					}

					public View getDropDownView(int position, View convertView,
							ViewGroup parent) {
						View v = super.getDropDownView(position, convertView,
								parent);

						Typeface Sp_Font = Typeface.createFromAsset(
								getAssets(), "fonts/AGENCYR.TTF");
						((TextView) v).setTypeface(Sp_Font);
						((TextView) v).setText(state_list.get(position));
						// ((TextView) v)
						// .setTextColor(Color.parseColor("#999999"));
						// ((TextView) v)
						// .setTextSize(getResources().getDimension(R.dimen.edittext_size));
						return v;
					}
				};

				state_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				Spinner_state.setPrompt("Select State");
				Spinner_state.setAdapter(state_adapter);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private class RegisterOperation extends AsyncTask<String, Void, Void> {

		Dialog Dialog = new Dialog(RegisterActivity.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Register Now.....");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			Dialog.setContentView(R.layout.custom_image_dialog);
			Dialog.show();
			Dialog.setCancelable(false);
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {
			postData();
			Log.w("....", "...." + Response_code);
			try {
				JSONObject jobj = new JSONObject(Response_code);
				register_result = jobj.getString("ResponseCode");

				Log.e("Result", "result...." + register_result);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (NullPointerException n) {
				//n.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			try {
				register_result();
			} catch (Exception e) {
				//e.printStackTrace();
			}

		}
	}

	@SuppressWarnings("deprecation")
	public void register_result() {
		// TODO Auto-generated method stub
		if (register_result.endsWith("1")) {

			// session.createLoginSession(et_email.getText().toString());
			AlertDialog alertDialog = new AlertDialog.Builder(
					RegisterActivity.this).create();

			// Setting Dialog Title
			alertDialog.setTitle(" Register Successfully...");

			// Setting Dialog Message
			// alertDialog.setMessage("Welcome to AndroidHive.info");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.img_green);

			// Setting OK Button
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Write your code here to execute after dialog closed
					// Toast.makeText(getApplicationContext(),"Registration Successful...",
					// Toast.LENGTH_SHORT).show();
					Intent user_assign_i = new Intent(RegisterActivity.this,
							LoginActivity.class);
					startActivity(user_assign_i);
					clearEdittext();
				}
			});

			// Showing Alert Message
			alertDialog.show();

		} else if (register_result.endsWith("2")) {

			Toast.makeText(getApplicationContext(), "Email Already exists...",
					Toast.LENGTH_SHORT).show();

		} else if (register_result.endsWith("3")) {
			Toast.makeText(getApplicationContext(), "Could not enter data.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void clearEdittext() {
		et_phone_no.setText("");
		et_firstname.setText("");
		et_lastname.setText("");
		et_address_Line.setText("");
		et_zip_code.setText("");
		et_city.setText("");
		et_email.setText("");
		et_conform_email.setText("");
		et_password.setText("");
		et_conform_password.setText("");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				RegisterActivity.this, android.R.layout.simple_spinner_item,
				desiredquestion_list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner_desiredquestion.setAdapter(dataAdapter);

		ArrayAdapter<String> dataAdapter_state = new ArrayAdapter<String>(
				RegisterActivity.this, android.R.layout.simple_spinner_item,
				state_list);
		dataAdapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner_state.setAdapter(dataAdapter_state);
	}

	public void postData() {

		try {
			int desiredquestion_position = Spinner_desiredquestion
					.getSelectedItemPosition();

			Log.d("desiredquestion", "Position:-" + desiredquestion_position);
			Log.d("desiredquestion", "Name:-" + desiredquestion_position);
			JSONObject json = new JSONObject();
			json.put("firstname", firstname);
			json.put("lastname", lastname);
			json.put("phone_no", phone_no);
			json.put("desiredquestion",
					class_listId.get(desiredquestion_position - 1));
			json.put("email", email.toLowerCase());
			json.put("address", address_Line);
			json.put("state", state);
			json.put("city", city);
			json.put("zip", zip_code);
			json.put("password", password);

			String uri = StaticData.link + "createaccount.php";

			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());
			result = jsonStr;
		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
		}

		// Show response on activity
		Log.w("Reponse....", "Reponse:-" + result);
		Response_code = result;
	}
}

package com.contractorsintelligence.settings;

import android.app.Activity;
import android.app.Dialog;
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

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeCustomerInfo extends Activity implements OnClickListener {

	EditText et_phone_no, et_firstname, et_lastname, et_address_Line, et_city,
			et_email, et_zip_code;
	private static String State_url = StaticData.link + "statedisplay.php";
	private static final String array_state = "state";
	private static final String TAG_statename = "state_name";

	private static String desiredquestion_url = StaticData.link
			+ "displaycategory.php";
	private static final String array_desiredquestion = "category";
	private static final String TAG_desiredquestion = "name";
	private static final String TAG_access_levels = "access_levels";
	private static final String TAG_class_id = "id";
	Button btn_Change_info;
	Spinner Spinner_state, Spinner_desiredquestion;
	TextView txt_title, txt_peronal_information, txt_user_information;
	String Response_code;
	String register_result, result;
	List<String> state_list, desiredquestion_list, access_levels_list,
			class_listId, test;
	ScrollView changeregi_scrollview;
	String user_type;
	String email, conform_email, desiredquestion = null, state = null;
	String phone_no, firstname, lastname, address_Line, city, zip_code,
			current_email;
	String FirstName, LastName, PhoneNo, Description_Que_id, Email,
			AddressLine, StateName, CityName, ZipCodeno;
	JSONArray jsnarr_state = null;
	JSONArray jsnarr_desiredquestion = null;
	JSONArray get_details = null;
	JSONObject jobj;
	JSONObject jobj_userdetails;
	SessionManager session;
	HashMap<String, String> user;
	String[] fiilliste;
	int statePosition, desiredquestion_pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.change_customer_info);

			setContent();
			clickeEvent();
			new Getdesiredquestion().execute();
			new GetState().execute();
			new GetAllDetails().execute();

		} catch (Exception e) {
	//	e.printStackTrace();
		}
	}

	private void clickeEvent() {
		// TODO Auto-generated method stub
		btn_Change_info.setOnClickListener(this);
	}

	private void setContent() {
		// TODO Auto-generated method stub

		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		current_email = user.get(SessionManager.KEY_EMAIL);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_peronal_information = (TextView) findViewById(R.id.txt_peronal_information);
		txt_user_information = (TextView) findViewById(R.id.txt_user_information);
		changeregi_scrollview = (ScrollView) findViewById(R.id.changeregi_scrollview);
		Spinner_state = (Spinner) findViewById(R.id.spinner_state);
		Spinner_desiredquestion = (Spinner) findViewById(R.id.Spinner_desiredquestion);
		et_phone_no = (EditText) findViewById(R.id.et_phone_no);
		et_firstname = (EditText) findViewById(R.id.et_firstname);
		et_lastname = (EditText) findViewById(R.id.et_lastname);
		et_address_Line = (EditText) findViewById(R.id.et_address_Line);
		et_city = (EditText) findViewById(R.id.et_city);
		et_email = (EditText) findViewById(R.id.et_email);

		et_zip_code = (EditText) findViewById(R.id.et_zip_code);

		btn_Change_info = (Button) findViewById(R.id.btn_Change_info);
		Typeface tf = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "fonts/AGENCYR.TTF");
		btn_Change_info.setTypeface(tf);

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
		txt_peronal_information.setTypeface(tf);
		txt_user_information.setTypeface(tf);
		et_firstname.setTypeface(tf);
		et_lastname.setTypeface(tf);
		et_phone_no.setTypeface(tf);
		et_address_Line.setTypeface(tf);
		et_zip_code.setTypeface(tf);
		et_city.setTypeface(tf);
		et_email.setTypeface(tf);
		et_email.setClickable(false);

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_Change_info:
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

				if (firstname.length() <= 0 || firstname.equals("")) {
					Log.d("in ", ">1.1 ");
					changeregi_scrollview.scrollTo(0, 0);
					et_firstname.setError("Invalid First Name");
				} else if (lastname.length() <= 0 || lastname.equals("")) {
					changeregi_scrollview.scrollTo(0, 0);
					et_lastname.setError("Invalid Last Name");

				} else if (phone_no.length() <= 9 || phone_no.equals("")) {

					et_phone_no.setError("Invalid Phone Number");
				} else if (desiredquestion_list.get(0).equals(desiredquestion)) {
					changeregi_scrollview.scrollTo(0, 0);
					Toast.makeText(getApplicationContext(),
							"Please Select Desired Trade Questions",
							Toast.LENGTH_SHORT).show();
				} else if (!isValidEmail(email)) {
					et_email.setError("Invalid Email");
				} else if (address_Line.length() <= 0
						|| address_Line.equals("")) {
					changeregi_scrollview.scrollTo(0, 0);
					et_address_Line.setError("Invalid Driving Licence");
				} else if (city.length() <= 0 || city.equals("")) {

					et_city.setError("Invalid city Name");
				} else if (zip_code.length() <= 0 || zip_code.equals("")) {
					et_zip_code.setError("Invalid Zip Code");
				} else {
					if (StaticData.isNetworkConnected(getApplicationContext())) {
						ChaneInfoNow();
					} else {
						Toast.makeText(getApplicationContext(),
								"Not connected to Internet", Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;

			} catch (Exception e) {
			//	e.printStackTrace();
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

	private class GetAllDetails extends AsyncTask<String, Void, Void> {

		private Dialog Dialog = new Dialog(ChangeCustomerInfo.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Please wait...");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			Dialog.setContentView(R.layout.custom_image_dialog);
			Dialog.show();
			Dialog.setCancelable(false);
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {
			SendEmail();

			Log.d("....", "...." + Response_code);
			try {

				jobj = new JSONObject(Response_code);
				// login_result = jobj.getString("Result");
				//
				// Log.d("Result", "...." + login_result);
				jobj_userdetails = new JSONObject(Response_code);
				Log.d("UserDetails", "Details:--" + jobj_userdetails);
				get_details = jobj_userdetails.getJSONArray("userdetail");

				Log.d("Only userTrue", "GetUserdetails" + get_details);

				JSONObject jsnobjAll_details = get_details.getJSONObject(0);
				Log.d("JSONObJ_details", "type:--"
						+ jsnobjAll_details);

				FirstName = jsnobjAll_details.getString("name");
				LastName = jsnobjAll_details.getString("lname");
				PhoneNo = jsnobjAll_details.getString("mobilenum");
				Description_Que_id = jsnobjAll_details
						.getString("Classification");
				Email = jsnobjAll_details.getString("email");
				AddressLine = jsnobjAll_details.getString("address");
				StateName = jsnobjAll_details.getString("state");
				CityName = jsnobjAll_details.getString("city");
				ZipCodeno = jsnobjAll_details.getString("zip");

				Log.d("Description_Que_id", "Description_Que_id"
						+ Description_Que_id);
				Log.d("statename", "State NAme:-" + StateName);
				for (int i = -0; i < class_listId.size(); i++) {
					Log.d("i", "i:-" + i);
					if (Description_Que_id.equals(class_listId.get(i))) {
						Log.d("Get Position", "Pos:-" + i);
						Log.d("Get Id", "id:-" + class_listId.get(i));
						Log.d("Get Id", "id:-" + desiredquestion_list.get(i));
						desiredquestion_pos = i;
						break;
					}

				}

				for (int i = 0; i < state_list.size(); i++) {
					if (StateName.equals(state_list.get(i))) {
						Log.d("Get Position", "state Pos:-" + i);
						Log.d("Get Id", "statename:-" + state_list.get(i));
						statePosition = i;
						Log.d("Get Id", "statename:-" + StateName);

					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (NullPointerException n) {
			//	n.printStackTrace();
			} catch (IndexOutOfBoundsException i) {
			//	i.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(Void unused) {

			try {
				if (Dialog.isShowing())
					Dialog.dismiss();
				Log.d("category", "pos c:-" + desiredquestion_pos + 1);
				Log.d("state", "pos c:-" + statePosition);
				Spinner_desiredquestion.setSelection(desiredquestion_pos + 1);
				Spinner_state.setSelection(statePosition);
				et_firstname.setText(FirstName);
				et_lastname.setText(LastName);
				et_phone_no.setText(PhoneNo);
				et_address_Line.setText(AddressLine);
				et_zip_code.setText(ZipCodeno);
				et_city.setText(CityName);
				et_email.setText(current_email);

				// et_conform_password.setText(Password);
			} catch (Exception e) {
				// TODO: handle exception
			//	e.printStackTrace();
			}

		}
	}

	public void SendEmail() {
		// TODO Auto-generated method stub

		try {
			Log.d("email", "sent" + current_email);
			JSONObject json = new JSONObject();
			json.put("email", current_email);

			String uri = StaticData.link + "displayrecord.php";
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());

			result = jsonStr;

		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
		}

		Response_code = result;

	}

	private void ChaneInfoNow() {
		// TODO Auto-generated method stub
		// String serverURL = StaticData.link + "register.php";
		new ChaneInfoNowOperation().execute();

	}

	private class Getdesiredquestion extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog

		}

		@Override
		protected Void doInBackground(Void... arg0) throws NullPointerException {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to jsnarr_state_url and getting response
			//String jsonStr = sh.makeServiceCall(desiredquestion_url,ServiceHandler.GET);
			String jsonStr = sh.makeRequest(desiredquestion_url,ServiceHandler.GET);
			Log.d("Desi Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					jsnarr_desiredquestion = jsonObj.getJSONArray(array_desiredquestion);
					Log.d("jsnarr_state: ", "> " + jsnarr_desiredquestion);
					for (int i = 0; i < jsnarr_desiredquestion.length(); i++) {
						try {
							// Log.d("jsnarr desiredquestion length: ", "> "
							// + jsnarr_desiredquestion.length());
							JSONObject c = jsnarr_desiredquestion.getJSONObject(i);

							String desiredquestion = c.getString(TAG_desiredquestion);
							// String access_level =
							// c.getString(TAG_access_levels);
							String id = c.getString(TAG_class_id);

							// String[] separated =
							// desiredquestion.split("\\(");

							// Log.d("custome separated", "custome pos 0:- "+
							// separated[0]);

							Log.d("jsnarr : ", "> "
									+ desiredquestion);
							// access_levels_list.add(access_level);
							desiredquestion_list.add(desiredquestion);
							class_listId.add(id);
						} catch (Exception e) {
						//	e.printStackTrace();
						}
					}

				} catch (JSONException e) {
				//	e.printStackTrace();
				} catch (NullPointerException n) {
				//	n.printStackTrace();
				} catch (IndexOutOfBoundsException i) {
				//	i.printStackTrace();
				}
			} else {

				Log.e("ServiceHandler",
						"Couldn't get any data from the jsnarr_state_url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
				throws IndexOutOfBoundsException {
			super.onPostExecute(result);
			try {
				Log.d("Desiredquestion List: ", "> " + desiredquestion_list);

				// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				// RegisterActivity.this,
				// android.R.layout.simple_spinner_item,
				// desiredquestion_list);
				// dataAdapter
				// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Spinner_desiredquestion.setAdapter(dataAdapter);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						ChangeCustomerInfo.this, R.layout.spinner_list,
						desiredquestion_list) {

					public View getView(int position, View convertView,
							ViewGroup parent) throws NullPointerException,
							IndexOutOfBoundsException, ArithmeticException {
						View v = super.getView(position, convertView, parent);
						try {

							Typeface externalFont = Typeface.createFromAsset(
									getAssets(), "fonts/AGENCYR.TTF");
							((TextView) v).setTypeface(externalFont);
							((TextView) v).setText(desiredquestion_list
									.get(position));
						} catch (Exception e) {
						//	e.printStackTrace();
						}

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
				Spinner_desiredquestion.setSelection(desiredquestion_pos);
			} catch (Exception e) {
			//	e.printStackTrace();
			}
		}

	}

	private class GetState extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog

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
				//	e.printStackTrace();
				} catch (NullPointerException n) {
				//	n.printStackTrace();
				} catch (IndexOutOfBoundsException i) {
				//	i.printStackTrace();
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
				// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				// RegisterActivity.this,
				// android.R.layout.simple_spinner_item, state_list);
				// dataAdapter
				// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Spinner_state.setAdapter(dataAdapter);
				Log.d("State list: ", "> " + state_list);

				ArrayAdapter<String> state_adapter = new ArrayAdapter<String>(
						ChangeCustomerInfo.this, R.layout.spinner_list,
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
				Spinner_state.setSelection(statePosition);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private class ChaneInfoNowOperation extends AsyncTask<String, Void, Void> {

		private Dialog Dialog = new Dialog(ChangeCustomerInfo.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Please wait.....");
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
			//	e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {

			try {
				if (Dialog.isShowing())
					Dialog.dismiss();

				register_result();
			} catch (Exception e) {
			//	e.printStackTrace();
			}

		}
	}

	public void register_result() {
		// TODO Auto-generated method stub
		if (register_result.endsWith("1")) {

			// session.createLoginSession(et_email.getText().toString());

			Toast.makeText(getApplicationContext(),
					"Information Successfully Updated...", Toast.LENGTH_SHORT)
					.show();
			session.logoutUser();
			finish();
			// Intent user_assign_i = new Intent(this, SettingsActivity.class);
			// startActivity(user_assign_i);

			clearEdittext();
		} else if (register_result.endsWith("2")) {

			Toast.makeText(getApplicationContext(),
					"Could not Information Updated...", Toast.LENGTH_SHORT)
					.show();

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

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				ChangeCustomerInfo.this, android.R.layout.simple_spinner_item,
				desiredquestion_list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner_desiredquestion.setAdapter(dataAdapter);

		ArrayAdapter<String> dataAdapter_state = new ArrayAdapter<String>(
				ChangeCustomerInfo.this, android.R.layout.simple_spinner_item,
				state_list);
		dataAdapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner_state.setAdapter(dataAdapter_state);
	}

	public void postData() {

		try {
			int desiredquestion_position = Spinner_desiredquestion
					.getSelectedItemPosition();

			Log.d("desiredquestion", "Position");
			Log.d("e", "email:-" + email);
			JSONObject json = new JSONObject();
			json.put("email", email.toLowerCase());
			json.put("firstname", firstname);
			json.put("lastname", lastname);
			json.put("phone_no", phone_no);
			json.put("Classification",
					class_listId.get(desiredquestion_position - 1));

			json.put("address", address_Line);
			json.put("state", state);
			json.put("city", city);
			json.put("zip", zip_code);

			String uri = StaticData.link + "updateaccount.php";
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());

			result = jsonStr;

		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
			Log.d("Request failed", "fail:-" + t.toString());
		}

		// Show response on activity
		Log.w("Reponse....", "Reponse:-" + result);
		Response_code = result;
	}
}

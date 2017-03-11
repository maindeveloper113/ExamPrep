package com.contractorsintelligence.cis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import java.util.HashMap;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	public static String logout_id = "0";
	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "AndroidHivePref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";

	public static final String KEY_AccountType = "account_type";
	public static final String KEY_StudentId = "student_id";
	public static final String KEY_StudentPassword = "student_pass";
	public static final String KEY_F_Name = "f_name";
	public static final String KEY_L_Name = "l_name";
	public static final String	KEY_CHANGED_PASSWORD = "Password_changed";
	SessionManager session;
	HashMap<String, String> user;

	AsyncTask<Void, Void, Void> mRegisterTask;
	Controller aController;

	// String account_type;
	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String email, String account_type,
			String student_id, String student_pass, String f_name, String l_name) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		// Storing email in pref
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_AccountType, account_type);
		editor.putString(KEY_StudentId, student_id);
		editor.putString(KEY_StudentPassword, student_pass);
		editor.putString(KEY_F_Name, f_name);
		editor.putString(KEY_L_Name, l_name);
		editor.putBoolean(KEY_CHANGED_PASSWORD, true);
		// commit changes
		editor.commit();
	}

	public void changePassword (Boolean isChanged){
		editor.putBoolean(KEY_CHANGED_PASSWORD, isChanged);
		editor.commit();
	}
	public void ChangedPassword (){
		editor.putBoolean(IS_LOGIN, false);
		editor.commit();
	}
	public boolean isChangedPassword (){
		return pref.getBoolean(KEY_CHANGED_PASSWORD, false);
	}

	/**
	 * Check login method wil check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	public void checkLogin() {
		// Check login status

		// user = session.getUserDetails();
		// account_type =
		// user
		// .get(SessionManager.KEY_AccountType);
		// Log.d("Logintype", "type"+account_type);

		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);

		} else if (MainActivity.account_type.equals("0")) {
			Intent i = new Intent(_context, homeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			_context.startActivity(i);
		} else if (MainActivity.account_type.equals("1")) {
			Intent i = new Intent(_context, ApplicationhomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			_context.startActivity(i);
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();

		// user email id
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		user.put(KEY_AccountType, pref.getString(KEY_AccountType, null));
		user.put(KEY_StudentId, pref.getString(KEY_StudentId, null));
		user.put(KEY_StudentPassword, pref.getString(KEY_StudentPassword, null));
		user.put(KEY_F_Name, pref.getString(KEY_F_Name, null));
		user.put(KEY_L_Name, pref.getString(KEY_L_Name, null));
		// return user
		return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		try {

			// Yes button clicked, do something
			editor.clear();
			editor.putBoolean(KEY_CHANGED_PASSWORD,true);
			editor.commit();

			// After logout redirect user to Loing
			// Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			logout_id = "1";
			// Staring Login Activity
			_context.startActivity(i);
			aController = (Controller) _context;
			final Context context = _context;
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {

					// Register on our server
					// On server creates a new user
					aController.register(context, pref.getString(KEY_EMAIL, null), "0");

					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}

			};

			// execute AsyncTask
			mRegisterTask.execute(null, null, null);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}
}

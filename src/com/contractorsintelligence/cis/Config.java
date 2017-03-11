package com.contractorsintelligence.cis;

public interface Config {

	// CONSTANTS
	static final String YOUR_SERVER_URL = "https://www.contractorsischool.com/mobile-services/gcmregister.php";
	// YOUR_SERVER_URL : Server url where you have placed your server files
	// Google project id
	static final String GOOGLE_SENDER_ID = "755875772818"; // Place here your
															// Google project id

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCM Android Example";

	static final String DISPLAY_MESSAGE_ACTION = "com.androidexample.gcm.DISPLAY_MESSAGE";

	static final String EXTRA_MESSAGE = "message";


}

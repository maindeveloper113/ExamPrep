package com.contractorsintelligence.cis;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {
	SessionManager session;
	public static String account_type;
	HashMap<String, String> user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		try {

			setContentView(R.layout.mainscreen);

			session = new SessionManager(getApplicationContext());
			user = session.getUserDetails();
			new SelectDataTask().execute();
		} catch (Exception e) {
			//e.printStackTrace();
		} catch (OutOfMemoryError o) {
			//o.printStackTrace();
		}
	}
	
	

	private class SelectDataTask extends AsyncTask<String, Void, String> {
		// private final ProgressDialog dialog = new ProgressDialog(Main.this);

		// can use UI thread here
		protected void onPreExecute() {

		}

		// automatically done on worker thread (separate from UI thread)
		protected String doInBackground(final String... args) {
			return null;
		}

		// can use UI thread here
		protected void onPostExecute(final String result) {

			Thread splashThread = new Thread() {
				public void run() {

					synchronized (this) {
						try {
							wait(2000);
						} catch (InterruptedException e) {
							//e.printStackTrace();
						}
					}

					mainpage();
				
				}
				
			};

			splashThread.start();

		}
	}
	
	

	protected void mainpage() {
		// Intent i = new Intent(this, LoginActivity.class);
		// i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(i);
		// finish();
		try {
			// session.logoutUser();
			user = session.getUserDetails();
			account_type = user.get(SessionManager.KEY_AccountType);
			Log.d("Logintype", "type" + account_type);
			session.checkLogin();
			finish();
			
		} catch (Exception e) {
			//e.printStackTrace();
		} catch (OutOfMemoryError o) {
			//o.printStackTrace();
		}
	}

	public static class RestClient {

        public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
          //  e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
               // e.printStackTrace();
            }
        }
        return sb.toString();
    }

//    /* This is a test function which will connects to a given
//     * rest service and prints it's response to Android Log with
//     * labels "Praeda".
//     */
//    public static void connect(String url)
//    {
//
//        HttpClient httpclient = new DefaultHttpClient();
//
//        // Prepare a request object
//        HttpGet httpget = new HttpGet(url);
//
//        // Execute the request
//        HttpResponse response;
//        try {
//            response = httpclient.execute(httpget);
//            // Examine the response status
//            Log.i("Praeda",response.getStatusLine().toString());
//
//            // Get hold of the response entity
//            HttpEntity entity = response.getEntity();
//            // If the response does not enclose an entity, there is no need
//            // to worry about connection release
//
//            if (entity != null) {
//
//                // A Simple JSON Response Read
//                InputStream instream = entity.getContent();
//                String result= convertStreamToString(instream);
//                Log.i("Praeda",result);
//
//                // A Simple JSONObject Creation
//                JSONObject json=new JSONObject(result);
//                Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
//
//                // A Simple JSONObject Parsing
//                JSONArray nameArray=json.names();
//                JSONArray valArray=json.toJSONArray(nameArray);
//                for(int i=0;i<valArray.length();i++)
//                {
//                    Log.i("Praeda","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
//                            +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
//                }
//
//                // A Simple JSONObject Value Pushing
//                json.put("sample key", "sample value");
//                Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
//
//                // Closing the input stream will trigger connection release
//                instream.close();
//            }
//
//
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//          //  e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//         //   e.printStackTrace();
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//          //  e.printStackTrace();
//        }
//    }
    }
}

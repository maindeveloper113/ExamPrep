package com.contractorsintelligence.application;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.ApplicationhomeActivity;
import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.ServiceHandler;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ImageUpload extends Activity {

	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 4;

	private Uri fileUri; // file url to store image/video

	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "ExamCamera";

	private static String url;

	// JSON Node names

	private static final String TAG_View = "Applicationreupload";
	private static final String TAG_document_name = "doc_name";
	private static final String TAG_review_date = "date_time";
	private static final String TAG_review = "review";
	private Uri mImageCaptureUri;
	private ImageView mImageView;
	private AlertDialog dialog;
	//	HttpEntity resEntity;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	String ServerUploadPath, TAG = "UploadFile", mCurrentPhotoPath;
	File file;
	List<String> documnet_list;
	Button takeImage, btn_upload;
	TextView txt_s_n, txt_title_document_name, txt_title_review_date,
			txt_title_view;
	Typeface set_font;
	ListView lst_view_corrections;
	String Response_code, result, email;
	File photoFile = null;
	Bitmap rotatedBMP;
	static final int REQUEST_TAKE_PHOTO = 1;
	String result_upload;
	static String upload_id, photo_click_id, selectedImagePath;
	private static final int SELECT_PICTURE = 1;
	SessionManager session;
	HashMap<String, String> user;
	JSONArray ViewCorrectionJson = null;
	ArrayList<HashMap<String, String>> ViewCorrectionList;
	private Dialog pDialog;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.imageupload);
			if (Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			takeImage = (Button) findViewById(R.id.take_photo);
			// btn_upload = (Button) findViewById(R.id.btn_upload);
			mImageView = (ImageView) findViewById(R.id.imageview);
			setContent();
			SetFont();
			clickeEvent();

		} catch (Exception e) {
			//	e.printStackTrace();
		}
		try {
			if (StaticData.isNetworkConnected(getApplicationContext())) {
				new GetImageList().execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"Not connected to Internet", Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			//	e.printStackTrace();
		}
		// ServerUploadPath =
		// "http://powerfulhuman.com/heartfeltmessaging/Services/picture_upload.php";
		ServerUploadPath = "https://www.contractorsischool.com/mobile-services/pictureupload_android.php";
		// ServerUploadPath =
		// "https://www.contractorsintelligence.com/Services/picture_upload1.php";

		// ServerUploadPath =
		// "http://powerfulhuman.com/Services/picture_upload.php";
		takeImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImage1();
			}
		});
		btn_upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					sendPhoto(rotatedBMP);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//	e.printStackTrace();
				}

			}
		});

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
		session = new SessionManager(getApplicationContext());
		user = session.getUserDetails();
		email = user.get(SessionManager.KEY_EMAIL);
		url = StaticData.link + "dispalyapplicationupload.php?email=" + email;
		btn_upload = (Button) findViewById(R.id.btn_upload);
		mImageView = (ImageView) findViewById(R.id.imageview);

		btn_upload = (Button) findViewById(R.id.btn_upload);
		txt_s_n = (TextView) findViewById(R.id.txt_title_s_n);
		txt_title_document_name = (TextView) findViewById(R.id.txt_title_document_name);
		txt_title_review_date = (TextView) findViewById(R.id.txt_title_review_date);
		txt_title_view = (TextView) findViewById(R.id.txt_title_view);
		mImageView = (ImageView) findViewById(R.id.imageview);
		ViewCorrectionList = new ArrayList<HashMap<String, String>>();
		lst_view_corrections = (ListView) findViewById(R.id.lst_view_corrections);
		documnet_list = new ArrayList<String>();
	}

	private void SetFont() {
		set_font = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
		txt_s_n.setTypeface(set_font);
		txt_title_document_name.setTypeface(set_font);
		txt_title_review_date.setTypeface(set_font);
		txt_title_view.setTypeface(set_font);
		btn_upload.setTypeface(set_font);
	}

	private void clickeEvent() {
		// mTakePhoto.setOnClickListener(this);
		// btn_upload.setOnClickListener(this);

	}

	private class GetImageList extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {
				// Showing progress dialog
				// StaticData.Pdialog(getApplicationContext());
				pDialog = new Dialog(ImageUpload.this);
				pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				pDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));

				pDialog.setContentView(R.layout.custom_image_dialog);
				// pDialog.setMessage("Please wait...");
				pDialog.setCancelable(false);
				pDialog.show();
				// pd = new TransparentProgressDialog(ImageUpload.this,
				// R.drawable.ic_app);

			} catch (Exception e) {
				//	e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			try {

				JSONObject json = new JSONObject();
				json.put("email", email);
				String uri = StaticData.link+"dispalyapplicationupload.php";

				ServiceHandler sh = new ServiceHandler();
				// Making a request to url and getting response
				String jsonStr = sh.makeRequest(uri, ServiceHandler.POST, json.toString());

				Log.d("Response: ", "> " + jsonStr);

				if (jsonStr.contains("Applicationreupload")) {
					JSONObject jsonObj = new JSONObject(jsonStr);

					// Getting JSON Array node
					ViewCorrectionJson = jsonObj.getJSONArray(TAG_View);
					ViewCorrectionList.clear();
					documnet_list.clear();
					// looping through All Contacts
					int j = 1;
					for (int i = 0; i < ViewCorrectionJson.length(); i++) {
						JSONObject c = ViewCorrectionJson.getJSONObject(i);
						String sn = String.valueOf(j);
						j++;
						String document_name = c.getString(TAG_document_name);
						String review_date = c.getString(TAG_review_date);
						String review = c.getString(TAG_review);
						if (review.equals("0")) {
							review = "Waiting for Review";
						} else {
							review = "Review";
						}

						// tmp hashmap for single contact
						documnet_list.add(document_name);
						HashMap<String, String> ViewMap = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						// ViewMap.put("S.N", sn);
						ViewMap.put(TAG_document_name, document_name);
						ViewMap.put(TAG_review_date, review_date);
						ViewMap.put(TAG_review, review);

						// adding contact to contact list
						ViewCorrectionList.add(ViewMap);
					}
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
				if (pDialog.isShowing())
					pDialog.dismiss();
				// StaticData.Pdialog_dismiss();
				// pd.dismiss();
				/**
				 * Updating parsed JSON data into ListView
				 * */
				SpecialAdapter adapter = new SpecialAdapter(ImageUpload.this,
						ViewCorrectionList, R.layout.imagelist_list,
						new String[]{TAG_document_name, TAG_review_date,
								TAG_review}, new int[]{
						R.id.txt_document_name, R.id.txt_review_date,
						R.id.txt_review});

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
					// TextView txt_s_n = (TextView)
					// view.findViewById(R.id.txt_s_n);
					TextView txt_review = (TextView) view
							.findViewById(R.id.txt_review);
					Typeface Set_font = Typeface.createFromAsset(
							getApplicationContext().getAssets(),
							"fonts/AGENCYR.TTF");

					txt_document_name.setTypeface(Set_font);
					txt_review_date.setTypeface(Set_font);
					// txt_s_n.setTypeface(Set_font);
					txt_review.setTypeface(Set_font);
					txt_document_name.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Log.d("Url", "image" + documnet_list.get(position));
							Intent intent = new Intent(ImageUpload.this,
									ImageDisplay.class);
							intent.putExtra("imageurl",
									"http://www.contractorsischool.com/staff/review_document/"
											+ documnet_list.get(position));
							// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							// finish();
						}
					});
					txt_review_date.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Log.d("Url", "image" + documnet_list.get(position));
							Intent intent = new Intent(ImageUpload.this,
									ImageDisplay.class);
							intent.putExtra("imageurl",
									"http://www.contractorsischool.com/staff/review_document/"
											+ documnet_list.get(position));
							// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							// finish();

						}
					});

				} catch (Exception e) {
					//	e.printStackTrace();
				}
				return view;
			}
		}
	}

	private void selectImage1() {

		final CharSequence[] options = {"Take Photo", "Choose from Gallery",
				"Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(ImageUpload.this);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					photo_click_id = "0";
					//takePhotoCamrea();
					captureImage();
				} else if (options[item].equals("Choose from Gallery")) {
					photo_click_id = "1";
					takePhotoGallery();
				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}

		});
		builder.show();
	}

	private void takePhotoGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				SELECT_PICTURE);

	}
	public Bitmap resizeBitmap(Bitmap bitmap) {
		int originalWidth = bitmap.getWidth();
		int originalHeight = bitmap.getHeight();

		if (originalWidth > 4096 || originalHeight  > 4096) {

			int height;
			int width;

			if(originalHeight > originalWidth) {
				height = 4096;
				width  = (int)(originalWidth  / (originalHeight / 4096.0f));
			} else {
				width  = 4096;
				height = (int)(originalHeight / (originalWidth / 4096.0f));
			}

			Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

			return resizedBitmap;
		} else {
			return bitmap;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (photo_click_id == "0") {

				if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
					if (resultCode == RESULT_OK) {
						// successfully captured the image
						// display it in image view
						upload_id = "0";

//						Bitmap photo = (Bitmap) data.getExtras().get("data");
						mImageView.setVisibility(View.VISIBLE);

						// bimatp factory
						BitmapFactory.Options options = new BitmapFactory.Options();

						// downsizing image as it throws OutOfMemory Exception for larger
						// images
						options.inSampleSize = 1;
						Bitmap photo = BitmapFactory.decodeFile(fileUri.getPath(),options);

						photo = resizeBitmap(photo);
						rotatedBMP = photo;
						mImageView.setImageBitmap(photo);


					} else if (resultCode == RESULT_CANCELED) {
						// user cancelled Image capture
						Toast.makeText(getApplicationContext(),
								"User cancelled image capture", Toast.LENGTH_SHORT)
								.show();
					} else {
						// failed to capture image
						Toast.makeText(getApplicationContext(),
								"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
								.show();
					}
				}

			} else if (photo_click_id == "1") {

				if (resultCode == RESULT_OK) {

					if (requestCode == SELECT_PICTURE) {
						Uri selectedImageUri = data.getData();
						//selectedImagePath = getPath(selectedImageUri);
						selectedImagePath = selectedImageUri.getPath();
						fileUri = selectedImageUri;

						try {
							BitmapFactory.Options options = new BitmapFactory.Options();

							// downsizing image as it throws OutOfMemory Exception for larger
							// images
							options.inSampleSize = 1;

							Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
							if (bitmap == null) {
								bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
							}
							if (bitmap == null) {
								Toast.makeText(getApplicationContext(),
										"Please select image!", Toast.LENGTH_SHORT)
										.show();
								rotatedBMP = null;
								return;
							}
							// Log.d(TAG, String.valueOf(bitmap));

							//ImageView imageView = (ImageView) findViewById(R.id.imageView);
							bitmap = resizeBitmap(bitmap);
							rotatedBMP = bitmap;
							mImageView.setImageBitmap(bitmap);

						} catch (IOException e) {
							e.printStackTrace();
						}

					}

				} else if (resultCode == RESULT_CANCELED) {
					// user cancelled Image capture
					Toast.makeText(getApplicationContext(),
							"User cancelled image select", Toast.LENGTH_SHORT)
							.show();
				} else {
					// failed to capture image
					Toast.makeText(getApplicationContext(),
							"Sorry! Failed to select image", Toast.LENGTH_SHORT)
							.show();
				}
			} else {

			}

		} catch (Exception e) {
			//	e.printStackTrace();
		}
	}

	/**
	 * http://developer.android.com/training/camera/photobasics.html
	 */
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		String storageDir = Environment.getExternalStorageDirectory()
				+ "/picupload";
		File dir = new File(storageDir);
		if (!dir.exists())
			dir.mkdir();

		File image = new File(storageDir + "/" + imageFileName + ".jpg");

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = image.getAbsolutePath();
		Log.i(TAG, "photo path = " + mCurrentPhotoPath);

		return image;

	}

	private void setPic() {

		// Get the dimensions of the View
		// int targetW = mImageView.getWidth();
		// int targetH = mImageView.getHeight();
		int targetW = 480;
		int targetH = 700;
		Log.d("Get w", "width" + targetW);
		Log.d("Get H", "height" + targetH);
		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//		bmOptions.inJustDecodeBounds = true;
//		//BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//		int photoW = bmOptions.outWidth;
//		int photoH = bmOptions.outHeight;
//
//		// Determine how much to scale down the image
//		int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//
//		// Decode the image file into a Bitmap sized to fill the View
//		bmOptions.inJustDecodeBounds = false;
//		bmOptions.inSampleSize = scaleFactor << 1;
//		//bmOptions.inPurgeable = true;
		bmOptions.inSampleSize = 8;
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

//		Matrix mtx = new Matrix();
//		int roated;
//		if (upload_id == "1") {
//			roated = 0;
//		} else {
//			roated = 90;
//		}
//		// mtx.postRotate(roated);
//		// // Rotating Bitmap
//		// rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//		// bitmap.getHeight(), mtx, true);
//		// Log.d("Bitmap", "W:-" + bitmap.getWidth());
//		// Log.d("Bitmap", "H:-" + bitmap.getHeight());
//		// if (rotatedBMP != bitmap)
//		// bitmap.recycle();
//
//		// mImageView.setImageBitmap(rotatedBMP);
//		try {
//
//			File imageFile = new File(mCurrentPhotoPath);
//
//			ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
//			int orientation = exif.getAttributeInt(
//					ExifInterface.TAG_ORIENTATION,
//					ExifInterface.ORIENTATION_NORMAL);
//			Log.e("RotateImage", "Exif orientation: " + orientation);
//			switch (orientation) {
//			case ExifInterface.ORIENTATION_ROTATE_270:
//
//				mtx.postRotate(270);
//				rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0,
//						bitmap.getWidth(), bitmap.getHeight(), mtx, true);
//				if (rotatedBMP != bitmap)
//					bitmap.recycle();
//				mImageView.setImageBitmap(rotatedBMP);
//
//				break;
//			case ExifInterface.ORIENTATION_ROTATE_180:
//
//				mtx.postRotate(180);
//				rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0,
//						bitmap.getWidth(), bitmap.getHeight(), mtx, true);
//				if (rotatedBMP != bitmap)
//					bitmap.recycle();
//				mImageView.setImageBitmap(rotatedBMP);
//				break;
//			case ExifInterface.ORIENTATION_ROTATE_90:
//
//				mtx.postRotate(90);
//				rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0,
//						bitmap.getWidth(), bitmap.getHeight(), mtx, true);
//				if (rotatedBMP != bitmap)
//					bitmap.recycle();
//				mImageView.setImageBitmap(rotatedBMP);
//				break;
//			case ExifInterface.ORIENTATION_NORMAL:
//
//				mtx.postRotate(0);
//				rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0,
//						bitmap.getWidth(), bitmap.getHeight(), mtx, true);
//				if (rotatedBMP != bitmap)
//					bitmap.recycle();
//				mImageView.setImageBitmap(rotatedBMP);
//				break;
//			default:
//				mtx.postRotate(0);
//				rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0,
//						bitmap.getWidth(), bitmap.getHeight(), mtx, true);
//				if (rotatedBMP != bitmap)
//					bitmap.recycle();
//				mImageView.setImageBitmap(rotatedBMP);
////				mImageView.setImageBitmap(BitmapFactory
////						.decodeFile(mCurrentPhotoPath));
//
//			}
//
//
//
//		} catch (Exception e) {
//		//	e.printStackTrace();
//		}

		// try {
		// sendPhoto(rotatedBMP);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void sendPhoto(Bitmap bitmap) throws Exception {
		new UploadTask().execute(bitmap);
	}

	private class UploadTask extends AsyncTask<Bitmap, Void, Void> {
		private Dialog Dialog = new Dialog(ImageUpload.this);

		protected void onPreExecute() {
			// Dialog.setMessage("Image Upload...");
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));

			Dialog.setContentView(R.layout.custom_image_dialog);
			Dialog.setCancelable(false);
			Dialog.show();
		}

		protected Void doInBackground(Bitmap... bitmaps) {
			if (bitmaps[0] == null){
				return null;
			}
			setProgress(0);

			Bitmap bitmap = bitmaps[0];
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // convert
			// Bitmap
			// to
			// ByteArrayOutputStream
			// Bitmap
			// to
			// ByteArrayOutputStream
			InputStream fileInputStream = new ByteArrayInputStream(stream.toByteArray()); // convert
			// ByteArrayOutputStream
			// to
			// ByteArrayInputStream

//			ArrayList strPathArry = new ArrayList();
//			strPathArry.add(fileUri.toString());
//			uploadFile(strPathArry);

			{

				HttpURLConnection conn = null;
				DataOutputStream dos = null;
				String lineEnd = "\r\n";
				String twoHyphens = "--";
				String boundary = "*****";
				int bytesRead, bytesAvailable, bufferSize;
				byte[] buffer;
				int maxBufferSize = 1 * 1024 * 1024;

				ServerUploadPath = "https://www.contractorsischool.com/mobile-services/pictureupload_android.php";
				try {
					URL url = new URL(ServerUploadPath);
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoInput(true); // Allow Inputs
					conn.setDoOutput(true); // Allow Outputs
					conn.setUseCaches(false); // Don't use a Cached Copy
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Connection", "Keep-Alive");
					//conn.setRequestProperty("ENCTYPE", "multipart/form-data");
					conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
					//conn.setRequestProperty("profile_pic", fileName);

					dos = new DataOutputStream(conn.getOutputStream());

//Adding Parameter email

					dos.writeBytes(twoHyphens + boundary + lineEnd);
					dos.writeBytes("Content-Disposition: form-data; name=\"profile_pic\";filename=\"" + email +"\"" + lineEnd);
					dos.writeBytes(lineEnd);

					//out.write(("Content-Disposition: form-data; name=\""+ key+"\"; filename=\"" + fileName + "\"\r\n").getBytes());
					//dos.writeBytes("Content-Disposition: form-data; name=\"profile_pic\"; filename=\"" + email + "\"\r\n");
					//dos.writeBytes("Content-Disposition: form-data; name=\"profile_pic\"" + lineEnd);
					//dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
					//dos.writeBytes("Content-Length: " + name.length() + lineEnd);
					//dos.writeBytes(lineEnd);
					//dos.writeBytes(email); // mobile_no is String variable
					//dos.writeBytes(lineEnd);

					//dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter media file(audio,video and image)

//				dos.writeBytes(twoHyphens + boundary + lineEnd);
//
//				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
					// create a buffer of maximum size
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					buffer = new byte[bufferSize];
					// read file and write it into form...
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

					while (bytesRead > 0)
					{
						dos.write(buffer, 0, bufferSize);
						bytesAvailable = fileInputStream.available();
						bufferSize = Math.min(bytesAvailable, maxBufferSize);
						bytesRead = fileInputStream.read(buffer, 0, bufferSize);
					}

					// send multipart form data necesssary after file data...
					dos.writeBytes(lineEnd);
					dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


					BufferedReader in = new BufferedReader(
							new InputStreamReader(
									conn.getInputStream()));
					String inputLine = null;
					StringBuilder sb = new StringBuilder();
					while ((inputLine = in.readLine()) != null){
						sb.append(inputLine);
					}
					Response_code = sb.toString();

					Log.w("....", "...." + Response_code);
					try {
						JSONObject jobj = new JSONObject(Response_code);
						result_upload = jobj.getString("ResponseCode");

						Log.e("Result", "result...." + result_upload);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						//	e.printStackTrace();
					}

					// close the streams //
					fileInputStream.close();
					dos.flush();
					dos.close();

				} catch (MalformedURLException ex) {

					ex.printStackTrace();

					Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
				} catch (final Exception e) {

					e.printStackTrace();

				}

			}

//			DefaultHttpClient httpclient = new DefaultHttpClient();
//			try {
//				// HttpPost httppost = new
//				// HttpPost("http://moimagazine.iblinfotech.com/SteamGreenClean/image/picture_upload_image1.php");
//				// // server
//				HttpClient client = new DefaultHttpClient();
//				HttpPost httppost = new HttpPost(ServerUploadPath); // server
//
//				MultipartEntity reqEntity = new MultipartEntity();
//				// profile_pic
//				// reqEntity.addPart("uploaded_file",
//				// System.currentTimeMillis()+ ".jpg", in);
//				reqEntity.addPart("profile_pic", email, in);
////				 reqEntity.addPart("email", "demo@gmail.com");
//				httppost.setEntity(reqEntity);
//
//				Log.i(TAG, "request " + httppost.getRequestLine());
//				HttpResponse response = null;
//				response = client.execute(httppost);
//				// resEntity = response.getEntity();
//				// Log.d("Enter", "Get Response");
//
//				HttpEntity entity = response.getEntity();
//				// If the response does not enclose an entity, there is no need
//				if (entity != null) {
//
//					InputStream instream = entity.getContent();
//					result = MainActivity.RestClient.convertStreamToString(instream);
//					Log.i("Read from server", "Registration" + result);
//					Response_code = result;
//					// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//				}
//				try {
//					// final String response_str =
//					// EntityUtils.toString(resEntity);
//					// Log.i("RESPONSE", response_str);
//					// JSONObject jobj = new JSONObject(response_str);
//					// result_upload = jobj.getString("ResponseCode");
//					// Log.e("Result", "...." + result_upload);
//					Log.w("....", "...." + Response_code);
//					try {
//						JSONObject jobj = new JSONObject(Response_code);
//						result_upload = jobj.getString("ResponseCode");
//
//						Log.e("Result", "result...." + result_upload);
//
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						//	e.printStackTrace();
//					}
//					if (response != null) {
//						Log.i(TAG, "response " + response.getStatusLine().toString());
//					}
//				} finally {
//
//				}
//			} catch (ClientProtocolException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} finally {
//
//			}

//			if (stream != null) {
//				try {
//					stream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					//	e.printStackTrace();
//				}
//			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// Toast.makeText(Picture_Selection.this, R.string.uploaded,
			// Toast.LENGTH_LONG).show();

			try {
				if (Dialog.isShowing())
					Dialog.dismiss();
				if (result_upload.endsWith("1")) {
					try {
						new GetImageList().execute();
					} catch (Exception e) {
						//	e.printStackTrace();
					}
					// Intent intent = new Intent(getApplicationContext(),
					// ApplicationActivity.class);
					// ApplicationActivity.i0_select = 1;
					// ApplicationActivity.i1_unselect = 0;
					// ApplicationActivity.i2_unselect = 2;
					// ApplicationActivity.i3_unselect = 3;
					// intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(intent);
					// finish();
					// Toast.makeText(getApplicationContext(),"Upload Complete....",
					// Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getApplicationContext(),
							"Server Problem...", Toast.LENGTH_SHORT).show();

				}

			} catch (Exception e) {
				//	e.printStackTrace();
			}

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onResume: " + this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}


	private void captureImage() {

		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}
	/*
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}

		return mediaFile;
	}
}



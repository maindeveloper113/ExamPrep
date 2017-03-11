package com.contractorsintelligence.demo_practice_test;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.contractorsintelligence.cis.R;
import com.polites.android.GestureImageView;

import java.io.InputStream;
import java.net.URL;

public class PdfZoom extends Activity {
    private Dialog pDialog;
    private String pdfUrl;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_zoom);
        webView = (WebView)findViewById(R.id.pdf_webview);
        pdfUrl = getIntent().getExtras().getString("PDF_URL");
       /* webView.setWebViewClient(new WebViewClient() {

            // This method will be triggered when the Page Started Loading

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pDialog = new Dialog(PdfZoom.this);
			  // pDialog.setDismissMessage("Loading PDF");
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                pDialog.setCancelable(false);
                pDialog.setContentView(R.layout.custom_image_dialog);
                pDialog.show();
            }

            // This method will be triggered when the Page loading is completed

            public void onPageFinished(WebView view, String url) {
                pDialog.dismiss();
            }

            // This method will be triggered when error page appear

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                pDialog.dismiss();
                Toast.makeText(PdfZoom.this,
                        "PDF Does Not exist or Network Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
        */
        String url = "http://docs.google.com/gview?embedded=true&url=" + pdfUrl;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
      //  webView.getSettings().setLoadWithOverviewMode(true);
       // webView.getSettings().setUseWideViewPort(true);
    }


}

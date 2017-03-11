package com.contractorsintelligence.demo_practice_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.cis.SessionManager;
import com.contractorsintelligence.cis.StaticData;
import com.contractorsintelligence.online_test_subscription.EndTest;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;


public class StartLABtest extends Activity implements
        OnClickListener {


    private Dialog pDialog;



    String Question, remove_a_qoute, account_type;
    String Explanation;

    Runnable runnable;
    Handler handler = new Handler();
    TextView txt_title, txt_Question, txt_Que_a_tag_pdf, txt_id, txt_right_ans,
            txt_time_reamining, txt_current_score, txt_current_question,
            txt_title_time_reamining, txt_title_current_score,
            txt_title_current_question, txt_test_name, txt_explanation,
            txt_explanation_display;
    TextView txt_QuestionExtend;
    RadioGroup radioAnsGroup;
    RadioButton radio_ans1, radio_ans2, radio_ans3, radio_ans4;
    Button btn_next, btn_end_test, btn_logout;
    ArrayList<String> QuestionList, idList, right_ansList, ans1List, ans2List,
            ans3List, ans4List, ExplanationList;

    String flag = "0";
    String selectAns, test_name, CategoryName;
    Typeface set_font, set_font_bold;
    ArrayList<HashMap<String, String>> contactList;
    SessionManager session;
    HashMap<String, String> user;
    int i = 0, attempted_que = 0, current_que = 1, right_ans = 0,
            wrong_ans = 0, currentScore, totalquestions;
    int j, k, l;

    int second = 59;
    int minute = 59;
    int hours = 1;
    int Second = second, Minute = minute;
    String[] seprated_questions, seprated_Atag_questions, seprated_explanation;
    String[] seprated_questions1;
    String final_url[], final_url_explanation[];
    ImageView img_questions, img_explanation, img_ans1, img_ans2, img_ans3,
            img_ans4;
    WebView webView, webView_atag, webView_ans1, webView_ans2, webView_ans3, webView_ans4;
    WebView webViewAns1;
    ScrollView scroll_view;

    //BRX
    int selectedId;
    String[] separated_ans1,separated_ans2,separated_ans3,separated_ans4;
    String Ans1,Ans2,Ans3,Ans4;
    ImageView img_answer1,img_answer2,img_answer3,img_answer4;
    ////
    String dest_file_path = "test.pdf";
    int downloadedSize = 0, totalsize;
    float per = 0;
    ////

    String splitTag[];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startlab_test);


        setContent();
        clickEvent();
        setFont();
        setWebViewFontSize();

        radio_ans1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectedId=radio_ans1.getId();
                radio_ans1.setChecked(true);
                radio_ans2.setChecked(false);
                radio_ans3.setChecked(false);
                radio_ans4.setChecked(false);
            }
        });

        radio_ans2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectedId=radio_ans2.getId();
                radio_ans1.setChecked(false);
                radio_ans2.setChecked(true);
                radio_ans3.setChecked(false);
                radio_ans4.setChecked(false);
            }
        });

        radio_ans3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectedId=radio_ans3.getId();
                radio_ans1.setChecked(false);
                radio_ans2.setChecked(false);
                radio_ans3.setChecked(true);
                radio_ans4.setChecked(false);

            }
        });

        radio_ans4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectedId=radio_ans4.getId();
                radio_ans1.setChecked(false);
                radio_ans2.setChecked(false);
                radio_ans3.setChecked(false);
                radio_ans4.setChecked(true);
            }
        });

        try {
            new GetQuestionsData().execute();
        } catch (Exception e) {
          //  e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

    }

    private void setWebViewFontSize() {
        if (StaticData.isTablet(getApplicationContext())) {

            int i = 25;

            WebSettings settings= webView.getSettings();
            settings.setDefaultFontSize(i);

            WebSettings settings1= webView_ans1.getSettings();
            settings1.setDefaultFontSize(i);

            WebSettings settings2= webView_ans2.getSettings();
            settings2.setDefaultFontSize(i);

            WebSettings settings3= webView_ans3.getSettings();
            settings3.setDefaultFontSize(i);

            WebSettings settings4= webView_ans4.getSettings();
            settings4.setDefaultFontSize(i);

        }
    }
    private void StartTimer() {

        runnable = new Runnable() {
            @Override
            public void run() {
                if (minute == 0) {
                    hours--;
                    minute = Minute;
                    if (hours == -1) {

                        handler.removeCallbacks(runnable);

                        txt_time_reamining.setText("00:00:00");

                        Log.d("finish", "Test Finish");
                        Toast.makeText(getApplicationContext(), "Test Finish",
                                Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(StartLABtest.this, EndTest.class);

                        Bundle extras = new Bundle();
                        extras.putString("CategoryName", CategoryName);
                        extras.putString("TestName", test_name);
                        extras.putString("TotalQuestions",String.valueOf(QuestionList.size()));
                        extras.putString("AttemptedQuestions",String.valueOf(attempted_que));
                        extras.putString("RightAns", String.valueOf(right_ans));
                        extras.putString("WrongAnd", String.valueOf(wrong_ans));
                        extras.putString("CurrentScore",String.valueOf(currentScore));
                        i.putExtras(extras);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    }

                }

                int minute_length = String.valueOf(minute).length();
                int second_length = String.valueOf(second).length();
                if (hours == -1) {
                    handler.removeCallbacks(runnable);
                    txt_time_reamining.setText("00:00:00");
                } else {

                    if (minute_length == 1) {

                        txt_time_reamining.setText("0" + String.valueOf(hours)
                                + ":0" + String.valueOf(minute) + ":"
                                + String.valueOf(second));
                        if (second_length == 1) {
                            txt_time_reamining.setText("0"
                                    + String.valueOf(hours) + ":0"
                                    + String.valueOf(minute) + ":0"
                                    + String.valueOf(second));
                        }

                    } else {
                        if (second_length == 1) {
                            txt_time_reamining.setText("0"
                                    + String.valueOf(hours) + ":"
                                    + String.valueOf(minute) + ":0"
                                    + String.valueOf(second));
                        } else {
                            txt_time_reamining.setText("0"
                                    + String.valueOf(hours) + ":"
                                    + String.valueOf(minute) + ":"
                                    + String.valueOf(second));
                        }

                    }
                }

                second--;
                if (second >= 0) {

                    handler.postDelayed(this, 1000);
                } else {
                    // if (hours == -1) {
                    // handler.removeCallbacks(runnable);
                    // }
                    minute--;
                    second = Second;
                    StartTimer();
                }
            }

        };
        handler.post(runnable);
    }

    public void setContent() {
        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
        session = new SessionManager(getApplicationContext());

        user = session.getUserDetails();
        account_type = user.get(SessionManager.KEY_AccountType);
        Log.d("Logintype", "type" + account_type);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        img_questions = (ImageView) findViewById(R.id.img_questions);
        img_explanation = (ImageView) findViewById(R.id.img_explanation);

        img_answer1 = (ImageView) findViewById(R.id.img_answer1);
        img_answer2 = (ImageView) findViewById(R.id.img_answer2);
        img_answer3 = (ImageView) findViewById(R.id.img_answer3);
        img_answer4 = (ImageView) findViewById(R.id.img_answer4);

        img_ans1 = (ImageView) findViewById(R.id.img_ans1);
        img_ans2 = (ImageView) findViewById(R.id.img_ans2);
        img_ans3 = (ImageView) findViewById(R.id.img_ans3);
        img_ans4 = (ImageView) findViewById(R.id.img_ans4);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_test_name = (TextView) findViewById(R.id.txt_test_name);
        txt_title_time_reamining = (TextView) findViewById(R.id.txt_title_time_reamining);
        txt_title_current_score = (TextView) findViewById(R.id.txt_title_current_score);
        txt_title_current_question = (TextView) findViewById(R.id.txt_title_current_question);
        txt_Question = (TextView) findViewById(R.id.txt_Question);
        txt_QuestionExtend = (TextView) findViewById(R.id.txt_QuestionExtend);
        txt_Que_a_tag_pdf = (TextView) findViewById(R.id.txt_Que_a_tag_pdf);
        txt_time_reamining = (TextView) findViewById(R.id.txt_time_reamining);
        txt_current_score = (TextView) findViewById(R.id.txt_current_score);
        txt_current_question = (TextView) findViewById(R.id.txt_current_question);
        txt_explanation = (TextView) findViewById(R.id.txt_explanation);

        if (account_type.equals("0")) {
            if (PracticeExam.explanation_flag.equals("1")) {
                txt_explanation.setVisibility(View.VISIBLE);
            } else {
                txt_explanation.setVisibility(View.GONE);
            }
        } else {
            txt_explanation.setVisibility(View.VISIBLE);
        }
        txt_explanation_display = (TextView) findViewById(R.id.txt_explanation_display);
        webView = (WebView) findViewById(R.id.webView_explanation);
        webView_atag = (WebView) findViewById(R.id.webView_atag);

        webView_ans1 = (WebView) findViewById(R.id.webview_ans1);
        webView_ans2 = (WebView) findViewById(R.id.webview_ans2);
        webView_ans3 = (WebView) findViewById(R.id.webview_ans3);
        webView_ans4 = (WebView) findViewById(R.id.webview_ans4);

        radio_ans1 = (RadioButton) findViewById(R.id.radio_ans1);
        radio_ans2 = (RadioButton) findViewById(R.id.radio_ans2);
        radio_ans3 = (RadioButton) findViewById(R.id.radio_ans3);
        radio_ans4 = (RadioButton) findViewById(R.id.radio_ans4);

        btn_end_test = (Button) findViewById(R.id.btn_end_test);
        btn_next = (Button) findViewById(R.id.btn_next);
        // txt_id = (TextView) findViewById(R.id.txt_id);
        // txt_right_ans = (TextView) findViewById(R.id.txt_right_ans);
        contactList = new ArrayList<HashMap<String, String>>();
        // QuestionList = new ArrayList<String>();
        // right_ansList = new ArrayList<String>();
        // idList = new ArrayList<String>();
        // ans1List = new ArrayList<String>();
        // ans2List = new ArrayList<String>();
        // ans3List = new ArrayList<String>();
        // ans4List = new ArrayList<String>();
        //radioAnsGroup.clearCheck();

        img_answer1.setVisibility(View.GONE);
        img_answer2.setVisibility(View.GONE);
        img_answer3.setVisibility(View.GONE);
        img_answer4.setVisibility(View.GONE);

    }

    private void clickEvent() {
        btn_end_test.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    private void setFont() {
        set_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/AGENCYR.TTF");
        set_font_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/AGENCYB.TTF");
        txt_title_time_reamining.setTypeface(set_font);
        txt_title_current_score.setTypeface(set_font);
        txt_title_current_question.setTypeface(set_font);
        btn_end_test.setTypeface(set_font);
        btn_next.setTypeface(set_font);
        radio_ans1.setTypeface(set_font);
        radio_ans2.setTypeface(set_font);
        radio_ans3.setTypeface(set_font);
        radio_ans4.setTypeface(set_font);
        txt_test_name.setTypeface(set_font);
        txt_title.setTypeface(set_font);
        txt_Question.setTypeface(set_font);
        txt_QuestionExtend.setTypeface(set_font);
        txt_Que_a_tag_pdf.setTypeface(set_font);
        txt_time_reamining.setTypeface(set_font);
        txt_current_score.setTypeface(set_font);
        txt_current_question.setTypeface(set_font);
        txt_explanation.setTypeface(set_font_bold);
        txt_explanation_display.setTypeface(set_font);
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_end_test:
                    try {

                        AlertDialog.Builder builder_endtest = new AlertDialog.Builder(
                                this);
                        builder_endtest
                                .setTitle("End Test")
                                .setMessage(
                                        "Are you sure you want to end the exam?")
                                        // .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                // Yes button clicked, do something
                                                handler.removeCallbacks(runnable);
                                                second = 0;
                                                minute = 0;
                                                hours = 0;
                                                Intent i = new Intent(
                                                        StartLABtest.this,
                                                        EndTest.class);

                                                Bundle extras = new Bundle();
                                                extras.putString("CategoryName",
                                                        CategoryName);
                                                extras.putString("TestName",
                                                        test_name);
                                                extras.putString("TotalQuestions",
                                                        String.valueOf(QuestionList
                                                                .size()));
                                                extras.putString(
                                                        "AttemptedQuestions",
                                                        String.valueOf(attempted_que));
                                                extras.putString("RightAns",
                                                        String.valueOf(right_ans));
                                                extras.putString("WrongAnd",
                                                        String.valueOf(wrong_ans));
                                                extras.putString(
                                                        "CurrentScore",
                                                        String.valueOf(currentScore));
                                                i.putExtras(extras);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();
                                            }
                                        }).setNegativeButton("No", null) // Do
                                // nothing
                                // on no
                                .show();
                    } catch (Exception e) {
                        //   e.printStackTrace();
                    }
                    break;
                case R.id.btn_logout:
                    AlertDialog.Builder builder_logout = new AlertDialog.Builder(
                            this);
                    builder_logout
                            .setTitle("Logout")
                            .setMessage("Are you sure you want to log out?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // Yes button clicked, do something
                                            session.logoutUser();
                                            finish();
                                        }
                                    }).setNegativeButton("No", null) // Do nothing
                            // on no
                            .show();

                    break;

            }
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetQuestionsData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                // Showing progress dialog
                pDialog = new Dialog(StartLABtest.this);
                // pDialog.setMessage("Please wait...");
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                pDialog.setContentView(R.layout.custom_image_dialog);
                pDialog.setCancelable(false);
                pDialog.show();

            } catch (Exception e) {
             //   e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Void... arg0) throws NullPointerException {
            try {
                QuestionList = getIntent().getStringArrayListExtra("QuestionsList");
                idList = getIntent().getStringArrayListExtra("IdList");
                ans1List = getIntent().getStringArrayListExtra("Ans1List");
                ans2List = getIntent().getStringArrayListExtra("Ans2List");
                ans3List = getIntent().getStringArrayListExtra("Ans3List");
                ans4List = getIntent().getStringArrayListExtra("Ans4List");
                right_ansList = getIntent().getStringArrayListExtra("RightAnsList");
                ExplanationList = getIntent().getStringArrayListExtra("ExplanationList");
                test_name = getIntent().getStringExtra("TestName");
                CategoryName = getIntent().getStringExtra("CategoryName");

                Log.d("QuestionList Size", "setter Size:--" + QuestionList.size());

            } catch (Exception e) {
            //    e.printStackTrace();
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
            try {
                txt_test_name.setText(test_name);

                totalquestions = QuestionList.size() + 1;

                Log.d("Questions", "QuestionsList" + QuestionList.size());

                Log.d("Total Questions", "total" + totalquestions);

                QuestionsAndAnswerDisplay();

                StartTimer();

                btn_next.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            if (flag == "0") {
                                // int selectedId = radioAnsGroup.getCheckedRadioButtonId();
                                // radioAnsGroup.getCheckedRadioButtonId();
                                Log.d("Select id get", "button" + selectedId);
                                Log.d("Id A", "A" + R.id.radio_ans1);
                                Log.d("Id B", "B" + R.id.radio_ans2);
                                Log.d("Id C", "C" + R.id.radio_ans3);
                                Log.d("Id D", "D" + R.id.radio_ans4);

                                //Toast.makeText(getApplicationContext(),"Selected Answer"+selectedId,Toast.LENGTH_SHORT).show();

                                if (selectedId == 0) {
                                    Toast.makeText(getApplicationContext(),"Please Select The Answer",Toast.LENGTH_SHORT).show();
                                } else {
                                    if (selectedId == radio_ans1.getId()) {
                                        selectAns = "A";
                                    } else if (selectedId == radio_ans2.getId()) {
                                        selectAns = "B";
                                    } else if (selectedId == radio_ans3.getId()) {
                                        selectAns = "C";
                                    } else if (selectedId == radio_ans4.getId()) {
                                        selectAns = "D";
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Please Select The Answer",Toast.LENGTH_SHORT).show();
                                    }
                                    // selectAns = (String) radioSelectedButton
                                    // .getText();
                                    Log.w("Selected ans", "Answer:=="+ selectAns);

                                    Log.w("Right ans","List" + right_ansList.get(i));

                                    RadioButton selected_rdo_btn = (RadioButton) findViewById(selectedId);

                                    if (selectAns.equals(right_ansList.get(i))) {

                                        // Toast.makeText(getApplicationContext(),"Right Answer",Toast.LENGTH_SHORT).show();

                                        // selected_rdo_btn.setBackgroundResource(R.drawable.radio_btn_green);
                                        Log.d("selected_rdo_btn","selected btn:-" + selected_rdo_btn);
                                        // if (selectedId == R.id.radio_ans1) {
                                        //
                                        // }
                                        if (selectedId == R.id.radio_ans1) {
                                            img_ans1.setVisibility(View.VISIBLE);
                                            img_ans1.setImageResource(R.drawable.img_green);
                                        } else if (selectedId == R.id.radio_ans2) {
                                            img_ans2.setVisibility(View.VISIBLE);
                                            img_ans2.setImageResource(R.drawable.img_green);
                                        } else if (selectedId == R.id.radio_ans3) {
                                            img_ans3.setVisibility(View.VISIBLE);
                                            img_ans3.setImageResource(R.drawable.img_green);
                                        } else if (selectedId == R.id.radio_ans4) {
                                            img_ans4.setVisibility(View.VISIBLE);
                                            img_ans4.setImageResource(R.drawable.img_green);
                                        }
                                        right_ans++;
                                        Log.d("Right ans", "List"+ right_ansList.get(i));
                                        Log.d("right ans", "count" + right_ans);
                                        currentScore = Math.round(right_ans * 100 / QuestionList.size());

                                     //   webView.requestFocus();
                                    //    scroll_view.scrollTo(0, scroll_view.getBottom());

                                       // scroll_view.fullScroll(ScrollView.FOCUS_UP);
                                    } else {
                                        // Toast.makeText(getApplicationContext(),"Wrong Answer",Toast.LENGTH_SHORT).show();
                                        if (selectedId == R.id.radio_ans1) {
                                            img_ans1.setVisibility(View.VISIBLE);
                                            img_ans1.setImageResource(R.drawable.img_red);
                                        } else if (selectedId == R.id.radio_ans2) {
                                            img_ans2.setVisibility(View.VISIBLE);
                                            img_ans2.setImageResource(R.drawable.img_red);
                                        } else if (selectedId == R.id.radio_ans3) {
                                            img_ans3.setVisibility(View.VISIBLE);
                                            img_ans3.setImageResource(R.drawable.img_red);
                                        } else if (selectedId == R.id.radio_ans4) {
                                            img_ans4.setVisibility(View.VISIBLE);
                                            img_ans4.setImageResource(R.drawable.img_red);
                                        }
                                        // selected_rdo_btn.setBackgroundResource(R.drawable.radio_btn_red);

                                        Log.d("wrong ans", "1");
                                        Log.d("Right ans", "List"+ right_ansList.get(i));

                                        if (right_ansList.get(i).equals("A")) {
                                            img_ans1.setVisibility(View.VISIBLE);
                                            // radio_ans1.setBackgroundResource(R.drawable.radio_btn_green);
                                            img_ans1.setImageResource(R.drawable.img_green);
                                        } else if (right_ansList.get(i).equals("B")) {
                                            img_ans2.setVisibility(View.VISIBLE);
                                            // radio_ans2.setBackgroundResource(R.drawable.radio_btn_green);
                                            img_ans2.setImageResource(R.drawable.img_green);
                                        } else if (right_ansList.get(i).equals("C")) {
                                            img_ans3.setVisibility(View.VISIBLE);
                                            // radio_ans3.setBackgroundResource(R.drawable.radio_btn_green);
                                            img_ans3.setImageResource(R.drawable.img_green);
                                        } else if (right_ansList.get(i).equals("D")) {
                                            img_ans4.setVisibility(View.VISIBLE);
                                            // radio_ans4.setBackgroundResource(R.drawable.radio_btn_green);
                                            img_ans4.setImageResource(R.drawable.img_green);
                                        }
                                        wrong_ans++;
                                        Log.d("wrong ans", "count" + wrong_ans);
                                    }


                                    if (!ExplanationList.get(i).equals("")) {
                                         if (!ExplanationList.get(i).equals("null")) {
                                                // scroll_view.scrollBy(0, +20);
                                                // scroll_view.fullScroll(ScrollView.FOCUS_DOWN);
                                                webView.setBackgroundColor(0x00000000);
                                                if (account_type.equals("0")) {
                                                    if (PracticeExam.explanation_flag.equals("1")) {
                                                        txt_explanation_display.setVisibility(View.VISIBLE);
                                                        webView.setVisibility(View.VISIBLE);
                                                    } else {
                                                        txt_explanation_display.setVisibility(View.GONE);
                                                        webView.setVisibility(View.GONE);
                                                    }

                                                } else {
                                                    txt_explanation_display.setVisibility(View.VISIBLE);
                                                    webView.setVisibility(View.VISIBLE);
                                                }
                                                //BRX EXPLANTION
                                                // Explanation = removeHtmlFrom(ExplanationList.get(i)).toString();
                                                Explanation = (ExplanationList.get(i)).toString();
                                                Explanation = URLDecoder.decode(Explanation, "UTF-8");
                                                //webView.setVisibility(View.VISIBLE);
                                                webView.getSettings().setJavaScriptEnabled(true);
                                                //webView.getSettings().setAppCacheEnabled(false);
                                                //webView.getSettings().setCa
                                                webView.loadData(formatHtmlString2(Explanation), "text/html", "UTF-8");
                                                webView.requestFocus();
                                                scroll_view.scrollTo(0, scroll_view.getBottom() + 30);
                                          /*  seprated_explanation = Explanation.split("\\<img");
                                            //seprated_explanation = ExplanationList.get(i).split("\\<img");
                                            scroll_view.scrollBy(0, -20);

                                            //BRX
                                           // txt_explanation_display.setText(Html.fromHtml(superscript(seprated_explanation[0]).toString()));
                                            //Spanned sp = Html.fromHtml(superscript(seprated_explanation[0]).toString().replace("<li>","<br>&#149;"));
                                            Spanned sp = Html.fromHtml((seprated_explanation[0]).toString().replace("<li>", "<br>&#149;"));
                                            //String sp=formatHtmlString(superscript(seprated_explanation[0]).toString());
                                            txt_explanation_display.setText(sp);
                                            //txt_explanation_display.setText(Html.fromHtml(seprated_explanation[0]).toString());
                                            //txt_explanation_display.setText(Html.fromHtml(removeHtmlFrom(seprated_explanation[0]).toString()));
                                            if (seprated_explanation.length == 2) {
                                                // scroll_view.fullScroll(ScrollView.FOCUS_DOWN);
                                                // regiscrollview.scrollBy(0,
                                                // -20);
                                                Log.d("seprated_explanation","[1]:" + seprated_explanation[1]);
                                                if (seprated_explanation[1].startsWith(" src")|| seprated_explanation[1].startsWith(" src")) {

                                                    String que_img_tag[] = seprated_explanation[1].split("\\=");

                                                    String remove_qoute = que_img_tag[1].replace("\"", "");
                                                    Log.d("IMG_URL","==:-"+ remove_qoute);
                                                    Log.d("IMG_URL", "==" + que_img_tag[1]);
                                                    final_url_explanation = remove_qoute.split("\\>");

                                                    Log.d("IMG_URL_FINAL","" + final_url_explanation[0]);

                                                    if (account_type.equals("0")) {
                                                        img_explanation.setVisibility(View.GONE);
                                                    } else {
                                                        img_explanation.setVisibility(View.VISIBLE);
                                                    }
                                                    Picasso.with(StartLABtest.this).load(final_url_explanation[0])
                                                            .into(img_explanation);
                                                    img_explanation.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    //Log.d("URL_IMAGE","=="+ final_url_explanation[0]);
                                                    Log.d("URL_IMAGE","=>"+ splitTag[1]);
                                                    Intent intent = new Intent(StartLABtest.this,ImageZoom.class);
                                                    intent.putExtra("imageurl",splitTag[1]);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);

                                                     }
                                                    });
                                                }

                                            }*/

                                         }
                                    }

                                    attempted_que++;
                                    btn_next.setText("Next");
                                    //radioAnsGroup.clearCheck();
                                    //radioAnsGroup.setClickable(false);

                                    radio_ans1.setClickable(false);
                                    radio_ans2.setClickable(false);
                                    radio_ans3.setClickable(false);
                                    radio_ans4.setClickable(false);

                                    selectedId=0;

                                    flag = "1";
                                    if (current_que == QuestionList.size()) {
                                        Log.d("Finish", "test");
                                        btn_next.setText("Finish Test");
                                        flag = "3";

                                        // btn_next.setClickable(false);
                                    }
                                }

                            } else {
                                // scroll_view.scrollTo(0,
                                // scroll_view.getTop());
                                if (flag == "3") {
                                    handler.removeCallbacks(runnable);
                                    second = 0;
                                    minute = 0;
                                    hours = 0;
                                    Intent i = new Intent(StartLABtest.this,EndTest.class);

                                    Bundle extras = new Bundle();
                                    extras.putString("CategoryName",
                                            CategoryName);
                                    extras.putString("TestName", test_name);
                                    extras.putString("TotalQuestions",
                                            String.valueOf(QuestionList.size()));
                                    extras.putString("AttemptedQuestions",
                                            String.valueOf(attempted_que));
                                    extras.putString("RightAns",
                                            String.valueOf(right_ans));
                                    extras.putString("WrongAnd",
                                            String.valueOf(wrong_ans));
                                    extras.putString("CurrentScore",
                                            String.valueOf(currentScore));
                                    i.putExtras(extras);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();

                                } else {

                                    radio_ans1.setChecked(false);
                                    radio_ans2.setChecked(false);
                                    radio_ans3.setChecked(false);
                                    radio_ans4.setChecked(false);

                                    radio_ans1.setClickable(true);
                                    radio_ans2.setClickable(true);
                                    radio_ans3.setClickable(true);
                                    radio_ans4.setClickable(true);

                                    img_answer1.setVisibility(View.GONE);
                                    img_answer2.setVisibility(View.GONE);
                                    img_answer3.setVisibility(View.GONE);
                                    img_answer4.setVisibility(View.GONE);

                                    i++;
                                    current_que++;
                                    Log.d("Questions Id", "id" + idList.get(i));
                                    QuestionsAndAnswerDisplay();
                                    //radioAnsGroup.clearCheck();
                                    //radioAnsGroup.setClickable(false);


                                    flag = "0";
                                    btn_next.setText("Submit");
                                }

                            }

                        } catch (Exception e) {
                         //   e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
            //     e.printStackTrace();
                Log.d("I increment process", "I++:- ");
            }
        }

        private void QuestionsAndAnswerDisplay() {

            try {

                img_ans1.setVisibility(View.VISIBLE);
                img_ans2.setVisibility(View.VISIBLE);
                img_ans3.setVisibility(View.VISIBLE);
                img_ans4.setVisibility(View.VISIBLE);
                img_ans1.setImageResource(R.drawable.img_white);
                img_ans2.setImageResource(R.drawable.img_white);
                img_ans3.setImageResource(R.drawable.img_white);
                img_ans4.setImageResource(R.drawable.img_white);
                scroll_view.scrollTo(0, 0);
                scroll_view.fullScroll(ScrollView.FOCUS_UP);

                //BRX
                img_questions.setVisibility(View.GONE);

                webView.setVisibility(View.GONE);
                txt_explanation_display.setVisibility(View.GONE);
                img_explanation.setVisibility(View.GONE);
                // txt_explanation_display.setVisibility(View.GONE);
                webView_atag.setVisibility(View.GONE);
                txt_Que_a_tag_pdf.setVisibility(View.GONE);
                txt_Question.setVisibility(View.VISIBLE);
                txt_QuestionExtend.setVisibility(View.GONE);

                Question = QuestionList.get(i);
                Question = URLDecoder.decode(Question, "UTF-8");

                seprated_questions = Question.split("\\<img");

                seprated_Atag_questions = Question.split("\\<a");

               // seprated_questions1 = Question.split("\\|");

                Log.d("img size", "img tag" + seprated_questions.length);
                Log.d("A size", "a tag" + seprated_Atag_questions.length);
                Log.d("separated_questions 0", "=" + seprated_questions[0]);
                //if(seprated_questions1.length != 1){
                //Log.d("seprated_questions1", "seprated_questions1:==" + seprated_questions1[1]);
                //txt_QuestionExtend.setText(Html.fromHtml(seprated_questions1[1]));
                //}

                if (seprated_questions.length > 1) {
                    Log.d("Seprate1", "1:==" + seprated_questions[1]);
                    //Log.d("Seprate1", "1:==" + seprated_questions[2]);
                    txt_Question.setText(Html.fromHtml(seprated_questions[0].trim()));

                    if (seprated_questions.equals(0)){

                    }
                    int src_pos = seprated_questions[1].indexOf("src=");
                    if ( src_pos > -1){
                        int start_pos = seprated_questions[1].indexOf("\"", src_pos);
                        int end_pos = seprated_questions[1].indexOf("\"", start_pos+1);

                      String imagePath = seprated_questions[1].substring(start_pos+1, end_pos);

                     final String real_imagePath = imagePath.replace("&amp;","&");
                  //  if (seprated_questions[1].startsWith(" src") || seprated_questions[1].startsWith("src")) {
                        /**
                        String que_img_tag[] = seprated_questions[1].split("\\=");
                        String remove_qoute = que_img_tag[1].replace("\"", "");
                        Log.d("IMG_URL_QUESTION", "REMOVE==" + remove_qoute);
                        Log.d("IMG_URL_QUESTION", "==" + que_img_tag[1]);
                        final_url = remove_qoute.split("\\>");
                        */
                        String que_img_tag[] = seprated_questions[1].split("\\=");

                        splitTag = que_img_tag[1].split("\"");

                        String remove_qoute = que_img_tag[1];

                        final_url = remove_qoute.split("\\>");

                        // Log.d("IMG_URL_QUESTION", "==" + final_url[0]);
                        //txt_QuestionExtend.setText(Html.fromHtml(seprated_questions1[1]));
                        img_questions.setVisibility(View.VISIBLE);
                        Picasso.with(StartLABtest.this).load(real_imagePath).into(img_questions);
                        img_questions.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Intent intent = new Intent(StartLABtest.this,ImageZoom.class);
                                intent.putExtra("imageurl", real_imagePath);
                                startActivity(intent);

                            }
                        });
                    }

                } else if (seprated_Atag_questions.length > 1) {
                    // txt_Question.setVisibility(View.GONE);

                    txt_Question.setText(Html.fromHtml(seprated_Atag_questions[0]));
                    //ON BEFORE ORIG
                    /**
                    txt_Question.setText(Html.fromHtml(removeHtmlFrom(seprated_Atag_questions[0]).toString().trim()));
                    */
                    txt_Que_a_tag_pdf.setVisibility(View.VISIBLE);
                    webView_atag.setVisibility(View.GONE);
                    webView_atag.getSettings().setJavaScriptEnabled(true);
                    webView_atag.getSettings().setPluginState(PluginState.ON);
                    // webView_atag.loadData(seprated_Atag_questions[0],
                    // "text/html", null);
                    Log.d("Seprate1", "1:==" + seprated_Atag_questions[1]);
                    String que_a_tag[] = seprated_Atag_questions[1] .split("\\=");
                    Log.d("que_a_tag", "que_a_tag:-" + que_a_tag[1]);
                    String que_a_tag1[] = que_a_tag[1].split("\\.pdf");
                    Log.d("que_a_tag 1", "que_a_tag 1:-" + que_a_tag[0]);
                    remove_a_qoute = que_a_tag1[0].replace("\"", "");
                   remove_a_qoute = remove_a_qoute.replace("&amp;","&");
                   /* String pdfPaths[] = remove_a_qoute.split("/");
                    int pathLength = pdfPaths.length;
                    String pdfName = pdfPaths[pathLength-1];*/
                    Log.e("final atag", "f a tag:-" + remove_a_qoute + ".pdf");

                    txt_Que_a_tag_pdf.setText("View PDF");
                    txt_Que_a_tag_pdf.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent pdfIntent = new Intent(StartLABtest.this, PdfZoom.class);
                            pdfIntent.putExtra("PDF_URL", remove_a_qoute+".pdf");
                            startActivity(pdfIntent);

                        }
                    });

                } else {
                    Log.d("que", "Questuions 0 :-" + seprated_questions[0]);
                    txt_Question.setText(Html.fromHtml(Question));
                  //  webView_atag.setVisibility(View.VISIBLE);
                  //  webView_atag.loadData(formatHtmlString(Question),"text/html", "UTF-8");
                    Log.d("que1", "Questuion" + Question);
                }

                Log.d("Questions", "que:-" + Question);
                Log.d("Right Ans", "Ans" + right_ansList.get(i));
                idList.get(i);
                right_ansList.get(i);

                Ans1 = ans1List.get(i);
                Ans2 = ans2List.get(i);
                Ans3 = ans3List.get(i);
                Ans4 = ans4List.get(i);

                Ans1 = URLDecoder.decode(Ans1, "UTF-8");
                Ans2 = URLDecoder.decode(Ans2, "UTF-8");
                Ans3 = URLDecoder.decode(Ans3, "UTF-8");
                Ans4 = URLDecoder.decode(Ans4, "UTF-8");

                Log.d("Ans1", "Ans1->" + Ans1);
                Log.d("Ans2", "Ans2->" + Ans2);
                Log.d("Ans3", "Ans3->" + Ans3);
                Log.d("Ans4", "Ans4->" + Ans4);

           /*     Ans1=stripHtml(Ans1.toString().trim());
                Ans2=stripHtml(Ans2.toString().trim());
                Ans3=stripHtml(Ans3.toString().trim());
                Ans4=stripHtml(Ans4.toString().trim());
                */

                separated_ans1 = Ans1.split("\\<img");
                separated_ans2 = Ans2.split("\\<img");
                separated_ans3 = Ans3.split("\\<img");
                separated_ans4 = Ans4.split("\\<img");

                if (Ans1.contains("<img")|| Ans2.contains("<img")||Ans3.contains("<img")||Ans4.contains("<img")){

                    Log.d("ANSWER","HAVE IMAGE");


                } else {

                    Log.d("ANSWER 1","NO IMAGE");

                }

                if (separated_ans1.length == 2) {

                    Log.d("separated_ans1", "[1]==" + separated_ans1[1]);

                    radio_ans1.setText(Html.fromHtml(separated_ans1[0].trim()));

                    if (separated_ans1[1].startsWith(" src") || separated_ans1[1].startsWith("src")) {
                        String que_img_tag[] = separated_ans1[1].split("\\=");
                        String remove_qoute = que_img_tag[1].replace("\"", "");
                        Log.d("img", "remove_qoute:-" + remove_qoute);
                        Log.d("img", "url:-" + que_img_tag[1]);
                        final_url = remove_qoute.split("\\>");
                        Log.d("Final_Url", "final url:-" + final_url[0]);
                        img_answer1.setVisibility(View.VISIBLE);
                        Picasso.with(StartLABtest.this).load(final_url[0]).into(img_answer1);
                        img_answer1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("URL_IMG", "=" + final_url[0]);
                                Intent intent = new Intent(StartLABtest.this, ImageZoom.class);
                                intent.putExtra("imageurl", final_url[0]);
                                startActivity(intent);
                            }
                        });
                    }
                } else {

                    // Spanned ans1 = Html.fromHtml(Ans1.toString().trim());
                    // formatHtmlString(Ans1.toString().trim());
                    // stripHtml(Ans1.toString().trim());

                    //radio_ans1.setText(Html.fromHtml(Ans1.toString().trim()));
                    //radio_ans1.setText(Html.fromHtml(formatHtmlString(Ans1)));
                  //  webView_ans1.clearCache(true);
                  //  webView_ans1.clearHistory();
                    webView_ans1.getSettings().setJavaScriptEnabled(true);
                    webView_ans1.loadData(formatHtmlString2(Ans1),"text/html", "UTF-8");
                  //  webView_ans1.reload();


                    txt_current_question.setText(String.valueOf(current_que)+ " of " + QuestionList.size());
                    Log.d("QuestionList", "Size" + QuestionList.size());
                    Log.d("Current score", "Score" + currentScore);
                    txt_current_score.setText(String.valueOf(currentScore) + "%");

                    Log.d("Explanation", "->" + ExplanationList.get(i));
                    if (ExplanationList.get(i).equals("")) {
                        scroll_view.scrollTo(0, 0);
                        scroll_view.fullScroll(ScrollView.FOCUS_UP);
                        Log.d("null", "expla null");
                    } else {
                        if (!ExplanationList.get(i).equals("null")) {
                            scroll_view.scrollTo(0, 0);
                            webView.setVisibility(View.GONE);
                            txt_explanation_display.setVisibility(View.GONE);
                            img_explanation.setVisibility(View.GONE);
                            scroll_view.fullScroll(ScrollView.FOCUS_UP);
                            scroll_view.scrollTo(0, 0);
                        }
                    }


                }

                    // Ans2
                    if (separated_ans2.length == 2) {
                        Log.d("separated_ans2", "[0]==" + separated_ans2[0]);
                        Log.d("separated_ans2", "[1]==" + separated_ans2[1]);

                        radio_ans2.setText(Html.fromHtml(separated_ans2[0].trim()));

                        if (separated_ans2[1].startsWith(" src") || separated_ans2[1].startsWith("src")) {
                            String que_img_tag[] = separated_ans2[1].split("\\=");
                            String remove_qoute = que_img_tag[1].replace("\"", "");
                            Log.d("img", "remove_qoute:-" + remove_qoute);
                            Log.d("img", "url:-" + que_img_tag[1]);
                            final_url = remove_qoute.split("\\>");
                            Log.d("Final Url", "final url:-" + final_url[0]);
                            img_answer2.setVisibility(View.VISIBLE);
                            Picasso.with(StartLABtest.this).load(final_url[0]).into(img_answer2);
                            img_answer2.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("Url", "image" + final_url[0]);
                                    Intent intent = new Intent(StartLABtest.this, ImageZoom.class);
                                    intent.putExtra("imageurl", final_url[0]);
                                    startActivity(intent);

                                }
                            });
                        }
                    } else{

                        //radio_ans1.setText(Html.fromHtml(Ans1.toString().trim()));
                      //  radio_ans2.setText(Html.fromHtml(Ans2.toString().trim()));
                       // radio_ans3.setText(Html.fromHtml(Ans3.toString().trim()));
                        //radio_ans4.setText(Html.fromHtml(Ans4.toString().trim()));
                        webView_ans2.getSettings().setJavaScriptEnabled(true);
                        webView_ans2.loadData(formatHtmlString2(Ans2),"text/html", "UTF-8");
                        txt_current_question.setText(String.valueOf(current_que)+ " of " + QuestionList.size());
                        Log.d("QuestionList", "Size" + QuestionList.size());
                        Log.d("Current score", "Score" + currentScore);
                        txt_current_score.setText(String.valueOf(currentScore) + "%");

                        Log.d("Explanation", "->" + ExplanationList.get(i));
                        if (ExplanationList.get(i).equals("")) {
                            scroll_view.scrollTo(0, 0);
                            scroll_view.fullScroll(ScrollView.FOCUS_UP);
                            Log.d("null", "expla null");
                        } else {
                            if (!ExplanationList.get(i).equals("null")) {
                                scroll_view.scrollTo(0, 0);
                                webView.setVisibility(View.GONE);
                                txt_explanation_display.setVisibility(View.GONE);
                                img_explanation.setVisibility(View.GONE);
                                scroll_view.fullScroll(ScrollView.FOCUS_UP);
                                scroll_view.scrollTo(0, 0);
                            }
                        }

                    }
                        // ANS3
                        if (separated_ans3.length == 2) {
                            Log.d("separated_ans3", "[0]==" + separated_ans3[0]);
                            Log.d("separated_ans3", "[1]==" + separated_ans3[1]);

                            radio_ans3.setText(Html.fromHtml(separated_ans3[0].trim()));

                            if (separated_ans3[1].startsWith(" src") || separated_ans3[1].startsWith("src")) {
                                String que_img_tag[] = separated_ans3[1].split("\\=");
                                String remove_qoute = que_img_tag[1].replace("\"", "");
                                Log.d("img", "remove_qoute:-" + remove_qoute);
                                Log.d("img", "url:-" + que_img_tag[1]);
                                final_url = remove_qoute.split("\\>");
                                Log.d("Final Url", "final url:-" + final_url[0]);
                                img_answer3.setVisibility(View.VISIBLE);
                                Picasso.with(StartLABtest.this).load(final_url[0]).into(img_answer3);
                                img_answer3.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("Url", "image" + final_url[0]);
                                        Intent intent = new Intent(StartLABtest.this, ImageZoom.class);
                                        intent.putExtra("imageurl", final_url[0]);
                                        startActivity(intent);

                                    }
                                });
                            }
                        }else {

                            //radio_ans1.setText(Html.fromHtml(Ans1.toString().trim()));
                            //radio_ans2.setText(Html.fromHtml(Ans2.toString().trim()));
                           // radio_ans3.setText(Html.fromHtml(Ans3.toString().trim()));
                            //radio_ans4.setText(Html.fromHtml(Ans4.toString().trim()));
                            webView_ans3.getSettings().setJavaScriptEnabled(true);
                            webView_ans3.loadData(formatHtmlString2(Ans3),"text/html", "UTF-8");
                            txt_current_question.setText(String.valueOf(current_que)+ " of " + QuestionList.size());
                            Log.d("QuestionList", "Size" + QuestionList.size());
                            Log.d("Current score", "Score" + currentScore);
                            txt_current_score.setText(String.valueOf(currentScore) + "%");

                            Log.d("Explanation", "->" + ExplanationList.get(i));
                            if (ExplanationList.get(i).equals("")) {
                                scroll_view.scrollTo(0, 0);
                                scroll_view.fullScroll(ScrollView.FOCUS_UP);
                                Log.d("null", "expla null");
                            } else {
                                if (!ExplanationList.get(i).equals("null")) {
                                    scroll_view.scrollTo(0, 0);
                                    webView.setVisibility(View.GONE);
                                    txt_explanation_display.setVisibility(View.GONE);
                                    img_explanation.setVisibility(View.GONE);
                                    scroll_view.fullScroll(ScrollView.FOCUS_UP);
                                    scroll_view.scrollTo(0, 0);
                                }
                            }



                        }

                         // ANS4
                         if (separated_ans4.length == 2) {
                             Log.d("separated_ans4", "[0]==" + separated_ans4[0]);
                             Log.d("separated_ans4", "[1]==" + separated_ans4[1]);

                             radio_ans4.setText(Html.fromHtml(separated_ans4[0].trim()));

                             if (separated_ans4[1].startsWith(" src") || separated_ans4[1].startsWith("src")) {
                                 String que_img_tag[] = separated_ans4[1].split("\\=");
                                 String remove_qoute = que_img_tag[1].replace("\"", "");
                                 Log.d("img", "remove_qoute:-" + remove_qoute);
                                 Log.d("img", "url:-" + que_img_tag[1]);
                                 final_url = remove_qoute.split("\\>");
                                 Log.d("Final Url", "final url:-" + final_url[0]);
                                 img_answer4.setVisibility(View.VISIBLE);
                                 Picasso.with(StartLABtest.this).load(final_url[0]).into(img_answer4);
                                 img_answer4.setOnClickListener(new OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Log.d("Url", "image" + final_url[0]);
                                         Intent intent = new Intent(StartLABtest.this, ImageZoom.class);
                                         intent.putExtra("imageurl", final_url[0]);
                                         startActivity(intent);

                                     }
                                 });
                             }
                         }else{

                             //radio_ans1.setText(Html.fromHtml(Ans1.toString().trim()));
                             //radio_ans2.setText(Html.fromHtml(Ans2.toString().trim()));
                             //radio_ans3.setText(Html.fromHtml(Ans3.toString().trim()));
                            // radio_ans4.setText(Html.fromHtml(Ans4.toString().trim()));
                             webView_ans4.getSettings().setJavaScriptEnabled(true);
                             webView_ans4.loadData(formatHtmlString2(Ans4),"text/html", "UTF-8");
                             txt_current_question.setText(String.valueOf(current_que)+ " of " + QuestionList.size());
                             Log.d("QuestionList", "Size" + QuestionList.size());
                             Log.d("Current score", "Score" + currentScore);
                             txt_current_score.setText(String.valueOf(currentScore) + "%");

                             Log.d("Explanation", "->" + ExplanationList.get(i));
                             if (ExplanationList.get(i).equals("")) {
                                 scroll_view.scrollTo(0, 0);
                                 scroll_view.fullScroll(ScrollView.FOCUS_UP);
                                 Log.d("null", "expla null");
                             } else {
                                 if (!ExplanationList.get(i).equals("null")) {
                                     scroll_view.scrollTo(0, 0);
                                     webView.setVisibility(View.GONE);
                                     txt_explanation_display.setVisibility(View.GONE);
                                     img_explanation.setVisibility(View.GONE);
                                     scroll_view.fullScroll(ScrollView.FOCUS_UP);
                                     scroll_view.scrollTo(0, 0);


                                 }
                             }


                         }


              //  } else
/**
                radio_ans1.setText(Html.fromHtml(Ans1.toString().trim()));
                radio_ans2.setText(Html.fromHtml(Ans2.toString().trim()));
                radio_ans3.setText(Html.fromHtml(Ans3.toString().trim()));
                radio_ans4.setText(Html.fromHtml(Ans4.toString().trim()));

                // txt_id.setText(Id);
                txt_current_question.setText(String.valueOf(current_que)+ " of " + QuestionList.size());
                Log.d("QuestionList", "Size" + QuestionList.size());
                // currentScore = right_ans * 100 / QuestionList.size();
//				currentScore = Math.round(right_ans * 100 / QuestionList.size());
                Log.d("Current score", "Score" + currentScore);
                txt_current_score.setText(String.valueOf(currentScore) + "%");

                Log.d("Explanation", "->" + ExplanationList.get(i));
                if (ExplanationList.get(i).equals("")) {
                    scroll_view.scrollTo(0, 0);
                    // webView.setVisibility(View.GONE);
                    scroll_view.fullScroll(ScrollView.FOCUS_UP);
                    Log.d("null", "expla null");
                } else {
                    if (!ExplanationList.get(i).equals("null")) {
                        scroll_view.scrollTo(0, 0);
                        webView.setVisibility(View.GONE);
                        txt_explanation_display.setVisibility(View.GONE);
                        img_explanation.setVisibility(View.GONE);
                        scroll_view.fullScroll(ScrollView.FOCUS_UP);
                        scroll_view.scrollTo(0, 0);
                    }
                }
                */
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }


    }

    //EXPLANATION
    private CharSequence removeHtmlFrom(String html) {

        //NOT TESTED IF WORKING QUESTION 80
        html = html.replace("%26quot%3B", "\"");
        html = html.replace("%3B",";");
        html = html.replace("%3F","?");
        html = html.replace("%3A",":");
        html = html.replace("23%","#");
        html = html.replace("%3D","=");
        //html = html.replace("24%","$");
        html = html.replace("%2C",",");
        html = html.replace("+"," ");
        //html = html.replace("25%","%");
        html = html.replace("%7E","~");
        html = html.replace("%3C","<");
        html = html.replace("%3E","> ");
        html = html.replace("%22","\"");
        html = html.replace("%2F","/");
        //EXPLANATION
        html = html.replace("%26quot;","\"");
        html = html.replace("&26divide;","÷");
        html = html.replace("%26","&");
        //html = html.replace("26%","&");
        html = html.replace("%2#22","\"3\"");
        html = html.replace("%0D","");
        html = html.replace("%0A","");

        // html = html.replace("<p>", "");
        //html = html.replace("</p>", "");
        //html = html.replace("<em>", "");
        //html = html.replace("</em>", "");
        //html = html.replace("<strong>", "");
        //html = html.replace("</strong>", "");

        html = html.replace("%28", "(");
        html = html.replace("%29", ")");
        html = html.replace("%26nbsp;", " ");
        html = html.replace("%27", "'");
        html = html.replace("&#39;", "'");
        html = html.replace("%&2339;", "'");
        html = html.replace("%2B", "+");
        html = html.replace("%5B", "[");
        html = html.replace("%5D", "]");
        html = html.replace("%25", "%");
        html = html.replace("%21", "!");

        //NEW
        html = html.replace("%24", "$");
        html = html.replace("%&238220;", "-");
        html = html.replace("%&238220;", "\"");
        html = html.replace("%&238221;", "\"");
        html = html.replace("%F7", "÷");
        html = html.replace("&#960;", "π");
        html = html.replace("%9D", "");
        html = html.replace("&23960;", "");

        html = html.replace("%23", "#");

        html = html.replace("%09", "");
        html = html.replace("&#176;", "");
        html = html.replace("&quot;", "\"");
        //NEW
        html = html.replace("%2A", "*");
        html = html.replace("&#176;", "°");
        //NEW
        //html = html.replace(" 2</sup>", "²");
        //html =  html.replace(" 2</sub>", "₂");
        //html = html.replace(" 1.85</sup>", "²");

        return html;
    }

    //QUESTION ANSWER
    private String removeStringFrom(String string) {

        string = string.replace("+"," ");
        string = string.replace("26%","&");
        string = string.replace("%3B",";");
        string = string.replace("%3F","?");
        string = string.replace("%3A",":");
        string = string.replace("23%","#");
        string = string.replace("%3D","=");
        string = string.replace("24%","$");
        string = string.replace("%2C",",");
        string = string.replace("25%","%");
        string = string.replace("%7E","~");
        string = string.replace("%3C","<");
        string = string.replace("%3E","> ");
        string = string.replace("%22","\"");
        string = string.replace("%2F","/");
        string = string.replace("%26","&");
        string = string.replace("%0D","");
        string = string.replace("%0A","");
        string = string.replace("<p>", "");
        string = string.replace("</p>", "");
        string = string.replace("&nbsp;", " ");
        string = string.replace("%28", "(");
        string = string.replace("%29", ")");
        string = string.replace("%27", "'");
        string = string.replace("&#39;", "'");
        string = string.replace("&quot;", "\"");
        string = string.replace("%B0", "°");
        string = string.replace("%21", "!");
        string = string.replace("%2B", "+");
        string = string.replace("%24", "$");
        string = string.replace("%&238220;", "-");
        string = string.replace("%&238220;", "\"");
        string = string.replace("%&238221;", "\"");
        string = string.replace("%F7", "÷");
        string = string.replace("&#960;", "π");
        string = string.replace("%9D", "");
        string = string.replace("&23960;", "");

        //string = string.replace("%23", "#");

        string = string.replace("%09", "");
        string = string.replace("&#176;", "");
        string = string.replace("%25", "%");

        //NEW
        string = string.replace("%2A", "*");

        string = string.replace("&#176;", "°");


        return string;
    }

    public static String superscript(String str) {
        str = str.replaceAll("0", "0");
        str = str.replaceAll("1", "1");
        str = str.replaceAll("2", "2");
        str = str.replaceAll("3", "3");
        str = str.replaceAll("4", "4");
        str = str.replaceAll("5", "5");
        str = str.replaceAll("6", "6");
        str = str.replaceAll("7", "7");
        str = str.replaceAll("8", "8");
        str = str.replaceAll("9", "9");
        return str;
    }

    public static String subscript(String str) {
        str = str.replaceAll("0", "₀");
        str = str.replaceAll("1", "₁");
        str = str.replaceAll("2", "₂");
        str = str.replaceAll("3", "₃");
        str = str.replaceAll("4", "₄");
        str = str.replaceAll("5", "₅");
        str = str.replaceAll("6", "₆");
        str = str.replaceAll("7", "₇");
        str = str.replaceAll("8", "₈");
        str = str.replaceAll("9", "₉");
        return str;
    }

    public static String formatHtmlString(String stringIn) {
        String format ="<![CDATA[<div style=\"background-color:red;\"><p margin=\"0px\">hello</p></div>]]";

        /*String format = "<html>"
                + "  <head>"
                + "    <style type='text/css'>"
                + "      body { "
                + "           font-family: \"HelveticaNeue-Light\", \"Helvetica Neue Light\"; "
                + "           font-size:14px;" + "           color:#000;"
                + "      }" + "      b { " + "           font-size:14px;"
                + "       }" + "      i { " + "           font-size:10px;"
                + "           color:#333333;" + "           opacity:0.75;"
                + "       }" + "    </style>" + "  </head>" + "  <body>"
                + stringIn + "</body>" + "</html>";
*/
        return format;
    }

    public String formatHtmlString2(String stringIn){

        String format ="<html><head><style type=\"text/css\"> body{color:grey;}img{width:"+320+"!important; height:auto!important;"+"} p{margin:0px!important; color:grey!important;}</style></head><body>"+stringIn+"</body></html>";

        return format;
    }
    public String formatHtmlString3(String stringIn){

        String format ="<html><head><style type=\"text/css\">img{width:"+320+"!important; height:auto!important;"+"} p{margin:0px!important; color:red;}</style></head><body>"+stringIn+"</body></html>";

        return format;
    }
    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }
}
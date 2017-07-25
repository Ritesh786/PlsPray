package plspray.infoservices.lue.plspray;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import plspray.infoservices.lue.plspray.Async.DownloadThread;
import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.databind.User;
import plspray.infoservices.lue.plspray.utilities.AppConstants;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout mobileNoLayout;
    EditText mobilenodEdit, musernmefirst, musernamelast;
    Button signupBtn;
    Context context;
    private static final int REQUEST_PERMISSION = 10;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new UserSessionManager(getApplicationContext());

        mobileNoLayout = (TextInputLayout) findViewById(R.id.mobileNoLayout);
        mobilenodEdit = (EditText) findViewById(R.id.mobilenodEdit);
        musernmefirst = (EditText) findViewById(R.id.usernamefirst_edttxt);
        musernamelast = (EditText) findViewById(R.id.usernamelast_edttxt);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendUserInfo().execute();
            }
        });

        context = this;
        //  initialize();
        Log.d("120", "onnonono");


//        requestAppPermissions(new String[]{
//
//                        Manifest.permission.READ_CONTACTS},
//                R.string.msg, REQUEST_PERMISSION);


    }

//    @Override
//    public void onPermissionsGranted(int requestCode) {
//
//    }

//    private void initialize()
//    {
//        mobileNoLayout=(TextInputLayout)findViewById(R.id.mobileNoLayout);
//        mobilenodEdit=(EditText)findViewById(R.id.mobilenodEdit);
//        musernmefirst = (EditText)findViewById(R.id.usernamefirst_edttxt);
//        musernamelast = (EditText)findViewById(R.id.usernamelast_edttxt);
//        signupBtn=(Button)findViewById(R.id.signupBtn);
//        Log.d("1200000","onnonono99999");
//
//        signupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("120000002020","onnonono9999924242");
//                if(!mobilenodEdit.getText().toString().trim().equals(""))
//                {
//                 JSONObject jsonObject=new JSONObject();
//                    try {
//                        jsonObject.accumulate("phone", mobilenodEdit.getText().toString());
//                        jsonObject.accumulate("device_id", UtilityClass.getMacId(context));
//                        jsonObject.accumulate("registration_key",SharedPreferenceClass.getToken(context));
//                        jsonObject.accumulate("first_name", musernmefirst.getText().toString());
//                        jsonObject.accumulate("last_name", musernamelast.getText().toString());
//                        signUp(jsonObject);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//
//    public void signUp(JSONObject jsonObject)
//    {
//        new DownloadThread(this, Urls.signup,jsonObject.toString(), new DownloadThread.AsyncResponse() {
//            @Override
//            public void processFinish(String output) {
//                try {
//                    JSONObject jsonObject= new JSONObject(output);
//                    if (!jsonObject.getBoolean("error")) {
//                        JSONArray jsonArray=jsonObject.getJSONArray("message");
//                        if(jsonArray.length()>0) {
//                            User user = new User(jsonArray.getJSONObject(0));
//                            if (user != null) {
//                                SharedPreferenceClass.setUserInfo(context, user);
//                                startActivity(new Intent(context,MainActivity.class));
//                                finish();
//                            }
//                        }
//                    }
//                }catch (Exception e){e.printStackTrace();}
//            }
//        },true).execute();
//    }


    private class SendUserInfo extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String mno = mobilenodEdit.getText().toString().trim();
        String fname = musernmefirst.getText().toString().trim();
        String lname = musernamelast.getText().toString().trim();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Fetching Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";
            //  Log.d("Addno", "addno11 " + text);

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Urls.signup);
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("phone", mno);
                jsonObject.accumulate("device_id", UtilityClass.getMacId(context));
                jsonObject.accumulate("registration_key", SharedPreferenceClass.getToken(context));
                jsonObject.accumulate("first_name", fname);
                jsonObject.accumulate("last_name", lname);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
                Log.d("tag11245", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return s;

        }

        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.d("OnPOst", " " + json);
            pDialog.dismiss();
            String fname = null;
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (!jsonObject.getBoolean("error")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject movie = jsonArray.getJSONObject(i);
                            fname  = movie.getString("first_name");
                        }
                        User user = new User(jsonArray.getJSONObject(0));
                        if (user != null) {
                            SharedPreferenceClass.setUserInfo(context, user);
                            session.createUserLoginSession(fname);
                            startActivity(new Intent(context, MainActivity.class));
                            finish();

                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private String readResponse(HttpResponse httpResponse) {

        InputStream is = null;
        String return_text = "";
        try {
            is = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
            Log.d("return_text", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
    }

}

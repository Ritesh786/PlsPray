package plspray.infoservices.lue.plspray;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.databind.User;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mfname,mlaneme,mmobileno,mpassword;
    Button mregisterbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mfname = (EditText) findViewById(R.id.reg_fname);
        mlaneme = (EditText) findViewById(R.id.reg_lname);
        mmobileno = (EditText) findViewById(R.id.reg_mobile);
        mpassword = (EditText) findViewById(R.id.reg_password);

        mregisterbtn = (Button) findViewById(R.id.btn_Register);
        mregisterbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

     new SendUserInfo().execute();

    }

    private class SendUserInfo extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String fname = mfname.getText().toString().trim();
        String lname = mlaneme.getText().toString().trim();
        String mobile = mmobileno.getText().toString().trim();
        String password = mpassword.getText().toString().trim();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
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

                jsonObject.accumulate("phone", mobile);
                jsonObject.accumulate("device_id", UtilityClass.getMacId(getApplicationContext()));
                jsonObject.accumulate("registration_key", SharedPreferenceClass.getToken(getApplicationContext()));
                jsonObject.accumulate("first_name", fname);
                jsonObject.accumulate("last_name", lname);
                jsonObject.accumulate("password", password);

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
            String lname = null;
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (!jsonObject.getBoolean("error")) {
//                    JSONArray jsonArray = jsonObject.getJSONArray("message");
//                    if (jsonArray.length() > 0) {
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject movie = jsonArray.getJSONObject(i);
//                            fname  = movie.getString("first_name");
//                            lname  = movie.getString("last_name");
//                        }
//                        User user = new User(jsonArray.getJSONObject(0));
//                        if (user != null) {
//                            SharedPreferenceClass.setUserInfo(context, user);
//                            session.createUserLoginSession(lname);
//                            session.SaveName(fname);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        }

                    }catch (Exception e){

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

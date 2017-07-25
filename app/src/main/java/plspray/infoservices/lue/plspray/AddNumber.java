package plspray.infoservices.lue.plspray;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plspray.infoservices.lue.plspray.adapter.RecycleAdapter;
import plspray.infoservices.lue.plspray.databind.PhoneNumber;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;

public class AddNumber extends AppCompatActivity {

    String userphoneno;

    private ProgressDialog pDialog;
    private List<PhoneNumber> phonelist = new ArrayList<PhoneNumber>();

    private RecyclerView recyclerView;

    private RecycleAdapter adapter;
    SendPhoneNumber sendno;
    List sendnolist;
    List<String>UserId=new ArrayList<>();


    Button creategroupbtn;
    JSONArray jnumberarray;
    String id;
    String[] namesArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);

        userphoneno = getIntent().getStringExtra("phoneno");
        id = getIntent().getStringExtra("id");
        Log.d("phonr00", "00000"+userphoneno);

        creategroupbtn = (Button) findViewById(R.id.creategroup_btn);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddNumber.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sendnolist = new ArrayList();


        sendno = new SendPhoneNumber();
        sendno.execute();

        creategroupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("checked"," "+"efbfeuh");
                StringBuffer responseText = new StringBuffer();
                for(int i=0;i<phonelist.size();i++){
                    Log.d("checked"," "+"efbfsseuh");
                    PhoneNumber country = phonelist.get(i);
                    if(phonelist.get(i).getGetno()!=null) {

                        String fnjnjn = phonelist.get(i).getGetno();
                        UserId.add(fnjnjn);

                        Log.d("checkeduser"," "+UserId);
                    }
                }

                   /* PhoneNumber phoneNumber = new PhoneNumber();
                    sendnolist.add(phoneNumber.getGetno());
                    Log.d("num", "num11" + sendnolist);*/

               if(phonelist.size()==0){

               }else {
                   new SendGroupNumber().execute();
               }



            }
        });

    }

    private class SendPhoneNumber extends AsyncTask<String, Void, String> {

        String URL = "http://aceresults.info/api/get_users";
        private ProgressDialog pDialog;
        String phone = userphoneno;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNumber.this);
            pDialog.setMessage("Create Group...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";
            Log.d("Addno", "addno11 " + phone);

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_phone", phone);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
                Log.d("tag11", " " + s);
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


            try {
                JSONObject obj = new JSONObject(json);
                String successmsg = obj.getString("error");
                if (successmsg.equals("false")) {

                    JSONArray messageObj = obj.getJSONArray("message");

                    Log.d("messageObj00", "messageObj11 " + messageObj.toString());

                    for (int i = 0; i < messageObj.length(); i++) {

                        JSONObject jarray = messageObj.getJSONObject(i);
                        PhoneNumber movie = new PhoneNumber();
                        movie.setPhonenu(jarray.getString("phone"));
                        movie.setName(jarray.getString("first_name"));
                        phonelist.add(movie);

                        Log.d("phonelist00", "phonelist11 " + jarray.getString("phone"));
                        Log.d("phonelistsize00", "phonelistsize11 " + phonelist.size());
                    }

                }

                adapter = new RecycleAdapter(phonelist);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.d("adapt00", "adapter " + adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class SendGroupNumber extends AsyncTask<String, Void, String> {

        String URL = "http://aceresults.info/api/add_chat_group_member";
        private ProgressDialog pDialog;
        String groupid = id;

        String[] arr = UserId.toArray(new String[UserId.size()]);

        String text = Arrays.toString(arr).replace("[", "").replace("]", "");


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNumber.this);
            pDialog.setMessage("Fetching Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";
            Log.d("Addno", "addno11 " + text);

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("id", groupid);
                jsonObject.accumulate("member_id", text);

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

            try {
                JSONObject obj = new JSONObject(json);
                String successmsg = obj.getString("error");
                if (successmsg.equals("false")) {

                    Intent intent = new Intent(AddNumber.this, MainActivity.class);
                    startActivity(intent);

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

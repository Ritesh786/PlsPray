package plspray.infoservices.lue.plspray.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.ArrayList;
import java.util.List;

import plspray.infoservices.lue.plspray.AddNumber;
import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.MainActivity;
import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.SendGroupActivity;
import plspray.infoservices.lue.plspray.SendScheduleActivity;
import plspray.infoservices.lue.plspray.adapter.RecycleAdapter;
import plspray.infoservices.lue.plspray.databind.Contact;
import plspray.infoservices.lue.plspray.databind.ContactList;
import plspray.infoservices.lue.plspray.databind.PhoneNumber;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupTwo extends Fragment {


    private ProgressDialog pDialog;
    private List<ContactList> movieList = new ArrayList<ContactList>();

    private RecyclerView recyclerView;

    private DApter adapter;
      SendPhoneNumber sendno;

    public GroupTwo() {
        // Required empty public constructor
    }
    // TODO: Customize parameter initialization


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_two, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

       //   adapter = new DApter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getContext(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        ContactList mo123 = movieList.get(position);

                        Intent newsdetailintnt = new Intent(getContext(),SendGroupActivity.class);
                        newsdetailintnt.putExtra("group_id",mo123.getId());
                        newsdetailintnt.putExtra("group_name",mo123.getName());
                        newsdetailintnt.putExtra("group_number",mo123.getNumber());
                  //     newsdetailintnt.putExtra("image",mo123.getThumbnailUrl());
                        startActivity(newsdetailintnt);


                        // TODO Handle item click
                    }
                })
        );
        sendno = new SendPhoneNumber();
        sendno.execute();


        return view;
    }


    private class SendPhoneNumber extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
        String phone = SharedPreferenceClass.getUserInfo(getContext()).getPhone();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Fetching Data...");
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
                HttpPost httpPost = new HttpPost(Urls.group_by);
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
                JSONArray jsonArray1 = new JSONArray(json);
                Log.d("jsun00", "json101" + jsonArray1.toString());
                String number = "null";
                String imageUrl = "null";
                String id = "null";
                String firstname = "null";
//                            JSONArray jsonArray=jsonObject.getJSONArray("groups");
                for (int i = 0; i < jsonArray1.length(); i++) {

                    JSONObject movie = jsonArray1.getJSONObject(i);
                    JSONArray jsonArray = movie.getJSONArray("groups");
                    Log.d("jsungrop00", "jsungrop00 " + jsonArray.toString());

                    for (int iml = 0; iml < jsonArray.length(); iml++) {

                        number = jsonArray.getJSONObject(iml).getString("member_id");
                        imageUrl = jsonArray.getJSONObject(iml).getString("group_image");
                        id = jsonArray.getJSONObject(iml).getString("group_id");
                        firstname = jsonArray.getJSONObject(iml).getString("group_name");
                        Log.d("AllAra00", "AllAra0012 " + number + imageUrl + id + firstname);

                        int index;
//                                    if((index=clist.indexOf(number))!=-1)
//                                }
//
//                                MainActivity.contactListList.add(new ContactList(contactList.get(index).getName(), number, imageUrl, id));
//                            }

                        ContactList contactList = new ContactList();
                        contactList.setNumber(number);
                        contactList.setImageUrl(imageUrl);
                        contactList.setId(id);
                        contactList.setName(firstname);
                        movieList.add(contactList);


                    }



                  //  MainActivity.contactListList.add(new ContactList(firstname, number, imageUrl, id));


                }
                adapter = new DApter(movieList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.d("adapt001120", "adapter2222 " + adapter);

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



package plspray.infoservices.lue.plspray;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView imageView;
    ImageButton changePicBtn;
    private final int CAMERA_REQUESTCODE = 1;
    private final int GALLERY_REQUESTCODE = 2;
    ProgressDialog progressDialog;
    Context context;
    String id = "";
    Button saveBtn;
    EditText groupNameEdit;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changePicBtn = (ImageButton) findViewById(R.id.changePicBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        groupNameEdit = (EditText) findViewById(R.id.groupNameEdit);

        saveBtn.setOnClickListener(this);

        changePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void selectImage() {
        final MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.upload_doc_dialog, false)
                .backgroundColor(getResources().getColor(android.R.color.white)).build();
        LinearLayout cameraBtnLayout = (LinearLayout) materialDialog.findViewById(R.id.cameraBtnLayout);
        LinearLayout galleryBtnLayout = (LinearLayout) materialDialog.findViewById(R.id.galleryBtnLayout);
        materialDialog.show();

        cameraBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeImageFromCamera();
                materialDialog.dismiss();
            }
        });


        galleryBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeImageFromGallery();
                materialDialog.dismiss();
            }
        });


    }

    public void takeImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, GALLERY_REQUESTCODE);
        }
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bmp == null){}
        else {
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        }
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;

    }

    private void takeImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUESTCODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUESTCODE && resultCode == RESULT_OK) {
//            bitmap = data.getParcelableExtra("data");
//            imageView.setImageBitmap(bitmap);
            bitmap = (Bitmap) data.getExtras().get("data");
            Log.d("bit00", "btmp" + bitmap.toString());
            imageView.setImageBitmap(bitmap);

        } else if (requestCode == GALLERY_REQUESTCODE && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            bitmap = UtilityClass.getImageFromUri(this, fullPhotoUri);
            imageView.setImageBitmap(bitmap);
//        } else if (bitmap != null) {
//            imageView.setImageBitmap(bitmap);
//            saveBtn.setVisibility(View.VISIBLE);
//            final Bitmap finalBitmap = bitmap;
//            saveBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                }
//            });
//        }


        }

    }


        @Override
        public void onClick (View v){

          new SendUserGroupInfo().execute();

        }


    private class SendUserGroupInfo extends AsyncTask<String, Void, String> {

        String URL = "http://aceresults.info/api/create_chat_group.php";
        private ProgressDialog pDialog;
        String phone = SharedPreferenceClass.getUserInfo(CreateGroupActivity.this).getPhone();
        String groupname  = groupNameEdit.getText().toString().trim();
        String imge = getStringImage(bitmap);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateGroupActivity.this);
            pDialog.setMessage("Sending Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";
            Log.d("sendindat","datas "+phone + groupname + imge);

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("group_name", groupname);
                jsonObject.accumulate("createdby", phone);
                jsonObject.accumulate("image", imge);

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
                if(successmsg.equals("false")){

                    JSONArray messageObj = obj.getJSONArray("msg");
                    for (int i = 0; i < messageObj.length(); i++) {

                        JSONObject jarray = messageObj.getJSONObject(i);

                        Intent addnumbetintent = new Intent(CreateGroupActivity.this,AddNumber.class);
                    addnumbetintent.putExtra("phoneno", jarray.getString("createdby"));
                        addnumbetintent.putExtra("id", jarray.getString("id"));
                    Log.d("no00","numb "+ jarray.getString("createdby").toString());
                    startActivity(addnumbetintent);

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
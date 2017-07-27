package plspray.infoservices.lue.plspray;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.utilities.GlobalVariables;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class ProfilePicActivity extends AppCompatActivity {


    ImageView imageView;
    ImageButton changePicBtn;
    private final int CAMERA_REQUESTCODE=1;
    private final int GALLERY_REQUESTCODE=2;
    ProgressDialog progressDialog;
    Context context;
    String id="";
    Button updateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog=new ProgressDialog(this);
        context=this;

        imageView=(ImageView)findViewById(R.id.imageView);
        changePicBtn=(ImageButton)findViewById(R.id.changePicBtn);
        updateBtn=(Button)findViewById(R.id.updateBtn);
        updateBtn.setVisibility(View.INVISIBLE);

        changePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });



      id= SharedPreferenceClass.getUserInfo(this).getId();

//        if(GlobalVariables.profilePic!=null)
//            imageView.setImageBitmap(GlobalVariables.profilePic);

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

    private void selectImage()
    {
        final MaterialDialog materialDialog=new MaterialDialog.Builder(this)
                .customView(R.layout.upload_doc_dialog, false)
                .backgroundColor(getResources().getColor(android.R.color.white)).build();
        LinearLayout cameraBtnLayout=(LinearLayout)materialDialog.findViewById(R.id.cameraBtnLayout);
        LinearLayout galleryBtnLayout=(LinearLayout)materialDialog.findViewById(R.id.galleryBtnLayout);
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

    private void takeImageFromCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUESTCODE);
        }
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

         Bitmap bitmap=null;

        if (requestCode == CAMERA_REQUESTCODE && resultCode == RESULT_OK) {
              bitmap = data.getParcelableExtra("data");
        }
        else if (requestCode == GALLERY_REQUESTCODE && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            bitmap= UtilityClass.getImageFromUri(this,fullPhotoUri);
        }
        if(bitmap!=null) {
            imageView.setImageBitmap(bitmap);
            updateBtn.setVisibility(View.VISIBLE);
            final Bitmap finalBitmap = bitmap;
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPhoto(finalBitmap);
                }
            });
        }



    }

    private void uploadPhoto(Bitmap bitmap)
    {
        String encodedImage = Base64.encodeToString(UtilityClass.convertBitmapToByte(bitmap), Base64.NO_WRAP);
        GlobalVariables.profilePic=bitmap;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ProfilePicActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("imageurl",encodedImage);
        Log.d("mp'npm","mpmpm"+encodedImage);
        editor.apply();

        if(UtilityClass.isOnline(this))
            uploadImage(id, Urls.upload_photo,encodedImage);

    }



    private void uploadImage(final String id, String url, final String photo){
        //Showing the progress dialog
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        if(progressDialog.isShowing())  progressDialog.dismiss();
                        Intent i=new Intent();
                        setResult(RESULT_OK,i);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        if(progressDialog.isShowing()) progressDialog.dismiss();

                        //Showing toast
                        Toast.makeText(context, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("id", id);
                params.put("photo",photo);
                //returning parameters
                return params;
            }
        };

        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}

package plspray.infoservices.lue.plspray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.gsm.GsmCellLocation;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import plspray.infoservices.lue.plspray.Async.DownloadThread;
import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.databind.User;
import plspray.infoservices.lue.plspray.utilities.GlobalVariables;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    final int PROFILEPIC_CHANGE=1;
    CircularImageView userImageVIew;
    EditText firsttNameEdit,lastNameEdit,phoneNoEdit;
    TextView editDetailsText;
    Button updateBtn;
    Context context;
    User user;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        UtilityClass.setStatusBarColor(this);
        context=this;
        initialize();
    }


    private void initialize()
    {
        userImageVIew=(CircularImageView)findViewById(R.id.userImageVIew);

        firsttNameEdit=(EditText)findViewById(R.id.firsttNameEdit);
        lastNameEdit=(EditText)findViewById(R.id.lastNameEdit);
        phoneNoEdit=(EditText)findViewById(R.id.phoneNoEdit);

        editDetailsText=(TextView)findViewById(R.id.editDetailsText);
        updateBtn=(Button) findViewById(R.id.updateBtn);
        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.coordinatorLayout);


        updateBtn.setOnClickListener(this);
        editDetailsText.setOnClickListener(this);
        userImageVIew.setOnClickListener(this);


        setProfileInfo();

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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.updateBtn:
                updateProfile();
                break;
            case R.id.editDetailsText:
                enableInfo();
                break;
            case R.id.userImageVIew:
                Intent i=new Intent(context,ProfilePicActivity.class);
                startActivityForResult(i,PROFILEPIC_CHANGE);
                break;

        }
    }


    private void enableInfo()
    {
        firsttNameEdit.setEnabled(true);
        lastNameEdit.setEnabled(true);
        phoneNoEdit.setEnabled(true);
    }



    private void setProfileInfo()
    {
        user=SharedPreferenceClass.getUserInfo(context);
        if(user!=null)
        {
//           if(GlobalVariables.profilePic!=null)
//               userImageVIew.setImageBitmap(GlobalVariables.profilePic);
            firsttNameEdit.setText(user.getFirst_name().trim());
            lastNameEdit.setText(user.getLast_name().trim());
            phoneNoEdit.setText(user.getPhone().trim());
            firsttNameEdit.setEnabled(false);
            lastNameEdit.setEnabled(false);
            phoneNoEdit.setEnabled(false);
        }
    }

    private boolean validate()
    {
        boolean validate=true;
        if(firsttNameEdit.getText().toString().trim().equals(""))
        {
            validate=false;
            firsttNameEdit.setError(getString(R.string.enter)+" "+getString(R.string.first_name));
        }else user.setFirst_name(firsttNameEdit.getText().toString().trim());

        if(lastNameEdit.getText().toString().trim().equals(""))
        {
            validate=false;
            lastNameEdit.setError(getString(R.string.enter)+" "+getString(R.string.last_name));
        }else user.setLast_name(lastNameEdit.getText().toString().trim());

        if(phoneNoEdit.getText().toString().trim().equals(""))
        {
            validate=false;
            phoneNoEdit.setError(getString(R.string.enter)+" "+getString(R.string.phone));
        }else user.setPhone(phoneNoEdit.getText().toString().trim());
        return validate;
    }




    private void updateProfile() {

        if (validate()) {
            user.setFirst_name(firsttNameEdit.getText().toString());
            user.setLast_name(lastNameEdit.getText().toString());
            user.setPhone(phoneNoEdit.getText().toString());

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("id", user.getId());
            } catch (JSONException e) {
            }
            try {
                jsonObject.accumulate("first_name", user.getFirst_name());
            } catch (JSONException e) {
            }
            try {
                jsonObject.accumulate("last_name", user.getLast_name());
            } catch (JSONException e) {
            }
            try {
                jsonObject.accumulate("phone", user.getPhone());
            } catch (JSONException e) {
            }

            new DownloadThread(this, Urls.update_profileDetails, jsonObject.toString(), new DownloadThread.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        if (!jsonObject.getBoolean("error")) {
                            if (jsonObject.getBoolean("message")) {
                                SharedPreferenceClass.setUserInfo(context,user);
                                setProfileInfo();
                                MainActivity.mainActivity.setUserInfo();
                                Snackbar.make(coordinatorLayout,getString(R.string.updated_successfully),Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },true).execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PROFILEPIC_CHANGE)
        {
            if(GlobalVariables.profilePic!=null)
            {
                userImageVIew.setImageBitmap(GlobalVariables.profilePic);

            }
        }
    }
}

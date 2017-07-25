package plspray.infoservices.lue.plspray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import plspray.infoservices.lue.plspray.databind.User;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class SplashScreen extends LoginActivity {

    Timer timer;
    int count;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;


//        if(SharedPreferenceClass.getLogin(this))
//        {
//            User user= SharedPreferenceClass.getUserInfo(this);
//
//            if(user!=null) {
//                JSONObject jsonObject = new JSONObject();
//                try {
//                        jsonObject.accumulate("phone", user.getPhone());
//                    jsonObject.accumulate("device_id", UtilityClass.getMacId(context));
//                    jsonObject.accumulate("registration_key",SharedPreferenceClass.getToken(context));
//                    jsonObject.accumulate("first_name", user.getFirst_name());
//                    jsonObject.accumulate("last_name", user.getLast_name());
//                    signUp(jsonObject);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        else
            startTimer();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void startTimer()
    {
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(count>2)
                {
                    if(timer!=null) timer.cancel();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                count++;
            }
        },1000,1000);
    }

}

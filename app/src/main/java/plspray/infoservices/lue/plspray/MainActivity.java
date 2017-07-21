package plspray.infoservices.lue.plspray;

import android.*;
import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;


import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import plspray.infoservices.lue.plspray.databind.ContactList;
import plspray.infoservices.lue.plspray.databind.User;
import plspray.infoservices.lue.plspray.fragment.ContactListFragment;
import plspray.infoservices.lue.plspray.fragment.GroupFragment;
import plspray.infoservices.lue.plspray.fragment.GroupTwo;
import plspray.infoservices.lue.plspray.utilities.AppConstants;
import plspray.infoservices.lue.plspray.utilities.GlobalVariables;
import plspray.infoservices.lue.plspray.utilities.MarshmallowPermission;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ContactListFragment.OnListFragmentInteractionListener,GroupFragment.OnListFragmentInteractionListener {

    TextView usernameText, emailText, phoneText;
    CircularImageView userImageVIew;
    View header;
    Context context;
    public static MainActivity mainActivity;
    String userid="";
    String groupId="";
   public static List<ContactList> contactListList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        mainActivity = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        initialize();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void initialize() {
        usernameText = (TextView) header.findViewById(R.id.usernameText);
        userImageVIew = (CircularImageView) header.findViewById(R.id.userImageVIew);
        phoneText = (TextView) header.findViewById(R.id.phoneText);
        setUserInfo();
//        MarshmallowPermission permission = new MarshmallowPermission(this, Manifest.permission.READ_CONTACTS);
//        if (permission.result == -1 || permission.result == 0) {
//            try {
                replaceFragment();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (permission.result == 1) {
//            replaceFragment();
//        }

    }


    public void setUserInfo() {
        User user = SharedPreferenceClass.getUserInfo(this);
        if (user != null) {
            usernameText.setText(user.getFirst_name() + " " + user.getLast_name());
            if (!user.getPhone().trim().equals("")) phoneText.setText(user.getPhone());
            UtilityClass.getImage(context, user.getPhoto(), userImageVIew, R.drawable.user_default_image);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            ContactListFragment contactListFragment=new ContactListFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame1,contactListFragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.createGroup) {
            startActivity(new Intent(context, CreateGroupActivity.class));
        }
        else if (id == R.id.profile) {
            startActivity(new Intent(context, ProfileActivity.class));
        }
        else if (id == R.id.sendmsggroup) {

            GroupTwo groupFragment=new GroupTwo();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame1,groupFragment);
            fragmentTransaction.commit();

       }
       else if (id == R.id.logout) {

            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logOut() {
        SharedPreferenceClass.setLogin(context, false);
        SharedPreferenceClass.clearUserInfo(context);
        GlobalVariables.profilePic = null;
        startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void onListFragmentInteraction(ContactList item) {
       Intent i=new Intent(context,SendScheduleActivity.class);
        i.putExtra(AppConstants.GROUPID,groupId);
        if(!item.getName().trim().equals(""))
            i.putExtra(AppConstants.CONTACT_NAME,item.getName());
        else  i.putExtra(AppConstants.CONTACT_NAME,item.getNumber());
        i.putExtra(AppConstants.USERID,item.getId());
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void replaceFragment()
    {
        ContactListFragment contactListFragment=new ContactListFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame1,contactListFragment);
        fragmentTransaction.commit();
    }




}

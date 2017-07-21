package plspray.infoservices.lue.plspray.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import plspray.infoservices.lue.plspray.databind.TimeSchedule;
import plspray.infoservices.lue.plspray.databind.User;


public class SharedPreferenceClass {
    public final static String LANG_KEY="_LANGUAGE";
    public final static String PRODUCT_KEY="product_key";
    public final static String PRODUCT="product";
    public final static String USER_KEY="_USERKEY";
    public final static String APP_KEY="appkey";
    public final static String USER="_USER";
    public final static String LANG="_LANG";
    public static final String ID="id";
    public static final String FIRST_NAME="first_name";
    public static final String LAST_NAME="last_name";
    public static final String SIGNUP_TYPE="signup_type";
    public static final String PHONE="phone";
    public static final String PHOTO="photo";
    public static final String USER_NAME="user_name";
    public static final String PASSWORD="password";
    public static final String EMAIL="email";
    public static final String DEVICEID_KEY="deviceid_key";
    public static final String DEVICEID="deviceid";
    public static final String ISLOGINWITHGOOGLE="loginwithgoogle";
    public final static String LOGINKEY="LOGINKEY";
    public final static String IMAGE_URL="Image_url";
    public static final String ALLDATA="alldata";
    public static final String IS_LOGIN="is_login";




    public static final String CATEGORY_ID="category_id";
    public static final String SUBCATEGORY_ID="sub_category_id";
    public static final String SUBCATEGORY_NAME="sub_category_name";
    public static final String PRODUCT_NAME="product_name";
    public static final String LOCATION="location";
    public static final String PRICE="price";
    public static final String DESCRIPTION="description";
    public static final String IMAGES="images";
    public static final String DISCOUNT="discount";
    public static final String LATITUDE="latitude";
    public static final String LONGITUDE="longitude";
    public static final String CATEGORY_NAME="category_name";
    public static final String SALES_UNIT="sales_unit";
    public static final String VIDEO="video";
    public static final String TOKEN="token";
    public static final String SCHEDULE="schedule";




    public static void setLogin(Context context,boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_LOGIN,value);
        editor.commit();
    }

    public static void saveToken(Context context,String token) {
        SharedPreferences prefs = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN,token);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN,"");
    }

    public static boolean getLogin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_LOGIN,false);
    }

    public static void setUserInfo(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(user!=null) {
            editor.putString(ID, user.getId());
            editor.putString(FIRST_NAME, user.getFirst_name());
            editor.putString(LAST_NAME, user.getLast_name());
            editor.putString(DEVICEID, user.getDevice_id());
            editor.putString(PHONE, user.getPhone());
            editor.putString(PHOTO, user.getPhoto());
            editor.commit();
            setLogin(context,true);
        }
    }




    public static User getUserInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        User user=new User();
        user.setId(prefs.getString(ID,""));
        user.setFirst_name(prefs.getString(FIRST_NAME,""));
        user.setLast_name(prefs.getString(LAST_NAME,""));
        user.setDevice_id(prefs.getString(DEVICEID,""));
        user.setPhone(prefs.getString(PHONE,""));
        user.setPhoto(prefs.getString(PHOTO,""));
        return user;

    }

    public static void clearUserInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ID, "");
        editor.putString(FIRST_NAME,"");
        editor.putString(LAST_NAME,"");
        editor.putString(DEVICEID,"");
        editor.putString(PHONE, "");
        editor.putString(PHOTO,"");
        editor.commit();

    }



    public static void setSchedule(Context context, String schedule) {
        SharedPreferences prefs = context.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(schedule!=null) {
            editor.putString(SCHEDULE, schedule);
            editor.commit();
        }
    }

    public static TimeSchedule getSchedule(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        TimeSchedule timeSchedule=null;
        try {
            JSONObject jsonObject=new JSONObject(prefs.getString(SCHEDULE,""));
             timeSchedule=new TimeSchedule();
            timeSchedule.setMondayTime(jsonObject.getString("Monday"));
            timeSchedule.setTuesDayTime(jsonObject.getString("Tuesday"));
            timeSchedule.setWednesDayTime(jsonObject.getString("Wednesday"));
            timeSchedule.setThursDayTime(jsonObject.getString("Thursday"));
            timeSchedule.setFriDayTime(jsonObject.getString("Friday"));
            timeSchedule.setSaturDayTime(jsonObject.getString("Saturday"));
            timeSchedule.setSunDayTime(jsonObject.getString("Sunday"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
      return timeSchedule;
    }

}

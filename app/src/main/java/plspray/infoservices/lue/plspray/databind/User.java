package plspray.infoservices.lue.plspray.databind;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lue on 13-06-2017.
 */

public class User {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String id="";
    private String first_name="";
    private String last_name="";
    private String device_id="";
    private String phone="";
    private String photo="";

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    private String favorite="";

    public User(JSONObject jsonObject)
    {
        try {
            id=jsonObject.getString("id");
        } catch (JSONException e) {}
        try {
            first_name=jsonObject.getString("first_name");
            Log.d("name00","name1100"+first_name);
        } catch (JSONException e) {}
        try {
            last_name=jsonObject.getString("last_name");
        } catch (JSONException e) {}

        try {
            device_id=jsonObject.getString("device_id");
        } catch (JSONException e) {}
        try {
            phone=jsonObject.getString("phone");

        } catch (JSONException e) {}
        try {
            photo=jsonObject.getString("photo");
            Log.d("photo00","photopic00"+photo);
        } catch (JSONException e) {}
        try {
            favorite=jsonObject.getString("favorite");
        } catch (JSONException e) {}
    }

    public User(){}

}

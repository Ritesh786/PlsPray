package plspray.infoservices.lue.plspray.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MySharedPreference";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if(SharedPreferenceClass.getToken(this).equals("")) {
            SharedPreferenceClass.saveToken(this,refreshedToken);
        }
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}

package plspray.infoservices.lue.plspray.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import plspray.infoservices.lue.plspray.Async.DownloadThread;
import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.MainActivity;
import plspray.infoservices.lue.plspray.Manifest;
import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.databind.Contact;
import plspray.infoservices.lue.plspray.databind.ContactList;
import plspray.infoservices.lue.plspray.databind.User;
import plspray.infoservices.lue.plspray.utilities.MarshmallowPermission;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ContactListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    List<Contact> contactList;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ContactListFragment newInstance(int columnCount) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_item_list, container, false);




        MainActivity.contactListList=new ArrayList<>();
//        MarshmallowPermission permission=new MarshmallowPermission(getActivity(), Manifest.permission.READ_CONTACTS);
//        if(permission.result==-1 || permission.result==0)
//        contactList=getContacts();
//        else if(permission.result==1)
//            contactList=getContacts();

     //   contactList=getContacts();




        getUsers();
        // Set the adapter

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ContactList item);
    }


//    private List<Contact> getContacts() {
//
//        List<Contact> contactList = new ArrayList<>();
//         contactList.clear();
//        String phoneNumber = null;
//        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
//        String _ID = ContactsContract.Contacts._ID;
//        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
//        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
//        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
//        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
//
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
//        // Iterate every contact in the phone
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                Contact contact = new Contact();
//                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
//                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
//                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
//                contact.setName(name);
//                if (hasPhoneNumber > 0) {
//                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
//                    while (phoneCursor.moveToNext()) {
//                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
//                      if(!phoneNumber.contains("*") && !phoneNumber.contains("#") && !phoneNumber.trim().equals("")) {
//                          phoneNumber=phoneNumber.replace(" ","");
//                          phoneNumber=phoneNumber.replace("+","");
//                          phoneNumber=phoneNumber.replace("(","");
//                          phoneNumber=phoneNumber.replace(")","");
//                          phoneNumber=phoneNumber.replace("-","");
//                          phoneNumber=phoneNumber.replace("91","");
//                          phoneNumber=phoneNumber.replace("971","");
//                          phoneNumber=phoneNumber.replace("966","");
//                          phoneNumber=phoneNumber.replace("974","");
//                          contact.setNumber(phoneNumber);
//                          contactList.add(contact);
//
//                      }
//
//                    }
//                    phoneCursor.close();
//
//                }
//            }
//        }
//        return contactList;
//    }


    public void getUsers()
    {
//        StringBuilder stringBuilder=new StringBuilder();
//        final List<String> clist=new ArrayList<>();
//        for(int i=0;i<contactList.size();i++)
//        {
//            if(i<contactList.size()-1) stringBuilder.append(contactList.get(i).getNumber()+",");
//            else  stringBuilder.append(contactList.get(i).getNumber());
//            clist.add(contactList.get(i).getNumber());
//        }
        JSONObject jsonObject=new JSONObject();
        try {
        //    jsonObject.accumulate("phones",stringBuilder.toString());
            jsonObject.accumulate("user_phone",SharedPreferenceClass.getUserInfo(getContext()).getPhone());

            new DownloadThread(getActivity(), Urls.get_users,jsonObject.toString(), new DownloadThread.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        JSONObject jsonObject= new JSONObject(output);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray=jsonObject.getJSONArray("message");
                            if(jsonArray.length()>0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String number = jsonArray.getJSONObject(i).getString("phone");
                                    String imageUrl = jsonArray.getJSONObject(i).getString("photo");
                                    String id = jsonArray.getJSONObject(i).getString("id");
                                    String firstname = jsonArray.getJSONObject(i).getString("first_name");
                                    int index;
//                                    if((index=clist.indexOf(number))!=-1)
//                                }
//
//                                MainActivity.contactListList.add(new ContactList(contactList.get(index).getName(), number, imageUrl, id));
//                            }
                                    MainActivity.contactListList.add(new ContactList(firstname,number, imageUrl, id));


                             }

                                if (view instanceof RecyclerView) {
                                    Context context = view.getContext();
                                    RecyclerView recyclerView = (RecyclerView) view;
                                    if (mColumnCount <= 1) {
                                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                    } else {
                                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                    }
                                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(MainActivity.contactListList, mListener));
                                }
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
            },true).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

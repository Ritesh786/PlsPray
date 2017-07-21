package plspray.infoservices.lue.plspray.fragment;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import plspray.infoservices.lue.plspray.Async.DownloadThread;
import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.MainActivity;
import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.databind.Contact;
import plspray.infoservices.lue.plspray.databind.ContactList;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    List<Contact> contactList;
    View view;
    RecyclerView mRecycler;
    private List<ContactList> movieList = new ArrayList<ContactList>();

    public GroupFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GroupFragment newInstance(int columnCount) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        MainActivity.contactListList=new ArrayList<>();
//        MarshmallowPermission permission=new MarshmallowPermission(getActivity(), Manifest.permission.READ_CONTACTS);
//        if(permission.result==-1 || permission.result==0)
//        contactList=getContacts();
//        else if(permission.result==1)
//            contactList=getContacts();

      contactList=getContacts();




        getUsers();
        // Set the adapter
        return  view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GroupFragment.OnListFragmentInteractionListener) {
            mListener = (GroupFragment.OnListFragmentInteractionListener) context;
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


    private List<Contact> getContacts() {

        List<Contact> contactList = new ArrayList<>();
        contactList.clear();
        String phoneNumber = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Contact contact = new Contact();
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                contact.setName(name);
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        if(!phoneNumber.contains("*") && !phoneNumber.contains("#") && !phoneNumber.trim().equals("")) {
                            phoneNumber=phoneNumber.replace(" ","");
                            phoneNumber=phoneNumber.replace("+","");
                            phoneNumber=phoneNumber.replace("(","");
                            phoneNumber=phoneNumber.replace(")","");
                            phoneNumber=phoneNumber.replace("-","");
                            phoneNumber=phoneNumber.replace("91","");
                            phoneNumber=phoneNumber.replace("971","");
                            phoneNumber=phoneNumber.replace("966","");
                            phoneNumber=phoneNumber.replace("974","");
                            contact.setNumber(phoneNumber);
                            contactList.add(contact);

                        }

                    }
                    phoneCursor.close();

                }
            }
        }
        return contactList;
    }


    public void getUsers()
    {
        StringBuilder stringBuilder=new StringBuilder();
        final List<String> clist=new ArrayList<>();
        for(int i=0;i<contactList.size();i++)
        {
            if(i<contactList.size()-1) stringBuilder.append(contactList.get(i).getNumber()+",");
            else  stringBuilder.append(contactList.get(i).getNumber());
            clist.add(contactList.get(i).getNumber());
        }
        JSONObject jsonObject=new JSONObject();
        try {
            //    jsonObject.accumulate("phones",stringBuilder.toString());
            jsonObject.accumulate("user_phone", SharedPreferenceClass.getUserInfo(getContext()).getPhone());

            new DownloadThread(getActivity(), Urls.group_by,jsonObject.toString(), new DownloadThread.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        JSONArray jsonArray1= new JSONArray(output);
                        Log.d("jsun00","json101"+jsonArray1.toString());
                        String number = "null";
                        String imageUrl = "null";
                        String id = "null";
                        String firstname = "null";
//                            JSONArray jsonArray=jsonObject.getJSONArray("groups");
                            if(jsonArray1.length()>0) {
                                for (int i = 0; i < jsonArray1.length(); i++) {

                                    JSONObject movie = jsonArray1.getJSONObject(i);
                                    JSONArray jsonArray = movie.getJSONArray("groups");
                                    Log.d("jsungrop00","jsungrop00 "+jsonArray.toString());

                                    for (int iml = 0; iml < jsonArray.length(); iml++) {

                                       number = jsonArray.getJSONObject(iml).getString("member_id");
                                     imageUrl = jsonArray.getJSONObject(iml).getString("group_image");
                                         id = jsonArray.getJSONObject(iml).getString("group_id");
                                         firstname = jsonArray.getJSONObject(iml).getString("group_name");
                                        Log.d("AllAra00","AllAra0012 "+ number + imageUrl + id + firstname);
                                        int index;
//                                    if((index=clist.indexOf(number))!=-1)
//                                }
//
//                                MainActivity.contactListList.add(new ContactList(contactList.get(index).getName(), number, imageUrl, id));
//                            }
                                    }

                                 //   MainActivity.contactListList.add(new ContactList(firstname, number, imageUrl, id));

                                    ContactList contactList = new ContactList();
                                    contactList.setNumber(number);
                                    contactList.setImageUrl(imageUrl);
                                    contactList.setId(id);
                                    contactList.setName(firstname);
                                    movieList.add(contactList);

                                }

                                    if (view instanceof RecyclerView) {
                                        Context context = view.getContext();
                                        Log.d("vcvcvv",context.toString());
                                       RecyclerView recyclerView = (RecyclerView) view;
                                        Log.d("vcvcvv1204",mRecycler.toString());
                                        if (mColumnCount <= 1) {
                                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                            Log.d("vcvcvv1204mmm", String.valueOf(mColumnCount));
                                        } else {
                                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                        }
                             //           recyclerView.setAdapter(new DApter(movieList, mListener));
                                        Log.d("bbibibibib","nobobob");
                                    }

                            }

                    }catch (Exception e){Log.d("nibibibidv",e.toString());}
                }
            },true).execute();
        } catch (JSONException e) {
            Log.d("bbbb111111",e.toString());
        }

    }

}

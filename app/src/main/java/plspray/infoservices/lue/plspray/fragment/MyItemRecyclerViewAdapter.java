package plspray.infoservices.lue.plspray.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import plspray.infoservices.lue.plspray.MainActivity;
import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.databind.ContactList;
import plspray.infoservices.lue.plspray.fragment.ContactListFragment.OnListFragmentInteractionListener;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;


import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<ContactList> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<ContactList> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        UtilityClass.getImage(MainActivity.mainActivity,holder.mItem.getImageUrl(),holder.userImg,R.drawable.user_default_image);

        holder.nameText.setText(mValues.get(position).getName());
        holder.contactText.setText(mValues.get(position).getNumber());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CircularImageView userImg;
        public final TextView nameText,contactText;
        public ContactList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            userImg = (CircularImageView) view.findViewById(R.id.userImg);
            nameText = (TextView) view.findViewById(R.id.nameText);
            contactText = (TextView) view.findViewById(R.id.contactText);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameText.getText() + "'";
        }
    }
}

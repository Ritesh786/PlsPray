package plspray.infoservices.lue.plspray.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.databind.PhoneNumber;

/**
 * Created by Fujitsu on 27/05/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {


    private  List<PhoneNumber> movieItems;
    Context context;
    ArrayList<String> selectedStrings = new ArrayList<String>();



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox phonrno;
        public TextView nametxt;

        public MyViewHolder(View view) {
            super(view);

            phonrno = (CheckBox) view.findViewById(R.id.numbercheck);
            nametxt = (TextView) view.findViewById(R.id.nametxt);

        }
    }


    public RecycleAdapter(List<PhoneNumber> moviesList) {

        this.movieItems = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.numberadd, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final PhoneNumber movie = movieItems.get(position);

        holder.phonrno.setText(movie.getPhonenu());
        holder.nametxt.setText(movie.getName());

        holder.phonrno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (holder.phonrno.isChecked()) {

                    PhoneNumber movie = new PhoneNumber();
                    movie.setGetno(holder.phonrno.getText().toString().trim());
                    movieItems.add(movie);

                }

            }
        });
    }
/*holder.phonrno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                PhoneNumber country = (PhoneNumber) cb.getTag();
               *//* Toast.makeText(context,
                        "Clicked on Checkbox: " + cb.getText() +
                                " is " + cb.isChecked(),
                        Toast.LENGTH_LONG).show();*//*
                country.setSelected(cb.isChecked());
            }
        });

    }*/


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        return movieItems.size();
    }


}


package com.pidois.ester.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pidois.ester.Adapter.ViewHolder.ProfilePersonalInfoHolder;
import com.pidois.ester.Models.Person;
import com.pidois.ester.R;

import java.util.List;

public class ProfilePersonalInfoAdapter extends RecyclerView.Adapter<ProfilePersonalInfoHolder> {

    //public static Context mContext;
    public List<Person> dataPerson;

    public ProfilePersonalInfoAdapter (List<Person> dataPerson){
        //this.mContext = mContext;
        this.dataPerson = dataPerson;
    }


    @NonNull
    @Override
    public ProfilePersonalInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView view = (CardView) inflater.inflate(R.layout.profile_personal_item, parent, false);

        return new ProfilePersonalInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePersonalInfoHolder holder, int position) {
        holder.name.setText("Diboas");
        holder.birthday.setText("16/01/1960");
        holder.email.setText("diboas@gmail.com");

    }

    @Override
    public int getItemCount() {
        return dataPerson.size();
    }
}

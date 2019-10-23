package com.pidois.ester.Adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pidois.ester.Models.Person;
import com.pidois.ester.Models.Strap;
import com.pidois.ester.ProfileItem;
import com.pidois.ester.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter {

    private List<ProfileItem> mProfileItem;
    private List<Person> dataPerson;
    private List<Strap> dataStrap;

    public ProfileAdapter (List<Person> dataPerson, List<Strap> dataStrap, List<ProfileItem> mProfileItem){
        this.dataStrap = dataStrap;
        this.dataPerson = dataPerson;
        this.mProfileItem = mProfileItem;
    }

    class ProfilePersonalInfoHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView email;
        public TextView birthday;
        public RelativeLayout headerLayout;
        public RelativeLayout footerLayout;
        public ValueAnimator vAnimator;

        public ProfilePersonalInfoHolder(CardView card) {

            super(card);
            this.name = card.findViewById(R.id.profile_person_name);
            this.email = card.findViewById(R.id.profile_person_email);
            this.birthday = card.findViewById(R.id.profile_person_birthday);
            this.headerLayout = card.findViewById(R.id.profile_personal_header);
            this.footerLayout = card.findViewById(R.id.profile_personal_footer);

            this.footerLayout.setVisibility(View.GONE);

            this.footerLayout.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {

                        @Override
                        public boolean onPreDraw() {
                            footerLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                            footerLayout.setVisibility(View.GONE);

                            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            footerLayout.measure(widthSpec, heightSpec);

                            vAnimator = slideAnimator(0, footerLayout.getMeasuredHeight(), footerLayout);
                            return true;
                        }
                    });

            this.headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("LOG", "onClickListener of headerLayout clicked");
                    if (footerLayout.getVisibility() == View.GONE) {
                        Log.i("LOG", "Expand Click");
                        expand(footerLayout, vAnimator);
                    } else {
                        Log.i("LOG", "Collapse Click");
                        collapse(footerLayout);
                    }
                }
            });


        }

        /*void bindView (int position){
            Person person = (Person) mProfileItem.get(position);
            name.setText(dataPerson.get(position).getName());
            birthday.setText(dataPerson.get(position).getBirthday());
            email.setText(dataPerson.get(position).getEmail());
         }*/


    }

    class ProfileStrapHolder extends RecyclerView.ViewHolder {

        public TextView scalePos1;
        public TextView scalePos2;
        public TextView scalePos3;
        public TextView date;
        public RelativeLayout headerLayout;
        public RelativeLayout footerLayout;
        public ValueAnimator vAnimator;

        public ProfileStrapHolder (CardView card){
            super(card);
            this.scalePos1 = card.findViewById(R.id.profile_strap_scale_data1);
            this.scalePos2 = card.findViewById(R.id.profile_strap_scale_data2);
            this.scalePos3 = card.findViewById(R.id.profile_strap_scale_data3);
            this.date = card.findViewById(R.id.profile_strap_date);
            this.headerLayout = card.findViewById(R.id.profile_strap_header);
            this.footerLayout = card.findViewById(R.id.profile_strap_footer);

            this.footerLayout.setVisibility(View.GONE);

            this.footerLayout.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {

                        @Override
                        public boolean onPreDraw() {
                            footerLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                            footerLayout.setVisibility(View.GONE);

                            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            footerLayout.measure(widthSpec, heightSpec);

                            vAnimator = slideAnimator(0, footerLayout.getMeasuredHeight(), footerLayout);
                            return true;
                        }
                    });

            this.headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("LOG", "onClickListener of headerLayout clicked");
                    if (footerLayout.getVisibility() == View.GONE) {
                        Log.i("LOG", "Expand Click");
                        expand(footerLayout, vAnimator);
                    } else {
                        Log.i("LOG", "Collapse Click");
                        collapse(footerLayout);
                    }
                }
            });
        }

        /*void bindView (int position) {
            Strap strap = (Strap) mProfileItem.get(position);
            scalePos1.setText(dataStrap.get(position).getTremorPos1());
            scalePos2.setText(dataStrap.get(position).getTremorPos2());
            scalePos3.setText(dataStrap.get(position).getTremorPos3());
            date.setText(dataStrap.get(position).getDate());
        }*/

    }

    private void expand(RelativeLayout footerLayout, Animator vAnimator) {
        Log.i("LOG", "Expand enter, View.VISIBLE");
        footerLayout.setVisibility(View.VISIBLE);
        vAnimator.start();
    }

    private void collapse(final RelativeLayout footerLayout) {
        int finalHeight = footerLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, footerLayout);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i("LOG", "collapse onAnimationEnd enter, View.GONE");
                footerLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final RelativeLayout footerLayout ) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = footerLayout.getLayoutParams();
                layoutParams.height = value;
                footerLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    @Override
    public int getItemViewType(int position) {
        return mProfileItem.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView itemView;
        switch (viewType) {
            case ProfileItem.TYPE_STRAP:
                itemView = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.profile_strap_item, parent, false);
                return new ProfileStrapHolder(itemView);
            default: // TYPE_PERSONAL_INFO
                itemView = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.profile_personal_item, parent, false);
                return new ProfilePersonalInfoHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ProfileItem.TYPE_PERSONAL_INFO:
                //((ProfilePersonalInfoHolder) holder).bindView(position);
                ((ProfilePersonalInfoHolder) holder).name.setText(dataPerson.get(position).getName());
                ((ProfilePersonalInfoHolder) holder).birthday.setText(dataPerson.get(position).getBirthday());
                ((ProfilePersonalInfoHolder) holder).email.setText(dataPerson.get(position).getEmail());
                break;
            case ProfileItem.TYPE_STRAP:
                //((ProfileStrapHolder) holder).bindView(position);
                ((ProfileStrapHolder) holder).scalePos1.setText(dataStrap.get(position).getTremorPos1());
                ((ProfileStrapHolder) holder).scalePos2.setText(dataStrap.get(position).getTremorPos2());
                ((ProfileStrapHolder) holder).scalePos3.setText(dataStrap.get(position).getTremorPos3());
                ((ProfileStrapHolder) holder).date.setText(dataStrap.get(position).getDate());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mProfileItem == null) {
            return 0;
        } else {
            return mProfileItem.size();
        }
    }

    public void setProfileItem(List<? extends ProfileItem> profileItem) {
        if (mProfileItem == null){
            mProfileItem = new ArrayList<>();
        }
        mProfileItem.clear();
        mProfileItem.addAll(profileItem);
        //mProfileItem.addAll(profileItem2);
        notifyDataSetChanged();
    }

}

package com.pidois.ester.Adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
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
import com.pidois.ester.Models.Profile;
import com.pidois.ester.Models.Strap;
import com.pidois.ester.ProfileItem;
import com.pidois.ester.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter {

    private static int TYPE_PERSON = 1;
    private static int TYPE_STRAP = 2;
    private static int TYPE_COGNITIVE = 3;

    //private List<ProfileItem> mProfileItem;
    private Context context;
    //private List<Person> dataPerson;
    //private List<Strap> dataStrap;
    private List<Profile> dataProfile;

    public ProfileAdapter(Context context, List<Profile> dataProfile) {
        this.dataProfile = dataProfile;
        this.context = context;
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
    }

    class ProfileCognitiveHolder extends RecyclerView.ViewHolder {

        public TextView totalAnswers;
        public TextView rightAnswers;
        public TextView wrongAnswers;
        public TextView date;
        public RelativeLayout headerLayout;
        public RelativeLayout footerLayout;
        public ValueAnimator vAnimator;

        public ProfileCognitiveHolder(CardView card) {

            super(card);
            this.totalAnswers = card.findViewById(R.id.profile_cognitive_total);
            this.rightAnswers = card.findViewById(R.id.profile_cognitive_right);
            this.wrongAnswers = card.findViewById(R.id.profile_cognitive_wrong);
            this.date = card.findViewById(R.id.profile_cognitive_date);
            this.headerLayout = card.findViewById(R.id.profile_cognitive_header);
            this.footerLayout = card.findViewById(R.id.profile_cognitive_footer);

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
    }

    class ProfileStrapHolder extends RecyclerView.ViewHolder {

        public TextView scalePos1;
        public TextView scalePos2;
        public TextView scalePos3;
        public TextView date;
        public RelativeLayout headerLayout;
        public RelativeLayout footerLayout;
        public ValueAnimator vAnimator;

        public ProfileStrapHolder(CardView card) {
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

    private ValueAnimator slideAnimator(int start, int end, final RelativeLayout footerLayout) {
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
        if (dataProfile.get(position).getType() == 1) {
            return TYPE_PERSON;
        } else if (dataProfile.get(position).getType() == 2) {
            return TYPE_STRAP;
        } else {
            return TYPE_COGNITIVE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView itemView;
        if (viewType == TYPE_PERSON) {
            itemView = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_personal_item, parent, false);
            return new ProfilePersonalInfoHolder(itemView);
        } else if (viewType == TYPE_STRAP) {
            itemView = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_strap_item, parent, false);
            return new ProfileStrapHolder(itemView);
        } else {
            itemView = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_cognitive_item, parent, false);
            return new ProfileCognitiveHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PERSON) {
            //((ProfilePersonalInfoHolder) holder).bindView(position);
            ((ProfilePersonalInfoHolder) holder).name.setText(dataProfile.get(position).getName());
            ((ProfilePersonalInfoHolder) holder).birthday.setText(dataProfile.get(position).getBirthday());
            ((ProfilePersonalInfoHolder) holder).email.setText(dataProfile.get(position).getEmail());
        } else if (getItemViewType(position) == TYPE_STRAP) {
            //((ProfileStrapHolder) holder).bindView(position);
            ((ProfileStrapHolder) holder).scalePos1.setText(dataProfile.get(position).getTremorPos1());
            ((ProfileStrapHolder) holder).scalePos2.setText(dataProfile.get(position).getTremorPos2());
            ((ProfileStrapHolder) holder).scalePos3.setText(dataProfile.get(position).getTremorPos3());
            ((ProfileStrapHolder) holder).date.setText(dataProfile.get(position).getDate());
        } else {
            ((ProfileCognitiveHolder) holder).totalAnswers.setText(dataProfile.get(position).getTotalAnswers());
            ((ProfileCognitiveHolder) holder).rightAnswers.setText(dataProfile.get(position).getRightAnswers());
            ((ProfileCognitiveHolder) holder).wrongAnswers.setText(dataProfile.get(position).getWrongAnswers());
            ((ProfileCognitiveHolder) holder).date.setText(dataProfile.get(position).getCognitiveDate());
        }
    }

    @Override
    public int getItemCount() {
        return dataProfile.size();
    }

    /*public void setProfileItem(List<? extends ProfileItem> profileItem) {
        if (mProfileItem == null) {
            mProfileItem = new ArrayList<>();
        }
        mProfileItem.clear();
        mProfileItem.addAll(profileItem);
        //mProfileItem.addAll(profileItem2);
        notifyDataSetChanged();
    }*/

}

package com.pidois.ester.Controller.Adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pidois.ester.Controller.Charts;
import com.pidois.ester.Controller.ExercisesActivity;
import com.pidois.ester.Models.Profile;
import com.pidois.ester.R;


import java.io.BufferedReader;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter {

    private static int TYPE_PERSON = 1;
    private static int TYPE_STRAP = 2;
    private static int TYPE_COGNITIVE = 3;
    private static int TYPE_SOUND = 4;
    private static int TYPE_COLOR = 5;

    private Context context;
    private List<Profile> dataProfile;

    public ProfileAdapter(Context context, List<Profile> dataProfile) {
        this.dataProfile = dataProfile;
        this.context = context;
    }

    private class ProfilePersonalInfoHolder extends RecyclerView.ViewHolder {

        private TextView name, email, birthday;
        private RelativeLayout headerLayout, footerLayout;
        private ValueAnimator vAnimator;

        private ProfilePersonalInfoHolder(CardView card) {

            super(card);
            this.name = card.findViewById(R.id.profile_person_name);
            this.email = card.findViewById(R.id.profile_person_email);
            //this.birthday = card.findViewById(R.id.profile_person_birthday);
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

    private class ProfileSoundHolder extends RecyclerView.ViewHolder {

        private TextView level, rightAnswers, date;
        private RelativeLayout headerLayout, footerLayout;
        private ValueAnimator vAnimator;
        private Button chartButton;

        private ProfileSoundHolder(CardView card) {

            super(card);
            this.level = card.findViewById(R.id.profile_sound_level);
            this.rightAnswers = card.findViewById(R.id.profile_sound_right);
            this.date = card.findViewById(R.id.profile_sound_date);
            this.headerLayout = card.findViewById(R.id.profile_sound_header);
            this.footerLayout = card.findViewById(R.id.profile_sound_footer);
            this.chartButton = card.findViewById(R.id.profile_sound_button);

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

            this.chartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("PROFILE", "Sound btn click");
                }
            });


        }
    }

    private class ProfileColorHolder extends RecyclerView.ViewHolder {

        private TextView level, rightAnswers, date;
        private RelativeLayout headerLayout, footerLayout;
        private ValueAnimator vAnimator;
        private Button chartButton;

        private ProfileColorHolder(CardView card) {

            super(card);
            this.level = card.findViewById(R.id.profile_color_level);
            this.rightAnswers = card.findViewById(R.id.profile_color_right);
            this.date = card.findViewById(R.id.profile_color_date);
            this.headerLayout = card.findViewById(R.id.profile_color_header);
            this.footerLayout = card.findViewById(R.id.profile_color_footer);
            this.chartButton = card.findViewById(R.id.profile_color_button);

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

            this.chartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("PROFILE", "Color btn click");
                }
            });


        }
    }

    private class ProfileCognitiveHolder extends RecyclerView.ViewHolder {

        private TextView totalAnswers, rightAnswers, wrongAnswers, date;
        private RelativeLayout headerLayout, footerLayout;
        private ValueAnimator vAnimator;
        private Button chartButton;

        private ProfileCognitiveHolder(CardView card) {

            super(card);
            this.totalAnswers = card.findViewById(R.id.profile_cognitive_total);
            this.rightAnswers = card.findViewById(R.id.profile_cognitive_right);
            this.wrongAnswers = card.findViewById(R.id.profile_cognitive_wrong);
            this.date = card.findViewById(R.id.profile_cognitive_date);
            this.headerLayout = card.findViewById(R.id.profile_cognitive_header);
            this.footerLayout = card.findViewById(R.id.profile_cognitive_footer);
            this.chartButton = card.findViewById(R.id.profile_cognitive_button);

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

            this.chartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("PROFILE", "Cognitive btn click");
                    Intent intent = new Intent(view.getContext(), Charts.class);
                    view.getContext().startActivity(intent);
                }
            });

        }
    }

    private class ProfileStrapHolder extends RecyclerView.ViewHolder {

        private TextView scalePos1, scalePos2, scalePos3, date;
        private RelativeLayout headerLayout, footerLayout;
        private ValueAnimator vAnimator;
        private Button chartButton;

        private ProfileStrapHolder(CardView card) {
            super(card);
            this.scalePos1 = card.findViewById(R.id.profile_strap_scale_data1);
            this.scalePos2 = card.findViewById(R.id.profile_strap_scale_data2);
            this.scalePos3 = card.findViewById(R.id.profile_strap_scale_data3);
            this.date = card.findViewById(R.id.profile_strap_date);
            this.headerLayout = card.findViewById(R.id.profile_strap_header);
            this.footerLayout = card.findViewById(R.id.profile_strap_footer);
            this.chartButton = card.findViewById(R.id.profile_strap_button);

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

            this.chartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("PROFILE", "Strap btn click");
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
        } else if (dataProfile.get(position).getType() == 3) {
            return TYPE_COGNITIVE;
        } else if (dataProfile.get(position).getType() == 4) {
            return TYPE_SOUND;
        } else {
            return TYPE_COLOR;
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
        } else if (viewType == TYPE_COGNITIVE) {
            itemView = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_cognitive_item, parent, false);
            return new ProfileCognitiveHolder(itemView);
        } else if (viewType == TYPE_SOUND) {
            itemView = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_sound_item, parent, false);
            return new ProfileSoundHolder(itemView);
        } else {
            itemView = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_color_item, parent, false);
            return new ProfileColorHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PERSON) {
            //((ProfilePersonalInfoHolder) holder).bindView(position);
            ((ProfilePersonalInfoHolder) holder).name.setText(dataProfile.get(position).getName());
            //((ProfilePersonalInfoHolder) holder).birthday.setText(dataProfile.get(position).getBirthday());
            ((ProfilePersonalInfoHolder) holder).email.setText(dataProfile.get(position).getEmail());
        } else if (getItemViewType(position) == TYPE_STRAP) {
            //((ProfileStrapHolder) holder).bindView(position);
            ((ProfileStrapHolder) holder).scalePos1.setText(dataProfile.get(position).getTremorPos1());
            ((ProfileStrapHolder) holder).scalePos2.setText(dataProfile.get(position).getTremorPos2());
            ((ProfileStrapHolder) holder).scalePos3.setText(dataProfile.get(position).getTremorPos3());
            ((ProfileStrapHolder) holder).date.setText(dataProfile.get(position).getDate());
        } else if (getItemViewType(position) == TYPE_COGNITIVE) {
            ((ProfileCognitiveHolder) holder).totalAnswers.setText(dataProfile.get(position).getTotalAnswers());
            ((ProfileCognitiveHolder) holder).rightAnswers.setText(dataProfile.get(position).getRightAnswers());
            ((ProfileCognitiveHolder) holder).wrongAnswers.setText(dataProfile.get(position).getWrongAnswers());
            ((ProfileCognitiveHolder) holder).date.setText(dataProfile.get(position).getCognitiveDate());
        } else if (getItemViewType(position) == TYPE_SOUND) {
            ((ProfileSoundHolder) holder).level.setText(dataProfile.get(position).getSoundLevel());
            ((ProfileSoundHolder) holder).rightAnswers.setText(dataProfile.get(position).getSoundRightAnswers());
            ((ProfileSoundHolder) holder).date.setText(dataProfile.get(position).getSoundDate());
        } else {
            ((ProfileColorHolder) holder).level.setText(dataProfile.get(position).getColorLevel());
            ((ProfileColorHolder) holder).rightAnswers.setText(dataProfile.get(position).getColorRightanswers());
            ((ProfileColorHolder) holder).date.setText(dataProfile.get(position).getColorDate());
        }
    }

    @Override
    public int getItemCount() {
        return dataProfile.size();
    }

}

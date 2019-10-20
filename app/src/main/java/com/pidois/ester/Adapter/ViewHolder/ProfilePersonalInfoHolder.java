package com.pidois.ester.Adapter.ViewHolder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pidois.ester.R;

public class ProfilePersonalInfoHolder extends RecyclerView.ViewHolder {

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

                        vAnimator = slideAnimator(0, footerLayout.getMeasuredHeight());
                        return true;
                    }
                });

        this.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LOG", "onClickListener of headerLayout clicked");
                if (footerLayout.getVisibility() == View.GONE) {
                    Log.i("LOG", "Expand Click");
                    expand();
                } else {
                    Log.i("LOG", "Collapse Click");
                    collapse();
                }
            }
        });


    }

    private void expand() {
        Log.i("LOG", "Expand enter, View.VISIBLE");
        footerLayout.setVisibility(View.VISIBLE);
        vAnimator.start();
    }

    private void collapse() {
        int finalHeight = footerLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);
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

    private ValueAnimator slideAnimator(int start, int end) {
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


}

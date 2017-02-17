package com.gdgvitvellore.sharepaper.Animations;

/**
 * Created by ramkishorevs on 05/02/17.
 */

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;



public class BackgroundCircularReveal {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator circularRevealSplash(View rootView,int cx, int cy) {

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        Animator circularReveal = android.view.ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1100);
        circularReveal.setStartDelay(30);
        circularReveal.setInterpolator(new AccelerateInterpolator());
        return circularReveal;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator destroyCircularRevealSplash(final View rootView, int cx, int cy) {

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        Animator circularReveal = android.view.ViewAnimationUtils.createCircularReveal(rootView, cx, cy, finalRadius, 0);
        circularReveal.setDuration(1500);
        circularReveal.setInterpolator(new AccelerateInterpolator());
        return circularReveal;
    }
}

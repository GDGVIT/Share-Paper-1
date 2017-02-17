package com.gdgvitvellore.sharepaper.StartScreens;

/**
 * Created by ramkishorevs on 15/02/17.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.gdgvitvellore.sharepaper.Activities.DashBoardActivity;
import com.gdgvitvellore.sharepaper.Activities.LoginActivity;
import com.gdgvitvellore.sharepaper.Handlers.DataHandler;
import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.Utils;


/**
 * Created by vsramkishore on 17/4/16.
 */
public class StartHolders extends AppCompatActivity {

    ViewPager viewPager;
    ImageView circle1,circle2,circle3;
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(DataHandler.getInstance(this).getPreference("regno", "1")) && !DataHandler.getInstance(this).getPreference("regno", "1").equals("1")) {

            startActivity(new Intent(this, DashBoardActivity.class));

        } else {

            setContentView(R.layout.onboarding_activity);
            circle1 = (ImageView) findViewById(R.id.circle1);
            circle2 = (ImageView) findViewById(R.id.circle2);
            circle3 = (ImageView) findViewById(R.id.circle3);
            proceed = (Button) findViewById(R.id.proceed_btn);


            proceed.setTypeface(new Utils().getFontType(Utils.HEADING_FONT, this));


            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(StartHolders.this, LoginActivity.class));
                }
            });

            viewPager = (ViewPager) findViewById(R.id.viewPager);
            viewPager.setAdapter(new MyStartScreensAdapter(getSupportFragmentManager()));
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == 0) {
                        circle1.setImageResource(R.drawable.indicator);
                        circle2.setImageResource(R.drawable.indicatorlight);
                        circle3.setImageResource(R.drawable.indicatorlight);


                    } else if (position == 1) {
                        circle2.setImageResource(R.drawable.indicator);
                        circle1.setImageResource(R.drawable.indicatorlight);
                        circle3.setImageResource(R.drawable.indicatorlight);
                    } else if (position == 2) {
                        circle3.setImageResource(R.drawable.indicator);
                        circle2.setImageResource(R.drawable.indicatorlight);
                        circle1.setImageResource(R.drawable.indicatorlight);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {


                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyStartScreensAdapter extends FragmentStatePagerAdapter {
        public MyStartScreensAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0: return new FirstFragment();
                case 1: return new SecondFragment();
                case 2: return new ThirdFragment();
                default: return new FirstFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
package com.gdgvitvellore.sharepaper.Activities;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gdgvitvellore.sharepaper.API.ConnectAPI;
import com.gdgvitvellore.sharepaper.Actors.Login;
import com.gdgvitvellore.sharepaper.Actors.Refresh;
import com.gdgvitvellore.sharepaper.Animations.BackgroundCircularReveal;
import com.gdgvitvellore.sharepaper.Handlers.DataHandler;
import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.Utils;

/**
 * Created by ramkishorevs on 01/02/17.
 */

public class LoginActivity extends AppCompatActivity implements ConnectAPI.ServerAuthenticateListener {

    EditText regno,password;
    Button login;
    ConnectAPI connectAPI;
    ProgressDialog dialog;
    CoordinatorLayout bgSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.login_activity);
            init();
            if (Build.VERSION.SDK_INT >= 21) {
                ViewTreeObserver viewTreeObserver = bgSplash.getViewTreeObserver();

                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onGlobalLayout() {

                            Animator reveal = BackgroundCircularReveal.circularRevealSplash(bgSplash, bgSplash.getWidth() / 2, 60);
                            reveal.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {


                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {

                                    try {
                                        Log.v("log","call");

                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                            reveal.start();


                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                bgSplash.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            } else {
                                bgSplash.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }

                        }
                    });
                }

            } else {
                bgSplash.setVisibility(View.VISIBLE);
            }



            setinit();
        }


    private void init()
    {
        regno=(EditText)findViewById(R.id.regno);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.email_sign_in_button);
        connectAPI=new ConnectAPI(this);
        dialog=new ProgressDialog(this);
        bgSplash=(CoordinatorLayout)findViewById(R.id.bgsplash);
    }


    private void setinit()
    {


        login.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,this));

        dialog.setMessage("Connecting please wait..");

        dialog.setCancelable(false);

        connectAPI.setServerAuthenticateListener(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (regno.getText().toString().matches("^[0-9]{2}[a-zA-Z]{3,}[0-9]{4}$")&&!TextUtils.isEmpty(password.getText().toString())) {

                    connectAPI.login(regno.getText().toString(),password.getText().toString());
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Invalid credinials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestInitiated(int code) {

        dialog.show();
    }

    @Override
    public void onRequestCompleted(int code, Object result) {

        if (code==ConnectAPI.LOGIN_CODE)
        {

              try {


                  Login login = (Login) result;
                  if (login.getStatus().getCode() == 0) {
                      connectAPI.refresh(regno.getText().toString(), password.getText().toString());
                  }
                  if (login.getStatus().getCode() == 12) {
                      dialog.dismiss();
                      Toast.makeText(LoginActivity.this, "Invalid credinials", Toast.LENGTH_SHORT).show();
                  }
              }catch (Exception e)
              {
                  e.printStackTrace();
                  Toast.makeText(LoginActivity.this,"Server error.. Please try again",Toast.LENGTH_SHORT).show();
              }
        }


        if (code==ConnectAPI.REFRESH_CODE)
        {
            Refresh refresh=(Refresh) result;
            DataHandler.getInstance(this).savePreference("regno",refresh.getReg_no());
            DataHandler.getInstance(this).savePreference("name",refresh.getName());
            DataHandler.getInstance(this).savePreference("school",refresh.getSchool());
            dialog.dismiss();
            startActivity(new Intent(LoginActivity.this,DashBoardActivity.class));

        }

    }

    @Override
    public void onRequestError(int code, String message) {


        dialog.dismiss();
        Toast.makeText(LoginActivity.this,"Server error",Toast.LENGTH_SHORT).show();
    }
}

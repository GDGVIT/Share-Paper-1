package com.gdgvitvellore.sharepaper.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.Utils;


/**
 * Created by ramkishorevs on 16/02/17.
 */

public class AboutUsActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_activity);
        init();
        setfonts();
        setinit();
    }


    private void init()
    {
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);

    }

    private void setfonts()
    {

        tv1.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,this));
        tv2.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,this));
        tv3.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,this));


    }


    private void setinit() {
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://gdgvitvellore.com/"));
                startActivity(browserIntent);
            }
        });

    }
}

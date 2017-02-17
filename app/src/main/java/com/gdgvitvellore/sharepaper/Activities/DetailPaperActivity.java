package com.gdgvitvellore.sharepaper.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artjimlop.altex.AltexImageDownloader;
import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.Utils;

import java.util.List;

import in.myinnos.imagesliderwithswipeslibrary.SliderLayout;
import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.BaseSliderView;
import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.TextSliderView;

/**
 * Created by ramkishorevs on 28/01/17.
 */

public class DetailPaperActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    List<String> images;
    SliderLayout sliderLayout;
    TextView textView;
    ImageView download;
    String IMG_BASE_URL="http://share-paper.cloudapp.net:1337";
    String FOLDER_NAME="Share Paper";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_paper_activity);

        getData();
        init();
        setfonts();
        setinit();
    }


    private void getData()
    {
       images= (List<String>) getIntent().getSerializableExtra("images");

    }


    private void init()
    {
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        download=(ImageView)findViewById(R.id.download);
        textView=(TextView)findViewById(R.id.textView);

    }

    private void setfonts()
    {
        textView.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,this));
    }

    private void setinit()
    {
        for (int i=0;i<images.size();i++)
        {
            TextSliderView textSliderView = new TextSliderView(DetailPaperActivity.this);
            // initialize a SliderLayout
            textSliderView
                    .description("Questions by Share Paper")
                    .image(IMG_BASE_URL+images.get(i))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(DetailPaperActivity.this);



            sliderLayout.addSlider(textSliderView);
        }


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {


                    for (int j = 0; j < images.size(); j++) {

                        AltexImageDownloader.writeToDisk(DetailPaperActivity.this, IMG_BASE_URL + images.get(j), FOLDER_NAME);


                    }

                    Toast.makeText(DetailPaperActivity.this, "Images saved to phone", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(DetailPaperActivity.this,"Unable to establish connection.. Try again later!",Toast.LENGTH_SHORT).show();
                }
                }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

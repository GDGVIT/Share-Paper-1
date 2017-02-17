package com.gdgvitvellore.sharepaper.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.claudiodegio.msv.MaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gdgvitvellore.sharepaper.Actors.Coarses;
import com.gdgvitvellore.sharepaper.Actors.Courses;
import com.gdgvitvellore.sharepaper.Actors.Refresh;
import com.gdgvitvellore.sharepaper.Handlers.DataHandler;
import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.StartScreens.StartHolders;
import com.gdgvitvellore.sharepaper.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkishorevs on 30/01/17.
 */

public class DashBoardActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    String course_json_list;
    List<Refresh> refresh;
    List<Courses> listses=new ArrayList<>();
    TextView tv1,name;
    ImageView reading;
    private static final int EXTERNAL_CODE=1;
    Toolbar toolbar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        init();
        setfonts();
        setanimations();
        getStoragePermission();
        setinit();
        setdata();
    }


    private void init()
    {
        tv1=(TextView)findViewById(R.id.tv1);
        name=(TextView)findViewById(R.id.name);
        reading=(ImageView)findViewById(R.id.reading_image);
        recyclerView=(RecyclerView)findViewById(R.id.course_list_recycler_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

    }


    @Override
    public void onBackPressed() {
     //   super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void setfonts()
    {
        tv1.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,this));
        name.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,this));

    }

    private void setanimations()
    {
        YoYo.with(Techniques.RotateInUpLeft).duration(1500).playOn(reading);
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(name);

    }

    private void setinit()
    {
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_logout) {

                    DataHandler.getInstance(DashBoardActivity.this).logout();
                    startActivity(new Intent(DashBoardActivity.this,StartHolders.class));

                }


                if (item.getItemId()==R.id.about_us)
                {
                    startActivity(new Intent(DashBoardActivity.this,AboutUsActivity.class));
                }

                return false;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setdata()
    {

        name.setText("Welcome "+DataHandler.getInstance(this).getPreference("name"," "));
        refresh= DataHandler.getInstance(this).getUserEntry();
        listses=refresh.get(0).getCourses();
        recyclerView.setAdapter(new CoursesAdapter());

    }
    private void getStoragePermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    EXTERNAL_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case EXTERNAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                }
        }
    }



    private class CoursesAdapter extends RecyclerView.Adapter<CoursesViewHolder> {
        @Override
        public CoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.coarses_row,parent,false);
            CoursesViewHolder coursesViewHolder=new CoursesViewHolder(view);
            return coursesViewHolder;

        }

        @Override
        public void onBindViewHolder(CoursesViewHolder holder, final int position) {

            holder.title.setText(listses.get(position).getCourse_title());
            holder.courseCode.setText(listses.get(position).getCourse_code());
            holder.facutlty.setText(listses.get(position).getFaculty());
            holder.slot.setText(listses.get(position).getSlot());

            holder.courses_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(DashBoardActivity.this,MainActivity.class);
                    intent.putExtra("courseCode",listses.get(position).getCourse_code());
                    intent.putExtra("slot",listses.get(position).getSlot());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return listses.size();
        }
    }

    private class CoursesViewHolder extends RecyclerView.ViewHolder {

        TextView facutlty,slot,courseCode,title,tv1,tv2,tv3;
        CardView courses_container;

        public CoursesViewHolder(View itemView) {
            super(itemView);
            this.facutlty=(TextView) itemView.findViewById(R.id.faculty);
            this.slot=(TextView) itemView.findViewById(R.id.slots);
            this.courseCode=(TextView) itemView.findViewById(R.id.course_code);
            this.title=(TextView) itemView.findViewById(R.id.course_title);
            this.tv1=(TextView) itemView.findViewById(R.id.tv1);
            this.tv2=(TextView) itemView.findViewById(R.id.tv2);
            this.tv3=(TextView) itemView.findViewById(R.id.tv3);
            this.courses_container=(CardView) itemView.findViewById(R.id.courses_container);
            slot.setSelected(true);
            this.slot.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            setfonts();
        }


        private void setfonts()
        {
            slot.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,DashBoardActivity.this));
            courseCode.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,DashBoardActivity.this));
            facutlty.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,DashBoardActivity.this));
            title.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,DashBoardActivity.this));
            tv1.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,DashBoardActivity.this));
            tv2.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,DashBoardActivity.this));
            tv3.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,DashBoardActivity.this));



        }
    }
}

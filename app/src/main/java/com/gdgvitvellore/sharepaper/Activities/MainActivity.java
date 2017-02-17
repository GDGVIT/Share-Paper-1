package com.gdgvitvellore.sharepaper.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgvitvellore.sharepaper.API.ConnectAPI;
import com.gdgvitvellore.sharepaper.Actors.ViewInfo;
import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.Utils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConnectAPI.ServerAuthenticateListener {

    FloatingActionButton addImage;
    Bitmap bitmap;
    ConnectAPI connectAPI;
    public static final int PICK_IMAGE_REQUEST = 1;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    List<ViewInfo.Paper> papers=new ArrayList<>();
    int size;
    String IMG_BASE_URL="http://share-paper.cloudapp.net:1337";
    TextView tv1,tv2;
    String couseCode,slot;
    ImageView noImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        init();
        getdata();
        setfonts();
        setinit();

    }

    private void init() {

        addImage = (FloatingActionButton) findViewById(R.id.add_image);
        recyclerView=(RecyclerView)findViewById(R.id.root_recyclerview_data);
        connectAPI=new ConnectAPI(this);
        progressDialog=new ProgressDialog(this);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        noImage=(ImageView)findViewById(R.id.noimage);

    }

    private void getdata()
    {
        couseCode=getIntent().getExtras().getString("courseCode");
        slot=getIntent().getExtras().getString("slot");
    }



    private void setfonts()
    {
        tv1.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,this));
        tv2.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,this));
    }

    private void setinit() {

        connectAPI.setServerAuthenticateListener(this);

        connectAPI.displayContent(couseCode);

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog.setMessage("Fetching papers..");

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,UploadPapersActivity.class);

                intent.putExtra("courseCode",couseCode);

                intent.putExtra("slot",slot);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MainActivity.this, addImage,"trans");
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onRequestInitiated(int code) {

        progressDialog.show();
    }

    @Override
    public void onRequestCompleted(int code, Object result) {

        if (code==ConnectAPI.VIEW_CONTENT_CODE)
        {

            Log.v("called","call");

            ViewInfo viewInfo=(ViewInfo) result;
            if (viewInfo.getStatus()==105)
            {
                noImage.setVisibility(View.GONE);
                papers=viewInfo.getPaper();

                recyclerView.setAdapter(new RootInfoAdapter());
            }

            if (viewInfo.getStatus()==104)
            {

                noImage.setVisibility(View.VISIBLE);
            }

            progressDialog.dismiss();

        }
    }

    @Override
    public void onRequestError(int code, String message) {


        progressDialog.dismiss();
        Toast.makeText(MainActivity.this,"Server error",Toast.LENGTH_SHORT).show();

    }

    private class RootInfoAdapter extends RecyclerView.Adapter<RootInfoviewHolder> {
        @Override
        public RootInfoviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.root_content_view_row,parent,false);
            RootInfoviewHolder rootInfoviewHolder=new RootInfoviewHolder(view);
            return rootInfoviewHolder;

        }

        @Override
        public void onBindViewHolder(RootInfoviewHolder holder, final int position) {

            holder.slot.setText(papers.get(position).getSlot());
            holder.course_code.setText(papers.get(position).getC_cd());
            holder.regno.setText(papers.get(position).getRegno());
            holder.image_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
            holder.image_recyclerView.setAdapter(new ImageAdapter(position));


        }

        @Override
        public int getItemCount() {
            return papers.size();
        }

        private class ImageAdapter extends RecyclerView.Adapter<ImageViewHolders> {
            int position1;

            public ImageAdapter(int position) {
                this.position1=position;
            }

            @Override
            public ImageViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_row,parent,false);
                ImageViewHolders imageViewHolders=new ImageViewHolders(view);
                return imageViewHolders;
            }

            @Override
            public void onBindViewHolder(ImageViewHolders holder, int position) {

                try {

                    Picasso.with(MainActivity.this).load(IMG_BASE_URL + papers.get(position1).getImg_arr().get(position)).fit().into(holder.image_question);


                    holder.image_question.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(MainActivity.this,DetailPaperActivity.class);
                            intent.putExtra("images", (Serializable) papers.get(position1).getImg_arr());
                            startActivity(intent);
                        }
                    });

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public int getItemCount() {
                return Integer.parseInt(papers.get(position1).getNo_of_images());
            }
        }

        private class ImageViewHolders extends RecyclerView.ViewHolder {

            ImageView image_question;

            public ImageViewHolders(View itemView) {
                super(itemView);
                this.image_question=(ImageView) itemView.findViewById(R.id.selected_image);
            }
        }
    }

    private class RootInfoviewHolder extends RecyclerView.ViewHolder {

        TextView slot,course_code,sem,tv1,tv2,regno;
        RecyclerView image_recyclerView;
        Button detail;

        public RootInfoviewHolder(View itemView) {
            super(itemView);
            this.slot=(TextView)itemView.findViewById(R.id.slot);
            this.course_code=(TextView) itemView.findViewById(R.id.course_code);
            this.image_recyclerView=(RecyclerView) itemView.findViewById(R.id.image_recycler_view);
            this.sem=(TextView) itemView.findViewById(R.id.sem);
            this.tv1=(TextView) itemView.findViewById(R.id.tv1);
            this.tv2=(TextView) itemView.findViewById(R.id.tv2);
            this.regno=(TextView) itemView.findViewById(R.id.regno);



            setFonts();
        }

        private void setFonts()
        {
            slot.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,MainActivity.this));
            course_code.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,MainActivity.this));
            sem.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,MainActivity.this));
            tv1.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,MainActivity.this));
            tv2.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,MainActivity.this));
            regno.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,MainActivity.this));

        }
    }
}

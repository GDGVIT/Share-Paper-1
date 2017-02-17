package com.gdgvitvellore.sharepaper.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgvitvellore.sharepaper.API.ConnectAPI;
import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.Utils;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkishorevs on 23/01/17.
 */

public class UploadPapersActivity extends AppCompatActivity implements ConnectAPI.ServerAuthenticateListener {

    private static final int CAMERA_CODE = 0;
    private static final int EXTERNAL_CODE=1;
    RecyclerView recyclerView;
    Bitmap bitmap;
    ArrayList<ImageEntry> imageEntries;
    FloatingActionButton done;
    Dialog dialog;
    ConnectAPI connectAPI;
    List<String> path=new ArrayList<>();
    ProgressDialog progressDialog;
    TextView tv1,tv2;
    ImageView uploadImage;
    String slot,courseCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_paper_activity);
      //  loadImagesIntent();
        init();
        getCameraPermission();
        //getStoragePermission();
        getData();
        setfonts();
        setInit();
    }

    private void init()
    {
        recyclerView=(RecyclerView)findViewById(R.id.images_recycler_view);
        done=(FloatingActionButton)findViewById(R.id.done);
        dialog=new Dialog(this);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        progressDialog=new ProgressDialog(this);
        connectAPI=new ConnectAPI(this);
        uploadImage=(ImageView)findViewById(R.id.upload_image);
    }


    private void getData()
    {
        slot=getIntent().getExtras().getString("slot");
        courseCode=getIntent().getExtras().getString("courseCode");
    }


    private void setfonts()
    {
        tv1.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,this));
        tv2.setTypeface(new Utils().getFontType(Utils.NORMAL_FONT,this));
    }

    private void setInit()
    {

        progressDialog.setMessage("Uploading please wait..");
        connectAPI.setServerAuthenticateListener(this);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagesIntent();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

     /*   done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setTitle("Enter details");
                dialog.setContentView(R.layout.custom_dialog_details);
                dialog.show();
                final EditText course_code,course_title,slot;
                Button upload;

                course_code=(EditText)dialog.findViewById(R.id.course_code);
                course_title=(EditText)dialog.findViewById(R.id.course_title);
                slot=(EditText) dialog.findViewById(R.id.slot);
                upload=(Button)dialog.findViewById(R.id.upload_btn);

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(course_code.getText().toString())&&!TextUtils.isEmpty(course_title.getText().toString())&&!TextUtils.isEmpty(slot.getText().toString()))
                        {


                            connectAPI.uploadContent(course_code.getText().toString(),slot.getText().toString(),path);

                        }
                        else
                        {
                            Toast.makeText(UploadPapersActivity.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });  */


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!path.isEmpty()) {
                    connectAPI.uploadContent(courseCode, slot, path);
                }

                else
                {
                    Toast.makeText(UploadPapersActivity.this,"No question paper images selected",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void loadImagesIntent()
    {
        new Picker.Builder(this,new MyPickListener(),R.style.MIP_theme)
                .build()
                .startActivity();
    }

    @Override
    public void onRequestInitiated(int code) {

       // progressDialog.show();
        Log.v("init","init_"+code);

        Toast.makeText(UploadPapersActivity.this,"Upload on progress..",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestCompleted(int code, Object result) {


       // progressDialog.dismiss();
    }

    @Override
    public void onRequestError(int code, String message) {

        Log.v("error",message);

        progressDialog.dismiss();
    }

    private void getCameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_CODE);
        }
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
            case CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }

                break;

            case EXTERNAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                }
        }
    }

    private class MyPickListener implements Picker.PickListener {

        @Override
        public void onPickedSuccessfully(final ArrayList<ImageEntry> images) {

            imageEntries=images;

            try {


                if (!imageEntries.isEmpty())
                {
                    uploadImage.setVisibility(View.GONE);
                }

                for (int i=0;i<imageEntries.size();i++) {

                    Bitmap bitmap = new Utils().decodeSampledBitmapFromResource(imageEntries.get(i).path, 1280, 720);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    //compress the photo's bytes into the byte array output stream
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                    //construct a File object to save the scaled file to
                    File f = new File(Environment.getExternalStorageDirectory() + File.separator + i + ".jpg");

                    Log.v("ok", "ok");
                    //create the file
                    f.createNewFile();

                    //create an FileOutputStream on the created file
                    FileOutputStream fo = new FileOutputStream(f);
                    //write the photo's bytes to the file
                    fo.write(bytes.toByteArray());


                    Log.v("path",f.getAbsolutePath());

                    path.add(f.getAbsolutePath());
                    //finish by closing the FileOutputStream
                    fo.close();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }



            recyclerView.setAdapter(new UploadPaperAdapter());


        }

        @Override
        public void onCancel() {
        }
    }

    private class UploadPaperAdapter extends RecyclerView.Adapter<UploadPaperViewHolder> {
        @Override
        public UploadPaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_row,parent,false);
            UploadPaperViewHolder uploadPaperViewHolder=new UploadPaperViewHolder(view);
            return uploadPaperViewHolder;
        }

        @Override
        public void onBindViewHolder(UploadPaperViewHolder holder, int position) {

            Log.v("image",imageEntries.get(position).path);
            try {

               Bitmap bitmap = new Utils().decodeSampledBitmapFromResource(imageEntries.get(position).path,100,100);




                holder.image.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return imageEntries.size();
        }




    }

    private class UploadPaperViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public UploadPaperViewHolder(View itemView) {
            super(itemView);
            this.image=(ImageView)itemView.findViewById(R.id.selected_image);
        }
    }
}

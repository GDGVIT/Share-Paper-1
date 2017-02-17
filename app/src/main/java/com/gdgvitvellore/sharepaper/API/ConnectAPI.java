package com.gdgvitvellore.sharepaper.API;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gdgvitvellore.sharepaper.Activities.MainActivity;
import com.gdgvitvellore.sharepaper.Actors.Coarses;
import com.gdgvitvellore.sharepaper.Actors.Login;
import com.gdgvitvellore.sharepaper.Actors.Refresh;
import com.gdgvitvellore.sharepaper.Actors.ViewInfo;
import com.gdgvitvellore.sharepaper.AppController;
import com.gdgvitvellore.sharepaper.ErrorDefenitions;
import com.gdgvitvellore.sharepaper.Handlers.DataHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;
import net.yazeed44.imagepicker.model.ImageEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by ramkishorevs on 25/01/17.
 */

public class ConnectAPI {


    ServerAuthenticateListener serverAuthenticateListener;

    int socketTimeout = 900000000;


    Context context;

    public static int UPLOAD_CONTENT_CODE=1;
    public static int VIEW_CONTENT_CODE=2;
    public static int LOGIN_CODE=3;
    public static int REFRESH_CODE=4;

    Realm realm;



    public ConnectAPI(Context context)
    {
        this.context=context;
        realm=Realm.getDefaultInstance();
    }

    public void uploadContent(final String course_code, final String slot, List<String> images) {
        if (serverAuthenticateListener != null) {


            serverAuthenticateListener.onRequestInitiated(UPLOAD_CONTENT_CODE);

            String uploadId = UUID.randomUUID().toString();


            try {
            MultipartUploadRequest multipart = new MultipartUploadRequest(context,APIConstants.UPLOAD_URL);




                       for (int i=0;i<images.size();i++)
                       {
                           multipart.addFileToUpload(images.get(i),"image");
                       }


               // multipart.setMaxRetries(3);


                multipart.addParameter("regno",DataHandler.getInstance(context).getPreference("regno","NA"));
                       multipart.addParameter("courseCode", course_code);
                       multipart.addParameter("slot",slot);
                       multipart.addParameter("noOfImages", String.valueOf(images.size()));
                       multipart.addParameter("sem","winter");
                       multipart.addParameter("year","2017");
                       multipart.setNotificationConfig(new UploadNotificationConfig());
                       multipart.setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(UploadInfo uploadInfo) {
                                serverAuthenticateListener.onRequestInitiated(UPLOAD_CONTENT_CODE);
                                Log.v("pok","pok");

                            }

                            @Override
                            public void onError(UploadInfo uploadInfo, Exception exception) {

                                Log.v("error",exception.getMessage());
                                serverAuthenticateListener.onRequestError(UPLOAD_CONTENT_CODE,ErrorDefenitions.ERROR_RESPONSE_INVALID);
                                Log.v("perror","perr");

                            }

                            @Override
                            public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {

                                Log.v("pover","over");
                                Gson gson=new Gson();
                                Log.v("ress",serverResponse.getBodyAsString());
                                com.gdgvitvellore.sharepaper.Actors.UploadInfo uploadInf= gson.fromJson(serverResponse.getBodyAsString(), com.gdgvitvellore.sharepaper.Actors.UploadInfo.class);
                                if (uploadInf.getStatus()==100)
                                Toast.makeText(context,"Please try again later images not uploaded..",Toast.LENGTH_SHORT).show();
                                else
                                {
                                    Toast.makeText(context,"Uploading finished..",Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(UploadInfo uploadInfo) {

                                Log.v("pcancel","pcancel");
                            }
                        });
                       multipart.startUpload();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

    }



    public void displayContent(String course_code)
    {
        if (serverAuthenticateListener != null) {


            serverAuthenticateListener.onRequestInitiated(VIEW_CONTENT_CODE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIConstants.VIEW_URL + course_code+"&sem=winter&year=2017", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.v("TAG", response);
                    if (!validateResponse(response.toString())) {
                        serverAuthenticateListener.onRequestError(VIEW_CONTENT_CODE, ErrorDefenitions.ERROR_RESPONSE_NULL);
                    } else {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        ViewInfo viewInfo=gson.fromJson(response,ViewInfo.class);
                        serverAuthenticateListener.onRequestCompleted(VIEW_CONTENT_CODE,viewInfo);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    serverAuthenticateListener.onRequestError(VIEW_CONTENT_CODE, ErrorDefenitions.ERROR_RESPONSE_INVALID);

                }
            }) ;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);


            AppController.getInstance().addToRequestQueue(stringRequest);

        }
    }


    public void login(final String regno, final String password)
    {

        if (serverAuthenticateListener!=null) {
            serverAuthenticateListener.onRequestInitiated(LOGIN_CODE);

            StringRequest postRequest = new StringRequest(Request.Method.POST, APIConstants.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.v("res", response);
                            Gson gson=new Gson();
                            Login login=gson.fromJson(response,Login.class);
                            serverAuthenticateListener.onRequestCompleted(LOGIN_CODE,login);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    serverAuthenticateListener.onRequestCompleted(LOGIN_CODE, ErrorDefenitions.ERROR_RESPONSE_INVALID);

                }
            }) {
                @Override
                protected Map<String, String> getParams() {


                    Map<String, String> params = new HashMap<String, String>();
                    params.put("regNo", regno);
                    params.put("psswd", password);
                    return params;
                }
            };

            // Adding request to request queue
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            AppController.getInstance().addToRequestQueue(postRequest);
        }
    }


    public void refresh(final String regno, final String password)
    {

        if (serverAuthenticateListener!=null) {
            serverAuthenticateListener.onRequestInitiated(REFRESH_CODE);

            StringRequest postRequest = new StringRequest(Request.Method.POST, APIConstants.REFRESH_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {


                            Log.v("res", response);
                            Gson gson=new Gson();
                            Log.v("test","test1");
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Refresh refresh= realm.createObjectFromJson(Refresh.class,response);
                                    Log.v("test","test2");
                                    serverAuthenticateListener.onRequestCompleted(REFRESH_CODE,refresh);

                                }
                            });




                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    serverAuthenticateListener.onRequestCompleted(REFRESH_CODE, ErrorDefenitions.ERROR_RESPONSE_INVALID);

                }
            }) {
                @Override
                protected Map<String, String> getParams() {


                    Map<String, String> params = new HashMap<String, String>();
                    params.put("regNo", regno);
                    params.put("psswd", password);
                    return params;
                }
            };

            // Adding request to request queue
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            AppController.getInstance().addToRequestQueue(postRequest);
        }
    }





    private boolean validateResponse(String response) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        try {
            JSONObject j = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    public void setServerAuthenticateListener(ServerAuthenticateListener listener) {
        serverAuthenticateListener = listener;
    }





    public interface ServerAuthenticateListener {

        /**
         * Called when the network request starts.
         *
         * @param code Event code which specifies, call to which API has been made.
         */
        void onRequestInitiated(int code);

        /**
         * Called when the request is successfully completed and returns the validated response.
         *
         * @param code   Event code which specifies, call to which API has been made.
         * @param result Result Object which needs to be casted to specific class as required
         */
        void onRequestCompleted(int code, Object result);

        /**
         * Called when unexpected error occurs.
         *
         * @param code    Event code which specifies, call to which API has been made.
         * @param message Error description
         */
        void onRequestError(int code, String message);

    }
}

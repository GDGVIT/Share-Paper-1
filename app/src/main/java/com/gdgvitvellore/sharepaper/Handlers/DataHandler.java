package com.gdgvitvellore.sharepaper.Handlers;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.gdgvitvellore.sharepaper.Actors.Coarses;
import com.gdgvitvellore.sharepaper.Actors.Refresh;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by VSRK on 10/27/2016.
 */

public class DataHandler {

    private static final String TAG = DataHandler.class.getSimpleName();
    //Singleton reference
    public static DataHandler myInstance;

    private Context mContext;
    private SharedPreferences mPreferences;

    private Realm realm;

    /**
     * Method to retrieve the singleton reference of this class
     *
     * @param context The context reference passed from the calling class
     * @return Returns static reference of {@link DataHandler} class
     */

    public static DataHandler getInstance(Context context) {
        if (myInstance == null) {
            myInstance = new DataHandler(context);
        }
        return myInstance;
    }

    /**
     * Private constructor. This class cannot be instantiated outside this class.
     * All class attributes should be initialised here
     *
     * @param context The context reference passed while instantiating
     */

    public DataHandler(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        realm = Realm.getDefaultInstance();
    }

    /**
     * Use this method to save {@link String} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value String value to store
     */

    public void savePreference(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }


    /**
     * Use this method to retrieve {@link String} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default String value to fetch
     * @return Returns String value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public String getPreference(String key, String def) {

        return mPreferences.getString(key, def);
    }

    /**
     * Use this method to save {@link boolean} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value boolean value to store
     */

    public void savePreference(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * Use this method to retrieve {@link boolean} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default boolean value to fetch
     * @return Returns boolean value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public boolean getPreference(String key, boolean def) {

        return mPreferences.getBoolean(key, def);
    }

    /**
     * Use this method to save {@link int} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value int value to store
     */
    public void savePreference(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }

    /**
     * Use this method to retrieve {@link int} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default int value to fetch
     * @return Returns int value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public int getPreference(String key, int def) {

        return mPreferences.getInt(key, def);

    }

    /**
     * Use this method to save {@link long} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value int value to store
     */
    public void savePreference(String key, long value) {
        mPreferences.edit().putLong(key, value).commit();
    }

    /**
     * Use this method to retrieve {@link long} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default long value to fetch
     * @return Returns int value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public long getPreference(String key, long def) {

        return mPreferences.getLong(key, def);

    }


    /**
     * Use this method to save {@link HashSet<String>} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value {@link HashSet<String>} value to store
     */

    public void savePreference(String key, HashSet<String> value) {
        mPreferences.edit().putStringSet(key, value).commit();
    }


    /**
     * Use this method to retrieve {@link HashSet} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default {@link HashSet<String>} value to fetch
     * @return Returns {@link HashSet<String>} value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public HashSet<String> getPreference(String key, HashSet<String> def) {

        return (HashSet<String>) mPreferences.getStringSet(key, def);

    }

    /**
     * Use this method to change whether user opened the app for first time or not.
     *
     * @param isFirstTimeUser pass true to set first time user and false if he has already been t the app before
     */
    public void saveFirstTimeUser(boolean isFirstTimeUser) {
        savePreference("firstTimeUser", isFirstTimeUser);
    }

    /**
     * Use this method to know whether user is a first time user or not
     *
     * @return Returns true if yes else false
     * If value doesn't exist, returns true.
     */
    public boolean isFirstTimeUser() {

        return getPreference("firstTimeUser", true);
    }



 /*   public void saveUserEntry(Refresh coarses)
    {

            Log.v("caled","Saved");
            realm.beginTransaction();
            realm.copyToRealm(coarses);
            realm.commitTransaction();
            Log.v("saved","Saved");
            getUserEntry();

    }

*/
    public List<Refresh> getUserEntry()
    {
        List<Refresh> list=null;
        RealmResults<Refresh> realmList=realm.where(Refresh.class).findAll();
        if (realmList!=null && realmList.size()>0)
        {
            list=new ArrayList<>();
            Iterator<Refresh> iterator=realmList.iterator();
            while (iterator.hasNext())
            {
                list.add(iterator.next());
            }


            Log.v("list",list.get(0).getCourses().get(0).getCourse_title());
            return list;

        }

        return null;
    }


    public void logout()
    {
        mPreferences.edit().clear().commit();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.clear(Refresh.class);
            }
        });
    }


}
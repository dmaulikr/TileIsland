package com.example.android.tileisland;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.android.tileisland.R.id.score;
import static java.lang.Integer.parseInt;


public class ActivityTracker {

    // Shared Preferences reference
    SharedPreferences sharedPreferences;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static Integer count = 1;

    // Sharedpref file name
    private static final String SHARED_ACTIVITY_FILENAME = "KidsReport";

    // Constructor
    public ActivityTracker(Context context, String userName) {
        this.context = context;
        Toast.makeText(context, "Activity tracker: " + userName, Toast.LENGTH_SHORT).show();
        sharedPreferences = context.getSharedPreferences(SHARED_ACTIVITY_FILENAME + "_" + userName, Context.MODE_APPEND);
        editor = sharedPreferences.edit();

    }

    public void updateActivity(String stage, String score) {
        //Using Shared Preference
        editor.putString(stage + "_attempt" + ActivityTracker.count++, score);
        editor.commit();
    }

    /*
     * Get stored session data
     */
    public static ArrayList<String> getAllScores(Context context, String userName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_ACTIVITY_FILENAME + "_" + userName,
                Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        ArrayList<String> lst = new ArrayList<>();

        for (String str : map.keySet()) {
            lst.add((String) map.get(str));
        }

        return lst;
    }

    public static ArrayList<String> getAllKidsScores(Context context,String filname) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(filname,
                Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        ArrayList<String> lst = new ArrayList<>();

        for (String str : map.keySet()) {
            lst.add((String) map.get(str));
        }

        return lst;
    }
}

package com.example.android.tileisland;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.key;

public class LeaderBoard extends AppCompatActivity {

    //User session management class
    UserSessionManagement session;

    //Activty tracker class
    ActivityTracker activityTracker;

    private String logInKid;
    private TextView getLeaderBoard;
    StringBuilder builder = new StringBuilder();
    HashMap<String, Integer> leaderBoardData = new HashMap<String, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        // Session class instance
        session = new UserSessionManagement(getApplicationContext());
        logInKid = session.getUserDetails();

        getLeaderBoard = (TextView) findViewById(R.id.get_leader_board_textView);

        File prefsdir = new File(getApplicationInfo().dataDir,"shared_prefs");

        if(prefsdir.exists() && prefsdir.isDirectory()){
            String[] list = prefsdir.list();
            for (String filename : list) {
                Integer obtainedScore=0;
                Integer totalScore=0;
                String fName = filename.split(".xml")[0];
                //Toast.makeText(getApplicationContext(), fName, Toast.LENGTH_SHORT).show();
                if (fName.contains("_")) {
                    ArrayList<String> activity = ActivityTracker.getAllKidsScores(getApplicationContext(),fName);
                    for (String score : activity) {
                        obtainedScore = Integer.parseInt(score);
                        totalScore += obtainedScore;
                    }
                    String playerName = fName.split("_")[1];
                    //Toast.makeText(getApplicationContext(), totalScore.toString(), Toast.LENGTH_SHORT).show();
                    leaderBoardData.put(playerName,totalScore);
                }
            }
        }

        getLeaderBoard();
    }

    private void getLeaderBoard() {
        StringBuilder builder = new StringBuilder();
        //Toast.makeText(getApplicationContext(), "in get leaderboard", Toast.LENGTH_SHORT).show();
        builder.append(padRight("PlayerName", 20)+"Score");
        builder.append("\n");
        for(String key: leaderBoardData.keySet()){
            //Toast.makeText(getApplicationContext(), padRight(key, 30)+leaderBoardData.get(key).toString(), Toast.LENGTH_SHORT).show();
            builder.append(padRight(key, 30)+leaderBoardData.get(key).toString());
            builder.append("\n");
        }
        getLeaderBoard.setText(builder.toString());
    }

    public static String padRight(String s,int n){
        return String.format("%1$-" + n + "s",s);
    }

    public static String padLeft( String s,int n){
        return String.format("%1$" + n + "s",s);
    }
}

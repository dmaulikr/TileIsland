package com.example.android.tileisland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeLevelTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_level_two);
    }

    public void onPlayClick(View view) {
        Intent intent = new Intent(this,LevelTwoStages.class);
        startActivity(intent);
    }
}

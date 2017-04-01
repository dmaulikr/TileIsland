package com.example.android.tileisland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeLevelOne extends AppCompatActivity {

    //TODO:add back button and functionality

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_level_one);
    }

    public void onPlayClick(View view) {
        Intent intent = new Intent(this,LevelOneStages.class);
        startActivity(intent);
    }
}

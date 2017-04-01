package com.example.android.tileisland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelOneStages extends AppCompatActivity {

    //TODO:add back button and functionality

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one_stages);
    }

    public void onLevelOneStageOneClick(View view) {
        Intent intent = new Intent(this,LevelOneStageOne.class);
        startActivity(intent);
    }

    public void onLevelOneStageTwoClick(View view) {
        Intent intent = new Intent(this,LevelOneStageTwo.class);
        startActivity(intent);
    }

    public void onLevelOneStageThreeClick(View view) {
        Intent intent = new Intent(this,LevelOneStageThree.class);
        startActivity(intent);
    }
}

package com.example.android.tileisland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelTwoStages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two_stages);
    }

    public void onLevelTwoStageOneClick(View view) {
        Intent intent = new Intent(this,LevelTwoStageOne.class);
        startActivity(intent);
    }

    public void onLevelTwoStageTwoClick(View view) {
        Intent intent = new Intent(this,LevelTwoStageTwo.class);
        startActivity(intent);
    }

    public void onLevelTwoStageThreeClick(View view) {
        Intent intent = new Intent(this,LevelTwoStageThree.class);
        startActivity(intent);
    }
}

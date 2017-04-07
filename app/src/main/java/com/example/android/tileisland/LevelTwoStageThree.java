/*
 * This is the level two stage three of the game.
 **/
package com.example.android.tileisland;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.animation;


public class LevelTwoStageThree extends AppCompatActivity {


    StartDraggingLsntr myStartDraggingLsntr;
    EndDraggingLsntr myEndDraggingLsntr;
    Button playButton;
    ImageView player;
    MediaPlayer waves;
    AnimatorSet set;
    TextView coinsCollected, totalScore, extraScore;
    int score;
    int coins;
    int extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two_stage_three);

        //listeners for drag and drop
        myStartDraggingLsntr = new StartDraggingLsntr();
        myEndDraggingLsntr = new EndDraggingLsntr();

        //buttons binding to the activity
        player = (ImageView) findViewById(R.id.player);
        playButton = (Button) findViewById(R.id.playButton);
        findViewById(R.id.playButton).setOnLongClickListener(myStartDraggingLsntr);
        findViewById(R.id.moveUp).setOnLongClickListener(myStartDraggingLsntr);
        findViewById(R.id.moveDown).setOnLongClickListener(myStartDraggingLsntr);
        findViewById(R.id.moveLeft).setOnLongClickListener(myStartDraggingLsntr);
        findViewById(R.id.moveRight).setOnLongClickListener(myStartDraggingLsntr);

        // selected moves
        findViewById(R.id.firstAction).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.secondAction).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.thirdAction).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.fourthAction).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.fifthAction).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.sixthAction).setOnDragListener(myEndDraggingLsntr);

        player.bringToFront();

        //updating score as per the coins are collected
        score = 0;
        coins = 0;
        extra = 0;
        coinsCollected = (TextView) findViewById(R.id.coinsCollected);
        totalScore = (TextView) findViewById(R.id.score);
        extraScore = (TextView) findViewById(R.id.extraPoints_score);
        updateScore();

        //initializing music for coin pickup
        waves = MediaPlayer.create(LevelTwoStageThree.this, R.raw.coinpickup);

    }

    //updating score simultaneously coins are collected
    private void updateScore() {
        score = coins * 1000 + extra * 2000;
        coinsCollected.setText(Integer.toString(coins));
        totalScore.setText(Integer.toString(score));
        extraScore.setText(Integer.toString(extra));
    }

    //navigates to previous activity
    public void onBackClick(View view) {
        Intent intent = new Intent(this, LevelOneStages.class);
        startActivity(intent);
    }

    //check the selected move by the player and return the list
    public ArrayList<String> getActionSequenceOne() {

        String moveOne = (String) findViewById(R.id.firstAction).getContentDescription();
        String moveTwo = (String) findViewById(R.id.secondAction).getContentDescription();

        ArrayList<String> actionSequenceOne = new ArrayList<String>();
        actionSequenceOne.add(moveOne);
        actionSequenceOne.add(moveTwo);
        return actionSequenceOne;
    }

    //check the selected move by the player and return the list
    public ArrayList<String> getActionSequenceTwo() {

        String moveOne = (String) findViewById(R.id.firstAction).getContentDescription();
        String moveTwo = (String) findViewById(R.id.secondAction).getContentDescription();
        String moveThree = (String) findViewById(R.id.thirdAction).getContentDescription();

        ArrayList<String> actionSequenceTwo = new ArrayList<String>();
        actionSequenceTwo.add(moveOne);
        actionSequenceTwo.add(moveTwo);
        actionSequenceTwo.add(moveThree);
        return actionSequenceTwo;
    }

    //check the selected move by the player and return the list
    public ArrayList<String> getActionSequenceThree() {

        String moveOne = (String) findViewById(R.id.firstAction).getContentDescription();
        String moveTwo = (String) findViewById(R.id.secondAction).getContentDescription();
        String moveThree = (String) findViewById(R.id.thirdAction).getContentDescription();
        String moveFour = (String) findViewById(R.id.fourthAction).getContentDescription();

        ArrayList<String> actionSequenceThree = new ArrayList<String>();
        actionSequenceThree.add(moveOne);
        actionSequenceThree.add(moveTwo);
        actionSequenceThree.add(moveThree);
        actionSequenceThree.add(moveFour);
        return actionSequenceThree;
    }

    //check the selected move by the player and return the list
    public ArrayList<String> getActionSequenceFour() {

        String moveOne = (String) findViewById(R.id.firstAction).getContentDescription();
        String moveTwo = (String) findViewById(R.id.secondAction).getContentDescription();
        String moveThree = (String) findViewById(R.id.thirdAction).getContentDescription();
        String moveFour = (String) findViewById(R.id.fourthAction).getContentDescription();
        String moveFive = (String) findViewById(R.id.fifthAction).getContentDescription();

        ArrayList<String> actionSequenceFour= new ArrayList<String>();
        actionSequenceFour.add(moveOne);
        actionSequenceFour.add(moveTwo);
        actionSequenceFour.add(moveThree);
        actionSequenceFour.add(moveFour);
        actionSequenceFour.add(moveFive);
        return actionSequenceFour;
    }

    // animates the player as per the selected moves
    public void onPlayClick(View view) {

        ArrayList<String> actionSequenceOne = getActionSequenceOne();
        //required sequence defined in the list
        ArrayList<String> requiredActionSequenceOne = new ArrayList<String>();
        requiredActionSequenceOne.add("down");
        requiredActionSequenceOne.add("right");

        ArrayList<String> actionSequenceTwo = getActionSequenceTwo();
        //required sequence defined in the list
        ArrayList<String> requiredActionSequenceTwo = new ArrayList<String>();
        requiredActionSequenceTwo.add("right");
        requiredActionSequenceTwo.add("down");
        requiredActionSequenceTwo.add("right");

        ArrayList<String> actionSequenceThree = getActionSequenceThree();
        //required sequence defined in the list
        ArrayList<String> requiredActionSequenceThree = new ArrayList<String>();
        requiredActionSequenceThree.add("down");
        requiredActionSequenceThree.add("right");
        requiredActionSequenceThree.add("down");
        requiredActionSequenceThree.add("right");

        ArrayList<String> actionSequenceFour = getActionSequenceFour();
        //required sequence defined in the list
        ArrayList<String> requiredActionSequenceFour = new ArrayList<String>();
        requiredActionSequenceFour.add("right");
        requiredActionSequenceFour.add("down");
        requiredActionSequenceFour.add("left");
        requiredActionSequenceFour.add("down");
        requiredActionSequenceFour.add("right");

        // checks if the required and selected sequence and move the player accordingly
        if(requiredActionSequenceFour.equals(actionSequenceFour)){
            ObjectAnimator actionOneAnimation = ObjectAnimator.ofFloat(player, "translationX", 0f, 950f);
            ObjectAnimator actionTwoAnimation = ObjectAnimator.ofFloat(player, "translationY", 0f, 275f);
            ObjectAnimator actionThreeAnimation = ObjectAnimator.ofFloat(player, "translationX", 950f, 0f);
            ObjectAnimator actionFourAnimation = ObjectAnimator.ofFloat(player, "translationY", 275f, 550f);
            ObjectAnimator actionFiveAnimation = ObjectAnimator.ofFloat(player, "translationX", 0f, 1300f);
            set = new AnimatorSet();
            set.setDuration(4000);
            //animating all moves simultaneously
            set.playSequentially(actionOneAnimation,
                   actionTwoAnimation,
                    actionThreeAnimation,
                    actionFourAnimation,
                    actionFiveAnimation);
            set.start();

            actionOneAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition >= 280) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin1).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 1;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 400) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin2).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 2;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 870) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin3).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 3;
                        updateScore();
                    }
                }
            });

            actionThreeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition <= 600) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_extraPoints1).setVisibility(View.INVISIBLE);
                        //updating score
                        extra = 1;
                        updateScore();
                    }
                    if ((int) imageXPosition <= 500) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin4).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 4;
                        updateScore();
                    }
                    if ((int) imageXPosition <= 250) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_extraPoints2).setVisibility(View.INVISIBLE);
                        //updating score
                        extra = 2;
                        updateScore();
                    }
                }
            });

            actionFiveAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition >= 10) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_extraPoints3).setVisibility(View.INVISIBLE);
                        //updating score
                        extra = 3;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 250) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin5).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 5;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 550) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin6).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 6;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1000) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin7).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 7;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1300) {

                        // Dailouge for playing again the same level or next level
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(LevelTwoStageThree.this);
                        LayoutInflater factory = LayoutInflater.from(LevelTwoStageThree.this);
                        final View youwin = factory.inflate(R.layout.activity_winning, null);
                        alertadd.setView(youwin);
                        alertadd.setMessage("Score: " + score);
                        alertadd.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, LevelTwoStageThree.class);
                                startActivity(intent);
                            }
                        });
                        alertadd.setNegativeButton("End Game!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, WelcomeLevelOne.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alert = alertadd.create();
                        alert.show();
                        waves = MediaPlayer.create(LevelTwoStageThree.this, R.raw.gameover);
                        waves.start();

                    }
                }
            });



        }else if (requiredActionSequenceThree.equals(actionSequenceThree)) {

            ObjectAnimator actionOneAnimation = ObjectAnimator.ofFloat(player, "translationY", 0f, 275f);
            ObjectAnimator actionTwoAnimation = ObjectAnimator.ofFloat(player, "translationX", 0f, 950f);
            ObjectAnimator actionThreeAnimation = ObjectAnimator.ofFloat(player, "translationY", 275f, 550f);
            ObjectAnimator actionFourAnimation = ObjectAnimator.ofFloat(player, "translationX", 950f, 1280f);
            set = new AnimatorSet();
            set.setDuration(4000);
            //animating all moves simultaneously
            set.playSequentially(actionOneAnimation,
                    actionTwoAnimation,
                    actionThreeAnimation,
                    actionFourAnimation);
            set.start();

            actionTwoAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition >= 250) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_extraPoints2).setVisibility(View.INVISIBLE);
                        //updating score
                        extra = 1;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 500) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin4).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 1;
                        updateScore();
                    }

                    if ((int) imageXPosition >= 600) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_extraPoints1).setVisibility(View.INVISIBLE);
                        //updating score
                        extra = 2;
                        updateScore();
                    }
                }
            });

            actionFourAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition >= 1000) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin7).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 2;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1280) {

                        // Dailouge for playing again the same level or next level
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(LevelTwoStageThree.this);
                        LayoutInflater factory = LayoutInflater.from(LevelTwoStageThree.this);
                        final View youwin = factory.inflate(R.layout.activity_winning, null);
                        alertadd.setView(youwin);
                        alertadd.setMessage("Score: " + score);
                        alertadd.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, LevelTwoStageThree.class);
                                startActivity(intent);
                            }
                        });
                        alertadd.setNegativeButton("End Game!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, WelcomeLevelOne.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alert = alertadd.create();
                        alert.show();
                        waves = MediaPlayer.create(LevelTwoStageThree.this, R.raw.gameover);
                        waves.start();

                    }
                }
            });


        } else if (requiredActionSequenceTwo.equals(actionSequenceTwo)) {

            ObjectAnimator actionOneAnimation = ObjectAnimator.ofFloat(player, "translationX", 0f, 950f);
            ObjectAnimator actionTwoAnimation = ObjectAnimator.ofFloat(player, "translationY", 0f, 550f);
            ObjectAnimator actionThreeAnimation = ObjectAnimator.ofFloat(player, "translationX", 950f, 1380f);
            set = new AnimatorSet();
            set.setDuration(4000);
            //animating all moves simultaneously
            set.playSequentially(actionOneAnimation,
                    actionTwoAnimation,
                    actionThreeAnimation);
            set.start();

            actionOneAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition >= 280) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin1).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 1;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 400) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin2).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 2;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 870) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin3).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 3;
                        updateScore();
                    }
                }
            });
            actionThreeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition >= 1000) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin7).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 4;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1300) {

                        // Dailouge for playing again the same level or next level
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(LevelTwoStageThree.this);
                        LayoutInflater factory = LayoutInflater.from(LevelTwoStageThree.this);
                        final View youwin = factory.inflate(R.layout.activity_winning, null);
                        alertadd.setView(youwin);
                        alertadd.setMessage("Score: " + score);
                        alertadd.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, LevelTwoStageThree.class);
                                startActivity(intent);
                            }
                        });
                        alertadd.setNegativeButton("End Game!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, WelcomeLevelOne.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alert = alertadd.create();
                        alert.show();
                        waves = MediaPlayer.create(LevelTwoStageThree.this, R.raw.gameover);
                        waves.start();

                    }
                }
            });

        }else if(requiredActionSequenceOne.equals(actionSequenceOne)){

            ObjectAnimator actionOneAnimation = ObjectAnimator.ofFloat(player, "translationY", 0f, 550f);
            ObjectAnimator actionTwoAnimation = ObjectAnimator.ofFloat(player, "translationX", 150f, 1380f);
            set = new AnimatorSet();
            set.setDuration(4000);
            //animating all moves simultaneously
            set.playSequentially(actionOneAnimation,
                    actionTwoAnimation);
            set.start();

            actionTwoAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if ((int) imageXPosition >= 150) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_extraPoints3).setVisibility(View.INVISIBLE);
                        //updating score
                        extra = 1;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 250) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin5).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 1;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 550) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin6).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 2;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1000) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagethree_coin7).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 3;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1300) {

                        // Dailouge for playing again the same level or next level
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(LevelTwoStageThree.this);
                        LayoutInflater factory = LayoutInflater.from(LevelTwoStageThree.this);
                        final View youwin = factory.inflate(R.layout.activity_winning, null);
                        alertadd.setView(youwin);
                        alertadd.setMessage("Score: " + score);
                        alertadd.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, LevelTwoStageThree.class);
                                startActivity(intent);
                            }
                        });
                        alertadd.setNegativeButton("End Game!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageThree.this, WelcomeLevelOne.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alert = alertadd.create();
                        alert.show();
                        waves = MediaPlayer.create(LevelTwoStageThree.this, R.raw.gameover);
                        waves.start();

                    }
                }
            });

        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Please select correct sequence!");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }

    }

    // Restarts the same activity
    public void onStopClick(View view) {

        Intent intent = new Intent(this, LevelOneStageThree.class);
        startActivity(intent);

    }

    //game exit
    public void onExitClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    //start dragging lisntr
    private class StartDraggingLsntr implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            WithDraggingShadow shadow = new WithDraggingShadow(v);
            ClipData data = ClipData.newPlainText("", "");
            v.startDrag(data, shadow, v, 0);
            return false;
        }
    }

    //end dragging lisntr
    private class EndDraggingLsntr implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                ((Button) v).setBackground(((Button) event.getLocalState()).getBackground());
                ((Button) v).setContentDescription(((Button) event.getLocalState()).getContentDescription());
            }

            return true;
        }
    }

    //Bitmap image;
    private class WithDraggingShadow extends View.DragShadowBuilder {
        public WithDraggingShadow(View view) {
            super(view);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
        }
    }
}


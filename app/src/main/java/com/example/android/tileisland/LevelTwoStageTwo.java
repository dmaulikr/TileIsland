/*
 * This is the level two of the game.
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

public class LevelTwoStageTwo extends AppCompatActivity {


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
        setContentView(R.layout.activity_level_two_stage_two);

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

        player.bringToFront();

        //updating score as per the coins are collected
        score = 0;
        coins = 0;
        extra = 0;
        coinsCollected = (TextView) findViewById(R.id.coinsCollected);
        totalScore = (TextView) findViewById(R.id.score);
        extraScore = (TextView) findViewById(R.id.extrapoints_score);
        updateScore();

        //initializing music for coin pickup
        waves = MediaPlayer.create(LevelTwoStageTwo.this, R.raw.coinpickup);

    }

    //updating score simultaneously coins are collected
    private void updateScore() {
        score = coins * 1000 + extra * 5000;
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
        String moveThree = (String) findViewById(R.id.thirdAction).getContentDescription();
        String moveFour = (String) findViewById(R.id.fourthAction).getContentDescription();
        String moveFive = (String) findViewById(R.id.fifthAction).getContentDescription();

        ArrayList<String> actionSequence = new ArrayList<String>();
        actionSequence.add(moveOne);
        actionSequence.add(moveTwo);
        actionSequence.add(moveThree);
        actionSequence.add(moveFour);
        actionSequence.add(moveFive);
        return actionSequence;
    }

    //check the selected move by the player and return the list
    public ArrayList<String> getActionSequenceTwo() {

        String moveOne = (String) findViewById(R.id.firstAction).getContentDescription();
        String moveTwo = (String) findViewById(R.id.secondAction).getContentDescription();
        String moveThree = (String) findViewById(R.id.thirdAction).getContentDescription();

        ArrayList<String> actionSequence = new ArrayList<String>();
        actionSequence.add(moveOne);
        actionSequence.add(moveTwo);
        actionSequence.add(moveThree);
        return actionSequence;
    }

    // animates the player as per the selected moves
    public void onPlayClick(View view) {

        ArrayList<String> actionSequenceOne = getActionSequenceOne();
        ArrayList<String> actionSequenceTwo = getActionSequenceTwo();

        //required sequence one defined in the list
        ArrayList<String> requiredActionSequenceOne = new ArrayList<String>();
        requiredActionSequenceOne.add("right");
        requiredActionSequenceOne.add("up");
        requiredActionSequenceOne.add("right");
        requiredActionSequenceOne.add("down");
        requiredActionSequenceOne.add("right");

        //required sequence one defined in the list
        ArrayList<String> requiredActionSequenceTwo = new ArrayList<String>();
        requiredActionSequenceTwo.add("right");
        requiredActionSequenceTwo.add("down");
        requiredActionSequenceTwo.add("right");

        // checks if the reqired sequence One and selected sequence is correct
        if (requiredActionSequenceOne.equals(actionSequenceOne)) {

            ObjectAnimator actionOneAnimation = ObjectAnimator.ofFloat(player, "translationX", 0f, 330f);
            ObjectAnimator actionTwoAnimation = ObjectAnimator.ofFloat(player, "translationY", 0f, -330f);
            ObjectAnimator actionThreeAnimation = ObjectAnimator.ofFloat(player, "translationX", 330f, 990f);
            ObjectAnimator actionFourAnimation = ObjectAnimator.ofFloat(player, "translationY", -330f, 175f);
            ObjectAnimator actionFiveAnimation = ObjectAnimator.ofFloat(player, "translationX", 990f, 1480f);
            set = new AnimatorSet();
            set.setDuration(4000);

            //animating all moves simultaneously
            set.playSequentially(actionOneAnimation,
                    actionTwoAnimation,
                    actionThreeAnimation,
                    actionFourAnimation,
                    actionFiveAnimation);
            set.start();
            actionTwoAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageYPosition = (Float) animation.getAnimatedValue();
                    if (imageYPosition <= -100) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_coin1).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 1;
                        updateScore();
                    }

                }
            });
            actionThreeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();
                    if (imageXPosition >= 420) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_coin2).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 2;
                        updateScore();
                    }
                    if (imageXPosition >= 570) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_coin3).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 3;
                        updateScore();
                    }

                }
            });
            actionFiveAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();

                    if (imageXPosition >= 970) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_coin4).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 4;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1170) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_coin5).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 5;
                        updateScore();
                    }
                    if ((int) imageXPosition == 1340) {

                        // Dailouge for playing again the same level or next level
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(LevelTwoStageTwo.this);
                        LayoutInflater factory = LayoutInflater.from(LevelTwoStageTwo.this);
                        final View youwin = factory.inflate(R.layout.activity_winning, null);
                        alertadd.setView(youwin);
                        alertadd.setMessage("Score: " + score);
                        alertadd.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageTwo.this, LevelTwoStageTwo.class);
                                startActivity(intent);
                            }
                        });
                        alertadd.setNegativeButton("Next Level >>", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageTwo.this, LevelTwoStageThree.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alert = alertadd.create();
                        alert.show();
                        waves = MediaPlayer.create(LevelTwoStageTwo.this, R.raw.gameover);
                        waves.start();

                    }
                }

            });

        }
        // checks if the reqired sequence Two and selected sequence is correct
        else if (requiredActionSequenceTwo.equals(actionSequenceTwo)) {

            ObjectAnimator actionOneAnimation = ObjectAnimator.ofFloat(player, "translationX", 0f, 330f);
            ObjectAnimator actionTwoAnimation = ObjectAnimator.ofFloat(player, "translationY", 0f, 180f);
            ObjectAnimator actionThreeAnimation = ObjectAnimator.ofFloat(player, "translationX", 330f, 1480f);
            set = new AnimatorSet();
            set.setDuration(3000);

            //animating all moves simultaneously
            set.playSequentially(actionOneAnimation,
                    actionTwoAnimation,
                    actionThreeAnimation);
            set.start();

            actionThreeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageXPosition = (Float) animation.getAnimatedValue();

                    if (imageXPosition >= 570) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_extrapoints).setVisibility(View.INVISIBLE);
                        //updating score
                        extra = 1;
                        updateScore();
                    }
                    if (imageXPosition >= 970) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_coin4).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 1;
                        updateScore();
                    }
                    if ((int) imageXPosition >= 1170) {
                        //coin music
                        waves.start();
                        //collecting coins
                        findViewById(R.id.stagetwo_coin5).setVisibility(View.INVISIBLE);
                        //updating score
                        coins = 2;
                        updateScore();
                    }
                    if ((int) imageXPosition == 1340) {

                        // Dailouge for playing again the same level or next level
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(LevelTwoStageTwo.this);
                        LayoutInflater factory = LayoutInflater.from(LevelTwoStageTwo.this);
                        final View youwin = factory.inflate(R.layout.activity_winning, null);
                        alertadd.setView(youwin);
                        alertadd.setMessage("Score: " + score);
                        alertadd.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageTwo.this, LevelTwoStageTwo.class);
                                startActivity(intent);
                            }
                        });
                        alertadd.setNegativeButton("Next Level >>", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int something) {
                                Intent intent = new Intent(LevelTwoStageTwo.this, LevelTwoStageThree.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alert = alertadd.create();
                        alert.show();
                        waves = MediaPlayer.create(LevelTwoStageTwo.this, R.raw.gameover);
                        waves.start();

                    }
                }

            });

        } else {
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
        Intent intent = new Intent(this, LevelOneStageTwo.class);
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


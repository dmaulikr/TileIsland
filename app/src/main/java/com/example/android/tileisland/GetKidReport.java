/*
* This class is used to display the activity history of the user.
*
* Whenever the user visits any activity the visited activity log
* is updated in the shared pref file.
*/

package com.example.android.tileisland;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.data;
import static com.example.android.tileisland.UserSessionManagement.SHARED_PREF_FILENAME;

public class GetKidReport extends AppCompatActivity {

    //User session management class
    UserSessionManagement session;

    //Activty tracker class
    ActivityTracker activityTracker;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private TextView textView;
    private TextView successAttempt;
    private TextView totalAttempt;
    private TextView failAttempt;
    private Integer successfullAttempt = 0;
    private Integer totalAttempts = 0;
    private Integer failedAttempt;
    private Integer score;
    private String logInKid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_kid_report);

        sharedPreferences = getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor = sharedPreferences.edit();

        // Session class instance
        session = new UserSessionManagement(getApplicationContext());
        logInKid = session.getUserDetails();

        textView = (TextView) findViewById(R.id.get_report_textView);
        successAttempt = (TextView)findViewById(R.id.successful_attempt_textView);
        failAttempt = (TextView) findViewById(R.id.failed_attempt_textView);
        totalAttempt = (TextView)findViewById(R.id.total_attempt_textView);

        getKidReport();
    }

    private float[] calculateData(float[] data) {

        // TODO Auto-generated method stub
        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360*(data[i]/total);
        }
        return data;
    }

    private void getKidReport() {
        // String "activity" stores the activity history
        StringBuilder builder = new StringBuilder();
        ArrayList<String> activity = ActivityTracker.getAllScores(getApplicationContext(), logInKid);
        for (String str : activity) {
            score = Integer.parseInt(str);
            if(score > 0){
                successfullAttempt++;
            }
            totalAttempts++;

            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(str);
            //textView.setMovementMethod(new ScrollingMovementMethod());
            //textView.setText(builder.toString());
            failedAttempt = totalAttempts - successfullAttempt;
        }
        successAttempt.setText("Successfull Attempts : "+successfullAttempt);
        failAttempt.setText("Failed Attempts : "+ failedAttempt);
        totalAttempt.setText("Total Attempts : "+totalAttempts);

        float[] values = {successfullAttempt,failedAttempt};
        RelativeLayout relative=(RelativeLayout) findViewById(R.id.activity_get_kid_report);
        values=calculateData(values);
        relative.addView(new MyGraphView(this,values));
    }

    public void onExitClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    public class MyGraphView extends View {

               private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] value_degree;
        private int[] COLORS={Color.GREEN,Color.RED};
        RectF rectf = new RectF (100, 100, 600, 600);
        int temp=0;
        public MyGraphView(Context context, float[] values) {

            super(context);
            value_degree=new float[values.length];
            for(int i=0;i<values.length;i++)
            {
                value_degree[i]=values[i];
            }
        }
        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            int temp=0;
            for (int i = 0; i < value_degree.length; i++) {//values2.length; i++) {
                if (i == 0) {
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, 0, value_degree[i], true, paint);
                }
                else
                {
                    temp += (int) value_degree[i - 1];
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, temp, value_degree[i], true, paint);
                }
            }
        }

    }
}
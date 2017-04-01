package com.example.android.tileisland;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.tileisland.utils.KidsContract;
import com.example.android.tileisland.utils.KidsDBHelper;


public class ParentsActivity extends AppCompatActivity {

    private EditText etKidName;
    private EditText etKidPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);

        etKidName = (EditText)findViewById(R.id.etKidName);
        etKidPassword = (EditText)findViewById(R.id.etKidPassword);
    }

    public void onGetReportClick(View view) {
        String kidName = etKidName.getText().toString().trim();
        String password = etKidPassword.getText().toString().trim();

        // validate username and password
        if (verifyData(kidName, password)) {

            if (view.getId() == R.id.bGetReport) {

                // verify if the user exits
                if (verifyKidExist(kidName)) {

                    Intent intent = new Intent(ParentsActivity.this, GetKidReport.class);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    /*//creates session for the logged in user
                    signInUser = session.createUserLoginSession(signInUsernameStr);

                    //updating the activity tracking file
                    activityTracker = new ActivityTracker(getApplicationContext(), signInUser);
                    activityTracker.updateActivity(signInUser + " signed in!");*/

                    startActivity(intent);

                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setMessage("User Name or Password does not match");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();

                }
            }

        } else {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Your kid is not registered here!");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alertDialog.show();

        }
    }

    private boolean verifyKidExist(String kidName) {

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        KidsDBHelper dbHelper = new KidsDBHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //builds query to match the entered password and the stored password in the database with unique username
        Cursor cursor = db.query(KidsContract.KidEntry.TABLE_NAME,
                new String[]{KidsContract.KidEntry.COLUMN_PASSWORD},
                " firstname = ?",
                new String[]{kidName},
                null,
                null,
                null,
                null);

        //after receiving the result of the query moves the cursor to first row of the result and checks that received data
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            if (etKidPassword.equals(cursor.getString(0))) {

                /*session.createUserLoginSession(uName);

                activityTracker = new ActivityTracker(getApplicationContext(), uName);
                activityTracker.updateActivity(uName + " signed in!");*/

            }

            cursor.close();
            return true;
        }

        db.close();
        return false;
    }

    private boolean verifyData(String kidName, String password) {

        Boolean isValid = true;

        //Validates Username
        if (TextUtils.isEmpty(kidName)) {
            etKidName.setError("Please enter kid's name.");
            isValid = false;
        }

        //Validates Password
        //String passwordPattern = "/^.{6,}$/";
        if (TextUtils.isEmpty(password)) {
            etKidPassword.setError("Incorrect Password");
            isValid = false;
        }

        return  isValid;
    }
}

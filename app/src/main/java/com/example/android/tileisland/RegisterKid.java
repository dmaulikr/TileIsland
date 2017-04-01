package com.example.android.tileisland;

import android.content.ContentValues;
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

import com.example.android.tileisland.utils.KidsContract.KidEntry;
import com.example.android.tileisland.utils.KidsDBHelper;


public class RegisterKid extends AppCompatActivity {

    //TODO:add back button and functionality

    private KidsDBHelper dbHelper;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_kid);

        // Create database helper
        dbHelper = new KidsDBHelper(this);

        etFirstName = (EditText)findViewById(R.id.etRegisterFirstName);
        etLastName = (EditText)findViewById(R.id.etRegisterLastName);
        etPassword = (EditText)findViewById(R.id.etRegisterPassword);
    }

    public void onRegisterKidClick(View view) {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        clearErrors();
        if (verifyData(firstName, lastName, password)) {

            if (view.getId() == R.id.bRegister) {

                //after receiving the result of the query moves the cursor to first row of the result and checks the received data
                if (verifyKidExist(firstName)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setMessage("Kid already exits!");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();

                } else {

                    if (writeKidDetails(firstName, lastName, password) == -1) {
                        // If the row ID is -1, then there was an error with insertion.
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setMessage("Error with saving kids details");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                    } else {
                       /* session.createUserLoginSession(regUsernameStr);
                        activityTracker = new ActivityTracker(getApplicationContext(), regUsernameStr);
                        activityTracker.updateActivity(regUsernameStr + " registered!");*/
                        // Student Registration is successful after passing all the validations
                        Intent intent = new Intent(RegisterKid.this, WelcomeLevelOne.class);
                        startActivity(intent);
                    }
                }
            }
        }

    }

    private boolean verifyData(String firstName, String lastName, String password) {
        Boolean isValid = true;

        // Firstname validation
        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError("Please enter your firstname");
            isValid = false;
        }

        // Lastname validation
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError("Please enter your lastname");
            isValid = false;
        }

        // Password validation
        //String passwordPattern = "/^.{6,}$/";
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter min 6 characters");
            isValid = false;
        }

        return isValid;
    }

    private void clearErrors() {
    }

    public boolean verifyKidExist(String uName) {

        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //builds query to match the entered password and the stored password in the database with unique username
        Cursor cursor = db.query(KidEntry.TABLE_NAME,
                new String[]{KidEntry.COLUMN_FIRSTNAME},
                " firstname = ?",
                new String[]{uName},
                null,
                null,
                null,
                null);

        //after receiving the result of the query moves the cursor to first row of the result and checks the received data
        if (cursor != null && cursor.getCount() > 0)
            return true;
        cursor.close();
        db.close();
        return false;

    }

    public long writeKidDetails(String firstName, String lastName, String password) {

        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and students attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(KidEntry.COLUMN_FIRSTNAME, firstName);
        values.put(KidEntry.COLUMN_LASTNAME, lastName);
        values.put(KidEntry.COLUMN_PASSWORD, password);

        // Insert a new row for student in the database, returning the ID of that new row.
        long newRowId = db.insert(KidEntry.TABLE_NAME, null, values);

        db.close();
        return newRowId;
    }
}

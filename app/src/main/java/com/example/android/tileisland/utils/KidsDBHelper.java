package com.example.android.tileisland.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.tileisland.utils.KidsContract.KidEntry;

public class KidsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Kids.db";

    public static final int DATABASE_VERSION = 1;

    public KidsDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the students table
        String SQL_CREATE_STUDENTS_TABLE = "CREATE TABLE " + KidEntry.TABLE_NAME + " ("
                + KidEntry.COLUMN_FIRSTNAME + " TEXT PRIMARY KEY NOT NULL, "
                + KidEntry.COLUMN_LASTNAME + " TEXT NOT NULL, "
                + KidEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_STUDENTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}

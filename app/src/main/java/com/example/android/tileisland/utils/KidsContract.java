package com.example.android.tileisland.utils;


import android.provider.BaseColumns;

public class KidsContract {

    // To prevent someone from accidentally instantiating the Student Contract class,
    // give it an empty constructor.
    private KidsContract() {
    }

    /**
     * Inner class that defines constant values for the player database table.
     * Each entry in the table represents a single kid.
     */
    public static final class KidEntry implements BaseColumns {

        /**
         * Name of database table for kids
         */
        public final static String TABLE_NAME = "kids";

        /**
         * Unique First name of the kid.
         * Type: TEXT
         */
        public final static String COLUMN_FIRSTNAME = "firstname";

        /**
         * Last name of the kid.
         * Type: TEXT
         */
        public final static String COLUMN_LASTNAME = "lastname";

        /**
         * Password of the kid.
         * Type: TEXT
         */
        public final static String COLUMN_PASSWORD = "password";

    }
}

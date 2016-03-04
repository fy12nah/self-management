package com.nooraalhassen.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nooraalhassen
 */
public class DBmanager extends SQLiteOpenHelper {

    static int DB_VERSION = 4;
    static String DB_NAME = "Selfmanaging.db";

    public DBmanager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating tables
        db.execSQL(UsersTable.sql_create);
        db.execSQL(UsersProfileTable.sql_create);
        db.execSQL(UsersPhysicalTable.sql_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dropping tables
        db.execSQL(UsersTable.sql_drop);
        db.execSQL(UsersProfileTable.sql_drop);
        db.execSQL(UsersPhysicalTable.sql_drop);
        onCreate(db);
    }


    // creating a UserTable in database
    private static class UsersTable implements BaseColumns {

        // creating columns in UserTable
        public static String table_name = "userRegister";
        public static String Col_username = "username";
        public static String Col_password = "password";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_username+ " TEXT not null, "+
                Col_password+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }

    // creating UserProfileTable in database
    private static class UsersProfileTable implements BaseColumns {

        // creating columns in UserProfileTable
        public static String table_name = "userProfile";
        public static String Col_userID = "user_id";
        public static String Col_name = "name";
        public static String Col_birthdate = "birthdate";
        public static String Col_gender = "gender";
        public static String Col_startStudy = "startStudy";
        public static String Col_gradStudy = "gradStudy";
        public static String Col_mostUsed = "Most_Used";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userID+ " INTEGER not null, "+
                Col_name+ " TEXT not null, "+
                Col_birthdate+ " TEXT not null, "+
                Col_gender+ " TEXT not null, "+
                Col_startStudy+ " TEXT, "+
                Col_gradStudy+ " TEXT, "+
                Col_mostUsed + " REAL, "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    // creating a UserPhysicalTable in database
    private static class UsersPhysicalTable implements BaseColumns {

        // creating columns in UserPhysicalTable
        public static String table_name = "userPhysical";
        public static String Col_weight = "weight";
        public static String Col_height = "height";
        public static String Col_date = "date";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_weight+ " REAL, "+
                Col_height+ " REAL, "+
                Col_date+ " TEXT"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    public boolean signup (String username, String name, String password, Date birthdate, char gender){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // input values in col
        ContentValues values = new ContentValues();
        values.put(UsersTable.Col_username, username);
        values.put(UsersTable.Col_password, password);
        long id = db.insert(UsersTable.table_name, null, values);

        if (id != -1){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ContentValues vals = new ContentValues();
            vals.put(UsersProfileTable.Col_userID, id);
            vals.put(UsersProfileTable.Col_name, name);
            vals.put(UsersProfileTable.Col_birthdate, simpleDateFormat.format(birthdate));
            vals.put(UsersProfileTable.Col_gender, Character.toString(gender));

            id = db.insert(UsersProfileTable.table_name, null, vals);
        }

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }
        return true;
    }

    public String searchPass(String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select username, password from "+UsersTable.table_name;
        Cursor c = db.rawQuery(query, null);
        String uname, pass;
        pass = "Not found!";
        if (c.moveToFirst()){
            do{
                uname = c.getString(0);

                if (uname == username){
                    pass = c.getString(1);
                    break;
                }
            }
            while (c.moveToNext());
        }
        return pass;


    }


    public boolean updateProfile(String name, Date birthdate, char gender, Date start_Study, Date grad_Study, char mostUsed){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(UsersProfileTable.Col_name, name);
        values.put(UsersProfileTable.Col_birthdate, simpleDateFormat.format(birthdate));
        values.put(UsersProfileTable.Col_gender, Character.toString(gender));
        values.put(UsersProfileTable.Col_startStudy, simpleDateFormat.format(start_Study));
        values.put(UsersProfileTable.Col_gradStudy, simpleDateFormat.format(grad_Study));
        values.put(UsersProfileTable.Col_mostUsed, Character.toString(mostUsed));

        long id = db.update(UsersProfileTable.table_name, values, null, null);

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }
        return true;
    }

    public Cursor getProfile(long profileid){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(UsersProfileTable.table_name, null, " _id = ? ", new String[]{String.valueOf(profileid)}, null, null, null);
        return c;
    }


    // insert data into userPhysicalTable
    public boolean insert_physical (String weight, String height, Date physicaldate){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // date form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ContentValues values = new ContentValues();

        // input values in col
        values.put(UsersPhysicalTable.Col_weight, weight);
        values.put(UsersPhysicalTable.Col_height, height);
        values.put(UsersPhysicalTable.Col_date, simpleDateFormat.format(physicaldate));
        long id = db.insert(UsersPhysicalTable.table_name, null, values);


        // Closing database
        db.close();

        if (id == -1){
            return false;
        }
        return true;
    }
}

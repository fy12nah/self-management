package com.nooraalhassen.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.view.View;

import com.nooraalhassen.myapplication.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by nooraalhassen
 */
public class DBmanager extends SQLiteOpenHelper {

    static int DB_VERSION = 6;
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
        db.execSQL(CategoryTable.sql_create);
        db.execSQL(ProfileCategoryTable.sql_create);

        populateCategotyTable(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dropping tables
        db.execSQL(UsersTable.sql_drop);
        db.execSQL(UsersProfileTable.sql_drop);
        db.execSQL(UsersPhysicalTable.sql_drop);
        db.execSQL(CategoryTable.sql_drop);
        db.execSQL(ProfileCategoryTable.sql_drop);
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


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userID+ " INTEGER not null, "+
                Col_name+ " TEXT not null, "+
                Col_birthdate+ " TEXT not null, "+
                Col_gender+ " TEXT not null, "+
                Col_startStudy+ " TEXT, "+
                Col_gradStudy+ " TEXT "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }

    // creating a UserTable in database
    private static class ProfileCategoryTable implements BaseColumns {

        // creating columns in UserTable
        public static String table_name = "profileCategory";
        public static String Col_profileId = "profileId";
        public static String Col_categoryId = "categoryId";
        public static String Col_showInLanding = "showLanding";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_profileId+ " INTEGER not null, "+
                Col_categoryId+ " INTEGER not null, "+
                Col_showInLanding+ " INTEGER not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }

    // creating a UserTable in database
    private static class CategoryTable implements BaseColumns {

        // creating columns in UserTable
        public static String table_name = "category";
        public static String Col_categoryname = "categoryName";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_categoryname+ " TEXT not null "+
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
                Col_date+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    public long signup (String username, String name, String password, Date birthdate, char gender){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // input values in col
        ContentValues values = new ContentValues();
        values.put(UsersTable.Col_username, username);
        values.put(UsersTable.Col_password, password);
        long user_id = db.insert(UsersTable.table_name, null, values);

        if (user_id != -1){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
            ContentValues vals = new ContentValues();
            vals.put(UsersProfileTable.Col_userID, user_id);
            vals.put(UsersProfileTable.Col_name, name);
            vals.put(UsersProfileTable.Col_birthdate, simpleDateFormat.format(birthdate));
            vals.put(UsersProfileTable.Col_gender, Character.toString(gender));


            long profileId = db.insert(UsersProfileTable.table_name, null, vals);

            populateProfileCategories(db, profileId);

        }

        // Closing database
        db.close();

        return user_id;
    }

    private void populateProfileCategories(SQLiteDatabase db, long profileId) {
        String SQL = "insert into "+ProfileCategoryTable.table_name+
                "("+ProfileCategoryTable.Col_profileId + ","+ ProfileCategoryTable.Col_categoryId+","+ProfileCategoryTable.Col_showInLanding+")"+
                " select "+profileId+" ,"+
                CategoryTable._ID+" ,1"+
                " from "+CategoryTable.table_name;
        db.execSQL(SQL);
    }

    private void populateCategotyTable(SQLiteDatabase db) {
        String SQL = "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Profile');";
        db.execSQL(SQL);
        SQL= "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Physical');";
        db.execSQL(SQL);
        SQL= "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Illness');";
        db.execSQL(SQL);
        SQL= "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Meals');";
        db.execSQL(SQL);
        SQL= "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Mood Status');";
        db.execSQL(SQL);
        SQL= "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Exercises');";
        db.execSQL(SQL);
        SQL= "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Sleeping Hours');";
        db.execSQL(SQL);
    }

    public long authenticate(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select username, password from "+UsersTable.table_name+" where username";
        String selection = UsersTable.Col_username+ " = ? and "+UsersTable.Col_password+ " = ? ";
        String[] args = new String[]{username, password};
        Cursor c = db.query(UsersTable.table_name, null, selection, args, null, null, null);
        long id = -1;

        if (c.moveToFirst()){
                id = c.getLong(c.getColumnIndex(UsersTable._ID));
        }
        return id;
    }


    public boolean updateProfile(Profile profile){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersProfileTable.Col_name, profile.getName());
        values.put(UsersProfileTable.Col_birthdate, simpleDateFormat.format(profile.getBirthdate()));
        values.put(UsersProfileTable.Col_gender, Character.toString(profile.getGender()));
        values.put(UsersProfileTable.Col_startStudy, simpleDateFormat.format(profile.getStart_Study()));
        values.put(UsersProfileTable.Col_gradStudy, simpleDateFormat.format(profile.getGrad_Study()));


        long id = db.update(UsersProfileTable.table_name, values, UsersProfileTable._ID+" = "+profile.getId(), null);

        for (HashMap.Entry<String, Boolean> s: profile.getCategories().entrySet()){
            values = new ContentValues();
            values.put(ProfileCategoryTable.Col_showInLanding, s.getValue()?1:0);
            db.update(ProfileCategoryTable.table_name, values, ProfileCategoryTable.Col_profileId+" = ? and "
                    +ProfileCategoryTable.Col_categoryId+" = (select "+CategoryTable._ID+" from "+CategoryTable.table_name+
                    " where "+CategoryTable.Col_categoryname+" = ?)", new String[]{String.valueOf(profile.getId()), s.getKey()});
        }

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }
        return true;
    }

    public Profile getProfile(long userid){
        SQLiteDatabase db = getReadableDatabase();


        Cursor c = db.query(UsersProfileTable.table_name, null, UsersProfileTable.Col_userID+ " = ? ",
                new String[]{String.valueOf(userid)}, null, null, null);

        Profile p = null;
        if (c.moveToFirst()){

            try {
                long id = c.getLong(c.getColumnIndex(UsersProfileTable._ID));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
            String name = c.getString(c.getColumnIndex(UsersProfileTable.Col_name));

                Date birthdate = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersProfileTable.Col_birthdate)));
                String temp = c.getString(c.getColumnIndex(UsersProfileTable.Col_startStudy));
                Date start_Study = null;
                Date grad_Study = null;

                if (temp != null){
                    start_Study = simpleDateFormat.parse(temp);
                }

                temp = c.getString(c.getColumnIndex(UsersProfileTable.Col_gradStudy));
                if (temp != null){
                    grad_Study = simpleDateFormat.parse(temp);
                }

                char gender = c.getString(c.getColumnIndex(UsersProfileTable.Col_gender)).charAt(0);
                p = new Profile(id,name, birthdate, start_Study, grad_Study, gender);

                c.close();

                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(ProfileCategoryTable.table_name+" inner join "+CategoryTable.table_name+" on "+
                        ProfileCategoryTable.Col_categoryId+" = "+CategoryTable.table_name+"."+CategoryTable._ID);

                c = queryBuilder.query(db, null, ProfileCategoryTable.Col_profileId+ " = ? ", new String[]{String.valueOf(id)}, null, null, null);

                while (c.moveToNext()){
                    String catName = c.getString(c.getColumnIndex(CategoryTable.Col_categoryname));
                    int temp2 = c.getInt(c.getColumnIndex(ProfileCategoryTable.Col_showInLanding));
                    Boolean show = temp2 == 1;

                    p.addCategory(catName, show);
                }




            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return p;

    }


    // insert data into userPhysicalTable
    public boolean insert_physical (String weight, String height, Date physicaldate){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // date form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

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


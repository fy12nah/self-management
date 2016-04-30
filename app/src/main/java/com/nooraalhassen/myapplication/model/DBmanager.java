package com.nooraalhassen.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

import com.nooraalhassen.myapplication.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nooraalhassen
 */

public class DBmanager extends SQLiteOpenHelper {

    static int DB_VERSION = 36;
    static String DB_NAME = "Selfmanaging.db";

    public DBmanager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating tables
        db.execSQL(UsersTable.sql_create);
        db.execSQL(UsersProfileTable.sql_create);
        db.execSQL(CategoryTable.sql_create);
        db.execSQL(ProfileCategoryTable.sql_create);
        db.execSQL(UsersPhysicalTable.sql_create);
        db.execSQL(UsersMealsTable.sql_create);
        db.execSQL(MealDetailsTable.sql_create);
        db.execSQL(UsersSleepTable.sql_create);
        db.execSQL(ExerciseTypeTable.sql_create);
        db.execSQL(UsersExerciseTable.sql_create);
        db.execSQL(MoodNameTable.sql_create);
        db.execSQL(UsersMoodTable.sql_create);
        db.execSQL(STINameTable.sql_create);
        db.execSQL(LTINameTable.sql_create);
        db.execSQL(UsersIllnessTable.sql_create);
        db.execSQL(IllnessDetailsTable.sql_create);

        Log.d("Noora", UsersTable.sql_create);
        Log.d("Noora", UsersProfileTable.sql_create);
        Log.d("Noora", CategoryTable.sql_create);
        Log.d("Noora", ProfileCategoryTable.sql_create);
        Log.d("Noora", UsersPhysicalTable.sql_create);
        Log.d("Noora", UsersMealsTable.sql_create);
        Log.d("Noora", MealDetailsTable.sql_create);
        Log.d("Noora", UsersSleepTable.sql_create);
        Log.d("Noora", UsersExerciseTable.sql_create);
        Log.d("Noora", ExerciseTypeTable.sql_create);
        Log.d("Noora", UsersMoodTable.sql_create);
        Log.d("Noora", MoodNameTable.sql_create);
        Log.d("Noora", STINameTable.sql_create);
        Log.d("Noora", LTINameTable.sql_create);
        Log.d("Noora", UsersIllnessTable.sql_create);
        Log.d("Noora", IllnessDetailsTable.sql_create);

        populateCategotyTable(db);
        populateMoodNameTable(db);
        populateExericeTypeTable(db);
        populateSTINameTable(db);
        populateLTINameTable(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dropping tables
        db.execSQL(UsersTable.sql_drop);
        db.execSQL(UsersProfileTable.sql_drop);
        db.execSQL(CategoryTable.sql_drop);
        db.execSQL(ProfileCategoryTable.sql_drop);
        db.execSQL(UsersPhysicalTable.sql_drop);
        db.execSQL(UsersMealsTable.sql_drop);
        db.execSQL(MealDetailsTable.sql_drop);
        db.execSQL(UsersSleepTable.sql_drop);
        db.execSQL(UsersExerciseTable.sql_drop);
        db.execSQL(ExerciseTypeTable.sql_drop);
        db.execSQL(UsersMoodTable.sql_drop);
        db.execSQL(MoodNameTable.sql_drop);
        db.execSQL(UsersIllnessTable.sql_drop);
        db.execSQL(STINameTable.sql_drop);
        db.execSQL(LTINameTable.sql_drop);
        db.execSQL(IllnessDetailsTable.sql_drop);

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
        public static String Col_uniName = "uniName";
        public static String Col_startStudy = "startStudy";
        public static String Col_gradStudy = "gradStudy";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userID+ " INTEGER not null, "+
                Col_name+ " TEXT not null, "+
                Col_birthdate+ " TEXT not null, "+
                Col_gender+ " TEXT not null, "+
                Col_startStudy+ " TEXT, "+
                Col_uniName+" TEXT, "+
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
        public static String Col_userID = "user_id";
        public static String Col_weight = "weight";
        public static String Col_height = "height";
        public static String Col_date = "date";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userID+ " INTEGER not null, "+
                Col_weight+ " REAL, "+
                Col_height+ " REAL, "+
                Col_date+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    // creating a UsersMealsTable in database
    private static class UsersMealsTable implements BaseColumns {

        // creating columns in UserMealsTable
        public static String table_name = "userMeals";
        public static String Col_userId = "userId";
        public static String Col_mealType = "mealType";
        public static String Col_mealName = "mealName";
        public static String Col_mealDate = "mealDate";
        public static String Col_mealTime = "mealTime";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userId+ " INTEGER not null, "+
                Col_mealType+" TEXT not null, "+
                Col_mealName+ " TEXT, "+
                Col_mealDate+ " TEXT not null, "+
                Col_mealTime+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }

    // MealDetailsTable for meals' items
    public static class MealDetailsTable implements BaseColumns{

        // creating columns
        public static String table_name = "userMealItems";
        public static String Col_mealID = "mealID";
        public static String Col_mealItem = "mealItem";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_mealID+ " INTEGER not null, "+
                Col_mealItem+ " TEXT "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    // creating a UsersMoodTable in database
    private static class UsersMoodTable implements BaseColumns {

        // creating columns in UserMoodTable
        public static String table_name = "userMood";
        public static String Col_userId = "userId";
        public static String Col_moodNameID = "moodNameID";
        public static String Col_moodReason = "moodReason";
        public static String Col_moodDate = "moodDate";
        public static String Col_moodTime = "moodTime";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userId+ " INTEGER not null, "+
                Col_moodNameID+ " INTEGER not null, "+
                Col_moodReason+ " TEXT not null, "+
                Col_moodDate+ " TEXT not null, "+
                Col_moodTime+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    public static class MoodNameTable implements BaseColumns{

        // creating columns in UserMoodTable
        public static String table_name = "moodNameLookup";
        public static String Col_moodName = "moodName";
        public static String Col_userID = "userId";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_moodName+ " TEXT not null, "+
                Col_userID+ " INTEGER "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    private void populateMoodNameTable(SQLiteDatabase db) {
        String SQL = "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Happy');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Sad');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Pressured');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Stressed');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Depressed');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Sleepy');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Moody');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Sick');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Calm');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Energized');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Confused');";
        db.execSQL(SQL);
        SQL= "insert into "+MoodNameTable.table_name+"("+MoodNameTable.Col_moodName+") values('Relieved');";
        db.execSQL(SQL);
    }


    // creating a UsersSleepTable in database
    private static class UsersSleepTable implements BaseColumns {

        // creating columns in UserSleepTable
        public static String table_name = "userSleepingHours";
        public static String Col_userId = "userId";
        public static String Col_sleepDate = "sleepDate";
        public static String Col_sSleepTime = "sleepStartTime";
        public static String Col_eSleepTime = "sleepEndTime";
        public static String Col_sleepDur = "sleepDuration";



        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userId+" INTEGER not null, "+
                Col_sleepDate+ " TEXT not null, "+
                Col_sSleepTime+ " TEXT not null, "+
                Col_eSleepTime+ " TEXT not null, "+
                Col_sleepDur+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    // creating a UsersExerciseTable in database
    private static class UsersExerciseTable implements BaseColumns {

        // creating columns in UserExerciseTable
        public static String table_name = "userExercises";
        public static String Col_userID = "userId";
        public static String Col_exerTypeID = "exerciseTypeID";
        public static String Col_exerDate = "exerciseDate";
        public static String Col_sExerTime = "exerciseStartTime";
        public static String Col_eExerTime = "exerciseEndTime";
        public static String Col_exerDur = "exerciseDuration";



        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userID+ " INTEGER not null, "+
                Col_exerTypeID+ " INTEGER not null, "+
                Col_exerDate+ " TEXT not null, "+
                Col_sExerTime+ " TEXT not null, "+
                Col_eExerTime+ " TEXT not null, "+
                Col_exerDur+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }



    public static class ExerciseTypeTable implements BaseColumns{

        // creating columns in ExerciseTypeTable
        public static String table_name = "exerciseTypeLookup";
        public static String Col_exerType = "exeriseType";
        public static String Col_userID = "userId";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_exerType+ " TEXT not null, "+
                Col_userID+ " INTEGER "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    private void populateExericeTypeTable(SQLiteDatabase db) {
        String SQL = "insert into "+ExerciseTypeTable.table_name+"("+ExerciseTypeTable.Col_exerType+") values('Walking');";
        db.execSQL(SQL);
        SQL= "insert into "+ExerciseTypeTable.table_name+"("+ExerciseTypeTable.Col_exerType+") values('Running');";
        db.execSQL(SQL);
        SQL= "insert into "+ExerciseTypeTable.table_name+"("+ExerciseTypeTable.Col_exerType+") values('Jogging');";
        db.execSQL(SQL);

    }


    // creating a UsersIllnessTable in database
    private static class UsersIllnessTable implements BaseColumns {

        // creating columns in UserIllnessTable
        public static String table_name = "userIllness";
        public static String Col_userID = "userId";
        public static String Col_illnessType = "illnessType";
        public static String Col_illnessNameID = "illnessNameID";
        public static String Col_sIllnessDate = "illnessStartDate";
        public static String Col_eIllnessDate = "illnessEndDate";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_userID+ " INTEGER not null, "+
                Col_illnessType+ " TEXT not null, "+
                Col_illnessNameID+ " INTEGER not null, "+
                Col_sIllnessDate+ " TEXT, "+
                Col_eIllnessDate+ " TEXT "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    // creating a table for Illness' meds
    public static class IllnessDetailsTable implements BaseColumns{

        // creating columns
        public static String table_name = "userIllnessMed";
        public static String Col_illnessID = "illnessID";
        public static String Col_illnessMed = "illnessMed";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_illnessID+ " INTEGER not null, "+
                Col_illnessMed+ " TEXT not null"+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;

    }


    public static class STINameTable implements BaseColumns{

        // creating columns in ExerciseTypeTable
        public static String table_name = "STINameLookup";
        public static String Col_illnessName = "IllnessName";
        public static String Col_userID = "userId";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_illnessName+ " TEXT not null, "+
                Col_userID+ " INTEGER "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }



    public static class LTINameTable implements BaseColumns{

        // creating columns in ExerciseTypeTable
        public static String table_name = "LTINameLookup";
        public static String Col_illnessName = "IllnessName";
        public static String Col_userID = "userId";


        public static String sql_create = "create table "+table_name+ "("+
                _ID + " INTEGER Primary key AUTOINCREMENT, "+
                Col_illnessName+ " TEXT not null, "+
                Col_userID+ " INTEGER "+
                ")";

        public static String sql_drop = "drop table if exists "+table_name;
    }


    private void populateSTINameTable(SQLiteDatabase db) {
        String SQL = "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Fever');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Cold');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Flu');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Headache');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Backache');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Toothache');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Abdominal Pain');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Diarrhea');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Sore throat');";
        db.execSQL(SQL);
        SQL= "insert into "+STINameTable.table_name+"("+STINameTable.Col_illnessName+") values('Cough');";
        db.execSQL(SQL);
    }


    private void populateLTINameTable(SQLiteDatabase db) {
        String SQL = "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Cancer');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Asthma');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Diabetes');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Cardiac Failure');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Chronic Renal');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Epilepsy');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Hypertension');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Hypothyroidism');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Rheumatoid Arthritis');";
        db.execSQL(SQL);
        SQL= "insert into "+LTINameTable.table_name+"("+LTINameTable.Col_illnessName+") values('Ulcerative Colitis');";
        db.execSQL(SQL);
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

        Log.d("Noora", "populateCategotyTable()");
        String SQL= "insert into "+CategoryTable.table_name+"("+CategoryTable.Col_categoryname+") values('Physical');";
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


    // method to allow users to update their profiles
    public boolean updateProfile(Profile profile){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersProfileTable.Col_name, profile.getName());
        values.put(UsersProfileTable.Col_uniName, profile.getUniname());
        values.put(UsersProfileTable.Col_birthdate, simpleDateFormat.format(profile.getBirthdate()));
        values.put(UsersProfileTable.Col_gender, Character.toString(profile.getGender()));
        values.put(UsersProfileTable.Col_startStudy, simpleDateFormat.format(profile.getStart_Study()));
        values.put(UsersProfileTable.Col_gradStudy, simpleDateFormat.format(profile.getGrad_Study()));


        long id = db.update(UsersProfileTable.table_name, values, UsersProfileTable._ID + " = " + profile.getId(), null);

        for (HashMap.Entry<String, Boolean> s: profile.getCategories().entrySet()){
            values = new ContentValues();
            values.put(ProfileCategoryTable.Col_showInLanding, s.getValue() ? 1 : 0);
            db.update(ProfileCategoryTable.table_name, values, ProfileCategoryTable.Col_profileId + " = ? and "
                    + ProfileCategoryTable.Col_categoryId + " = (select " + CategoryTable._ID + " from " + CategoryTable.table_name +
                    " where " + CategoryTable.Col_categoryname + " = ?)", new String[]{String.valueOf(profile.getId()), s.getKey()});
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

        Cursor c = db.query(UsersProfileTable.table_name, null, UsersProfileTable.Col_userID + " = ? ",
                new String[]{String.valueOf(userid)}, null, null, null);

        Profile p = null;
        if (c.moveToFirst()){

            try {
                long id = c.getLong(c.getColumnIndex(UsersProfileTable._ID));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
                String name = c.getString(c.getColumnIndex(UsersProfileTable.Col_name));
                String uniName = c.getString(c.getColumnIndex(UsersProfileTable.Col_uniName));

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
                p = new Profile(id, name, birthdate, uniName, start_Study, grad_Study, gender);

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


    // method to allow users to update their physical attributes
    public boolean updatePhysical(long id, String weight, String height, Date date){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersPhysicalTable.Col_weight, weight);
        values.put(UsersPhysicalTable.Col_height, height);
        values.put(UsersPhysicalTable.Col_date, simpleDateFormat.format(date));

        long count = db.update(UsersPhysicalTable.table_name, values, UsersPhysicalTable._ID + " = " + id, null);

        // Closing database
        db.close();

        if (count == 0){
            return false;
        }
        return true;
    }



    // method to allow users to update their exercise entries
    public boolean updateExercise(long id, long exerTypeID, Date exerDate, String sExerTime, String eExerTime, String exerDur){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersExerciseTable.Col_exerTypeID, exerTypeID);
        values.put(UsersExerciseTable.Col_exerDate, simpleDateFormat.format(exerDate));
        values.put(UsersExerciseTable.Col_sExerTime, sExerTime);
        values.put(UsersExerciseTable.Col_eExerTime, eExerTime);
        values.put(UsersExerciseTable.Col_exerDur, exerDur);

        long count = db.update(UsersExerciseTable.table_name, values, UsersExerciseTable._ID + " = " + id, null);

        // Closing database
        db.close();

        if (count == 0){
            return false;
        }
        return true;
    }



    // method to allow users to update their sleeping entries
    public boolean updateSleeping(long id, Date dateSlp, String sleepStart, String sleepEnd, String durSlp){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersSleepTable.Col_sSleepTime, sleepStart);
        values.put(UsersSleepTable.Col_eSleepTime, sleepEnd);
        values.put(UsersSleepTable.Col_sleepDate, simpleDateFormat.format(dateSlp));
        values.put(UsersSleepTable.Col_sleepDur, durSlp);

        long count = db.update(UsersSleepTable.table_name, values, UsersSleepTable._ID+" = "+id, null);

        // Closing database
        db.close();

        if (count == 0){
            return false;
        }
        return true;
    }


    // method to allow users to update their mood entries
    public boolean updateMood(long id, long moodnameID, String moodreason, Date moodDate, String moodtime){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersMoodTable.Col_moodNameID, moodnameID);
        values.put(UsersMoodTable.Col_moodReason, moodreason);
        values.put(UsersMoodTable.Col_moodDate, simpleDateFormat.format(moodDate));
        values.put(UsersMoodTable.Col_moodTime, moodtime);

        long count = db.update(UsersMoodTable.table_name, values, UsersMoodTable._ID+" = "+id, null);

        // Closing database
        db.close();

        if (count == 0){
            return false;
        }
        return true;
    }



    // method to allow users to update any of their meals' entries
    public boolean updateMeal(long id, String mealType, String mealName, Date mealDate, String mealTime, ArrayList<String>items){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersMealsTable.Col_mealType, mealType);
        values.put(UsersMealsTable.Col_mealName, mealName);
        values.put(UsersMealsTable.Col_mealDate, simpleDateFormat.format(mealDate));
        values.put(UsersMealsTable.Col_mealTime, mealTime);

        long count = db.update(UsersMealsTable.table_name, values, UsersMealsTable._ID + " = " + id, null);

        if (items != null) {

            for (String s : items) {
                values = new ContentValues();
                values.put(MealDetailsTable.Col_mealID, id);
                values.put(MealDetailsTable.Col_mealItem, s);
                db.insert(MealDetailsTable.table_name, null, values);
            }
        }

        // Closing database
        db.close();

        if (count == 0){
            return false;
        }
        return true;
    }


    // method to allow users to update any of their illness entries
    public boolean updateIllness(long id, String illnessType, long illnessNameID, Date sIllnessDate, Date eIllnessDate, ArrayList<String>meds){
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);
        ContentValues values = new ContentValues();
        values.put(UsersIllnessTable.Col_illnessType, illnessType);
        values.put(UsersIllnessTable.Col_illnessNameID, illnessNameID);
        values.put(UsersIllnessTable.Col_sIllnessDate, simpleDateFormat.format(sIllnessDate));
        values.put(UsersIllnessTable.Col_eIllnessDate, simpleDateFormat.format(eIllnessDate));

        long count = db.update(UsersIllnessTable.table_name, values, UsersIllnessTable._ID + " = " + id, null);

        if (meds != null) {

            for (String s : meds) {
                values = new ContentValues();
                values.put(IllnessDetailsTable.Col_illnessID, id);
                values.put(IllnessDetailsTable.Col_illnessMed, s);
                db.insert(IllnessDetailsTable.table_name, null, values);
            }
        }

        // Closing database
        db.close();

        if (count == 0){
            return false;
        }
        return true;
    }


    // insert data into userPhysicalTable
    public boolean insert_physical (long userId, String weight, String height, Date physicaldate){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // date form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        ContentValues values = new ContentValues();

        // input values in col
        values.put(UsersPhysicalTable.Col_userID, userId);
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


    // insert data into usersMealsTable
    public boolean insert_meal(long user_id, String mealType, String mealname, Date mealdate, String mealtime, ArrayList<String> items){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // time form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        ContentValues values = new ContentValues();

        // input values in col
        values.put(UsersMealsTable.Col_userId, user_id);

        if (mealType.equals("")) values.putNull(UsersMealsTable.Col_mealType);
        else values.put(UsersMealsTable.Col_mealType, mealType);

        if (mealname.equals("")) values.putNull(UsersMealsTable.Col_mealName);
        else values.put(UsersMealsTable.Col_mealName, mealname);

        values.put(UsersMealsTable.Col_mealDate, simpleDateFormat.format(mealdate));

        if (mealtime.equals("")) values.putNull(UsersMealsTable.Col_mealTime);
        else values.put(UsersMealsTable.Col_mealTime, mealtime);

        long id = db.insert(UsersMealsTable.table_name, null, values);


        if (id == -1){
            return false;
        }

        if (items != null) {

            for (String s : items) {
                values = new ContentValues();
                values.put(MealDetailsTable.Col_mealID, id);
                values.put(MealDetailsTable.Col_mealItem, s);
                db.insert(MealDetailsTable.table_name, null, values);

            }
        }

        // Closing database
        db.close();
        return true;
    }



    // insert data into usersMoodTable
    public boolean insert_mood(long user_id, long moodnameID, String moodreason, Date moodDate, String moodtime){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // date form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        ContentValues values = new ContentValues();

        // input values in col
        values.put(UsersMoodTable.Col_userId, user_id);
        values.put(UsersMoodTable.Col_moodNameID, moodnameID);
        values.put(UsersMoodTable.Col_moodReason, moodreason);
        values.put(UsersMoodTable.Col_moodDate, simpleDateFormat.format(moodDate));
        values.put(UsersMoodTable.Col_moodTime, moodtime);
        long id = db.insert(UsersMoodTable.table_name, null, values);

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }

        return true;
    }


    // insert users' entries into UsersSleepTable
    public boolean insert_sleep(long user_id, Date slpDate, String startSleep, String endSleep, String sleepDur){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // date form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        ContentValues values = new ContentValues();

        // input values in col
        values.put(UsersSleepTable.Col_userId, user_id);
        values.put(UsersSleepTable.Col_sleepDate, simpleDateFormat.format(slpDate));

        if (startSleep.equals("")) values.putNull(UsersSleepTable.Col_sSleepTime);
        else values.put(UsersSleepTable.Col_sSleepTime, startSleep);

        if (endSleep.equals("")) values.putNull(UsersSleepTable.Col_eSleepTime);
        else values.put(UsersSleepTable.Col_eSleepTime, endSleep);

        if (sleepDur.equals("")) values.putNull(UsersSleepTable.Col_sleepDur);
        values.put(UsersSleepTable.Col_sleepDur, sleepDur);

        long id = db.insert(UsersSleepTable.table_name, null, values);

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }

        return true;
    }


    // insert entries into UsersExercisesTable
    public boolean insert_exer(long user_id, long exerTypeID, Date exerDate, String startExer, String endExer, String exerDur){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // date and time form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        ContentValues values = new ContentValues();

        // input values in col
        values.put(UsersExerciseTable.Col_userID, user_id);

        values.put(UsersExerciseTable.Col_exerTypeID, exerTypeID);
        values.put(UsersExerciseTable.Col_exerDate, simpleDateFormat.format(exerDate));

        if (startExer.equals("")) values.putNull(UsersExerciseTable.Col_sExerTime);
        else values.put(UsersExerciseTable.Col_sExerTime, startExer);

        if (endExer.equals("")) values.putNull(UsersExerciseTable.Col_eExerTime);
        else values.put(UsersExerciseTable.Col_eExerTime, endExer);

        if (exerDur.equals("")) values.putNull(UsersExerciseTable.Col_exerDur);
        else values.put(UsersExerciseTable.Col_exerDur, exerDur);

        long id = db.insert(UsersExerciseTable.table_name, null, values);


        // Closing database
        db.close();

        if (id == -1){
            return false;
        }

        return true;
    }

    // insert entries into UsersIllnessTable
    public boolean insert_illness(long user_id, String illnessType, long illnessNameID, Date startillness, Date endillness,
                                  ArrayList<String> illnessMed){
        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        // time form
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        ContentValues values = new ContentValues();

        // input values in col
        values.put(UsersIllnessTable.Col_userID, user_id);

        if (illnessType.equals("")) values.putNull(UsersIllnessTable.Col_illnessType);
        else values.put(UsersIllnessTable.Col_illnessType, illnessType);

        values.put(UsersIllnessTable.Col_illnessNameID, illnessNameID);

        if (startillness.equals("")) values.putNull(UsersIllnessTable.Col_sIllnessDate);
        else values.put(UsersIllnessTable.Col_sIllnessDate, simpleDateFormat.format(startillness));

        if (endillness == null) values.putNull(UsersIllnessTable.Col_eIllnessDate);
        else values.put(UsersIllnessTable.Col_eIllnessDate, simpleDateFormat.format(endillness));

        long id = db.insert(UsersIllnessTable.table_name, null, values);

        if (id == -1){
            return false;
        }


        for(String s: illnessMed) {
            values = new ContentValues();
            values.put(IllnessDetailsTable.Col_illnessID, id);
            values.put(IllnessDetailsTable.Col_illnessMed, s);
            db.insert(IllnessDetailsTable.table_name, null, values);
        }

        // Closing database
        db.close();
        return true;
    }

    // getting physical entries
    public Physical getPhysicalAtDate(Date d, long user_id){

        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        //Log.d("Noora", simpleDateFormat.format(d));
        Cursor c = db.query(UsersPhysicalTable.table_name, null, UsersPhysicalTable.Col_userID + " = ? and " + UsersPhysicalTable.Col_date +
                " = ?", new String[]{String.valueOf(user_id), simpleDateFormat.format(d)}, null, null, null);

        Physical p = null;
        if (c.moveToFirst()){

            long id = c.getLong(c.getColumnIndex(UsersPhysicalTable._ID));

            float weight = -1;
            if (!c.isNull(c.getColumnIndex(UsersPhysicalTable.Col_weight)))
            weight = c.getFloat(c.getColumnIndex(UsersPhysicalTable.Col_weight));

            float height = -1;
            if (!c.isNull(c.getColumnIndex(UsersPhysicalTable.Col_height)))
            height = c.getFloat(c.getColumnIndex(UsersPhysicalTable.Col_height));

            p = new Physical(id, user_id, weight, height, d);

        }
        db.close();
        return p;
    }


    // getting Illness entries
    public ArrayList<Illness> getIllnessAtDate(Date d, long user_id){

        SQLiteDatabase db = getReadableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersIllnessTable.table_name, null, UsersIllnessTable.Col_userID + " = ? and " +
                UsersIllnessTable.Col_sIllnessDate + " <= ? and (" + UsersIllnessTable.Col_eIllnessDate + " is null or "
                + UsersIllnessTable.Col_eIllnessDate + " >= ?)", new String[]{String.valueOf(user_id),
                simpleDateFormat.format(d), simpleDateFormat.format(d)}, null, null, null);

        ArrayList<Illness> list = new ArrayList<>();

        if (c.moveToFirst()){
            do {

                Illness p = null;
                long id = c.getLong(c.getColumnIndex(UsersIllnessTable._ID));
                String illnessType = c.getString(c.getColumnIndex(UsersIllnessTable.Col_illnessType));
                long illnessNameID = c.getLong(c.getColumnIndex(UsersIllnessTable.Col_illnessNameID));

                String temp = c.getString(c.getColumnIndex(UsersIllnessTable.Col_eIllnessDate));
                Date endDate = null;

                if (temp != null) {
                    try {
                        endDate = simpleDateFormat.parse(temp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                IllnessName in = getSTIName(illnessNameID);
                p = new Illness(id, user_id, illnessType, in, d, endDate);

                fillIllnessMeds(p);
                list.add(p);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }

    // filling the list of Illness' meds
    public void fillIllnessMeds(Illness illness){

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(IllnessDetailsTable.table_name, null, IllnessDetailsTable.Col_illnessID + " = ? ",
                new String[]{String.valueOf(illness.getId())}, null, null, null);

        IllnessMed mi = null;
        if (c.moveToFirst()){

            do {

                long id = c.getLong(c.getColumnIndex(IllnessDetailsTable._ID));

                String medName = c.getString(c.getColumnIndex(IllnessDetailsTable.Col_illnessMed));

                mi = new IllnessMed(id, illness, medName);
                illness.add(mi);
            } while (c.moveToNext());

        }
        db.close();
    }



    // getting Meals entries
    public ArrayList<Meal> getMealAtDate(Date d, long user_id){

        ArrayList<Meal> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);


        Cursor c = db.query(UsersMealsTable.table_name, null, UsersMealsTable.Col_userId + " = ? and " + UsersMealsTable.Col_mealDate +
                " = ?", new String[]{String.valueOf(user_id), simpleDateFormat.format(d)}, null, null, null);

        Meal p = null;
        if (c.moveToFirst()){
            do {

                long id = c.getLong(c.getColumnIndex(UsersMealsTable._ID));
                String mealType = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealType));
                String mealName = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealName));
                String mealTime = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealTime));

                p = new Meal(id, user_id, mealType, mealName, d, mealTime);

                fillMealItems(p);
                list.add(p);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }

    // filling meal's items list
    public void fillMealItems(Meal meal){

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(MealDetailsTable.table_name, null, MealDetailsTable.Col_mealID + " = ? ",
                new String[]{String.valueOf(meal.getId())}, null, null, null);

        MealItem mi = null;
        if (c.moveToFirst()){

            do {

                long id = c.getLong(c.getColumnIndex(MealDetailsTable._ID));
                String itemName = c.getString(c.getColumnIndex(MealDetailsTable.Col_mealItem));

                mi = new MealItem(id, meal, itemName);
                meal.add(mi);
            } while (c.moveToNext());

        }
        db.close();
    }


    // getting Mood entries
    public ArrayList<Mood> getMoodAtDate(Date d, long user_id){

        ArrayList<Mood> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersMoodTable.table_name, null, UsersMoodTable.Col_userId + " = ? and " + UsersMoodTable.Col_moodDate +
                " = ?", new String[]{String.valueOf(user_id), simpleDateFormat.format(d)}, null, null, null);

        Mood p = null;
        if (c.moveToFirst()){
            do {

                long id = c.getLong(c.getColumnIndex(UsersMoodTable._ID));
                long moodNameID = c.getLong(c.getColumnIndex(UsersMoodTable.Col_moodNameID));
                String moodReason = c.getString(c.getColumnIndex(UsersMoodTable.Col_moodReason));
                String moodTime = c.getString(c.getColumnIndex(UsersMoodTable.Col_moodTime));

                MoodName mn = getMoodName(moodNameID);
                p = new Mood(id, user_id, mn, moodReason, d, moodTime);
                list.add(p);
            } while (c.moveToNext());
        }
        db.close();
        return list;

    }


    // getting Exercises entries for display by a date
    public ArrayList<Exercise> getExerciseAtDate(Date d, long user_id){

        ArrayList<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersExerciseTable.table_name, null, UsersExerciseTable.Col_userID + " = ? and "+
                UsersExerciseTable.Col_exerDate+ " = ?", new String[]{String.valueOf(user_id), simpleDateFormat.format(d)},
                null, null, null);

        Exercise p = null;
        if (c.moveToFirst()){

            do {
                long id = c.getLong(c.getColumnIndex(UsersExerciseTable._ID));

                long exerTypeId = c.getLong(c.getColumnIndex(UsersExerciseTable.Col_exerTypeID));
                String exerDur = c.getString(c.getColumnIndex(UsersExerciseTable.Col_exerDur));
                String startExerTime = c.getString(c.getColumnIndex(UsersExerciseTable.Col_sExerTime));
                String endExerTime = c.getString(c.getColumnIndex(UsersExerciseTable.Col_eExerTime));

                ExerciseType ex = getExerType(exerTypeId);
                p = new Exercise(id, user_id, ex, d, startExerTime, endExerTime, exerDur);
                list.add(p);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }

    // getting Sleeping entries
    public ArrayList<Sleeping> getSleepingAtDate(Date d, long user_id){

        ArrayList<Sleeping> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersSleepTable.table_name, null, UsersSleepTable.Col_userId + " = ? and "+ UsersSleepTable.Col_sleepDate+
                " = ?", new String[]{String.valueOf(user_id), simpleDateFormat.format(d)}, null, null, null);

        Sleeping p = null;
        if (c.moveToFirst()){

            do {
                long id = c.getLong(c.getColumnIndex(UsersSleepTable._ID));

                String sleepDur = c.getString(c.getColumnIndex(UsersSleepTable.Col_sleepDur));
                String startTime = c.getString(c.getColumnIndex(UsersSleepTable.Col_sSleepTime));
                String endTime = c.getString(c.getColumnIndex(UsersSleepTable.Col_eSleepTime));

                p = new Sleeping(id, user_id, d, startTime, endTime, sleepDur);
                list.add(p);
            }while(c.moveToNext());
        }
        db.close();
        return list;
    }


    // getting physical entries to json file
    public List<Physical> getPhysicalInDateRange(Date from, Date to, long user_id) {

        ArrayList<Physical> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersPhysicalTable.table_name, null, UsersPhysicalTable.Col_userID + " = ? and " +
                UsersPhysicalTable.Col_date + " between ? and ?", new String[]{String.valueOf(user_id), simpleDateFormat.format(from),
                simpleDateFormat.format(to)}, null, null, null);

        Physical p = null;
        if (c.moveToFirst()){

            do {
                long id = c.getLong(c.getColumnIndex(UsersPhysicalTable._ID));

                float weight = -1;
                if (!c.isNull(c.getColumnIndex(UsersPhysicalTable.Col_weight)))
                    weight = c.getFloat(c.getColumnIndex(UsersPhysicalTable.Col_weight));

                float height = -1;
                if (!c.isNull(c.getColumnIndex(UsersPhysicalTable.Col_height)))
                    height = c.getFloat(c.getColumnIndex(UsersPhysicalTable.Col_height));

                Date d = null;
                if (!c.isNull(c.getColumnIndex(UsersPhysicalTable.Col_date)))
                    try {
                        d = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersPhysicalTable.Col_date)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                p = new Physical(id, user_id, weight, height, d);
                list.add(p);
            } while (c.moveToNext());

        }
        db.close();
        return list;
    }


    // getting mood entries to json file
    public List<Mood> getMoodInDateRange(Date from, Date to, long user_id){

        ArrayList<Mood> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersMoodTable.table_name, null, UsersMoodTable.Col_userId + " = ? and " +
                UsersMoodTable.Col_moodDate + " between ? and ?", new String[]{String.valueOf(user_id),
                simpleDateFormat.format(from), simpleDateFormat.format(to)}, null, null, null);

        Mood p = null;
        if (c.moveToFirst()){
            do {

                long id = c.getLong(c.getColumnIndex(UsersMoodTable._ID));

                long moodNameID = c.getLong(c.getColumnIndex(UsersMoodTable.Col_moodNameID));
                String moodReason = c.getString(c.getColumnIndex(UsersMoodTable.Col_moodReason));
                String moodTime = c.getString(c.getColumnIndex(UsersMoodTable.Col_moodTime));

                Date d = null;
                if (!c.isNull(c.getColumnIndex(UsersMoodTable.Col_moodDate)))
                    try {
                        d = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersMoodTable.Col_moodDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                MoodName mn = getMoodName(moodNameID);
                p = new Mood(id, user_id, mn, moodReason, d, moodTime);
                list.add(p);
            } while (c.moveToNext());
        }
        db.close();
        return list;
    }



    // getting Exercises entries to json file
    public List<Exercise> getExerciseInDateRange(Date from, Date to, long user_id){

        ArrayList<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersExerciseTable.table_name, null, UsersExerciseTable.Col_userID + " = ? and " +
                UsersExerciseTable.Col_exerDate + " between ? and ?", new String[]{String.valueOf(user_id),
                simpleDateFormat.format(from), simpleDateFormat.format(to)}, null, null, null);

        Exercise p = null;
        if (c.moveToFirst()){

            do {
                long id = c.getLong(c.getColumnIndex(UsersMealsTable._ID));

                long exerTypeId = c.getLong(c.getColumnIndex(UsersExerciseTable.Col_exerTypeID));
                String exerDur = c.getString(c.getColumnIndex(UsersExerciseTable.Col_exerDur));
                String startExerTime = c.getString(c.getColumnIndex(UsersExerciseTable.Col_sExerTime));
                String endExerTime = c.getString(c.getColumnIndex(UsersExerciseTable.Col_eExerTime));

                Date d = null;
                if (!c.isNull(c.getColumnIndex(UsersExerciseTable.Col_exerDate)))
                    try {
                        d = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersExerciseTable.Col_exerDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                ExerciseType ex = getExerType(exerTypeId);
                p = new Exercise(id, user_id, ex, d, startExerTime,endExerTime, exerDur);
                list.add(p);

            } while (c.moveToNext());
        }
        db.close();
        return list;
    }


    // getting sleeping entries to json file
    public List<Sleeping> getSleepingInDateRange(Date from, Date to, long user_id){

        ArrayList<Sleeping> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersSleepTable.table_name, null, UsersSleepTable.Col_userId + " = ? and " +
                UsersSleepTable.Col_sleepDate + " between ? and ?", new String[]{String.valueOf(user_id),
                simpleDateFormat.format(from), simpleDateFormat.format(to)}, null, null, null);

        if (c.moveToFirst()) {
            do {
                Sleeping p = null;
                long id = c.getLong(c.getColumnIndex(UsersSleepTable._ID));

                String sleepDur = c.getString(c.getColumnIndex(UsersSleepTable.Col_sleepDur));
                String startTime = c.getString(c.getColumnIndex(UsersSleepTable.Col_sSleepTime));
                String endTime = c.getString(c.getColumnIndex(UsersSleepTable.Col_eSleepTime));

                Date date = null;
                if (!c.isNull(c.getColumnIndex(UsersSleepTable.Col_sleepDate)))
                    try {
                        date = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersSleepTable.Col_sleepDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                p = new Sleeping(id, user_id, date, startTime, endTime, sleepDur);
                list.add(p);
            } while (c.moveToNext());
        }
        db.close();
        return list;
    }




    // getting physical entries to json file
    public List<Meal> getMealsInDateRange(Date from, Date to, long user_id) {

        ArrayList<Meal> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersMealsTable.table_name, null, UsersMealsTable.Col_userId + " = ? and " +
                UsersMealsTable.Col_mealDate + " between ? and ?", new String[]{String.valueOf(user_id), simpleDateFormat.format(from),
                simpleDateFormat.format(to)}, null, null, null);

        if (c.moveToFirst()){

            do {
                Meal p = null;
                long id = c.getLong(c.getColumnIndex(UsersMealsTable._ID));

                String mealType = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealType));
                String mealName = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealName));
                String mealTime = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealTime));

                Date date = null;
                if (!c.isNull(c.getColumnIndex(UsersMealsTable.Col_mealDate)))
                    try {
                        date = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersMealsTable.Col_mealDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                p = new Meal(id, user_id, mealType, mealName, date, mealTime);

                fillMealItems(p);
                list.add(p);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }


    // getting Illness entries for download
    public List<Illness> getIllnessInDateRange(Date from, Date to, long user_id){

        ArrayList<Illness> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersIllnessTable.table_name, null, UsersIllnessTable.Col_userID + " = ? and " +
                UsersIllnessTable.Col_sIllnessDate + " between ? and ?", new String[]{String.valueOf(user_id),
                simpleDateFormat.format(from), simpleDateFormat.format(to)}, null, null, null);

        if (c.moveToFirst()){
            do {

                Illness p = null;
                long id = c.getLong(c.getColumnIndex(UsersIllnessTable._ID));

                String illnessType = c.getString(c.getColumnIndex(UsersIllnessTable.Col_illnessType));
                long illnessNameID = c.getLong(c.getColumnIndex(UsersIllnessTable.Col_illnessNameID));

                String temp = c.getString(c.getColumnIndex(UsersIllnessTable.Col_eIllnessDate));
                Date endDate = null;

                if (temp != null) {
                    try {
                        endDate = simpleDateFormat.parse(temp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Date startD = null;
                if (!c.isNull(c.getColumnIndex(UsersIllnessTable.Col_sIllnessDate)))
                    try {
                        startD = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersIllnessTable.Col_sIllnessDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                IllnessName in = getSTIName(illnessNameID);
                p = new Illness(id, user_id, illnessType, in, startD, endDate);

                fillIllnessMeds(p);
                list.add(p);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }


    // get sleeping entries by ID for edit
    public Sleeping getSleepingByID(long id, long user_id){

        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersSleepTable.table_name, null, UsersSleepTable.Col_userId + " = ? and " +
                UsersSleepTable._ID + " = ? ", new String[]{String.valueOf(user_id),
                String.valueOf(id)}, null, null, null);

        Sleeping p = null;
        if (c.moveToFirst()) {

            String sleepDur = c.getString(c.getColumnIndex(UsersSleepTable.Col_sleepDur));
            String startTime = c.getString(c.getColumnIndex(UsersSleepTable.Col_sSleepTime));
            String endTime = c.getString(c.getColumnIndex(UsersSleepTable.Col_eSleepTime));

            Date date = null;
            if (!c.isNull(c.getColumnIndex(UsersSleepTable.Col_sleepDate)))
                try {
                    date = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersSleepTable.Col_sleepDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            p = new Sleeping(id, user_id, date, startTime, endTime, sleepDur);
        }
        db.close();
        return p;
    }


    // get mood entries by ID for edit
    public Mood getMoodByID(long id, long user_id) {

        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersMoodTable.table_name, null, UsersMoodTable.Col_userId + " = ? and " +
                UsersMoodTable._ID + " = ? ", new String[]{String.valueOf(user_id),
                String.valueOf(id)}, null, null, null);

        Mood p = null;
        if (c.moveToFirst()) {

            long moodNameID = c.getLong(c.getColumnIndex(UsersMoodTable.Col_moodNameID));
            String moodReason = c.getString(c.getColumnIndex(UsersMoodTable.Col_moodReason));
            String moodTime = c.getString(c.getColumnIndex(UsersMoodTable.Col_moodTime));

            Date date = null;
            if (!c.isNull(c.getColumnIndex(UsersMoodTable.Col_moodDate)))
                try {
                    date = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersMoodTable.Col_moodDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            MoodName mn = getMoodName(moodNameID);
            p = new Mood(id, user_id, mn, moodReason, date, moodTime);

        }
        db.close();
        return p;
    }


    // get exercise entries by ID for edit
    public Exercise getExerciseByID(long id, long user_id) {

        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersExerciseTable.table_name, null, UsersExerciseTable.Col_userID + " = ? and " +
                UsersExerciseTable._ID + " = ? ", new String[]{String.valueOf(user_id),
                String.valueOf(id)}, null, null, null);

        Exercise p = null;
        if (c.moveToFirst()) {

            long exerTypeId = c.getLong(c.getColumnIndex(UsersExerciseTable.Col_exerTypeID));
            String exerDur = c.getString(c.getColumnIndex(UsersExerciseTable.Col_exerDur));
            String startExerTime = c.getString(c.getColumnIndex(UsersExerciseTable.Col_sExerTime));
            String endExerTime = c.getString(c.getColumnIndex(UsersExerciseTable.Col_eExerTime));

            Date date = null;
            if (!c.isNull(c.getColumnIndex(UsersExerciseTable.Col_exerDate)))
                try {
                    date = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersExerciseTable.Col_exerDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            ExerciseType ex = getExerType(exerTypeId);
            p = new Exercise(id, user_id, ex, date, startExerTime, endExerTime, exerDur);

        }
        db.close();
        return p;
    }



    // get physical entries by ID for edit
    public Physical getPhysicalByID(long id, long user_id) {

        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersPhysicalTable.table_name, null, UsersPhysicalTable.Col_userID + " = ? and " +
                UsersPhysicalTable._ID + " = ? ", new String[]{String.valueOf(user_id),
                String.valueOf(id)}, null, null, null);

        Physical p = null;
        if (c.moveToFirst()) {

            float weight = c.getFloat(c.getColumnIndex(UsersPhysicalTable.Col_weight));
            float height = c.getFloat(c.getColumnIndex(UsersPhysicalTable.Col_height));

            Date date = null;
            if (!c.isNull(c.getColumnIndex(UsersExerciseTable.Col_exerDate)))
                try {
                    date = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersExerciseTable.Col_exerDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            p = new Physical(id, user_id, weight, height, date);

        }
        db.close();
        return p;
    }


    // getting Illness entries for edit
    public Illness getIllnessByID(long id, long user_id){

        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersIllnessTable.table_name, null, UsersIllnessTable.Col_userID + " = ? and " +
                UsersIllnessTable._ID + " = ? ", new String[]{String.valueOf(user_id),
                String.valueOf(id)}, null, null, null);

        Illness p = null;
        if (c.moveToFirst()){

                String illnessType = c.getString(c.getColumnIndex(UsersIllnessTable.Col_illnessType));
                long illnessNameID = c.getLong(c.getColumnIndex(UsersIllnessTable.Col_illnessNameID));

                String temp = c.getString(c.getColumnIndex(UsersIllnessTable.Col_eIllnessDate));
                Date endDate = null;

                if (temp != null) {
                    try {
                        endDate = simpleDateFormat.parse(temp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Date startDate = null;
                if (!c.isNull(c.getColumnIndex(UsersIllnessTable.Col_sIllnessDate)))
                    try {
                        startDate = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersIllnessTable.Col_sIllnessDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

            IllnessName in = getSTIName(illnessNameID);
            p = new Illness(id, user_id, illnessType, in, startDate, endDate);
                fillIllnessMeds(p);
        }
        db.close();
        return p;
    }


    // getting physical entries to json file
    public Meal getMealByID(long id, long user_id) {

        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SQLite_DatePattern);

        Cursor c = db.query(UsersMealsTable.table_name, null, UsersMealsTable.Col_userId + " = ? and " +
                UsersMealsTable._ID + " = ? ", new String[]{String.valueOf(user_id),
                String.valueOf(id)}, null, null, null);

        Meal p = null;
        if (c.moveToFirst()){

                String mealType = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealType));
                String mealName = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealName));
                String mealTime = c.getString(c.getColumnIndex(UsersMealsTable.Col_mealTime));

                Date d = null;
                if (!c.isNull(c.getColumnIndex(UsersMealsTable.Col_mealDate)))
                    try {
                        d = simpleDateFormat.parse(c.getString(c.getColumnIndex(UsersMealsTable.Col_mealDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                p = new Meal(id, user_id, mealType, mealName, d, mealTime);
                fillMealItems(p);
        }
        db.close();
        return p;
    }




    // getting physical entries to json file
    public ExerciseType getExerType(long id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(ExerciseTypeTable.table_name, null, ExerciseTypeTable._ID + " = ? ",
                new String[]{String.valueOf(id)}, null, null, null);

        ExerciseType p = null;
        if (c.moveToFirst()){

            String exerType = c.getString(c.getColumnIndex(ExerciseTypeTable.Col_exerType));

            p = new ExerciseType(exerType, id);
        }
        db.close();
        return p;
    }


    public Cursor getExerciseTypeCursor(long user_id){

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(ExerciseTypeTable.table_name, null, "( "+ExerciseTypeTable.Col_userID + " is null or "+
                ExerciseTypeTable.Col_userID+ " = ? )", new String[]{String.valueOf(user_id)}, null, null, null);

        return c;
    }


    public MoodName getMoodName(long id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(MoodNameTable.table_name, null, MoodNameTable._ID + " = ? ",
                new String[]{String.valueOf(id)}, null, null, null);

        MoodName p = null;
        if (c.moveToFirst()){

            String moodName = c.getString(c.getColumnIndex(MoodNameTable.Col_moodName));

            p = new MoodName(moodName, id);
        }
        db.close();
        return p;
    }


    public Cursor getMoodNameCursor(long user_id){

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(MoodNameTable.table_name, null, "( "+MoodNameTable.Col_userID + " is null or "+MoodNameTable.Col_userID+
                " = ? )", new String[]{String.valueOf(user_id)}, null, null, null);

        return c;
    }


    // insert users' entries into UsersSleepTable
    public boolean insert_moodName(long user_id, String moodName){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // input values in col
        values.put(MoodNameTable.Col_userID, user_id);
        values.put(MoodNameTable.Col_moodName, moodName);


        long id = db.insert(MoodNameTable.table_name, null, values);

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }

        return true;
    }


    public boolean insert_exerType(long user_id, String exerType){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // input values in col
        values.put(ExerciseTypeTable.Col_userID, user_id);
        values.put(ExerciseTypeTable.Col_exerType, exerType);

        long id = db.insert(ExerciseTypeTable.table_name, null, values);

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }

        return true;
    }

    public IllnessName getSTIName(long id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(STINameTable.table_name, null, STINameTable._ID + " = ? ",
                new String[]{String.valueOf(id)}, null, null, null);

        IllnessName p = null;
        if (c.moveToFirst()){

            String STIname = c.getString(c.getColumnIndex(STINameTable.Col_illnessName));
            p = new IllnessName(STIname, id);
        }
        db.close();
        return p;
    }


    public Cursor getSTINameCursor(long user_id){

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(STINameTable.table_name, null, "( "+STINameTable.Col_userID + " is null or "+STINameTable.Col_userID+
                " = ? )", new String[]{String.valueOf(user_id)}, null, null, null);

        return c;
    }


    public Cursor getLTINameCursor(long user_id){

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(LTINameTable.table_name, null, "( "+LTINameTable.Col_userID + " is null or "+LTINameTable.Col_userID+
                " = ? )", new String[]{String.valueOf(user_id)}, null, null, null);

        return c;
    }


    public boolean insert_LTIName(long user_id, String illnessName){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // input values in col
        values.put(LTINameTable.Col_userID, user_id);
        values.put(LTINameTable.Col_illnessName, illnessName);

        long id = db.insert(LTINameTable.table_name, null, values);

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }

        return true;
    }


    public boolean insert_STI(long user_id, String illnessName){

        // allow to write into database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // input values in col
        values.put(STINameTable.Col_userID, user_id);
        values.put(STINameTable.Col_illnessName, illnessName);

        long id = db.insert(STINameTable.table_name, null, values);

        // Closing database
        db.close();

        if (id == -1){
            return false;
        }

        return true;
    }
}


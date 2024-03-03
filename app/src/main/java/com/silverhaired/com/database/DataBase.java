package com.silverhaired.com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;
import com.silverhaired.com.database.databaseTableFiled.ExerciseReportRecord;


public class DataBase extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "MoneyBookDB";
    public static final String BODY_INFO_RECORD = "BodyInfoRecord";
    public static final String EXERCISE_REPORT_RECORD = "ExerciseReportRecord";

    public static final int DATABASE_VERSION = 1;

    private static DataBase mInstance;

    public DataBase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBase getInstance(Context aContext)
    {
        if (mInstance == null)
            mInstance = new DataBase(aContext);
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase aSqLiteDatabase)
    {
        String sql = "CREATE TABLE " + BODY_INFO_RECORD + "("
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BodyInfoRecord.ID + " not null,"
                + BodyInfoRecord.DATES + " TEXT not null,"
                + BodyInfoRecord.HEIGHT + " FLOAT not null,"
                + BodyInfoRecord.WEIGHT + " FLOAT not null,"
                + BodyInfoRecord.BLOOD_PRESSURE + " FLOAT not null,"
                + BodyInfoRecord.BMI + " FLOAT not null,"
                + BodyInfoRecord.RECOMMEND_CALORIE +" FLOAT" + ");";

        String sqlExercise = "CREATE TABLE " + EXERCISE_REPORT_RECORD + "("
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExerciseReportRecord.ID + " not null,"
                + ExerciseReportRecord.DATES + " TEXT not null,"
                + ExerciseReportRecord.NAME + " TEXT not null,"
                + ExerciseReportRecord.COUNT +" INTEGER" + ");";

        aSqLiteDatabase.execSQL(sql);
        aSqLiteDatabase.execSQL(sqlExercise);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}

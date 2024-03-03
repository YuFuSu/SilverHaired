package com.silverhaired.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;
import com.silverhaired.com.database.databaseTableFiled.ExerciseReportRecord;

import java.util.ArrayList;

public class DatabaseManager
{
    public String mExceptionString = "";

    private Context mContext = null;

    private SQLiteDatabase mReadableDatabase = null;
    private SQLiteDatabase mWritableDatabase = null;

    private DataBase mDataBase = null;

    private static DatabaseManager mInstance;

    public enum DBSuccess
    {
        SUCCESS,
        ERROR,
    }

    public DatabaseManager(Context aContext)
    {
        mContext = aContext;
        init();
    }

    public static DatabaseManager getInstance(Context aContext)
    {
        if (mInstance == null)
            mInstance = new DatabaseManager(aContext);

        return mInstance;
    }

    private boolean init()
    {
        try
        {
            if (openDatabase() != DBSuccess.SUCCESS)
                return false;
        } catch (Exception e)
        {
            mExceptionString = e.getMessage();
            return false;
        }
        return true;
    }

    private DBSuccess openDatabase()
    {
        try
        {
            if (mContext != null && mDataBase == null)
                mDataBase = DataBase.getInstance(mContext);

            if (mDataBase == null)
                return DBSuccess.ERROR;

            mReadableDatabase = mDataBase.getReadableDatabase();
            mWritableDatabase = mDataBase.getWritableDatabase();

            if (mReadableDatabase == null || mWritableDatabase == null)
                return DBSuccess.ERROR;

            return DBSuccess.SUCCESS;
        } catch (Exception e)
        {
            mExceptionString = e.getMessage();
            return DBSuccess.ERROR;
        }
    }

    public DBSuccess addExerciseReportRecord(ExerciseReportRecord aExerciseReportRecord)
    {
        DBSuccess success;
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ExerciseReportRecord.ID, aExerciseReportRecord.mId);
            contentValues.put(ExerciseReportRecord.DATES, aExerciseReportRecord.mDates);
            contentValues.put(ExerciseReportRecord.NAME, aExerciseReportRecord.mName);
            contentValues.put(ExerciseReportRecord.COUNT, aExerciseReportRecord.mCount);

            long lSuccess = mWritableDatabase.insert(DataBase.EXERCISE_REPORT_RECORD, null, contentValues);
            if (lSuccess == -1)
            {
                success = DBSuccess.ERROR;
            } else
            {
                success = DBSuccess.SUCCESS;
            }

        } catch (OutOfMemoryError e)
        {
            mExceptionString = e.getMessage();
            success = DBSuccess.ERROR;
        } catch (Exception e)
        {
            mExceptionString = e.getMessage();
            success = DBSuccess.ERROR;
        }
        return success;
    }

    public DBSuccess addBodyInfoRecord(BodyInfoRecord aBodyInfoRecord)
    {
        DBSuccess success;
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BodyInfoRecord.ID, aBodyInfoRecord.mId);
            contentValues.put(BodyInfoRecord.DATES, aBodyInfoRecord.mDates);
            contentValues.put(BodyInfoRecord.HEIGHT, aBodyInfoRecord.mHeight);
            contentValues.put(BodyInfoRecord.WEIGHT, aBodyInfoRecord.mWeight);
            contentValues.put(BodyInfoRecord.BLOOD_PRESSURE, aBodyInfoRecord.mBloodPressure);
            contentValues.put(BodyInfoRecord.BMI, aBodyInfoRecord.mBMI);
            contentValues.put(BodyInfoRecord.RECOMMEND_CALORIE, aBodyInfoRecord.mRecommendCalorie);

            long lSuccess = mWritableDatabase.insert(DataBase.BODY_INFO_RECORD, null, contentValues);
            if (lSuccess == -1)
            {
                success = DBSuccess.ERROR;
            } else
            {
                success = DBSuccess.SUCCESS;
            }

        } catch (OutOfMemoryError e)
        {
            mExceptionString = e.getMessage();
            success = DBSuccess.ERROR;
        } catch (Exception e)
        {
            mExceptionString = e.getMessage();
            success = DBSuccess.ERROR;
        }
        return success;
    }

    public DBSuccess getBodyInfoRecord(ArrayList<BodyInfoRecord> aDailyRecords)
    {
        DBSuccess success;
        Cursor cursor=null;
        try
        {
            cursor = mReadableDatabase.query(DataBase.BODY_INFO_RECORD, null, null, null, null, null, BodyInfoRecord.DATES+" desc");

            if (cursor.getCount() > 0)
            {
                BodyInfoRecord BodyInfoRecord;
                while (cursor.moveToNext())
                {
                    BodyInfoRecord= new BodyInfoRecord();
                    BodyInfoRecord.mId = cursor.getString(1);
                    BodyInfoRecord.mDates = cursor.getString(2);
                    BodyInfoRecord.mHeight = cursor.getFloat(3);
                    BodyInfoRecord.mWeight = cursor.getFloat(4);
                    BodyInfoRecord.mBloodPressure = cursor.getFloat(5);
                    BodyInfoRecord.mBMI = cursor.getFloat(6);
                    BodyInfoRecord.mRecommendCalorie = cursor.getFloat(7);
                    aDailyRecords.add(BodyInfoRecord);
                }
            }

            success = DBSuccess.SUCCESS;
        } catch (Exception e)
        {
            mExceptionString = e.getMessage();
            success = DBSuccess.ERROR;
        }

        return success;
    }

    public DBSuccess deleteReport(String aReportID)
    {
        DBSuccess aSuccess = DBSuccess.SUCCESS;

        try
        {
            long lSuccess = mWritableDatabase.delete(DataBase.BODY_INFO_RECORD, BodyInfoRecord.ID+" = '" + aReportID + "'", null);

            if (lSuccess == -1)
                aSuccess = DBSuccess.ERROR;
        }
        catch (OutOfMemoryError e)
        {
            mExceptionString = e.getMessage();
            aSuccess = DBSuccess.ERROR;
        }
        catch (Exception e)
        {
            mExceptionString = e.getMessage();
            aSuccess = DBSuccess.ERROR;
        }
        return aSuccess;
    }

    public DBSuccess getExerciseReportRecord(String aUserFirebaseUUid,ArrayList<ExerciseReportRecord> aExerciseReportRecord)
    {
        DBSuccess success;
        Cursor cursor=null;
        try
        {
            cursor = mReadableDatabase.query(DataBase.EXERCISE_REPORT_RECORD, null, ExerciseReportRecord.ID+" = '" + aUserFirebaseUUid + "'", null, null, null, BodyInfoRecord.DATES+" desc");

            if (cursor.getCount() > 0)
            {
                ExerciseReportRecord exerciseReportRecord;
                while (cursor.moveToNext())
                {
                    exerciseReportRecord= new ExerciseReportRecord();
                    exerciseReportRecord.mId = cursor.getString(1);
                    exerciseReportRecord.mDates = cursor.getString(2);
                    exerciseReportRecord.mName = cursor.getString(3);
                    exerciseReportRecord.mCount = cursor.getInt(4);
                    aExerciseReportRecord.add(exerciseReportRecord);
                }
            }

            success = DBSuccess.SUCCESS;
        } catch (Exception e)
        {
            mExceptionString = e.getMessage();
            success = DBSuccess.ERROR;
        }

        return success;
    }
}

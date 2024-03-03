package com.silverhaired.com.database.databaseTableFiled;

public class ExerciseReportRecord
{
    public static final String ID = "ExerciseReportRecordID";
    public static final String DATES = "Dates";
    public static final String NAME = "Name";
    public static final String COUNT = "Count";

    public String mId;
    public String mDates;
    public String mName;
    public int mCount;

    public ExerciseReportRecord()
    {
    }

    public ExerciseReportRecord(String aId, String aDate, String aName, int aCount)
    {
        mId = aId;
        mDates = aDate;
        mName = aName;
        mCount = aCount;

    }
}

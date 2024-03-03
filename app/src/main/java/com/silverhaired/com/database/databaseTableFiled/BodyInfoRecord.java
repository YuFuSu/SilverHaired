package com.silverhaired.com.database.databaseTableFiled;

public class BodyInfoRecord
{
    public static final String ID = "RecordID";
    public static final String DATES = "Dates";

    public static final String HEIGHT = "Height";
    public static final String WEIGHT = "Weight";

    public static final String BLOOD_PRESSURE = "BloodPressure";

    public static final String BMI = "BMI";
    public static final String RECOMMEND_CALORIE = "RecommendCalorie";

    public float mHeight;
    public float mWeight;
    public float mBloodPressure;
    public float mBMI;
    public float mRecommendCalorie;

    public String mId;
    public String mDates;

    public BodyInfoRecord()
    {
    }

    public BodyInfoRecord(String aId, String aDate, float aHeight, float aWeight,float aBloodPressure, float aBMI,  float aRecommendCalorie)
    {
        mId = aId;
        mDates = aDate;
        mHeight = aHeight;
        mWeight = aWeight;

        mBMI = aBMI;
        mBloodPressure = aBloodPressure;
        mRecommendCalorie = aRecommendCalorie;
    }
}

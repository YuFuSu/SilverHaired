package com.silverhaired.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager
{
    private static PreferencesManager sInstance = null;
    private SharedPreferences mPreferences = null;

    private static final String KEY_PHONE = "KEY_PHONE";
    private static final String KEY_REMIND_ONE = "KEY_REMIND_ONE";
    private static final String KEY_REMIND_TWO = "KEY_REMIND_TWO";
    private static final String KEY_REMIND_THREE = "KEY_REMIND_THREE";
    private static final String KEY_REMIND_FOUR = "KEY_REMIND_FOUR";
    private static final String KEY_REMIND_FIVE = "KEY_REMIND_FIVE";
    private static final String KEY_REMIND_SIX= "KEY_REMIND_SIX";
    private static final String KEY_REMIND_TIME_ONE = "KEY_REMIND_TIME_ONE";
    private static final String KEY_REMIND_TIME_TWO = "KEY_REMIND_TIME_TWO";
    private static final String KEY_REMIND_TIME_THREE = "KEY_REMIND_TIME_THREE";
    private static final String KEY_REMIND_TIME_FOUR = "KEY_REMIND_TIME_FOUR";
    private static final String KEY_REMIND_TIME_FIVE = "KEY_REMIND_TIME_FIVE";
    private static final String KEY_REMIND_TIME_SIX= "KEY_REMIND_TIME_SIX";


    private static final String KEY_REMIND_CONTENT_ONE = "KEY_REMIND_CONTENT_ONE";
    private static final String KEY_REMIND_CONTENT_TWO = "KEY_REMIND_CONTENT_TWO";
    private static final String KEY_REMIND_CONTENT_THREE = "KEY_REMIND_CONTENT_THREE";
    private static final String KEY_REMIND_CONTENT_FOUR = "KEY_REMIND_CONTENT_FOUR";
    private static final String KEY_REMIND_CONTENT_FIVE = "KEY_REMIND_CONTENT_FIVE";
    private static final String KEY_REMIND_CONTENT_SIX= "KEY_REMIND_CONTENT_SIX";
    private static final String KEY_REMIND_COUNT= "KEY_REMIND_COUNT";

    private static Context sContext;

    public static PreferencesManager getInstance(Context aContext)
    {
        sContext=aContext;
        if (sInstance == null)
            sInstance = new PreferencesManager();

        return sInstance;
    }

    private PreferencesManager(){
        if (mPreferences == null)
            mPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
    }

    public String getPhone()
    {
        return mPreferences.getString(KEY_PHONE, "");
    }

    public void setPhone(String aValue)
    {
        mPreferences.edit().putString(KEY_PHONE, aValue).apply();
    }

    public String getRemindTime1()
    {
        return mPreferences.getString(KEY_REMIND_TIME_ONE,"");
    }

    public void setRemindTime1(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_TIME_ONE, aValue).apply();
    }

    public String getRemindTime2()
    {
        return mPreferences.getString(KEY_REMIND_TIME_TWO,"");
    }

    public void setRemindTime2(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_TIME_TWO, aValue).apply();
    }

    public String getRemindTime3()
    {
        return mPreferences.getString(KEY_REMIND_TIME_THREE,"");
    }

    public void setRemindTime3(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_TIME_THREE, aValue).apply();
    }

    public String getRemindTime4()
    {
        return mPreferences.getString(KEY_REMIND_TIME_FOUR,"");
    }

    public void setRemindTime4(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_TIME_FOUR, aValue).apply();
    }

    public String getRemindTime5()
    {
        return mPreferences.getString(KEY_REMIND_TIME_FIVE,"");
    }

    public void setRemindTime5(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_TIME_FIVE, aValue).apply();
    }

    public String getRemindTime6()
    {
        return mPreferences.getString(KEY_REMIND_TIME_SIX,"");
    }

    public void setRemindTime6(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_TIME_SIX, aValue).apply();
    }

    public boolean getRemind1()
    {
        return mPreferences.getBoolean(KEY_REMIND_ONE,false);
    }

    public void setRemind1(boolean aValue)
    {
        mPreferences.edit().putBoolean(KEY_REMIND_ONE, aValue).apply();
    }

    public boolean getRemind2()
    {
        return mPreferences.getBoolean(KEY_REMIND_TWO,false);
    }

    public void setRemind2(boolean aValue)
    {
        mPreferences.edit().putBoolean(KEY_REMIND_TWO, aValue).apply();
    }

    public boolean getRemind3()
    {
        return mPreferences.getBoolean(KEY_REMIND_THREE,false);
    }

    public void setRemind3(boolean aValue)
    {
        mPreferences.edit().putBoolean(KEY_REMIND_THREE, aValue).apply();
    }

    public boolean getRemind4()
    {
        return mPreferences.getBoolean(KEY_REMIND_FOUR,false);
    }

    public void setRemind4(boolean aValue)
    {
        mPreferences.edit().putBoolean(KEY_REMIND_FOUR, aValue).apply();
    }

    public boolean getRemind5()
    {
        return mPreferences.getBoolean(KEY_REMIND_FIVE,false);
    }

    public void setRemind5(boolean aValue)
    {
        mPreferences.edit().putBoolean(KEY_REMIND_FIVE, aValue).apply();
    }

    public boolean getRemind6()
    {
        return mPreferences.getBoolean(KEY_REMIND_SIX,false);
    }

    public void setRemind6(boolean aValue)
    {
        mPreferences.edit().putBoolean(KEY_REMIND_SIX, aValue).apply();
    }

    public String getRemindContent1()
    {
        return mPreferences.getString(KEY_REMIND_CONTENT_ONE,"");
    }

    public void setRemindContent1(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_CONTENT_ONE, aValue).apply();
    }

    public String getRemindContent2()
    {
        return mPreferences.getString(KEY_REMIND_CONTENT_TWO,"");
    }

    public void setRemindContent2(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_CONTENT_TWO, aValue).apply();
    }

    public String getRemindContent3()
    {
        return mPreferences.getString(KEY_REMIND_CONTENT_THREE,"");
    }

    public void setRemindContent3(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_CONTENT_THREE, aValue).apply();
    }

    public String getRemindContent4()
    {
        return mPreferences.getString(KEY_REMIND_CONTENT_FOUR,"");
    }

    public void setRemindContent4(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_CONTENT_FOUR, aValue).apply();
    }

    public String getRemindContent5()
    {
        return mPreferences.getString(KEY_REMIND_CONTENT_FIVE,"");
    }

    public void setRemindContent5(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_CONTENT_FIVE, aValue).apply();
    }

    public String getRemindContent6()
    {
        return mPreferences.getString(KEY_REMIND_CONTENT_SIX,"");
    }

    public void setRemindContent6(String aValue)
    {
        mPreferences.edit().putString(KEY_REMIND_CONTENT_SIX, aValue).apply();
    }

    public int getRemindCount()
    {
        return mPreferences.getInt(KEY_REMIND_COUNT,0);
    }

    public void setRemindCount(int aValue)
    {
        mPreferences.edit().putInt(KEY_REMIND_COUNT, aValue).apply();
    }


}

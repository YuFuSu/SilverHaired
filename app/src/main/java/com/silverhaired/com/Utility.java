package com.silverhaired.com;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.silverhaired.com.structure.ExerciseStructure;
import com.silverhaired.com.structure.MenuStructure;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class Utility
{
    public static final int ACTIVITY_RESULT_ADD_ITEM = 10001;
    public static final int ACTIVITY_RESULT_CHANGE_CATEGORY = 10002;

    public static int mWindowWidth;

    public static final String MENU = "MENU";
    public static final String EXERCISE = "EXERCISE";
    public static final String PREVIEW = "PREVIEW";
    public static final String HAS_COLLECTION = "HAS_COLLECTION";
    public static final String COUNTER = "COUNTER";
    public static final String NAME = "NAME";

    public static final String ON_ACTIVITY_RESULT_CHANGE_CATEGORY_ITEM = "ON_ACTIVITY_RESULT_CHANGE_CATEGORY_ITEM";

    public static final String DATE_PATTERN = "yyyy-MM-dd_HH:mm:ss";

    public static long getCurrentUnixTime()
    {
        return System.currentTimeMillis();
    }

    public static String convertUnixTimeToString(long unixTime, String pattern)
    {
        return convertUnixTimeToString(unixTime, pattern, TimeZone.getTimeZone("UTC"));
    }

    public static String convertUnixTimeToString(long unixTime, String pattern, TimeZone targetTimeZone)
    {
        return convertDateToString(new Date(unixTime), pattern, targetTimeZone);
    }

    public static String convertDateToString(Date date, String pattern, TimeZone targetTimeZone)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
        dateFormat.setTimeZone(targetTimeZone);
        return dateFormat.format(date);
    }

    public static String getUUID()
    {
        String aTempUUID = UUID.randomUUID().toString();
        String[] aBuffer = aTempUUID.split("-");
        String aUUID = "";
        for (int i = 0; i < aBuffer.length; i++)
        {
            aUUID += aBuffer[i];
        }
        String bUUID = aUUID.substring(0, 20);
        return bUUID;
    }

    public static Bitmap getImageFromAssetsFile(Context aContext, String aFileName)
    {
        Bitmap image = null;
        AssetManager am = aContext.getResources().getAssets();

        try
        {
            InputStream is = am.open(aFileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    public static ArrayList<MenuStructure> getMenuAssetsFile(Context aContext)
    {
        AssetManager am = aContext.getResources().getAssets();
        ArrayList<MenuStructure> menuStructure = new ArrayList<>();
        try
        {
            String[] fileName = am.list("menu");
            for(String file:fileName)
            {
               if(!file.equals("images")){
                   String temp = loadJSONFromAsset(aContext,"menu/"+file);
                   MenuStructure menuStructures =  MenuStructure.fromJson(temp);
                   menuStructure.add(menuStructures);
               }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return menuStructure;
    }

    public static ArrayList<ExerciseStructure> getExerciseAssetsFile(Context aContext)
    {
        AssetManager am = aContext.getResources().getAssets();
        ArrayList<ExerciseStructure> exerciseStructure = new ArrayList<>();
        try
        {
            String[] fileName = am.list("exercise");
            for(String file:fileName)
            {
                if(!file.equals("videos")){
                    String temp = loadJSONFromAsset(aContext,"exercise/"+file);
                    ExerciseStructure menuStructures =  ExerciseStructure.fromJson(temp);
                    exerciseStructure.add(menuStructures);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return exerciseStructure;
    }

    public static String getJson(Object aObject)
    {
        Gson gson = new Gson();
        String result = gson.toJson(aObject);
        return result;
    }

    public static String loadJSONFromAsset(Context aContext,String aFileName) {
        String json = null;
        try {
            InputStream is = aContext.getAssets().open(aFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // 去除前後空格後，是長度1以上的字串。
    public static boolean isValid(String aString)
    {
        return (aString != null && aString.trim().length() > 0);
    }

}

package com.silverhaired.com.structure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MenuStructure implements Serializable
{
    public static MenuStructure fromJson(String s)
    {
        return new Gson().fromJson(s, MenuStructure.class);
    }
    public String toString() {
        return new Gson().toJson(this);
    }

    @SerializedName("name")
    public String mName;

    @SerializedName("suggest")
    public String mSuggest;

    @SerializedName("menu_detail")
    public ArrayList<MenuDetailStructure> mMenuDetailStructure;
}

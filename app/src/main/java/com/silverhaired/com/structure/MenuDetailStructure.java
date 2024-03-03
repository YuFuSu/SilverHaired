package com.silverhaired.com.structure;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuDetailStructure implements Serializable
{
    @SerializedName("food_name")
    public String mName;

    @SerializedName("image_path")
    public String mImagePath;

    @SerializedName("calorie")
    public String mCalorie;
}

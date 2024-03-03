package com.silverhaired.com.structure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ExerciseStructure implements Serializable
{
    public static ExerciseStructure fromJson(String s)
    {
        return new Gson().fromJson(s, ExerciseStructure.class);
    }
    public String toString() {
        return new Gson().toJson(this);
    }

    @SerializedName("name")
    public String mName;

    @SerializedName("exercise_detail")
    public ArrayList<ExerciseDetailStructure> mExerciseDetailStructure;
}

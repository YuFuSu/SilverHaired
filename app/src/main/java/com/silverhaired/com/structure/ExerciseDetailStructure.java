package com.silverhaired.com.structure;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExerciseDetailStructure implements Serializable
{
    @SerializedName("exercise_name")
    public String mName;

    @SerializedName("suggest")
    public String mSuggest;

    @SerializedName("video_path")
    public String mVideoPath;
}

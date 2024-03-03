package com.silverhaired.com;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.webkit.WebViewAssetLoader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.silverhaired.com.database.DatabaseManager;
import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;
import com.silverhaired.com.database.databaseTableFiled.ExerciseReportRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExercisePreviewActivity extends Activity
{
    private final String mimeType = "text/html";
    private final String encoding = "UTF-8";//"base64";
    private String USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";

    private String mVideoPath;
    private String mName;
    private boolean mHasCollection;
    private int mCounter;
    private WebView mVideoWV;
    private DatabaseManager mDatabaseManager;
    private boolean mHasRecord=false;

    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_priview_activity_layout);
        Bundle getInfo = this.getIntent().getExtras();
        mVideoPath = getInfo.getString(Utility.PREVIEW);
        mName = getInfo.getString(Utility.NAME);
        mHasCollection = getInfo.getBoolean(Utility.HAS_COLLECTION);
        mCounter = getInfo.getInt(Utility.COUNTER);

        init();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mVideoWV.destroy();
    }

    private void init()
    {
        mUser=FirebaseAuth.getInstance().getCurrentUser();
        String uid=mUser.getUid();

        mVideoWV = findViewById(R.id.webview_video);

        mDatabaseManager = DatabaseManager.getInstance(ExercisePreviewActivity.this);
        ArrayList<ExerciseReportRecord> exerciseReportRecord = new ArrayList<>();

        mDatabaseManager.getExerciseReportRecord(uid,exerciseReportRecord);

        for(ExerciseReportRecord ex:exerciseReportRecord)
        {
            if(ex.mName.equals(mName))
            {
                mHasRecord = true;
                break;
            }
        }

        WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();

        mVideoWV.setWebViewClient(new WebViewClient()
        {
            private WebView view;
            private WebResourceRequest request;

            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                this.view = view;
                this.request = request;
                return assetLoader.shouldInterceptRequest(request.getUrl());
            }
        });

        mVideoWV.getSettings().setJavaScriptEnabled(true);
        mVideoWV.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mVideoWV.getSettings().setAllowContentAccess(true);
        mVideoWV.getSettings().setAllowFileAccess(true);
        mVideoWV.getSettings().setLoadWithOverviewMode(true);
        mVideoWV.getSettings().setUseWideViewPort(true);
        mVideoWV.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mVideoWV.getSettings().setUserAgentString(USERAGENT);
        mVideoWV.getSettings().setLoadsImagesAutomatically(true);

        mVideoWV.loadUrl(mVideoPath);
        mVideoWV.loadDataWithBaseURL("", mVideoPath, mimeType, encoding, "");

        updateCount();

        if(!mHasRecord)
        {
            String createTime = Utility.convertUnixTimeToString(Utility.getCurrentUnixTime(), Utility.DATE_PATTERN);
            String exerciseReportRecordID = mUser.getUid();

            ExerciseReportRecord exerciseReportRecords= new ExerciseReportRecord(exerciseReportRecordID,createTime,mName,mCounter);
            DatabaseManager.DBSuccess success = mDatabaseManager.addExerciseReportRecord(exerciseReportRecords);
        }

    }


    private void updateCount()
    {
        String uid=mUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(mHasCollection)
        {
            mCounter +=1;
            // Create a new user with a first and last name
            Map<String, Object> counter = new HashMap<>();
            counter.put("count", mCounter);

            db.collection("Users").document(uid).collection(mName).document("Counter").update(counter).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void unused)
                {
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                }
            });
        }
        else
        {
            // Create a new user with a first and last name
            Map<String, Object> counter = new HashMap<>();
            counter.put("count", 1);

            db.collection("Users").document(uid).collection(mName).document("Counter").set(counter).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void unused)
                {
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                }
            });
        }
    }
}

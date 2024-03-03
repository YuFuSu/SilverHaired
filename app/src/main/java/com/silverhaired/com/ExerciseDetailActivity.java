package com.silverhaired.com;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.webkit.WebViewAssetLoader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.silverhaired.com.structure.ExerciseStructure;

import java.util.Random;

public class ExerciseDetailActivity extends Activity
{
    private final String mimeType = "text/html";
    private final String encoding = "UTF-8";//"base64";
    private String USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";

    private int mRandomNumber = 0;

    private int mCounter=0;

    private boolean mHasCollection = true;

    private WebView mVideoWV;

    private Button mStartBT;

    private ImageView mBackIV;
    private TextView mDescriptionTV,mTitleTV;
    private ExerciseStructure mExerciseStructure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail_activity_layout);
        mExerciseStructure = (ExerciseStructure) getIntent().getSerializableExtra(Utility.EXERCISE);

        init();
        initClick();
    }

    private void init()
    {

        getRandom(mExerciseStructure.mExerciseDetailStructure.size());
        queryData();
        mStartBT = findViewById(R.id.button_start);

        mDescriptionTV = findViewById(R.id.textview_description);
        mBackIV = findViewById(R.id.imageview_back);
        mTitleTV = findViewById(R.id.textview_titles);
        mVideoWV = findViewById(R.id.webview_video);

        mTitleTV.setText(mExerciseStructure.mName);
        mDescriptionTV.setText(mExerciseStructure.mExerciseDetailStructure.get(mRandomNumber).mSuggest);

        WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();

        mVideoWV.setWebViewClient(new WebViewClient() {
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

        mVideoWV.loadUrl(mExerciseStructure.mExerciseDetailStructure.get(mRandomNumber).mVideoPath);
        mVideoWV.loadDataWithBaseURL("", mExerciseStructure.mExerciseDetailStructure.get(mRandomNumber).mVideoPath, mimeType, encoding, "");
    }

    private void initClick()
    {
        mBackIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        mStartBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ExerciseDetailActivity.this,ExercisePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Utility.PREVIEW, mExerciseStructure.mExerciseDetailStructure.get(mRandomNumber).mVideoPath);
                bundle.putString(Utility.NAME, mExerciseStructure.mExerciseDetailStructure.get(mRandomNumber).mName);
                bundle.putBoolean(Utility.HAS_COLLECTION,mHasCollection);
                bundle.putInt(Utility.COUNTER,mCounter);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void queryData()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null)
        {
            String uid=user.getUid();
            db.collection("Users").document(uid).collection(mExerciseStructure.mExerciseDetailStructure.get(mRandomNumber).mName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        //代表沒有新增過這個集合
                        if(task.getResult().isEmpty())
                        {
                            mHasCollection = false;

                        }else
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                mCounter = Integer.valueOf(document.getData().get("count").toString());
                            }
                        }
                    } else {
                    }
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                }
            });
        }
        else{
            Toast.makeText(ExerciseDetailActivity.this,"Error",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void getRandom(int aSize)
    {
        Random random =new Random();
        mRandomNumber = random.nextInt(aSize);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mVideoWV.destroy();
    }
}

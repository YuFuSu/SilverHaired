package com.silverhaired.com;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.adapter.ExerciseAdapter;
import com.silverhaired.com.callbacks.ExerciseClickCallback;
import com.silverhaired.com.structure.ExerciseStructure;

import java.util.ArrayList;

public class ExerciseActivity extends Activity implements ExerciseClickCallback
{

    private ImageView mBackIV;
    private RecyclerView mExerciseRV;
    private ExerciseAdapter mExerciseAdapter;
    private ProgressDialog mLoadingDialog = null;
    private ArrayList<ExerciseStructure> mExerciseStructure;

    public interface onAssetsCallback
    {
        void onAssetsResult(int aResultCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity_layout);
        init();
        initClick();
    }

    private void init()
    {
        mBackIV = findViewById(R.id.imageview_back);
        mExerciseRV = findViewById(R.id.recyclerView_exercise);

        if (mLoadingDialog == null)
        {
            mLoadingDialog = new ProgressDialog(this, R.style.transparentDialog);
            mLoadingDialog.setCancelable(false);
        }

        mExerciseStructure = new ArrayList<>();

        new ReadAssetsAsyncTask(new onAssetsCallback()
        {
            @Override
            public void onAssetsResult(int aResultCode)
            {
                mExerciseAdapter = new ExerciseAdapter(mExerciseStructure,ExerciseActivity.this,ExerciseActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExerciseActivity.this);
                mExerciseRV.setLayoutManager(linearLayoutManager);
                mExerciseRV.setAdapter(mExerciseAdapter);
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
    }

    @Override
    public void onClicks(int aIndex)
    {
        Intent intent = new Intent(ExerciseActivity.this,ExerciseDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Utility.EXERCISE, mExerciseStructure.get(aIndex));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class ReadAssetsAsyncTask extends AsyncTask<Void, Integer, String>
    {
        private int mReturnCode = 0;

        private onAssetsCallback mCallback;

        public ReadAssetsAsyncTask(onAssetsCallback aCallback)
        {
            mCallback = aCallback;
            mLoadingDialog.show();
        }

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected String doInBackground(Void... params)
        {
            mExerciseStructure = Utility.getExerciseAssetsFile(ExerciseActivity.this);

            return "ok";
        }

        @Override
        protected void onPostExecute(String aResult)
        {
            mLoadingDialog.dismiss();
            mCallback.onAssetsResult(mReturnCode);
        }
    }
}

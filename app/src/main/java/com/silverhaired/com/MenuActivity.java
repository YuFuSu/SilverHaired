package com.silverhaired.com;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.adapter.MenuAdapter;
import com.silverhaired.com.callbacks.MenuClickCallback;
import com.silverhaired.com.structure.MenuStructure;

import java.util.ArrayList;

public class MenuActivity extends Activity implements MenuClickCallback
{
    private ImageView mBackIV;
    private RecyclerView mMenuRV;
    private MenuAdapter mMenuAdapter;

    private ProgressDialog mLoadingDialog = null;

    private ArrayList<MenuStructure> mMenuStructure;

    public interface onAssetsCallback
    {
        void onAssetsResult(int aResultCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Utility.mWindowWidth = dm.widthPixels;

        init();
        initClick();
    }

    private void init()
    {
        if (mLoadingDialog == null)
        {
            mLoadingDialog = new ProgressDialog(this, R.style.transparentDialog);
            mLoadingDialog.setCancelable(false);
        }

        mMenuStructure = new ArrayList<>();

        mBackIV = findViewById(R.id.imageview_back);
        mMenuRV = findViewById(R.id.recyclerView_menu);


        new ReadAssetsAsyncTask(new onAssetsCallback()
        {
            @Override
            public void onAssetsResult(int aResultCode)
            {
                mMenuAdapter = new MenuAdapter(mMenuStructure,MenuActivity.this,MenuActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuActivity.this);
                mMenuRV.setLayoutManager(linearLayoutManager);
                mMenuRV.setAdapter(mMenuAdapter);
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
        Intent intent = new Intent(MenuActivity.this,MenuDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Utility.MENU, mMenuStructure.get(aIndex));
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
            mMenuStructure = Utility.getMenuAssetsFile(MenuActivity.this);

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

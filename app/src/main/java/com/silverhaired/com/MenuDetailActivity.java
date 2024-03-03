package com.silverhaired.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.adapter.MenuAdapter;
import com.silverhaired.com.adapter.MenuDetailAdapter;
import com.silverhaired.com.structure.MenuStructure;

public class MenuDetailActivity extends Activity
{

    private  TextView mTitleTV;
    private TextView mSuggestTV;
    private ImageView mBackIV;
    private RecyclerView mMenuDetailRV;

    private MenuDetailAdapter mMenuDetailAdapter;

    private ProgressDialog mLoadingDialog = null;

    private  MenuStructure mMenuStructure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_activity_layout);

        mMenuStructure = (MenuStructure) getIntent().getSerializableExtra(Utility.MENU);
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

        mTitleTV = findViewById(R.id.textview_title);
        mSuggestTV = findViewById(R.id.textview_suggest);
        mBackIV = findViewById(R.id.imageview_back);
        mMenuDetailRV = findViewById(R.id.recyclerView_menu_detail);

        mTitleTV.setText(mMenuStructure.mName);
        mSuggestTV.setText(mMenuStructure.mSuggest);

        mMenuDetailAdapter = new MenuDetailAdapter(mMenuStructure.mMenuDetailStructure,MenuDetailActivity.this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(MenuDetailActivity.this,2);

        mMenuDetailRV.setLayoutManager(linearLayoutManager);
        mMenuDetailRV.setAdapter(mMenuDetailAdapter);

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


}

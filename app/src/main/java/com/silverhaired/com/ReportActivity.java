package com.silverhaired.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.adapter.ReportAdapter;
import com.silverhaired.com.database.DatabaseManager;
import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;

import java.util.ArrayList;

public class ReportActivity extends Activity implements ReportClickListener
{
    private ArrayList<BodyInfoRecord> mBodyInfoRecord = new ArrayList<>();

    private ImageView mBackIV;
    private RecyclerView mReportRV;
    private ReportAdapter mReportAdapter;

    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity_layout);
        init();
        initClick();
    }

    private void init()
    {
        mDatabaseManager = DatabaseManager.getInstance(ReportActivity.this);

        mDatabaseManager.getBodyInfoRecord(mBodyInfoRecord);

        mBackIV = findViewById(R.id.imageview_back);
        mReportRV = findViewById(R.id.recyclerView_report);

        mReportAdapter = new ReportAdapter(mBodyInfoRecord,ReportActivity.this);
        mReportAdapter.setReportClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReportActivity.this);
        mReportRV.setLayoutManager(linearLayoutManager);
        mReportRV.setAdapter(mReportAdapter);
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
    public void onClickListener(int aIndex)
    {
            showMessageOKCancel("是否刪除資料?", new DialogInterface.OnClickListener() //OK
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    mDatabaseManager.deleteReport(mBodyInfoRecord.get(aIndex).mId);
                    mBodyInfoRecord.remove(aIndex);
                    mReportAdapter.notifyDataSetChanged();
                }
            },new DialogInterface.OnClickListener() //Cancel
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
         return;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener)
    {
        new AlertDialog.Builder(ReportActivity.this)
                .setMessage(message)
                .setPositiveButton("確認", okListener)
                .setNegativeButton("取消", cancelListener)
                .setCancelable(false)
                .show();
    }
}

package com.silverhaired.com;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.silverhaired.com.database.DatabaseManager;
import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;

public class NewInfoActivity extends Activity
{
    private Button mAddIV;

    private EditText mHeightET;
    private EditText mWeightET;
    private EditText mBloodPressureET;

    private ImageView mBackIV;

    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_info_activity_layout);

        init();
        initClick();
    }

    private void init()
    {
        mDatabaseManager = DatabaseManager.getInstance(NewInfoActivity.this);

        mBackIV = findViewById(R.id.imageview_back);
        mAddIV = findViewById(R.id.button_add);
        mHeightET = findViewById(R.id.edittext_height);
        mWeightET = findViewById(R.id.edittext_weight);
        mBloodPressureET = findViewById(R.id.edittext_blood_pressure);
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

        mAddIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String height = mHeightET.getText().toString().trim();
                String weight = mWeightET.getText().toString().trim();
                String bloodPressure = mBloodPressureET.getText().toString().trim();

                if (TextUtils.isEmpty(height)) {
                    mHeightET.setError("請填入身高");
                    return;
                }
                if (TextUtils.isEmpty(weight)) {
                    mWeightET.setError("請填入體重");
                    return;
                }
                if (TextUtils.isEmpty(bloodPressure)) {
                    mBloodPressureET.setError("請填入血壓");
                    return;
                }

                float heightF = Float.parseFloat(height)/100;
                float weightF = Float.parseFloat(weight);

                float BMI = weightF/(heightF*heightF);
                float recommendCalorie = weightF*30;

                BodyInfoRecord bodyInfoRecord = null;
                String createTime = Utility.convertUnixTimeToString(Utility.getCurrentUnixTime(), Utility.DATE_PATTERN);
                String bodyInfoRecordID = Utility.getUUID();

                bodyInfoRecord = new BodyInfoRecord(bodyInfoRecordID,createTime,Float.parseFloat(height),Float.parseFloat(weight),Float.parseFloat(bloodPressure),BMI,recommendCalorie);
                DatabaseManager.DBSuccess success = mDatabaseManager.addBodyInfoRecord(bodyInfoRecord);
                if(success.equals(DatabaseManager.DBSuccess.SUCCESS))
                {
                    Toast.makeText(NewInfoActivity.this,"新增成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(NewInfoActivity.this,"新增失敗",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}

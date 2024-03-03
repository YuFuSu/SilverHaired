package com.silverhaired.com;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class SettingActivity extends Activity
{
    private ImageView mBackIV;
    private ImageView mCallIV;
    private ImageView mEditIV;
    private EditText mPhoneET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_layout);
        init();
        initClick();
    }

    private void init()
    {
        mBackIV = findViewById(R.id.imageview_back);
        mCallIV = findViewById(R.id.imageview_call);
        mEditIV = findViewById(R.id.imageview_edit);
        mPhoneET = findViewById(R.id.edittext_phone);
        mPhoneET.setText(PreferencesManager.getInstance(SettingActivity.this).getPhone());
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

        mCallIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String phone = mPhoneET.getText().toString().trim();
                if (TextUtils.isEmpty(phone))
                {
                    mPhoneET.setError("請編輯電話號碼");
                    return;
                }
                Intent myInt=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
                startActivity(myInt);
            }
        });

        mEditIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mPhoneET.isEnabled())
                {
                    mPhoneET.setEnabled(true);
                    mPhoneET.requestFocus();

                    mEditIV.setImageResource(R.drawable.check);
                }
                else{

                    String phone = mPhoneET.getText().toString().trim();
                    if (!TextUtils.isEmpty(phone))
                    {
                        PreferencesManager.getInstance(SettingActivity.this).setPhone(phone);
                    }

                    mPhoneET.setEnabled(false);
                    mEditIV.setImageResource(R.drawable.edit);
                }
            }
        });
    }
}

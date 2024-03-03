package com.silverhaired.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends Activity
{
    private boolean mIsShowPassword = false;
    private boolean mIsShowPasswordConfirm = false;

    private ClearableEditText mAccountCET;

    private Button mResetBT;

    private ImageView mBackIV;

    private ProgressBar mProgressBar;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_layout);

        init();
        initClick();
    }

    private void init()
    {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mBackIV = findViewById(R.id.imageview_back);
        mAccountCET = findViewById(R.id.editText_account);
        mResetBT  = findViewById(R.id.button_forget_password);
        mProgressBar  = findViewById(R.id.progressBar_forget_password);
    }

    private void initClick()
    {
        mBackIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        mResetBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean hasAccount = false;

                final String account = mAccountCET.getText().toString();

                if (Utility.isValid(mAccountCET.getText().toString()))
                    hasAccount = true;

                if (hasAccount)
                {
                    mFirebaseAuth.sendPasswordResetEmail(account).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            Toast.makeText(ForgetPasswordActivity.this,"Reset Link Sent To Your Email.",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(ForgetPasswordActivity.this,"Error!Reset Link is Not Sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(ForgetPasswordActivity.this,"帳號輸入有誤",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

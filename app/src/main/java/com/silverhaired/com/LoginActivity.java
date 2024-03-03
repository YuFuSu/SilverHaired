package com.silverhaired.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity
{
    private boolean mHasInit = false;

    private boolean mIsShowPassword = false;

    private ClearableEditText mAccountCET;
    private ClearableEditText mPasswordCET;

    private TextView mRegister,mForgetPassword;

    private Button mLoginBT;

    private ImageView mShowPasswordIV;

    private ProgressBar mProgressBar;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        initClick();
    }

    public void init()
    {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAccountCET = findViewById(R.id.editText_account);
        mPasswordCET = findViewById(R.id.editText_password);

        mLoginBT = findViewById(R.id.button_login);

        mProgressBar = findViewById(R.id.progressBar_login);

        mShowPasswordIV = findViewById(R.id.imageView_show_password);

        mRegister= findViewById(R.id.textview_register);

        mForgetPassword= findViewById(R.id.textview_forget_password);
    }

    private void initClick()
    {
        mRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mForgetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        mLoginBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean hasAccount = false;
                boolean hasPassword = false;

                final String account = mAccountCET.getText().toString();
                final String password = mPasswordCET.getText().toString();

                if (Utility.isValid(mAccountCET.getText().toString()))
                    hasAccount = true;

                if (Utility.isValid(mPasswordCET.getText().toString()))
                    hasPassword = true;

                if (hasAccount && hasPassword)
                {
                    mProgressBar.setVisibility(View.VISIBLE);

                    mFirebaseAuth.signInWithEmailAndPassword(account,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else {
                                Toast.makeText(LoginActivity.this,"錯誤!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"請輸入帳號密碼",Toast.LENGTH_LONG).show();
                }
            }
        });

        mShowPasswordIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mIsShowPassword)
                {
                    mShowPasswordIV.setBackground(getResources().getDrawable(R.drawable.selector_view_password_off));
                    mPasswordCET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIsShowPassword = !mIsShowPassword;
                }
                else
                {
                    mShowPasswordIV.setBackground(getResources().getDrawable(R.drawable.selector_view_password_on));
                    mPasswordCET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIsShowPassword = !mIsShowPassword;
                }
            }
        });
    }
}

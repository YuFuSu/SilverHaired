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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity
{
    private boolean mIsShowPassword = false;
    private boolean mIsShowPasswordConfirm = false;

    private ClearableEditText mAccountCET;
    private ClearableEditText mPasswordCET;
    private ClearableEditText mPasswordConfirmCET;

    private Button mRegisterBT;

    private ImageView mShowPasswordIV;
    private ImageView mShowPasswordConfirmIV;
    private ImageView mBackIV;

    private ProgressBar mProgressBar;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        init();
        initClick();
    }

    private void init()
    {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mBackIV = findViewById(R.id.imageview_back);
        mAccountCET= findViewById(R.id.editText_account);
        mPasswordCET= findViewById(R.id.editText_password);
        mPasswordConfirmCET= findViewById(R.id.editText_password_confirm);
        mShowPasswordIV= findViewById(R.id.imageView_show_password);
        mShowPasswordConfirmIV= findViewById(R.id.imageView_show_password_confirm);
        mRegisterBT = findViewById(R.id.button_register);

        mProgressBar = findViewById(R.id.progressBar_register);
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

        mRegisterBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                boolean hasAccount = false;
                boolean hasPassword = false;
                boolean hasPasswordConfirm = false;

                final String account = mAccountCET.getText().toString();
                final String password = mPasswordCET.getText().toString();
                final String passwordConfirm = mPasswordConfirmCET.getText().toString();

                if (Utility.isValid(mAccountCET.getText().toString()))
                    hasAccount = true;

                if (Utility.isValid(mPasswordCET.getText().toString()))
                    hasPassword = true;

                if (Utility.isValid(mPasswordConfirmCET.getText().toString()))
                    hasPasswordConfirm = true;

                if (hasAccount && hasPassword && hasPasswordConfirm)
                {
                    mProgressBar.setVisibility(View.VISIBLE);

                    mFirebaseAuth.createUserWithEmailAndPassword(account,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                FirebaseUser user=mFirebaseAuth.getCurrentUser();
                                String email=user.getEmail();
                                String uid=user.getUid();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                // Create a new user with a first and last name
                                Map<String, Object> users = new HashMap<>();
                                users.put("email", email);
                                users.put("uuid", uid);

                                // Add a new document with a generated ID
                                db.collection("Users").document(uid).set(users).addOnSuccessListener(new OnSuccessListener<Void>()
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

                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "帳號創立", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else {
                                Toast.makeText(RegisterActivity.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"請輸入帳號密碼",Toast.LENGTH_LONG).show();
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


        mShowPasswordConfirmIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(mIsShowPasswordConfirm)
                {
                    mShowPasswordConfirmIV.setBackground(getResources().getDrawable(R.drawable.selector_view_password_off));
                    mPasswordConfirmCET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIsShowPasswordConfirm = !mIsShowPasswordConfirm;
                }
                else
                {
                    mShowPasswordConfirmIV.setBackground(getResources().getDrawable(R.drawable.selector_view_password_on));
                    mPasswordConfirmCET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIsShowPasswordConfirm = !mIsShowPasswordConfirm;
                }
            }
        });

    }
}

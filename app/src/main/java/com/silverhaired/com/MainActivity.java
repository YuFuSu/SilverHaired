package com.silverhaired.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private final int PERMISSIONS_REQUEST_CODE = 2000;

    private Button mNewInfoBT;
    private Button mMenuBT;
    private Button mExerciseBT;
    private Button mReportBT;
    private Button mRemindBT;
    private Button mSettingBT;
    private Button mExerciseReportBT;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initClick();
    }

    private void init()
    {
        mNewInfoBT = findViewById(R.id.button_new_info);
        mMenuBT = findViewById(R.id.button_menu);
        mExerciseBT = findViewById(R.id.button_exercise);
        mReportBT = findViewById(R.id.button_report);
        mRemindBT = findViewById(R.id.button_remind);
        mSettingBT = findViewById(R.id.button_setting);
        mExerciseReportBT = findViewById(R.id.button_exercise_report);

        checkPermissions("請給予程式通話的權限，才能做後續的動作", Manifest.permission.CALL_PHONE);
    }

    private void initClick()
    {
        mNewInfoBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,NewInfoActivity.class);
                startActivity(intent);
            }
        });

        mMenuBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });

        mExerciseBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ExerciseActivity.class);
                startActivity(intent);
            }
        });

        mReportBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });

        mRemindBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RemindActivity.class);
                startActivity(intent);
            }
        });

        mSettingBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

        mExerciseReportBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ExerciseReportActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener)
    {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("確認", okListener)
                .setNegativeButton("取消", cancelListener)
                .setCancelable(false)
                .show();
    }

    private void checkPermissions(final String message,final String aPermissions)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, aPermissions) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, aPermissions))
            {
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() //OK
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{aPermissions}, PERMISSIONS_REQUEST_CODE);
                            }
                        },new DialogInterface.OnClickListener() //Cancel
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(MainActivity.this,"請至設定開啟應用程式權限",Toast.LENGTH_LONG).show();
                                new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        finish();
                                    }
                                },3000);
                            }
                        });
                return;
            } else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{aPermissions}, PERMISSIONS_REQUEST_CODE);
            }
        } else
        {
            //不需要做任何事.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSIONS_REQUEST_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) //使用者允許權限
                {

                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) //使用者不允許權限
                {
                    Toast.makeText(MainActivity.this,"請至設定開啟應用程式權限",Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            finish();
                        }
                    },3000);
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
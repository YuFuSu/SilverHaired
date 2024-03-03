package com.silverhaired.com;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class RemindActivity extends Activity
{
    private ImageView mBackIV;
    private ImageView mEditIV1, mEditIV2, mEditIV3, mEditIV4, mEditIV5, mEditIV6;

    private TextView mTimeTV1, mTimeTV2, mTimeTV3, mTimeTV4, mTimeTV5, mTimeTV6;
    private TextView mContentTV1, mContentTV2, mContentTV3, mContentTV4, mContentTV5, mContentTV6;

    private Switch mOnOffSC1, mOnOffSC2, mOnOffSC3, mOnOffSC4, mOnOffSC5, mOnOffSC6;

    private Dialog mShowRebuildDialog;

    private TimePicker mTimePicker;

    private AlarmManager mAlarmManager;

    private String[] mTimeArray = new String[6];

    private PendingIntent mPendingIntent1, mPendingIntent2, mPendingIntent3, mPendingIntent4, mPendingIntent5, mPendingIntent6;

    private PreferencesManager mPreferencesManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remind_activity_layout);

        init();
        initClick();
    }

    private void init()
    {
        mPreferencesManager = PreferencesManager.getInstance(RemindActivity.this);

        mBackIV = findViewById(R.id.imageview_back);

        mEditIV1 = findViewById(R.id.imageview_edit1);
        mEditIV2 = findViewById(R.id.imageview_edit2);
        mEditIV3 = findViewById(R.id.imageview_edit3);
        mEditIV4 = findViewById(R.id.imageview_edit4);
        mEditIV5 = findViewById(R.id.imageview_edit5);
        mEditIV6 = findViewById(R.id.imageview_edit6);

        mTimeTV1 = findViewById(R.id.textview_time1);
        mTimeTV2 = findViewById(R.id.textview_time2);
        mTimeTV3 = findViewById(R.id.textview_time3);
        mTimeTV4 = findViewById(R.id.textview_time4);
        mTimeTV5 = findViewById(R.id.textview_time5);
        mTimeTV6 = findViewById(R.id.textview_time6);

        mContentTV1 = findViewById(R.id.textview_content1);
        mContentTV2 = findViewById(R.id.textview_content2);
        mContentTV3 = findViewById(R.id.textview_content3);
        mContentTV4 = findViewById(R.id.textview_content4);
        mContentTV5 = findViewById(R.id.textview_content5);
        mContentTV6 = findViewById(R.id.textview_content6);

        mOnOffSC1 = findViewById(R.id.switch1);
        mOnOffSC2 = findViewById(R.id.switch2);
        mOnOffSC3 = findViewById(R.id.switch3);
        mOnOffSC4 = findViewById(R.id.switch4);
        mOnOffSC5 = findViewById(R.id.switch5);
        mOnOffSC6 = findViewById(R.id.switch6);

        mOnOffSC1.setChecked(mPreferencesManager.getRemind1());
        mOnOffSC2.setChecked(mPreferencesManager.getRemind2());
        mOnOffSC3.setChecked(mPreferencesManager.getRemind3());
        mOnOffSC4.setChecked(mPreferencesManager.getRemind4());
        mOnOffSC5.setChecked(mPreferencesManager.getRemind5());
        mOnOffSC6.setChecked(mPreferencesManager.getRemind6());

        mShowRebuildDialog = new Dialog(RemindActivity.this, R.style.transparentDialog);
        mShowRebuildDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mShowRebuildDialog.setContentView(R.layout.remind_dialog_layout);
        mShowRebuildDialog.setCancelable(false);

        mTimePicker = (TimePicker) mShowRebuildDialog.findViewById(R.id.timePicker);

        initTime();

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private void setHistoryTime(String aTime, int aIndex)
    {
        String[] time = aTime.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));

        int am_pm = calendar.get(Calendar.AM_PM);
        int hour12 = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        String hours = "";
        String minute = "";
        String AmPm = "上午";
        if (am_pm != Calendar.AM) {
            hour12 = mTimePicker.getCurrentHour() % 12;
            AmPm = "下午";
        }

        if (hour12 < 10) {
            hours = "0" + hour12;
        }
        else {
            hours = String.valueOf(hour12);
        }

        if (minutes < 10) {
            minute = "0" + minutes;
        }
        else {
            minute = String.valueOf(minutes);
        }

        switch (aIndex) {
            case 1: {
                mTimeTV1.setText(AmPm + " " + hours + ":" + minute);
                mContentTV1.setText(mPreferencesManager.getRemindContent1());
            }
            break;
            case 2: {
                mTimeTV2.setText(AmPm + " " + hours + ":" + minute);
                mContentTV2.setText(mPreferencesManager.getRemindContent2());
            }
            break;
            case 3: {
                mTimeTV3.setText(AmPm + " " + hours + ":" + minute);
                mContentTV3.setText(mPreferencesManager.getRemindContent3());
            }
            break;
            case 4: {
                mTimeTV4.setText(AmPm + " " + hours + ":" + minute);
                mContentTV4.setText(mPreferencesManager.getRemindContent4());
            }
            break;
            case 5: {
                mTimeTV5.setText(AmPm + " " + hours + ":" + minute);
                mContentTV5.setText(mPreferencesManager.getRemindContent5());
            }
            break;
            case 6: {
                mTimeTV6.setText(AmPm + " " + hours + ":" + minute);
                mContentTV6.setText(mPreferencesManager.getRemindContent6());
            }
            break;
        }
    }

    private void initTime()
    {
        mTimeArray[0] = mPreferencesManager.getRemindTime1();
        mTimeArray[1] = mPreferencesManager.getRemindTime2();
        mTimeArray[2] = mPreferencesManager.getRemindTime3();
        mTimeArray[3] = mPreferencesManager.getRemindTime4();
        mTimeArray[4] = mPreferencesManager.getRemindTime5();
        mTimeArray[5] = mPreferencesManager.getRemindTime6();

        if (!mTimeArray[0].isEmpty()) {
            setHistoryTime(mTimeArray[0], 1);
        }
        if (!mTimeArray[1].isEmpty()) {
            setHistoryTime(mTimeArray[1], 2);
        }
        if (!mTimeArray[2].isEmpty()) {
            setHistoryTime(mTimeArray[2], 3);
        }
        if (!mTimeArray[3].isEmpty()) {
            setHistoryTime(mTimeArray[3], 4);
        }
        if (!mTimeArray[4].isEmpty()) {
            setHistoryTime(mTimeArray[4], 5);
        }
        if (!mTimeArray[5].isEmpty()) {
            setHistoryTime(mTimeArray[5], 6);
        }

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

        mOnOffSC1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(mTimeArray[0].isEmpty())
                {
                    Toast.makeText(RemindActivity.this,"請先設置時間",Toast.LENGTH_LONG).show();
                    return;
                }

                if (isChecked) {
                    String[] timeSplit = mTimeArray[0].split(":");

                    long time;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));

                    int am_pm = calendar.get(Calendar.AM_PM);
                    // String time=calendar.HOUR + ((am_pm==Calendar.AM)?"am":"pm"))

                    Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                    intent.addCategory("D" + "Alarm1");
                    intent.putExtra("msg", mContentTV1.getText().toString());
                    intent.putExtra("number", "1");
                    mPendingIntent1 = PendingIntent.getBroadcast(RemindActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        if (calendar.AM_PM == 0) {
                            time = time + (1000 * 60 * 60 * 12);
                        }
                        else {
                            time = time + (1000 * 60 * 60 * 24);
                        }
                    }

                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, (time), mPendingIntent1);
                    mPreferencesManager.setRemind1(true);
                }
                else {
                    if (mPendingIntent1 == null)
                    {
                        Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                        intent.addCategory("D" + "Alarm1");
                        mPendingIntent1 = PendingIntent.getBroadcast(RemindActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    }
                    mAlarmManager.cancel(mPendingIntent1);
                    mPreferencesManager.setRemind1(false);
                }
            }
        });

        mOnOffSC2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(mTimeArray[1].isEmpty())
                {
                    Toast.makeText(RemindActivity.this,"請先設置時間",Toast.LENGTH_LONG).show();
                    return;
                }

                if (isChecked) {
                    String[] timeSplit = mTimeArray[1].split(":");

                    long time;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));

                    int am_pm = calendar.get(Calendar.AM_PM);
                    // String time=calendar.HOUR + ((am_pm==Calendar.AM)?"am":"pm"))

                    Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                    intent.addCategory("D" + "Alarm2");
                    intent.putExtra("msg", mContentTV2.getText().toString());
                    intent.putExtra("number", "2");
                    mPendingIntent2 = PendingIntent.getBroadcast(RemindActivity.this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        if (calendar.AM_PM == 0) {
                            time = time + (1000 * 60 * 60 * 12);
                        }
                        else {
                            time = time + (1000 * 60 * 60 * 24);
                        }
                    }

                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, (time), mPendingIntent2);
                    mPreferencesManager.setRemind2(true);
                }
                else {
                    if (mPendingIntent2 == null)
                    {
                        Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                        intent.addCategory("D" + "Alarm2");
                        mPendingIntent2 = PendingIntent.getBroadcast(RemindActivity.this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
                    }

                    mAlarmManager.cancel(mPendingIntent2);
                    mPreferencesManager.setRemind2(false);
                }
            }
        });

        mOnOffSC3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(mTimeArray[2].isEmpty())
                {
                    Toast.makeText(RemindActivity.this,"請先設置時間",Toast.LENGTH_LONG).show();
                    return;
                }

                if (isChecked) {
                    String[] timeSplit = mTimeArray[2].split(":");

                    long time;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));

                    int am_pm = calendar.get(Calendar.AM_PM);
                    // String time=calendar.HOUR + ((am_pm==Calendar.AM)?"am":"pm"))

                    Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                    intent.addCategory("D" + "Alarm3");
                    intent.putExtra("msg", mContentTV3.getText().toString());
                    intent.putExtra("number", "3");
                    mPendingIntent3 = PendingIntent.getBroadcast(RemindActivity.this, 3, intent, PendingIntent.FLAG_ONE_SHOT);

                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        if (calendar.AM_PM == 0) {
                            time = time + (1000 * 60 * 60 * 12);
                        }
                        else {
                            time = time + (1000 * 60 * 60 * 24);
                        }
                    }

                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, (time), mPendingIntent3);
                    mPreferencesManager.setRemind3(true);
                }
                else {

                    if (mPendingIntent3 == null)
                    {
                        Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                        intent.addCategory("D" + "Alarm3");
                        mPendingIntent3 = PendingIntent.getBroadcast(RemindActivity.this, 3, intent, PendingIntent.FLAG_ONE_SHOT);
                    }
                        mAlarmManager.cancel(mPendingIntent3);
                        mPreferencesManager.setRemind3(false);

                }
            }
        });

        mOnOffSC4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(mTimeArray[3].isEmpty())
                {
                    Toast.makeText(RemindActivity.this,"請先設置時間",Toast.LENGTH_LONG).show();
                    return;
                }

                if (isChecked) {
                    String[] timeSplit = mTimeArray[3].split(":");

                    long time;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));

                    int am_pm = calendar.get(Calendar.AM_PM);
                    // String time=calendar.HOUR + ((am_pm==Calendar.AM)?"am":"pm"))

                    Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                    intent.addCategory("D" + "Alarm4");
                    intent.putExtra("msg", mContentTV4.getText().toString());
                    intent.putExtra("number", "4");
                    mPendingIntent4 = PendingIntent.getBroadcast(RemindActivity.this, 4, intent, PendingIntent.FLAG_ONE_SHOT);

                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        if (calendar.AM_PM == 0) {
                            time = time + (1000 * 60 * 60 * 12);
                        }
                        else {
                            time = time + (1000 * 60 * 60 * 24);
                        }
                    }

                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, (time), mPendingIntent4);
                    mPreferencesManager.setRemind4(true);
                }
                else {
                    if (mPendingIntent4 == null)
                    {
                        Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                        intent.addCategory("D" + "Alarm4");
                        mPendingIntent4 = PendingIntent.getBroadcast(RemindActivity.this, 4, intent, PendingIntent.FLAG_ONE_SHOT);
                    }
                    mAlarmManager.cancel(mPendingIntent4);
                    mPreferencesManager.setRemind4(false);

                }
            }
        });

        mOnOffSC5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(mTimeArray[4].isEmpty())
                {
                    Toast.makeText(RemindActivity.this,"請先設置時間",Toast.LENGTH_LONG).show();
                    return;
                }

                if (isChecked) {
                    String[] timeSplit = mTimeArray[4].split(":");

                    long time;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));

                    int am_pm = calendar.get(Calendar.AM_PM);
                    // String time=calendar.HOUR + ((am_pm==Calendar.AM)?"am":"pm"))

                    Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                    intent.addCategory("D" + "Alarm5");
                    intent.putExtra("msg", mContentTV5.getText().toString());
                    intent.putExtra("number", "5");

                    mPendingIntent5 = PendingIntent.getBroadcast(RemindActivity.this, 5, intent, PendingIntent.FLAG_ONE_SHOT);

                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        if (calendar.AM_PM == 0) {
                            time = time + (1000 * 60 * 60 * 12);
                        }
                        else {
                            time = time + (1000 * 60 * 60 * 24);
                        }
                    }

                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, (time), mPendingIntent5);
                    mPreferencesManager.setRemind5(true);
                }
                else {
                    if (mPendingIntent5 == null)
                    {
                        Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                        intent.addCategory("D" + "Alarm5");
                        mPendingIntent5 = PendingIntent.getBroadcast(RemindActivity.this, 5, intent, PendingIntent.FLAG_ONE_SHOT);
                    }
                        mAlarmManager.cancel(mPendingIntent5);
                        mPreferencesManager.setRemind5(false);

                }
            }
        });

        mOnOffSC6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(mTimeArray[5].isEmpty())
                {
                    Toast.makeText(RemindActivity.this,"請先設置時間",Toast.LENGTH_LONG).show();
                    return;
                }

                if (isChecked) {
                    String[] timeSplit = mTimeArray[5].split(":");

                    long time;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));

                    int am_pm = calendar.get(Calendar.AM_PM);
                    // String time=calendar.HOUR + ((am_pm==Calendar.AM)?"am":"pm"))

                    Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                    intent.addCategory("D" + "Alarm6");
                    intent.putExtra("msg", mContentTV6.getText().toString());
                    intent.putExtra("number", "6");

                    mPendingIntent6 = PendingIntent.getBroadcast(RemindActivity.this, 6, intent, PendingIntent.FLAG_ONE_SHOT);

                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        if (calendar.AM_PM == 0) {
                            time = time + (1000 * 60 * 60 * 12);
                        }
                        else {
                            time = time + (1000 * 60 * 60 * 24);
                        }
                    }

                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, (time), mPendingIntent6);
                    mPreferencesManager.setRemind6(true);
                }
                else {

                    if (mPendingIntent6 == null)
                    {
                        Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
                        intent.addCategory("D" + "Alarm6");
                        mPendingIntent6 = PendingIntent.getBroadcast(RemindActivity.this, 6, intent, PendingIntent.FLAG_ONE_SHOT);
                    }

                     mAlarmManager.cancel(mPendingIntent6);
                     mPreferencesManager.setRemind6(false);
                }
            }
        });

        mEditIV1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mTimeArray[0].isEmpty())
                {
                    String[] time = mTimeArray[0].split(":");
                    showTimerDialog(1, mContentTV1.getText().toString(),time[0], time[1]);
                }
                else{
                    showTimerDialog(1, "",null, null);
                }
            }
        });
        mEditIV2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mTimeArray[1].isEmpty())
                {
                    String[] time = mTimeArray[1].split(":");
                    showTimerDialog(2, mContentTV2.getText().toString(),time[0], time[1]);
                }
                else {
                    showTimerDialog(2, "",null, null);
                }
            }
        });
        mEditIV3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mTimeArray[2].isEmpty())
                {
                    String[] time = mTimeArray[2].split(":");
                    showTimerDialog(3, mContentTV3.getText().toString(),time[0], time[1]);
                }
                else {
                    showTimerDialog(3, "",null, null);
                }
            }
        });
        mEditIV4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mTimeArray[3].isEmpty())
                {
                    String[] time = mTimeArray[3].split(":");
                    showTimerDialog(4, mContentTV4.getText().toString(),time[0], time[1]);
                }
                else {
                    showTimerDialog(4, "",null, null);
                }
            }
        });
        mEditIV5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mTimeArray[4].isEmpty())
                {
                    String[] time = mTimeArray[4].split(":");
                    showTimerDialog(5, mContentTV5.getText().toString(),time[0], time[1]);
                }
                else {
                    showTimerDialog(5, "",null, null);
                }
            }
        });
        mEditIV6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!mTimeArray[5].isEmpty())
                {
                    String[] time = mTimeArray[5].split(":");
                    showTimerDialog(6, mContentTV6.getText().toString(),time[0], time[1]);
                }
                else {
                    showTimerDialog(6, "",null, null);
                }
            }
        });
    }


    private void showTimerDialog(int aIndex,String aContent, String aHour, String aMinute)
    {
        LinearLayout outside = (LinearLayout) mShowRebuildDialog.findViewById(R.id.linearLayout_outside);



        if(aHour!=null)
        {
            mTimePicker.setCurrentHour(Integer.parseInt(aHour));
            mTimePicker.setCurrentMinute(Integer.parseInt(aMinute));
        }
        else{
        }

        EditText contentET = mShowRebuildDialog.findViewById(R.id.edittext_content);
        contentET.setText(aContent);

        Button mConfirmCE = (Button) mShowRebuildDialog.findViewById(R.id.button_confirm);

        mConfirmCE.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());

                int am_pm = calendar.get(Calendar.AM_PM);
                int hour12 = mTimePicker.getCurrentHour();
                int minutes =  mTimePicker.getCurrentMinute();
                String hours = "";
                String minute = "";
                String AmPm = "上午";
                if (am_pm != Calendar.AM)
                {
                    hour12 = mTimePicker.getCurrentHour() % 12;
                    AmPm = "下午";
                }

                if (hour12 < 10) {
                    hours = "0" + hour12;
                }
                else {
                    hours = String.valueOf(hour12);
                }

                if (minutes < 10) {
                    minute = "0" + minutes;
                }
                else {
                    minute = String.valueOf(minutes);
                }



                switch (aIndex) {
                    case 1: {
                        mTimeArray[0] = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                        mTimeTV1.setText(AmPm + " " + hours + ":" + minute);
                        mContentTV1.setText(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindContent1(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindTime1(mTimeArray[0]);

                    }
                    break;
                    case 2: {
                        mTimeArray[1] = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                        mTimeTV2.setText(AmPm + " " + hours + ":" + minute);
                        mContentTV2.setText(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindContent2(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindTime2(mTimeArray[1]);
                    }
                    break;
                    case 3: {
                        mTimeArray[2] = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                        mTimeTV3.setText(AmPm + " " + hours + ":" + minute);
                        mContentTV3.setText(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindContent3(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindTime3(mTimeArray[2]);
                    }
                    break;
                    case 4: {
                        mTimeArray[3] = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                        mTimeTV4.setText(AmPm + " " + hours + ":" + minute);
                        mContentTV4.setText(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindContent4(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindTime4(mTimeArray[3]);
                    }
                    break;
                    case 5: {
                        mTimeArray[4] = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                        mTimeTV5.setText(AmPm + " " + hours + ":" + minute);
                        mContentTV5.setText(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindContent5(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindTime5(mTimeArray[4]);
                    }
                    break;
                    case 6: {
                        mTimeArray[5] = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
                        mTimeTV6.setText(AmPm + " " + hours + ":" + minute);
                        mContentTV6.setText(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindContent6(contentET.getText().toString().trim());
                        mPreferencesManager.setRemindTime6(mTimeArray[5]);
                    }
                    break;
                }

                mShowRebuildDialog.dismiss();
            }
        });

        outside.setOnClickListener(v ->
        {
            mShowRebuildDialog.dismiss();

        });

        if (!mShowRebuildDialog.isShowing() && !((Activity) RemindActivity.this).isFinishing()) {
            mShowRebuildDialog.show();
        }
    }
}

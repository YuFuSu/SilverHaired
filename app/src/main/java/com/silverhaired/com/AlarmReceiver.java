package com.silverhaired.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class AlarmReceiver extends BroadcastReceiver
{
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //we will use vibrator first
        Vibrator vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

        Bundle bData = intent.getExtras();
        String msg = (String)bData.get("msg");
        String number = (String)bData.get("number");
        PreferencesManager temp = PreferencesManager.getInstance(context);
        switch(Integer.parseInt(number))
        {
            case 1:{
                temp.setRemind1(false);
            }
            break;
            case 2:{
                temp.setRemind2(false);
            }
            break;
            case 3:{
                temp.setRemind3(false);
            }
            break;
            case 4:{
                temp.setRemind4(false);
            }
            break;
            case 5:{
                temp.setRemind5(false);
            }
            break;
            case 6:{
                temp.setRemind6(false);
            }
            break;
        }

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
    }
}

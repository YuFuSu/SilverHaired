package com.silverhaired.com;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Global extends Application implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        initFirebase();
    }

    private void initFirebase()
    {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("20211210JoshLog", "getInstanceId failed"+task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.e("20211210JoshLog", "getInstanceId token"+token);
                    }
                });
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle)
    {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle)
    {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity)
    {

    }
}

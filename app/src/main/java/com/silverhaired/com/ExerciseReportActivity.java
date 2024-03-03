package com.silverhaired.com;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.silverhaired.com.database.DatabaseManager;
import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;
import com.silverhaired.com.database.databaseTableFiled.ExerciseReportRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExerciseReportActivity extends Activity
{
    private ArrayList<BodyInfoRecord> mBodyInfoRecord = new ArrayList<>();

    private ImageView mBackIV;
    private RecyclerView mReportRV;
    private ExerciseReportAdapter mExerciseReportAdapter;
    private ProgressDialog mLoadingDialog = null;
    private DatabaseManager mDatabaseManager;
    private ArrayList<ExerciseReportRecord> mExerciseReportRecord = new ArrayList<>();
    private FirebaseUser mFirebaseUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_report_layout);
        init();
        initClick();
    }

    private void init()
    {
        if (mLoadingDialog == null)
        {
            mLoadingDialog = new ProgressDialog(this, R.style.transparentDialog);
            mLoadingDialog.setCancelable(false);
        }

        updateCount();

        mBackIV = findViewById(R.id.imageview_back);
        mReportRV = findViewById(R.id.recyclerView_exercise_report);

        mExerciseReportAdapter = new ExerciseReportAdapter(mExerciseReportRecord,ExerciseReportActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExerciseReportActivity.this);
        mReportRV.setLayoutManager(linearLayoutManager);
        mReportRV.setAdapter(mExerciseReportAdapter);
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
    }

    private void updateCount()
    {
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = mFirebaseUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mDatabaseManager = DatabaseManager.getInstance(ExerciseReportActivity.this);

        mDatabaseManager.getExerciseReportRecord(uid,mExerciseReportRecord);

        for(int i=0;i<mExerciseReportRecord.size();i++)
        {
            int index = i;
            db.collection("Users").document(uid).collection(mExerciseReportRecord.get(i).mName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        mExerciseReportRecord.get(index).mCount = Integer.valueOf(document.getData().get("count").toString());
                        mExerciseReportAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}

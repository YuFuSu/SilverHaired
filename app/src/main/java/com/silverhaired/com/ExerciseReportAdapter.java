package com.silverhaired.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;
import com.silverhaired.com.database.databaseTableFiled.ExerciseReportRecord;

import java.util.ArrayList;

public class ExerciseReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_NORMAL = 0;
    private final int TEM_TYPE_HEADER = 1;

    private int mPosition;

    private ArrayList<ExerciseReportRecord> mExerciseReportRecord;
    private Context mContext;

    public ExerciseReportAdapter(ArrayList<ExerciseReportRecord> aTeamRecord, Context aContext)
    {
        mExerciseReportRecord = aTeamRecord;
        mContext = aContext;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
        {
            return TEM_TYPE_HEADER;
        } else
        {
            mPosition = position;
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount()
    {
        return mExerciseReportRecord.size() == 0 ? 1 : mExerciseReportRecord.size()+1;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == TYPE_NORMAL)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.exercise_report_adapter_layout, parent, false);
            view.getLayoutParams().height = (int)mContext.getResources().getDimension(R.dimen.report_height);

            return new BindViewHolder(view,TYPE_NORMAL);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.exercise_report_adapter_header_layout, parent, false);
            view.getLayoutParams().height = (int)mContext.getResources().getDimension(R.dimen.report_height);

            return new BindViewHolder(view,TEM_TYPE_HEADER);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(position!=0)
        {
            int index = holder.getAdapterPosition()-1;

            final ExerciseReportRecord exerciseReportRecord = mExerciseReportRecord.get(index);
            BindViewHolder temp = (BindViewHolder)holder;

            temp.mCategoryIV.setText(exerciseReportRecord.mName);
            temp.mCounterTV.setText(String.valueOf(exerciseReportRecord.mCount));
        }
    }

    class BindViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mCounterTV,mCategoryIV;

        public BindViewHolder(View aItemView,int aType)
        {
            super(aItemView);
            if(aType == TYPE_NORMAL)
            {
                mCounterTV  = itemView.findViewById(R.id.textview_counter);
                mCategoryIV  = itemView.findViewById(R.id.textview_category);
            }
        }
    }

}

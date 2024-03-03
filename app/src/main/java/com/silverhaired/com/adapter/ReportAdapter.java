package com.silverhaired.com.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.MainActivity;
import com.silverhaired.com.R;
import com.silverhaired.com.ReportClickListener;
import com.silverhaired.com.database.DatabaseManager;
import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_NORMAL = 0;
    private final int TEM_TYPE_HEADER = 1;

    private int mPosition;

    private ArrayList<BodyInfoRecord>  mBodyInfoRecord;
    private Context mContext;

    private ReportClickListener mReportClickListener;

    public ReportAdapter(ArrayList<BodyInfoRecord> aTeamRecord, Context aContext)
    {
        mBodyInfoRecord = aTeamRecord;
        mContext = aContext;
    }

    public void setReportClickListener(ReportClickListener aReportClickListener)
    {
        mReportClickListener = aReportClickListener;
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
        return mBodyInfoRecord.size() == 0 ? 1 : mBodyInfoRecord.size()+1;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        if(viewType == TYPE_NORMAL)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.report_adapter_layout, parent, false);
            view.getLayoutParams().height = (int)mContext.getResources().getDimension(R.dimen.report_height);

            return new BindViewHolder(view,TYPE_NORMAL);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.report_adapter_header_layout, parent, false);
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

                final BodyInfoRecord bodyInfoRecord = mBodyInfoRecord.get(index);
                BindViewHolder temp = (BindViewHolder)holder;

                String[] date = bodyInfoRecord.mDates.split("_");
                temp.mDateTV.setText(date[0]);
                temp.mWeightTV.setText(String.valueOf(bodyInfoRecord.mWeight));
                temp.mBMITV.setText(String.valueOf(bodyInfoRecord.mBMI));
                temp.mBloodPressure.setText(String.valueOf(bodyInfoRecord.mBloodPressure));
                temp.mCalorieTV.setText(String.valueOf(bodyInfoRecord.mRecommendCalorie));

            temp.mItemLL.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    mReportClickListener.onClickListener(index);
                    return false;
                }
            });
        }
    }

    class BindViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout mItemLL;
        private TextView mDateTV,mWeightTV,mBMITV,mBloodPressure,mCalorieTV;

        public BindViewHolder(View aItemView,int aType)
        {
            super(aItemView);
            if(aType == TYPE_NORMAL)
            {
                mItemLL = itemView.findViewById(R.id.linearLayout_items);
                mDateTV  = itemView.findViewById(R.id.textview_date);
                mWeightTV  = itemView.findViewById(R.id.textview_weight);
                mBMITV  = itemView.findViewById(R.id.textview_bmi);
                mBloodPressure  = itemView.findViewById(R.id.textview_blood_pressure);
                mCalorieTV  = itemView.findViewById(R.id.textview_calorie);
            }
        }
    }



}

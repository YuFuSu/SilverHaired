package com.silverhaired.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.R;
import com.silverhaired.com.callbacks.ExerciseClickCallback;
import com.silverhaired.com.callbacks.MenuClickCallback;
import com.silverhaired.com.structure.ExerciseStructure;
import com.silverhaired.com.structure.MenuStructure;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<ExerciseStructure> mExerciseStructure;
    private Context mContext;
    private ExerciseClickCallback mOnClickCallback;

    public ExerciseAdapter(ArrayList<ExerciseStructure> aExerciseStructure, Context aContext, ExerciseClickCallback aOnClickCallback)
    {
        mExerciseStructure = aExerciseStructure;
        mContext = aContext;
        mOnClickCallback = aOnClickCallback;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public boolean isHeaderView(int position)
    {
        return position == 0;
    }

    @Override
    public int getItemCount()
    {
        return mExerciseStructure.size() == 0 ? 1 : mExerciseStructure.size();
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.exericse_adapter_layout, parent, false);
        view.getLayoutParams().height = (int)mContext.getResources().getDimension(R.dimen.report_height);

        return new BindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int index = holder.getAdapterPosition();

        final ExerciseStructure exerciseStructure = mExerciseStructure.get(index);
        BindViewHolder temp = (BindViewHolder)holder;

        temp.mNameTV.setText(exerciseStructure.mName);
        temp.mNameLL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnClickCallback.onClicks(holder.getAdapterPosition());
            }
        });
    }

    class BindViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mNameTV;
        private LinearLayout mNameLL;

        public BindViewHolder(View aItemView)
        {
            super(aItemView);

            mNameTV  = itemView.findViewById(R.id.textview_name);
            mNameLL  = itemView.findViewById(R.id.lineaylayout_name);
        }
    }
}

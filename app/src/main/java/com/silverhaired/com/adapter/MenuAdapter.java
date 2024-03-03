package com.silverhaired.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.MenuActivity;
import com.silverhaired.com.R;
import com.silverhaired.com.callbacks.MenuClickCallback;
import com.silverhaired.com.database.databaseTableFiled.BodyInfoRecord;
import com.silverhaired.com.structure.MenuStructure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_NORMAL = 0;
    private final int TEM_TYPE_HEADER = 1;

    private ArrayList<MenuStructure> mMenuStructure;
    private Context mContext;
    private MenuClickCallback mOnClickCallback;

    public MenuAdapter(ArrayList<MenuStructure> aMenuStructure, Context aContext, MenuClickCallback aOnClickCallback)
    {
        mMenuStructure = aMenuStructure;
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
        return mMenuStructure.size() == 0 ? 1 : mMenuStructure.size();
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_adapter_layout, parent, false);
        view.getLayoutParams().height = (int)mContext.getResources().getDimension(R.dimen.report_height);

        return new BindViewHolder(view,TYPE_NORMAL);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
       int index = holder.getAdapterPosition();

       final MenuStructure menuStructure = mMenuStructure.get(index);
       BindViewHolder temp = (BindViewHolder)holder;

       temp.mNameTV.setText(menuStructure.mName);
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

        public BindViewHolder(View aItemView,int aType)
        {
            super(aItemView);

            mNameTV  = itemView.findViewById(R.id.textview_name);
            mNameLL  = itemView.findViewById(R.id.lineaylayout_name);
        }
    }
}

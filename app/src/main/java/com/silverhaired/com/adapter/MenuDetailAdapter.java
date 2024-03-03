package com.silverhaired.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.silverhaired.com.R;
import com.silverhaired.com.Utility;
import com.silverhaired.com.callbacks.MenuClickCallback;
import com.silverhaired.com.structure.MenuDetailStructure;
import com.silverhaired.com.structure.MenuStructure;

import java.util.ArrayList;

public class MenuDetailAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<MenuDetailStructure> mMenuStructure;
    private Context mContext;

    public MenuDetailAdapter(ArrayList<MenuDetailStructure> aMenuStructure, Context aContext)
    {
        mMenuStructure = aMenuStructure;
        mContext = aContext;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public int getItemCount()
    {
        return mMenuStructure.size() == 0 ? 1 : mMenuStructure.size();
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_detail_adapter_layout, parent, false);

        view.getLayoutParams().width =   Utility.mWindowWidth/2;
        view.getLayoutParams().height =  (int)(Utility.mWindowWidth/1.5);

        return new BindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int index = holder.getAdapterPosition();

        final MenuDetailStructure menuStructure = mMenuStructure.get(index);
        BindViewHolder temp = (BindViewHolder)holder;
        temp.mNameTV.setText(menuStructure.mName);
        temp.mCalorieTV.setText(menuStructure.mCalorie);
        temp.mPicIV.setImageBitmap(Utility.getImageFromAssetsFile(mContext,"menu/images/"+menuStructure.mImagePath));
    }

    class BindViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mNameTV,mCalorieTV;
        private ImageView mPicIV;

        public BindViewHolder(View aItemView)
        {
            super(aItemView);

            mNameTV  = itemView.findViewById(R.id.textview_name);
            mCalorieTV  = itemView.findViewById(R.id.textview_calroies);
            mPicIV  = itemView.findViewById(R.id.imageview_pic);

        }
    }
}
package com.silverhaired.com;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

public class ClearableEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcherAdapter.TextWatcherListener {

    private boolean mIsEnableClearButton = true;

    interface Listener {
        void didClearText();
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    private Drawable mDrawableEye;

    private Drawable mDrawable;
    private Listener mListener;

    private Context mContext;

    public ClearableEditText(Context context) {
        super(context);
        mContext=context;
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        init();
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener l) {
        this.mOnTouchListener = l;
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener f) {
        this.mOnFocusChangeListener = f;
    }

    private View.OnTouchListener mOnTouchListener;
    private View.OnFocusChangeListener mOnFocusChangeListener;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - mDrawable.getIntrinsicWidth());
            if (tappedX) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setText("");
                    if (mListener != null) {
                        mListener.didClearText();
                    }
                }
                return true;
            }
        }
        if (mOnTouchListener != null) {
            return mOnTouchListener.onTouch(v, event);
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(Utility.isValid(getText().toString()));
        } else {
            setClearIconVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        if (isFocused()) {
            setClearIconVisible(Utility.isValid(text));
        }
    }

    private void init()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            setHintTextColor(getResources().getColor(R.color.clearable_edittext_hint_color, null));
            setTextColor(getResources().getColor(R.color.user_info, null));
            setPadding(0,0,convertDpToPixel(8,mContext),0);
        }
        else
        {
            setHintTextColor(getResources().getColor(R.color.clearable_edittext_hint_color));
            setTextColor(getResources().getColor(R.color.user_info));
            setPadding(0,0,convertDpToPixel(8,mContext),0);
        }

        mDrawable = getCompoundDrawables()[2];

        if (mDrawable == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDrawable = getResources().getDrawable(R.drawable.selector_list_login_clear, null);
            } else {
                mDrawable = getResources().getDrawable(R.drawable.selector_list_login_clear);
            }
        }



        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());

        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(new TextWatcherAdapter(this, this));
    }


    private static int convertDpToPixel(float dp, Context context){
        return (int)(dp * getDensity(context));
    }
    private static float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    protected void setClearIconVisible(boolean visible) {
        boolean wasVisible = (getCompoundDrawables()[2] != null);
        if (visible != wasVisible) {
            Drawable x = visible ? mDrawable : null;

            if(mIsEnableClearButton)
            {
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], x, getCompoundDrawables()[3]);
            }
            else
            {
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
            }
        }
    }

    public void setClearButtonEnable(boolean aEnable)
    {
        mIsEnableClearButton = aEnable;
    }
}


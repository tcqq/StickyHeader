package com.example.flexibleadapterstickyheaderexample.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayoutManager;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import eu.davidea.flexibleadapter.common.IFlexibleLayoutManager;

/**
 * Created by fisher on 22/2/18.
 */
public class FlexibleFlexBoxLayoutManager extends FlexboxLayoutManager implements IFlexibleLayoutManager {

    public FlexibleFlexBoxLayoutManager(Context context) {
        super(context);
    }

    public FlexibleFlexBoxLayoutManager(Context context, int flexDirection) {
        super(context, flexDirection);
    }

    public FlexibleFlexBoxLayoutManager(Context context, int flexDirection, int flexWrap) {
        super(context, flexDirection, flexWrap);
    }

    public FlexibleFlexBoxLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof RecyclerView.LayoutParams) {
            return new FlexboxLayoutManager.LayoutParams((RecyclerView.LayoutParams) lp);
        } else if (lp instanceof ViewGroup.MarginLayoutParams) {
            return new FlexboxLayoutManager.LayoutParams((ViewGroup.MarginLayoutParams) lp);
        } else {
            return new FlexboxLayoutManager.LayoutParams(lp);
        }
    }

    @Override
    public int getOrientation() {
        return OrientationHelper.VERTICAL;
    }

    @Override
    public int getSpanCount() {
        return 1;
    }
}

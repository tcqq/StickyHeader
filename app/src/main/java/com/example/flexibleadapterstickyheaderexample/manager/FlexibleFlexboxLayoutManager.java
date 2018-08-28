package com.example.flexibleadapterstickyheaderexample.manager;

import android.content.Context;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayoutManager;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import eu.davidea.flexibleadapter.common.IFlexibleLayoutManager;

public class FlexibleFlexboxLayoutManager extends FlexboxLayoutManager implements IFlexibleLayoutManager {

    public FlexibleFlexboxLayoutManager(Context context, int flexDirection, int flexWrap) {
        super(context, flexDirection, flexWrap);
    }

    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof RecyclerView.LayoutParams) {
            return new LayoutParams((RecyclerView.LayoutParams) lp);
        } else if (lp instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) lp);
        } else {
            return new LayoutParams(lp);
        }
    }

    @Override
    public int getOrientation() {
        return OrientationHelper.VERTICAL;
    }

    @Override
    public int getSpanCount() {
        return 0;
    }
}

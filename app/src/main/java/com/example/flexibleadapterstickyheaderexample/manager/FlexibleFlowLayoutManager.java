package com.example.flexibleadapterstickyheaderexample.manager;

import eu.davidea.flexibleadapter.common.IFlexibleLayoutManager;

/**
 * @author Alan Dreamer
 * @since 28/08/2018 Created
 */
public class FlexibleFlowLayoutManager extends FLMFlowLayoutManager implements IFlexibleLayoutManager {

    public FlexibleFlowLayoutManager(int orientation) {
        super(orientation);
    }

    @Override
    public int getSpanCount() {
        return 0;
    }

    @Override
    public int findFirstCompletelyVisibleItemPosition() {
        return 0;
    }

    @Override
    public int findFirstVisibleItemPosition() {
        return 0;
    }

    @Override
    public int findLastCompletelyVisibleItemPosition() {
        return 0;
    }

    @Override
    public int findLastVisibleItemPosition() {
        return 0;
    }
}

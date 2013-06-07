package com.funtify.beautypics;

import android.widget.AbsListView;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

/**
 * Created by mosida on 13-6-7.
 */
public class AbsListViewBaseFragment extends BaseFragment{

    protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
    protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";

    protected boolean pauseOnScroll = false;
    protected boolean pauseOnFling = true;

    protected AbsListView listView;

    private void applyScrollListener() {
        listView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
    }

    @Override
    public void onResume() {
        super.onResume();
        applyScrollListener();
    }
}

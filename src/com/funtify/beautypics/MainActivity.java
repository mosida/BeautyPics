package com.funtify.beautypics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";
    Button recent;
    Button hotest;

    Fragment mContent;

    public MainActivity() {
        super(R.string.app_name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        // set the Above View
        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if (mContent == null)
            mContent = new RecentFragment();
        Log.i(TAG, "setContentView");

        // set the Above View
        setContentView(R.layout.content_frame);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();

        Log.i(TAG, "setBehindView");

        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, new MenuFragment())
                .commit();
        Log.i(TAG, "sildingMenu");


        // customize the SlidingMenu
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }


    // Update fragment visibility based on current check box state.
    void updateFragmentVisibility() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        if (mCheckBox1.isChecked()) ft.show(mFragment1);
//        else ft.hide(mFragment1);
//        if (mCheckBox2.isChecked()) ft.show(mFragment2);
//        else ft.hide(mFragment2);
//        ft.commit();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }


    public void switchContent(Fragment fragment) {
        mContent = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        getSlidingMenu().showContent();
    }
}

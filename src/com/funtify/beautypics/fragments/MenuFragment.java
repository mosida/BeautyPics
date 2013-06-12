package com.funtify.beautypics.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.funtify.beautypics.MainActivity;
import com.funtify.beautypics.R;

/**
 * Created by mosida on 13-6-8.
 */
public class MenuFragment extends Fragment {
    ListView tabs;
    String[] tabsString;
    int[] tabsInt = {
            R.drawable.recent,
            R.drawable.hotest,
            R.drawable.category,
            R.drawable.topic,
            R.drawable.favorite
    };
//    LayoutInflater layoutInflater;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        layoutInflater = inflater;
        return inflater.inflate(R.layout.menu, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabs = (ListView) getView().findViewById(R.id.listTabs);
        tabsString = getResources().getStringArray(R.array.tabs);

        MenuAdapter menuAdapter = new MenuAdapter();
        tabs.setAdapter(menuAdapter);

        tabs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Fragment newContent = null;
                switch (position) {
                    case 0:
                        newContent = new RecentFragment();
                        break;
                    case 1:
                        newContent = new HotestFragment();
                        break;
                }
                if (newContent != null)
                    switchFragment(newContent);
            }
        });
    }

    // the meat of switching the above fragment
    private void switchFragment(Fragment fragment) {
        if (getActivity() == null)
            return;

        if (getActivity() instanceof MainActivity) {
            MainActivity fca = (MainActivity) getActivity();
            fca.switchContent(fragment);
        }
    }

    class MenuAdapter extends BaseAdapter{

        LayoutInflater layoutInflater;
        public MenuAdapter(){
            layoutInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return tabsString.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View container, ViewGroup viewGroup) {
            ViewHoder viewHoder = null;
            if (container==null){
                container = layoutInflater.inflate(R.layout.menu_list, null);
                viewHoder = new ViewHoder();
                viewHoder.menuIcon = (ImageView)container.findViewById(R.id.menu_icon);
                viewHoder.menuName = (TextView)container.findViewById(R.id.menu_text);
                container.setTag(viewHoder);
            }else{
                viewHoder = (ViewHoder) container.getTag();
            }
            viewHoder.menuName.setText(tabsString[i]);
            Log.i(MainActivity.TAG, "int is:"+tabsInt[i]);
            viewHoder.menuIcon.setImageResource(tabsInt[i]);
            return container;
        }

        class ViewHoder{
            ImageView menuIcon;
            TextView menuName;
        }
    }

}

package com.funtify.beautypics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by mosida on 13-6-8.
 */
public class MenuFragment extends Fragment {
    ListView tabs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabs = (ListView) getView().findViewById(R.id.tabs);
        String[] colors = getResources().getStringArray(R.array.tabs);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, colors);
        tabs.setAdapter(colorAdapter);

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


}

package com.funtify.beautypics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by mosida on 13-6-7.
 */
public class HotestFragment extends AbsListViewBaseFragment{

    private static final String TAG = "HotestFrament";

    String[] imageUrls;
    Bundle bundle;
    DisplayImageOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");


        imageUrls = Constants.IMAGES;
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated");

        bundle = savedInstanceState;
        ((GridView) listView).setAdapter(new ImageAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.ac_image_grid, container);
        listView = (GridView) view.findViewById(R.id.gridview);

        Log.i(TAG, "onCreateView over");
//        return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    public class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ImageView imageView;

            Log.i(TAG, "try to getView");
            if (convertView == null) {
                imageView = (ImageView) getLayoutInflater(bundle).inflate(R.layout.item_grid_image, parent, false);
            } else {
                imageView = (ImageView) convertView;
            }

            imageLoader.displayImage(imageUrls[position], imageView, options);

            return imageView;
        }
    }

    private void startImagePagerActivity(int position) {
        Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
        intent.putExtra(Constants.Extra.IMAGES, imageUrls);
        intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
        startActivity(intent);
    }
}

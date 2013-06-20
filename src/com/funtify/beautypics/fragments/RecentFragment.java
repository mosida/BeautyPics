package com.funtify.beautypics.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.funtify.beautypics.Constants;
import com.funtify.beautypics.ImagePagerActivity;
import com.funtify.beautypics.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mosida on 13-6-7.
 */
public class RecentFragment extends AbsListViewBaseFragment {

    private static final String TAG = "RecentFrament";

    String[] small_imageUrls;
    String[] middle_imageUrls;
    Bundle bundle;
    DisplayImageOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");

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
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constants.URLS.RECENT, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, response);
                if(response!=null){
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("code");
                        if (code==0){
                            JSONObject datas = jsonObject.getJSONObject("datas");
                            JSONArray idsArrays = datas.getJSONArray("ids");
                            int total = datas.getInt("total");
                            small_imageUrls = new String[total];
                            middle_imageUrls = new String[total];
                            for (int i=0; i<total; i++){
                                small_imageUrls[i] = Constants.URLS.PICURL+"?id="+idsArrays.getInt(i)+"&size=s";
                                middle_imageUrls[i] = Constants.URLS.PICURL+"?id="+idsArrays.getInt(i)+"&size=m";
                            }
                            ((GridView) listView).setAdapter(new ImageAdapter());
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    startImagePagerActivity(position);
                                }
                            });
                        }
                    }catch (Throwable throwable){
                        Log.e(TAG, "getData", throwable);
                    }

                }

            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.ac_image_grid, container);
        listView = (GridView) view.findViewById(R.id.gridview);
        TextView loadView = new TextView(getActivity());
        loadView.setText("loading...");
        listView.setEmptyView(loadView);

        Log.i(TAG, "onCreateView over");
//        return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    public class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return small_imageUrls.length;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageView imageView;

            Log.i(TAG, "try to getView");
            if (convertView == null) {
                imageView = (ImageView) getLayoutInflater(bundle).inflate(R.layout.item_grid_image, parent, false);
            } else {
                imageView = (ImageView) convertView;
            }

            imageLoader.displayImage(small_imageUrls[position], imageView, options);

            return imageView;
        }
    }

    private void startImagePagerActivity(int position) {
        Log.i(TAG, "startImagePageActivity");

        Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
        intent.putExtra(Constants.Extra.IMAGES, middle_imageUrls);
        intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
        startActivity(intent);
    }
}

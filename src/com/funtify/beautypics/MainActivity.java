package com.funtify.beautypics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AbsListViewBaseActivity {

    public static final String TAG = "MainActivity";
    String[] imageUrls;
//    int[] imageIds;
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_image_grid);
        listView = (GridView) findViewById(R.id.gridview);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://admin.pic.funtify.net/pic/recommend.json", new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, response);
                try{
                    JSONObject responseObj = new JSONObject(response);
                    JSONObject datasObj = responseObj.getJSONObject("datas");
                    JSONArray idsArray = datasObj.getJSONArray("ids");
                    imageUrls = new String[idsArray.length()];
                    for (int i=0; i<idsArray.length(); i++){
                        imageUrls[i] = Constants.URLS.PICURL+"/?id="+idsArray.get(i)+"&size=s";
                    }


                    ((GridView) listView).setAdapter(new ImageAdapter());
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startImagePagerActivity(position);
                        }
                    });
                }catch(Throwable throwable){

                }

            }
        });




        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        Log.i(TAG, "option builded");



        Log.i(TAG, "listview builded");

        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);
        menu.setSecondaryMenu(R.layout.menu);

        Log.i(TAG, "menu builded");
    }


    private void startImagePagerActivity(int position) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(Constants.Extra.IMAGES, imageUrls);
        intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
        startActivity(intent);
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
            if (convertView == null) {
                imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
            } else {
                imageView = (ImageView) convertView;
            }

            imageLoader.displayImage(imageUrls[position], imageView, options);

            return imageView;
        }
    }
}

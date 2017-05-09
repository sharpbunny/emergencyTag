package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GridAdapter extends BaseAdapter {

    private String TAG = GridAdapter.class.getSimpleName();

    private ArrayList<Picture> mDataSource;

    private Context context;



    public GridAdapter (Context context, ArrayList<Picture> mDataSource) {
        this.context = context;
        this.mDataSource = mDataSource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        Log.i(TAG, "Grid view...");
        if (convertView == null) {
            gridView = new View(context);

            // get layout from grid_element.xml
            gridView = inflater.inflate(R.layout.grid_element, null);

            // set value into textview
            //TextView textView = (TextView) gridView
            //        .findViewById(R.id.grid_item_label);
            //textView.setText(mDataSource.get(position).getPictureUrl());
            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
            try {
                String url = mDataSource.get(position).getThumbnailUrl();
                Picasso.with(context).load(url).placeholder(R.mipmap
                        .emergency).into(imageView);
            }catch (Exception e) {
                Log.i(TAG, "No picture for this item...");
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}


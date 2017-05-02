package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class GridAdapter extends BaseAdapter {

    public int imgIcon[] = { R.drawable.surprise};

    private Context context;


    public GridAdapter(Context c){
        context=c;
    }

    @Override
    public int getCount() {
        return imgIcon.length;
    }

    @Override
    public Object getItem(int position) {
        return imgIcon[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imgIcon[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new GridView.LayoutParams(240,240));
        return imageView;
    }
}


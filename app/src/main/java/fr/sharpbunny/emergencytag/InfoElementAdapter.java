package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;


/**
 * Class to handle Element of list.
 */
public class InfoElementAdapter extends BaseAdapter {

    private String TAG = InfoElementAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Item> mDataSource;

    public InfoElementAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Function who take recycle the view in function of the scrolling
     * @param position Position of the element to recycle
     * @param convertView The element to recycle (who is a view)
     * @param parent Group of view elements / The list view where it's associate
     * @return The recycled view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InfoElementViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ligne_info, parent, false);
            // create a new "Holder" with subviews
            holder = new InfoElementViewHolder();
            holder.itemLabelTypeView = (TextView) convertView.findViewById(R.id.item_labeltype);
            holder.itemDescriptionTypeView = (TextView) convertView.findViewById(R.id.item_descriptiontype);
            holder.itemCommentView = (TextView) convertView.findViewById(R.id.item_comment);
            holder.itemLatitudeView = (TextView) convertView.findViewById(R.id.item_latitude);
            holder.itemLongitudeView = (TextView) convertView.findViewById(R.id.item_longitude);
            holder.thumbnailPictureItemView = (ImageView) convertView.findViewById(R.id.item_photo);

            // hang onto this holder for future recycle
            convertView.setTag(holder);
        }
        else
        {
            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (InfoElementViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        TextView itemTypeView = holder.itemLabelTypeView;
        TextView itemDescriptionTypeView = holder.itemDescriptionTypeView;
        TextView itemCommentView = holder.itemCommentView;
        TextView itemLatitudeView = holder.itemLatitudeView;
        TextView itemLongitudeView = holder.itemLongitudeView;
        ImageView thumbnailImageView = holder.thumbnailPictureItemView;

        Item item = (Item) getItem(position);

        // Update row view's textviews to display items information
        itemCommentView.setText(item.getComment());
        itemTypeView.setText(item.getLabelType());
        itemDescriptionTypeView.setText(item.getDescriptionType());
        itemLatitudeView.setText(item.getItemLatitude().toString());
        itemLongitudeView.setText(item.getItemLongitude().toString());
        itemCommentView.setText(item.getComment());

        // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
        try {
            String url = item.getPictureListItem().get(0).getThumbnailUrl();
            Picasso.with(mContext).load(url).placeholder(R.mipmap
                    .emergency).into(thumbnailImageView);
        }catch (Exception e) {
            Log.i(TAG, "No picture for this item...");
        }

        return convertView;
    }

    private class InfoElementViewHolder {
        private TextView itemCommentView;
        private TextView itemLongitudeView;
        private TextView itemLatitudeView;
        private TextView itemLabelTypeView;
        private TextView itemDescriptionTypeView;
        private ImageView thumbnailPictureItemView;
    }
}
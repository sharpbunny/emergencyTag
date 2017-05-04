package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import com.squareup.picasso.Picasso;
import android.graphics.Typeface;

/**
 * Class to handle Element of list.
 */
public class InfoElementAdapter extends BaseAdapter {

    public static final String TAG = InfoElementAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<InfoElement> mDataSource;

    public InfoElementAdapter(Context context, ArrayList<InfoElement> items) {
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

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.ligne_info, parent, false);
            // create a new "Holder" with subviews
            holder = new InfoElementViewHolder();
            holder.itemNameView = (TextView) convertView.findViewById(R.id.item_name);
            holder.itemTypeView = (TextView) convertView.findViewById(R.id.item_type);
            holder.itemCommentView = (TextView) convertView.findViewById(R.id.item_comment);
            //holder.detailTextView = (TextView) convertView.findViewById(R.id.item_name);
            holder.thumbnailPictureItemView = (ImageView) convertView.findViewById(R.id.item_photo);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        }
        else {

            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (InfoElementViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        TextView itemNameView = holder.itemNameView;
        TextView itemTypeView = holder.itemTypeView;
        TextView itemCommentView = holder.itemCommentView;
        //TextView detailTextView = holder.detailTextView;
        ImageView thumbnailImageView = holder.thumbnailPictureItemView;

        InfoElement infoelement = (InfoElement) getItem(position);

        // Update row view's textviews to display items information
        itemNameView.setText(infoelement.nameItem);
        itemTypeView.setText(infoelement.typeItem);
        itemCommentView.setText(infoelement.commentItem);

        // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
        Picasso.with(mContext).load(infoelement.pictureItem).placeholder(R.mipmap
                .ic_launcher).into(thumbnailImageView);

        return convertView;
    }

    private class InfoElementViewHolder{
        private TextView itemNameView;
        private TextView itemTypeView;
        private TextView itemCommentView;
        private ImageView thumbnailPictureItemView;
    }
}
package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 34011-14-05 on 28/04/2017.
 */

public class InfoElementAdapter extends ArrayAdapter<InfoElement> {

    //tweets est la liste des models à afficher
    public InfoElementAdapter(Context context, List<InfoElement> infoElements) {
        super(context, 0, infoElements);
    }

    /**
     * @method Function who take recycle the view in function of the scrolling
     * @param position Position of the element to recycle
     * @param convertView The element to recycle (who is a view)
     * @param parent Group of view elements / The list view where it's associate
     * @return The recycled view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ligne_info,parent, false);
        }

        InfoElementViewHolder viewHolder = (InfoElementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new InfoElementViewHolder();
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.commentaire = (TextView) convertView.findViewById(R.id.commentaire);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(viewHolder);
        }

        InfoElement infoelement = getItem(position);

        viewHolder.type.setText(infoelement.getTitre());
        viewHolder.commentaire.setText(infoelement.getCommentaire());
        viewHolder.photo.setImageDrawable(new ColorDrawable(infoelement.getImage()));

        return convertView;
    }

    private class InfoElementViewHolder{
        public TextView type;
        public TextView commentaire;
        public ImageView photo;
    }
}
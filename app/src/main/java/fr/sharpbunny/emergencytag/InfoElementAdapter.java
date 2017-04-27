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
import fr.sharpbunny.emergencytag.InfoElement;
import fr.sharpbunny.emergencytag.InfoElementViewHolder;
import fr.sharpbunny.emergencytag.R;

/**
 * Created by 34011-14-05 on 27/04/2017.
 */

public class InfoElementAdapter extends ArrayAdapter<InfoElement>{
    public InfoElementAdapter(Context context,List<InfoElement> infoelements){
        super(context,0,infoelements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ligne_info, parent, false);
        }
        InfoElementViewHolder viewHolder =(InfoElementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new InfoElementViewHolder();
            viewHolder.photo = (ImageView)convertView.findViewById(R.id.photo);
            viewHolder.soustext = (TextView)convertView.findViewById(R.id.soustext);
            viewHolder.titre = (TextView)convertView.findViewById(R.id.titre);
            convertView.setTag(viewHolder);
        }
        InfoElement infoElement = getItem(position);


            viewHolder.titre.setText(infoElement != null ? infoElement.getTitre() : null);
            viewHolder.soustext.setText(infoElement != null ? infoElement.getSoustexte() : null);
            viewHolder.photo.setImageDrawable(new ColorDrawable(infoElement != null ? infoElement.getColor() : 0));

        return convertView;
    }
}

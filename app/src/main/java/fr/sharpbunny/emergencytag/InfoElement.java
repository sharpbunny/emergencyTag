
/**
 * Created by 34011-14-05 on 28/04/2017.
 */
package fr.sharpbunny.emergencytag;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 34011-14-05 on 27/04/2017.
 */

public class InfoElement {
    private int image;
    private String titre;
    private String commentaire;

    public InfoElement(int image, String titre, String commentaire) {
        this.image = image;
        this.titre = titre;
        this.commentaire = commentaire;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
package fr.sharpbunny.emergencytag;
/**
 * Created by 34011-14-05 on 27/04/2017.
 */

    public class InfoElement {
    private int color=0;
    private String titre="";
    private String soustexte="";

    public InfoElement(int color, String titre, String soustexte) {
        color = this.color;
        titre = this.titre;
        soustexte = this.soustexte;
    }

    public void setColor(int color){
        this.color = color;
    }

    public int getColor(){
        return color;
    }

    public void setTitre(String titre){
        this.titre = titre;
    }

    public String getTitre(){
        return titre;
    }

    public void setSoustexte(String soustexte){
        this.soustexte = soustexte;
    }

    public String getSoustexte(){
        return soustexte;
    }

}
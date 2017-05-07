package fr.sharpbunny.emergencytag;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class to describe items
 */
public class Item {
    private static String TAG = Item.class.getSimpleName();

    private int idItem;
    private String comment;
    private String majItem;
    private Double longitude;
    private Double latitude;
    private int idUser;
    private String nameUser;
    private String loginUser;
    private String firstnameUser;
    private String birthdateUser;
    private String emailUser;
    private String phoneUser;
    private int idType;
    private String labelType;
    private String descriptionType;
    private ArrayList<Picture> pictureListItem;

    /**
     * Constructor for Item
     */
    public Item(){

    }

    /**
     * Constructor of an Item
     * @param idItem id of Item
     * @param comment Comment on item
     * @param latitude latitude of item
     * @param longitude longitude of item
     */
    public Item (int idItem, String comment , Double latitude, Double longitude, int idUser, int idType) {
        super();
        this.idItem = idItem;
        this.comment = comment;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idUser = idUser;
        this.idType = idType;

    }

    /**
     * Class to describe picture
     */
    public class Picture {
        private int idPhoto;
        private String datePhoto;
        private String pictureUrl;
        private String thumbnailUrl;

        public Picture (){

        }

        public int getidPhoto() {
            return idPhoto;
        }

        public void setIdPhoto(int idPhoto) {
            this.idPhoto = idPhoto;
        }

        public String getDatePhoto() {
            return datePhoto;
        }

        public void setDatePhoto(String datePhoto) {
            this.datePhoto = datePhoto;
        }

        public String getPictureurl() {
            return pictureUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

    }

    public ArrayList<Item> getItemsList(JSONObject jsonresp) {
        final ArrayList<Item> itemList = new ArrayList<>();

        try {
            // Load data
            if (jsonresp != null) {
                JSONArray items = jsonresp.getJSONArray("response");

                // Get Item objects from data
                for (int i = 0; i < items.length(); i++) {
                    Item item = new Item();
                    item.pictureListItem = new ArrayList<>();

                    item.idItem = items.getJSONObject(i).getInt("idItem");
                    item.comment = items.getJSONObject(i).getString("commentaire");
                    item.majItem = items.getJSONObject(i).getString("majItem");
                    item.latitude = items.getJSONObject(i).getDouble("item_Lat");
                    item.longitude = items.getJSONObject(i).getDouble("item_Lon");
                    item.idUser = items.getJSONObject(i).getInt("idUser");
                    item.nameUser = items.getJSONObject(i).getString("nameUser");
                    item.loginUser = items.getJSONObject(i).getString("loginUser");
                    item.firstnameUser = items.getJSONObject(i).getString("firstnameUser");
                    item.birthdateUser = items.getJSONObject(i).getString("birthdateUser");
                    item.phoneUser = items.getJSONObject(i).getString("phoneUser");
                    item.emailUser = items.getJSONObject(i).getString("emailUser");
                    item.idType = items.getJSONObject(i).getInt("id_Type");
                    item.labelType = items.getJSONObject(i).getString("LabelType");
                    item.descriptionType = items.getJSONObject(i).getString("descriptionType");

                    // picture are in an json array
                    JSONArray pictures =  items.getJSONObject(i).getJSONArray("photo");
                    for (int j = 0; j < pictures.length(); j++) {
                        JSONObject row = pictures.getJSONObject(j);
                        if (row != null) {
                            Picture picture = new Picture();
                            picture.idPhoto = row.getInt("idPhoto");
                            picture.datePhoto = row.getString("datePhoto");
                            picture.pictureUrl = row.getString("adressUrlPhoto");
                            picture.thumbnailUrl = row.getString("thumbUrlPhoto");
                            item.pictureListItem.add(picture);
                        }
                    }

                    itemList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    /**
     * Get id of item
     * @return int id of item
     */
    public int getIdItem() {
        return idItem;
    }

    /**
     * Set id of item
     * @param idItem id of item
     */
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    /**
     * Get comment of item
     * @return string
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment of item
     * @param comment comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get date update of item
     * @return string
     */
    public String getUpdateDate() {
        return majItem;
    }

    /**
     * Set update date of item
     * @param majItem date
     */
    public void setUpdateDate(String majItem) {
        this.majItem = majItem;
    }

    /**
     * Get longitude of item
     * @return double longitude
     */
    public Double getItemLongitude() {
        return longitude;
    }

    /**
     * Set item longitude
     * @param longitude double longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get latitude of item
     * @return double latitude
     */
    public Double getItemLatitude() {
        return latitude;
    }

    /**
     * Set item latitude
     * @param latitude double latitude
     */
    public void setItemLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     */
    public int getIdType() {
        return idType;
    }

    /**
     *
     * @param idType
     */
    public void setIdType(int idType) {
        this.idType = idType;
    }

    /**
     * Get label of item
     * @return string label type
     */
    public String getLabelType() {
        return labelType;
    }

    /**
     * Get descrption type
     * @return string description type
     */
    public String getDescriptionType() {
        return descriptionType;
    }

    /**
     * Get id of item user creator
     * @return int idUser
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Set id of item user creator
     * @param idUser int idUser
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Get name of item user creator
     * @return string user name
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * Get login of item user creator
     * @return string login
     */
    public String getLoginUser() {
        return loginUser;
    }

    /**
     * Get firstname of item user creator
     * @return string firstname
     */
    public String getFirstnameUser() {
        return firstnameUser;
    }

    /**
     * Get birthday date of item user creator
     * @return string birthday
     */
    public String getBirthdateUser() {
        return birthdateUser;
    }

    /**
     * Get email of item user creator
     * @return string email
     */
    public String getEmailUser() {
        return emailUser;
    }

    /**
     * Get birthday date of item user creator
     * @return string birthday
     */
    public String getPhoneUser() {
        return phoneUser;
    }

    /**
     * Return an array with Picture item object
     * @return pictureListItem
     */
    public ArrayList<Picture> getPictureListItem() {
        return pictureListItem;
    }
}

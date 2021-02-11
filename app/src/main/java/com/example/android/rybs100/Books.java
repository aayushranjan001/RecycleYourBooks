package com.example.android.rybs100;

/**
 * Created by aayush on 17-03-2018.
 */

public class Books {
    //variables to keep track of image and text in the list view layout
    private String mUserName;
    private String mBookName;
    private String mBookPrice;
    private String mBookEdition;
    private String mBookAuthor;
    private String mContact;
    private String mCity;
    private String mPhotoUrl;
    private String mLatitude;
    private String mLongitude;

    public Books() {
    }

    public Books(String mLatitude, String mLongitude,String mUserName, String mBookName, String mBookPrice, String mBookEdition, String mBookAuthor, String mContact, String mCity, String mPhotoUrl) {
        this.mUserName = mUserName;
        this.mBookName = mBookName;
        this.mBookPrice = mBookPrice;
        this.mBookEdition = mBookEdition;
        this.mBookAuthor = mBookAuthor;
        this.mContact = mContact;
        this.mCity = mCity;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;

        this.mPhotoUrl = mPhotoUrl;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public String getmBookName() {
        return mBookName;
    }

    public void setmBookName(String mBookName) {
        this.mBookName = mBookName;
    }

    public String getmBookPrice() {
        return mBookPrice;
    }

    public void setmBookPrice(String mBookPrice) {
        this.mBookPrice = mBookPrice;
    }

    public String getmBookEdition() {
        return mBookEdition;
    }

    public void setmBookEdition(String mBookEdition) {
        this.mBookEdition = mBookEdition;
    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public void setmBookAuthor(String mBookAuthor) {
        this.mBookAuthor = mBookAuthor;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }
}

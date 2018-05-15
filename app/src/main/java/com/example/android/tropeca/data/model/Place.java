package com.example.android.tropeca.data.model;

import android.text.TextUtils;

public class Place {
    private String placeID;
    private byte[] placeImage;
    private String placeName;
    private String placeCategoryID;
    private String placeAddress;
    private String placeDescription;
    private double placeLat;
    private double placeLng;

    public Place(Builder builder) {
        this.placeID = builder.placeID;
        this.placeAddress = builder.placeAddress;
        this.placeCategoryID = builder.placeCategoryID;
        this.placeDescription = builder.placeDescription;
        this.placeImage = builder.placeImage;
        this.placeLat = builder.placeLat;
        this.placeLng = builder.placeLng;
        this.placeName = builder.placeName;
    }

    public String getPlaceID() {
        return placeID;
    }

    public byte[] getPlaceImage() {
        return placeImage;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceCategoryID() {
        return placeCategoryID;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public static class Builder {
        private String placeID;
        private byte[] placeImage;
        private String placeName;
        private String placeCategoryID;
        private String placeAddress;
        private String placeDescription;
        private double placeLat;
        private double placeLng;

        public Builder setPlaceID(String placeID) {
            this.placeID = placeID;
            return this;
        }

        public Builder setPlaceImage(byte[] placeImage) {
            this.placeImage = placeImage;
            return this;
        }

        public Builder setPlaceName(String placeName) {
            this.placeName = placeName;
            return this;
        }

        public Builder setPlaceCategoryID(String placeCategoryID) {
            this.placeCategoryID = placeCategoryID;
            return this;
        }

        public Builder setPlaceAddress(String placeAddress) {
            this.placeAddress = placeAddress;
            return this;
        }

        public Builder setPlaceDescription(String placeDescription) {
            this.placeDescription = placeDescription;
            return this;
        }

        public Builder setPlaceLat(double placeLat) {
            this.placeLat = placeLat;
            return this;
        }

        public Builder setPlaceLng(double placeLng) {
            this.placeLng = placeLng;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }

    static public boolean validateInput(String placeName, String placeAddress, String placeDescription, String placeCategoryID) {
        return (TextUtils.isEmpty(placeName) || TextUtils.isEmpty(placeAddress) || TextUtils.isEmpty(placeDescription) || TextUtils.isEmpty(placeCategoryID)) ? false : true;
    }
}

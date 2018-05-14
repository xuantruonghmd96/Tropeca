package com.example.android.tropeca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.tropeca.data.model.Category;
import com.example.android.tropeca.data.model.DBUtitls;
import com.example.android.tropeca.data.model.Place;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class PlaceRepo {

    private PlaceSqliteHelper placeSqliteHelper;
    private static PlaceRepo INSTANCE;

    private PlaceRepo(Context context) {
        placeSqliteHelper = new PlaceSqliteHelper(context);
    }

    public static PlaceRepo getInstance(Context context) {
        if (INSTANCE == null)
            return new PlaceRepo(context);
        else return INSTANCE;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DBUtitls.CATEGORY_COL_ID,
                DBUtitls.CATEGORY_COL_NAME
        };
        Cursor cursor = database.query(DBUtitls.CATEGORY_TBL_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.CATEGORY_COL_ID));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.CATEGORY_COL_NAME));

                categories.add(new Category(categoryID, categoryName));
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        database.close();

        return categories;
    }

    public List<Place> getPlaces(String catID) {
        List<Place> places = new ArrayList<>();

        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        String columns[] = {
                DBUtitls.PLACE_COL_ID,
                DBUtitls.PLACE_COL_CATEGORYID,
                DBUtitls.PLACE_COL_NAME,
                DBUtitls.PLACE_COL_ADDRESS,
                DBUtitls.PLACE_COL_DESCRIPTION,
                DBUtitls.PLACE_COL_IMAGE,
                DBUtitls.PLACE_COL_LAT,
                DBUtitls.PLACE_COL_LNG
        };

        String selection = DBUtitls.PLACE_COL_CATEGORYID + " = ?";
        String[] selectionArgs = {catID};

        Cursor cursor = database.query(DBUtitls.PLACE_TBL_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_ID));
                String placeCategoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_CATEGORYID));
                String placeName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_NAME));
                String placeAdress = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_ADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_DESCRIPTION));
                byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_IMAGE));
                double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_LAT));
                double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_LNG));

                Place place = new Place.Builder()
                        .setPlaceID(placeID)
                        .setPlaceName(placeName)
                        .setPlaceCategoryID(placeCategoryID)
                        .setPlaceAddress(placeAdress)
                        .setPlaceDescription(placeDescription)
                        .setPlaceImage(placeImage)
                        .setPlaceLat(placeLat)
                        .setPlaceLng(placeLng)
                        .build();
                places.add(place);
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        database.close();

        return places;
    }

    public Place getPlace(String plID) {
        Place place = null;

        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        String columns[] = {
                DBUtitls.PLACE_COL_ID,
                DBUtitls.PLACE_COL_CATEGORYID,
                DBUtitls.PLACE_COL_NAME,
                DBUtitls.PLACE_COL_ADDRESS,
                DBUtitls.PLACE_COL_DESCRIPTION,
                DBUtitls.PLACE_COL_IMAGE,
                DBUtitls.PLACE_COL_LAT,
                DBUtitls.PLACE_COL_LNG
        };

        String selection = DBUtitls.PLACE_COL_ID + " = ?";
        String[] selectionArgs = {plID};

        Cursor cursor = database.query(DBUtitls.PLACE_TBL_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_ID));
            String placeCategoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_CATEGORYID));
            String placeName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_NAME));
            String placeAdress = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_ADDRESS));
            String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_DESCRIPTION));
            byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_IMAGE));
            double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_LAT));
            double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.PLACE_COL_LNG));

            place = new Place.Builder()
                    .setPlaceID(placeID)
                    .setPlaceName(placeName)
                    .setPlaceCategoryID(placeCategoryID)
                    .setPlaceAddress(placeAdress)
                    .setPlaceDescription(placeDescription)
                    .setPlaceImage(placeImage)
                    .setPlaceLat(placeLat)
                    .setPlaceLng(placeLng)
                    .build();
        }

        if (cursor != null) {
            cursor.close();
        }
        database.close();

        return place;
    }

    public void insert(Place place){
        SQLiteDatabase database = placeSqliteHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtitls.PLACE_COL_ID, place.getPlaceID());
        contentValues.put(DBUtitls.PLACE_COL_CATEGORYID, place.getPlaceCategoryID());
        contentValues.put(DBUtitls.PLACE_COL_NAME, place.getPlaceName());
        contentValues.put(DBUtitls.PLACE_COL_ADDRESS, place.getPlaceAddress());
        contentValues.put(DBUtitls.PLACE_COL_DESCRIPTION, place.getPlaceDescription());
        contentValues.put(DBUtitls.PLACE_COL_IMAGE, place.getPlaceImage());
        contentValues.put(DBUtitls.PLACE_COL_LAT, place.getPlaceLat());
        contentValues.put(DBUtitls.PLACE_COL_LNG, place.getPlaceLng());

        database.insert(DBUtitls.PLACE_TBL_NAME, null, contentValues);
        database.close();
    }

    public void update(Place place){
        SQLiteDatabase database = placeSqliteHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtitls.PLACE_COL_ID, place.getPlaceID());
        contentValues.put(DBUtitls.PLACE_COL_CATEGORYID, place.getPlaceCategoryID());
        contentValues.put(DBUtitls.PLACE_COL_NAME, place.getPlaceName());
        contentValues.put(DBUtitls.PLACE_COL_ADDRESS, place.getPlaceAddress());
        contentValues.put(DBUtitls.PLACE_COL_DESCRIPTION, place.getPlaceDescription());
        contentValues.put(DBUtitls.PLACE_COL_IMAGE, place.getPlaceImage());
        contentValues.put(DBUtitls.PLACE_COL_LAT, place.getPlaceLat());
        contentValues.put(DBUtitls.PLACE_COL_LNG, place.getPlaceLng());

        String selection = DBUtitls.PLACE_COL_ID + " = ?";
        String[] selectionArgs = {place.getPlaceID()};

        database.update(DBUtitls.PLACE_TBL_NAME, contentValues, selection, selectionArgs);
        database.close();
    }

    public void delete(String plID){
        SQLiteDatabase database = placeSqliteHelper.getWritableDatabase();

        String selection = DBUtitls.PLACE_COL_ID + " = ?";
        String[] selectionArgs = {plID};

        database.delete(DBUtitls.PLACE_TBL_NAME, selection, selectionArgs);
        database.close();
    }

}

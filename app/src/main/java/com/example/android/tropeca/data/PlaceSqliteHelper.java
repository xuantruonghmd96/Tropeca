package com.example.android.tropeca.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.tropeca.data.model.DBUtitls;

import java.security.PrivilegedAction;

public class PlaceSqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PLACE";
    private static final int DB_VERSION = 1;

    private static final String CREATE_PLACE_TBL_SQL =
            "CREATE TABLE " + DBUtitls.PLACE_TBL_NAME + " ("
                    + DBUtitls.PLACE_COL_ID + " " + DBUtitls.TEXT_DATA_TYPE + " " + DBUtitls.PRIMARY_KEY + ", "
                    + DBUtitls.PLACE_COL_CATEGORYID + " " + DBUtitls.TEXT_DATA_TYPE + " " + DBUtitls.NOT_NULL + ", "
                    + DBUtitls.PLACE_COL_NAME + " " + DBUtitls.TEXT_DATA_TYPE + " " + DBUtitls.NOT_NULL + ", "
                    + DBUtitls.PLACE_COL_ADDRESS + " " + DBUtitls.TEXT_DATA_TYPE + " " + DBUtitls.NOT_NULL + ", "
                    + DBUtitls.PLACE_COL_DESCRIPTION + " " + DBUtitls.TEXT_DATA_TYPE + " " + DBUtitls.NOT_NULL + ", "
                    + DBUtitls.PLACE_COL_IMAGE + " " + DBUtitls.BLOB_DATA_TYPE + " " + DBUtitls.NOT_NULL + ", "
                    + DBUtitls.PLACE_COL_LAT + " " + DBUtitls.REAL_DATA_TYPE + " " + DBUtitls.NOT_NULL + ", "
                    + DBUtitls.PLACE_COL_LNG + " " + DBUtitls.REAL_DATA_TYPE + " " + DBUtitls.NOT_NULL
            + ")";

    private static final String CREATE_CATEGORY_TBL_SQL =
            "CREATE TABLE " + DBUtitls.CATEGORY_TBL_NAME + " ("
                    + DBUtitls.CATEGORY_COL_ID + " " + DBUtitls.TEXT_DATA_TYPE + " " + DBUtitls.PRIMARY_KEY + ", "
                    + DBUtitls.CATEGORY_COL_NAME + " " + DBUtitls.TEXT_DATA_TYPE + " " + DBUtitls.NOT_NULL
            + ")";

    private static final String INSERT_CATEGORY_SQL =
            "INSERT INTO " + DBUtitls.CATEGORY_TBL_NAME + "(" + DBUtitls.CATEGORY_COL_ID + ", " + DBUtitls.CATEGORY_COL_NAME + ")"
            + "VALUES "
            + "('0', 'Quán Cà phê'), "
            + "('1', 'Công viên'), "
            + "('2', 'Cửa hàng Pets'), "
            + "('3', 'Sân chơi pets')";

    public PlaceSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TBL_SQL);
        db.execSQL(CREATE_PLACE_TBL_SQL);
        db.execSQL(INSERT_CATEGORY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.Github.ShinChven.materialdemomenu.menu.repo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ShinChven on 2014/12/5.
 */
public class MenuDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "menu_db";
    public static final String _ID = "_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATE_NAME = "cate_name";
    public static final String UPDATE_TIME = "update_time";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_NAME = "item_name";
    public static final String DESCRIPTION = "description";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_ITEM = "item";
    public static final String IMG = "img";
    public static final String PARENT_ID = "parent_id";
    public static final String ITEM_STATUS = "item_status";
    public static final String PRICE = "price";


    private final String sql_category = "CREATE TABLE " + TABLE_CATEGORY + " (\n" +
            "  " + _ID + "         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + CATEGORY_ID + " INTEGER UNIQUE,\n" +
            "  " + PARENT_ID + " INTEGER,\n" +
            "  " + CATE_NAME + "   TEXT,\n" +
            "  " + IMG + "   TEXT,\n" +
            "  " + UPDATE_TIME + " long\n" +
            ")";
    private final String sql_item = "CREATE TABLE " + TABLE_ITEM + " (\n" +
            "  " + _ID + "         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + ITEM_ID + "     INTEGER UNIQUE,\n" +
            "  " + ITEM_NAME + "   TEXT,\n" +
            "  " + DESCRIPTION + " TEXT,\n" +
            "  " + CATEGORY_ID + " INTEGER,\n" +
            "  " + ITEM_STATUS + " INTEGER,\n" +
            "  " + PRICE + " FLOAT,\n" +
            "  " + IMG + "   TEXT,\n" +
            "  " + UPDATE_TIME + " long\n" +
            ")";

    public MenuDBHelper(Context context) {
        super(context, DB_NAME, null, 10);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_category);
        db.execSQL(sql_item);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE " + TABLE_ITEM);
        db.execSQL(sql_category);
        db.execSQL(sql_item);
    }




}

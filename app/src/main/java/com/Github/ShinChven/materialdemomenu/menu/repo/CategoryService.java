package com.Github.ShinChven.materialdemomenu.menu.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.Github.ShinChven.materialdemomenu.menu.repo.entity.CategoryEntity;
import com.Github.ShinChven.materialdemomenu.util.CursorExtractor;
import com.Github.ShinChven.materialdemomenu.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ShinChven on 2014/12/5.
 */
public class CategoryService extends MenuDBHelper {
    private CategoryService(Context context) {
        super(context);
    }

    public static long insertOrUpdateCategory(Context context, CategoryEntity category) {
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, category.getCATEGORY_ID());
        values.put(CATE_NAME, category.getCATE_NAME());
        values.put(UPDATE_TIME, category.getUPDATE_TIME().getTime());
        values.put(IMG, category.getIMG());
        values.put(PARENT_ID, category.getPARENT_ID());
        return insertOrUpdateCategory(context, values);
    }

    public static long insertOrUpdateCategory(Context context, ContentValues values) {
        Integer categoryId = values.getAsInteger(CATEGORY_ID);
        List<CategoryEntity> shrinkCates = findCategories(context, new String[]{_ID}, CATEGORY_ID + "=?", new String[]{String.valueOf(categoryId)}, null, null, null);
        MenuDBHelper menuDBHelper = new MenuDBHelper(context);
        SQLiteDatabase db = menuDBHelper.getWritableDatabase();
        if (shrinkCates.size() > 0) {
            try {
                return db.update(TABLE_CATEGORY, values, CATEGORY_ID + "=?", new String[]{String.valueOf(categoryId)});
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            } finally {
                if (db != null) {
                    try {
                        db.close();
                    } catch (Exception e1) {
                        LogUtil.printStackTrace(e1);
                    }
                }
            }
        } else {
            try {
                return db.insert(TABLE_CATEGORY, _ID, values);
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            } finally {
                if (db != null) {
                    try {
                        db.close();
                    } catch (Exception e1) {
                        LogUtil.printStackTrace(e1);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 查找分类
     *
     * @param context
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return
     */
    public static List<CategoryEntity> findCategories(
            Context context, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy
    ) {
        MenuDBHelper menuDBHelper = new MenuDBHelper(context);
        List<CategoryEntity> list = new ArrayList<CategoryEntity>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = menuDBHelper.getReadableDatabase();
            cursor = db.query(MenuDBHelper.TABLE_CATEGORY, columns, selection, selectionArgs, groupBy, having, orderBy);
            CursorExtractor extractor = new CursorExtractor(cursor);
            while (cursor.moveToNext()) {
                CategoryEntity category = new CategoryEntity();
                category.set_ID(extractor.getInt(_ID));
                category.setCATEGORY_ID(extractor.getInt(CATEGORY_ID));
                category.setCATE_NAME(extractor.getString(CATE_NAME));
                category.setIMG(extractor.getString(IMG));
                category.setUPDATE_TIME(extractor.getDate(UPDATE_TIME));
                list.add(category);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    LogUtil.printStackTrace(e);
                }
            }
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    LogUtil.printStackTrace(e);
                }
            }
        }
        return list;
    }

    public static Map<Integer, CategoryEntity> findCategoryMap(
            Context context, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy
    ) {
        MenuDBHelper menuDBHelper = new MenuDBHelper(context);
        Map<Integer, CategoryEntity> map = new LinkedHashMap<Integer, CategoryEntity>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = menuDBHelper.getReadableDatabase();
            cursor = db.query(MenuDBHelper.TABLE_CATEGORY, columns, selection, selectionArgs, groupBy, having, orderBy);
            CursorExtractor extractor = new CursorExtractor(cursor);
            while (cursor.moveToNext()) {
                CategoryEntity category = new CategoryEntity();
                category.set_ID(extractor.getInt(_ID));
                category.setCATEGORY_ID(extractor.getInt(CATEGORY_ID));
                category.setCATE_NAME(extractor.getString(CATE_NAME));
                category.setIMG(extractor.getString(IMG));
                category.setUPDATE_TIME(extractor.getDate(UPDATE_TIME));
                map.put(category.getCATEGORY_ID(), category);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    LogUtil.printStackTrace(e);
                }
            }
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    LogUtil.printStackTrace(e);
                }
            }
        }
        return map;
    }
}

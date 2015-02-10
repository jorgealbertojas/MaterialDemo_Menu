package com.Github.ShinChven.materialdemomenu.menu.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.Github.ShinChven.materialdemomenu.menu.repo.entity.ItemEntity;
import com.Github.ShinChven.materialdemomenu.util.CursorExtractor;
import com.Github.ShinChven.materialdemomenu.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ShinChven on 2014/12/5.
 */
public class ItemService extends MenuDBHelper {

    private ItemService(Context context) {
        super(context);
    }


    /**
     * 查找菜单
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
    public static List<ItemEntity> findItems(
            Context context, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy
    ) {
        MenuDBHelper menuDBHelper = new MenuDBHelper(context);
        List<ItemEntity> items = new ArrayList<ItemEntity>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = menuDBHelper.getReadableDatabase();
            cursor = db.query(MenuDBHelper.TABLE_ITEM, columns, selection, selectionArgs, groupBy, having, orderBy);
            CursorExtractor extractor = new CursorExtractor(cursor);
            while (cursor.moveToNext()) {
                ItemEntity item = new ItemEntity();
                item.set_ID(extractor.getInt(_ID));
                item.setITEM_ID(extractor.getInt(ITEM_ID));
                item.setITEM_NAME(extractor.getString(ITEM_NAME));
                item.setDESCRIPTION(extractor.getString(DESCRIPTION));
                item.setCATEGORY_ID(extractor.getInt(CATEGORY_ID));
                item.setPRICE(extractor.getFloat(PRICE));
                item.setIMG(extractor.getString(IMG));
                item.setUPDATE_TIME(extractor.getDate(UPDATE_TIME));
                items.add(item);
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
        return items;
    }

    public static long insertOrUpdateItem(Context context, ItemEntity item) {
        ContentValues values=new ContentValues();
        values.put(ITEM_ID,item.getITEM_ID());
        values.put(CATEGORY_ID,item.getCATEGORY_ID());
        values.put(IMG,item.getIMG());
        values.put(DESCRIPTION,item.getDESCRIPTION());
        values.put(ITEM_NAME,item.getITEM_NAME());
        values.put(ITEM_STATUS,item.getITEM_STATUS());
        values.put(UPDATE_TIME,item.getUPDATE_TIME().getTime());
        values.put(PRICE,item.getPRICE());
        return insertOrUpdateItem(context,values);
    }

    /**
     * @param context
     * @param values
     * @return
     */
    public static long insertOrUpdateItem(Context context, ContentValues values) {
        Integer itemId = values.getAsInteger(ITEM_ID);
        List<ItemEntity> shrinkItems = findItems(context, new String[]{_ID}, ITEM_ID + "=?", new String[]{String.valueOf(itemId)}, null, null, null);
        MenuDBHelper menuDBHelper = new MenuDBHelper(context);
        SQLiteDatabase db = menuDBHelper.getWritableDatabase();
        if (shrinkItems.size() > 0) {
            try {
                return db.update(TABLE_ITEM, values, ITEM_ID + "=?", new String[]{String.valueOf(itemId)});
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }finally {
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
                return db.insert(TABLE_ITEM, _ID, values);
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }finally {
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
}

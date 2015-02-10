package com.Github.ShinChven.materialdemomenu.util;

import android.database.Cursor;

import java.util.Date;

/**
 * Cursor 取数据，你有没有写烦？
 * Created by ShinChven on 2014/12/5.
 */
public class CursorExtractor {

    private Cursor cursor;

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public CursorExtractor(Cursor cursor) {
        this.cursor = cursor;
    }

    public int getInt(String key) {
        try {
            return cursor.getInt(cursor.getColumnIndex(key));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    public String getString(String key) {
        try {
            return cursor.getString(cursor.getColumnIndex(key));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public long getLong(String key) {
        try {
            return cursor.getLong(cursor.getColumnIndex(key));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    public short getShort(String key) {
        try {
            return cursor.getShort(cursor.getColumnIndex(key));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    public double getDouble(String key) {
        try {
            return cursor.getDouble(cursor.getColumnIndex(key));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    public float getFloat(String key) {
        try {
            return cursor.getFloat(cursor.getColumnIndex(key));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    public byte[] getBlob(String key) {
        try {
            return cursor.getBlob(cursor.getColumnIndex(key));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return new byte[0];
    }

    /**
     * 请传毫秒数！
     * @param key
     * @return
     */
    public Date getDate(String key) {
        try {
            long time = cursor.getLong(cursor.getColumnIndex(key));
            return new Date(time);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }
}

package com.Github.ShinChven.materialdemomenu.util;

import org.json.JSONObject;

/**
 * 安全地取出json
 * Created by ShinChven on 2014/9/19.
 */
public class SafeJsonUtil {

    public static String getString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return StringUtil.EMPTY;
        }
    }

    public static String getString(JSONObject obj, String key, String defaultVal) {
        try {
            return obj.getString(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public static int getInt(JSONObject obj, String key) {
        try {
            return obj.getInt(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return 0;
        }
    }

    public static int getInt(JSONObject obj, String key, int defaultVal) {
        try {
            return obj.getInt(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public static boolean getBoolean(JSONObject obj, String key, boolean defaultVal) {
        try {
            return obj.getBoolean(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public static Boolean getBoolean(JSONObject obj, String key) {
        try {
            return obj.getBoolean(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    public static double getDouble(JSONObject obj, String key) {
        try {
            return obj.getDouble(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return 0;
        }
    }

    public static double getDouble(JSONObject obj, String key, double defaultVal) {
        try {
            return obj.getDouble(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public static long getLong(JSONObject obj, String key) {
        try {
            return obj.getLong(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return 0;
        }
    }

    public static long getLong(JSONObject obj, String key, long defaultVal) {
        try {
            return obj.getLong(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }
}

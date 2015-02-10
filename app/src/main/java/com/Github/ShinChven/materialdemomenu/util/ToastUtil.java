package com.Github.ShinChven.materialdemomenu.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ShinChven on 14/5/28.
 */
public class ToastUtil {
    public static void toastShortly(Context context, int content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public static void toastShortly(Context context, String content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public static void toastLonger(Context context, int content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public static void toastLonger(Context context, String content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                e.printStackTrace();
            }
        }
    }

}

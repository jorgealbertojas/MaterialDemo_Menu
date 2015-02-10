package com.Github.ShinChven.materialdemomenu.util;

import android.os.Environment;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ShinChven on 2014/10/24.
 * 打包数据
 */
public class DataPackTool {

    /**
     * 生成数据包缓存文件
     *
     * @return
     */
    public static String getPayloadCachePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new StringBuilder()
                    .append(Environment.getExternalStorageDirectory())
                    .append(DATA_PATH)
                    .append("/payload/")
                    .append(System.currentTimeMillis())
                    .append(".cache").toString();
        } else {
            return null;
        }
    }

    public static final String DATA_PATH = "/lk_data";


    /**
     * @param jsonLengthCapacity json长度数据的容量<p>
     *                           比如输入4，json的长度将允许0-9999 长度的byte[] 来存放json
     * @param json               json对象
     * @param data               文件数据
     * @return
     */
    public static byte[] packData(int jsonLengthCapacity, JSONObject json, byte[] data) throws Exception {
        LogUtil.i("packData_json",json.toString());
        byte[] jsonBytes = json.toString().getBytes();

        int tempLength = getCapacity(jsonLengthCapacity) + jsonBytes.length;
        String lengthStr = String.valueOf(tempLength).substring(1);
        byte[] lengthStrBytes = lengthStr.getBytes();

        byte[] bucket = new byte[lengthStrBytes.length + jsonBytes.length + data.length];

        System.arraycopy(lengthStrBytes, 0, bucket, 0, lengthStrBytes.length);
        System.arraycopy(jsonBytes, 0, bucket, lengthStrBytes.length, jsonBytes.length);
        System.arraycopy(data, 0, bucket, lengthStrBytes.length + jsonBytes.length, data.length);

        return bucket;
    }

    /**
     * @param path               缓存路径
     * @param jsonLengthCapacity json长度数据的容量<p>
     * @param json               比如输入4，json的长度将允许0-9999 长度的byte[] 来存放json
     * @param data               json对象
     * @return
     */
    public static File packData(String path, int jsonLengthCapacity, JSONObject json, byte[] data) {
        byte[] payload = null;
        try {
            payload = packData(jsonLengthCapacity, json, data);
        } catch (Exception e) {
           LogUtil.printStackTrace(e);
            return null;
        }
        if (payload != null) {
            File file = new File(path);

            file.getParentFile().mkdirs();

            FileOutputStream fos = null;
            ByteArrayInputStream bis = new ByteArrayInputStream(payload);
            try {
                fos = new FileOutputStream(file.getAbsolutePath());
                byte[] b = new byte[1024 * 50]; //限制流的读取大小
                int len = 0;
                while ((len = bis.read(b)) > 0) {
                    fos.write(b, 0, len);
                }
            } catch (Exception e) {
                LogUtil.printStackTrace(e);

                return null;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        LogUtil.printStackTrace(e);
                    }
                }
                try {
                    bis.close();
                } catch (IOException e) {
                    LogUtil.printStackTrace(e);
                }
            }
            return file;
        } else {
            return null;
        }
    }

    private static int getCapacity(int times) {
        int base = 10;
        int capacity = 1;
        for (int i = 0; i < times; i++) {
            capacity *= base;
        }
        return capacity;
    }
}

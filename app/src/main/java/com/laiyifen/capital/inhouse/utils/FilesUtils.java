package com.laiyifen.capital.inhouse.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FilesUtils {
    public static void saveInfo(OutputStream outputStream, String wenjian){
        try {
            outputStream.write(wenjian.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readInfo(InputStream input) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = input.read(buffer))!=-1) {
                os.write(buffer, 0, len);
            }
            byte[] data = os.toByteArray();
            os.close();
            input.close();
            String str = new String(data);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

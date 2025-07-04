package com.example.projectmobile.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private static File file;
    public static File getFileFromUri(Context context, Uri uri, String extension) {
        file = null;
        try {
            String fileName = "temp_file_" + System.currentTimeMillis() + extension;
            file = new File(context.getCacheDir(), fileName);

            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }

            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            Log.e("FileUtils", "Error converting Uri to File", e);
        }
        return file;
    }
}

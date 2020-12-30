package com.example.mynmap;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Asset {
    public void copyAssets(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(context, path);
            } else {
                String fullPath = context.getFilesDir() + "/" + path;
                File dir = new File(fullPath);
                dir.mkdirs();
                for (String asset : assets) {
                    String p;
                    if (path.equals(""))
                        p = "";
                    else
                        p = path + "/";

                    copyAssets(context, p + asset);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    public void copyFile(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        InputStream inS = null;
        OutputStream outS = null;
        String newFileName = null;
        try {
            inS = assetManager.open(filename);
            newFileName = context.getFilesDir() + "/" + filename;
            outS = new FileOutputStream(newFileName);
            byte[] buffer = new byte[1024];

            int read;
            while ((read = inS.read(buffer)) != -1) {
                outS.write(buffer, 0, read);
            }

            inS.close();
            outS.flush();
            outS.close();
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of " + newFileName);
            Log.e("tag", "Exception in copyFile() " + e.toString());
        }
    }
}

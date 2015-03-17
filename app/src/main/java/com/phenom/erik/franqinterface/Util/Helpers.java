package com.phenom.erik.franqinterface.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik on 2/20/2015.
 */
public class Helpers implements Constants {

    public static void copyFolder(String name, Context mContext) {
        // "Name" is the name of your folder!
        AssetManager assetManager = mContext.getAssets();
        String[] files = null;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            // Checking file on assets subfolder
            try {
                files = assetManager.list(name);
            } catch (IOException e) {
                Log.e("ERROR", "Failed to get asset file list.", e);
            }
            // Analyzing all file on assets subfolder
            for(String filename : files) {
                InputStream in = null;
                OutputStream out = null;
                if(filename.contains("webkit") || filename.contains("images") || filename.contains("sounds"))
                    continue;
                // First: checking if there is already a target folder
                File folder = new File(Environment.getExternalStorageDirectory() + "/FranqInterface/" + name);
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }
                if (success) {
                    // Moving all the files on external SD
                    try {
                        in = assetManager.open(name + "/" +filename);
                        out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/FranqInterface/" + name + "/" + filename);
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    } catch(IOException e) {
                        Log.e(TAG + ": ERROR", "Failed to copy asset file: " + filename, e);
                    }
                }
                else {
                    // Do something else on failure
                }
            }
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            // is to know is we can neither read nor write
        }
    }

    // Method used by copyAssets() on purpose to copy a file.
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8192];
        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static void shuffleArray(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }

        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
    }

    /*private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e(TAG, "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            if(filename.contains("webkit") || filename.contains("images") || filename.contains("sounds"))
                continue;
            try {
                in = assetManager.open(filename);
                File outFile;
                if(filename.contains("mp3")) {
                    outFile = new File(Environment.getExternalStorageDirectory()+"/FranqInterface/audio/",filename);
                } else {
                    outFile = new File(Environment.getExternalStorageDirectory()+"/FranqInterface/verbe/",filename);
                }
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e(TAG, "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    } */
}

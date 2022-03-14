package com.zhenl.packer;

import android.content.Context;
import java.io.File;
import java.io.IOException;

public final class PackerNg {
    private static final String EMPTY_STRING = "";

    public static String getChannel(File file) {
        try {
            return PackerCommon.readChannel(file);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getChannel(Context context) {
        try {
            return getChannelOrThrow(context);
        } catch (Exception e) {
            return "";
        }
    }

    public static synchronized String getChannelOrThrow(Context context) throws IOException {
        String readChannel;
        synchronized (PackerNg.class) {
            readChannel = PackerCommon.readChannel(new File(context.getApplicationInfo().sourceDir));
        }
        return readChannel;
    }
}

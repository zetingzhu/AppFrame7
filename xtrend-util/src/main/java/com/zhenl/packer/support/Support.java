package com.zhenl.packer.support;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Support {
    public static ByteBuffer readBlock(File apkFile, int id) throws IOException {
        return PayloadReader.readBlock(apkFile, id);
    }

    public static byte[] readBytes(File apkFile, int id) throws IOException {
        return PayloadReader.readBytes(apkFile, id);
    }

    public static void writeBlock(File apkFile, int id, ByteBuffer buffer) throws IOException {
        PayloadWriter.writeBlock(apkFile, id, buffer);
    }

    public static void writeBlock(File apkFile, int id, byte[] bytes) throws IOException {
        PayloadWriter.writeBlock(apkFile, id, bytes);
    }
}

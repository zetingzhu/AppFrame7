package com.zhenl.packer.support;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;

public class PayloadReader {
    private PayloadReader() {
    }

    public static byte[] readBytes(File apkFile, int id) throws IOException {
        ByteBuffer buf = readBlock(apkFile, id);
        if (buf == null) {
            return null;
        }
        return getBytes(buf);
    }

    public static ByteBuffer readBlock(File apkFile, int id) throws IOException {
        Map<Integer, ByteBuffer> blocks = readAllBlocks(apkFile);
        if (blocks == null) {
            return null;
        }
        return blocks.get(Integer.valueOf(id));
    }

    private static byte[] getBytes(ByteBuffer byteBuffer) {
        byte[] array = byteBuffer.array();
        int arrayOffset = byteBuffer.arrayOffset();
        return Arrays.copyOfRange(array, byteBuffer.position() + arrayOffset, byteBuffer.limit() + arrayOffset);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002a A[SYNTHETIC, Splitter:B:14:0x002a] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002f A[SYNTHETIC, Splitter:B:17:0x002f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Map<java.lang.Integer, java.nio.ByteBuffer> readAllBlocks(java.io.File r7) throws java.io.IOException {
        /*
            r2 = 0
            r3 = 0
            r1 = 0
            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0027 }
            java.lang.String r5 = "r"
            r4.<init>(r7, r5)     // Catch:{ all -> 0x0027 }
            java.nio.channels.FileChannel r1 = r4.getChannel()     // Catch:{ all -> 0x003b }
            com.zhenl.packer.support.Pair r5 = com.zhenl.packer.support.ApkUtil.findApkSigningBlock(r1)     // Catch:{ all -> 0x003b }
            java.lang.Object r0 = r5.getFirst()     // Catch:{ all -> 0x003b }
            java.nio.ByteBuffer r0 = (java.nio.ByteBuffer) r0     // Catch:{ all -> 0x003b }
            java.util.Map r2 = com.zhenl.packer.support.ApkUtil.findIdValues(r0)     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0021
            r1.close()     // Catch:{ IOException -> 0x0033 }
        L_0x0021:
            if (r4 == 0) goto L_0x0026
            r4.close()     // Catch:{ IOException -> 0x0035 }
        L_0x0026:
            return r2
        L_0x0027:
            r5 = move-exception
        L_0x0028:
            if (r1 == 0) goto L_0x002d
            r1.close()     // Catch:{ IOException -> 0x0037 }
        L_0x002d:
            if (r3 == 0) goto L_0x0032
            r3.close()     // Catch:{ IOException -> 0x0039 }
        L_0x0032:
            throw r5
        L_0x0033:
            r5 = move-exception
            goto L_0x0021
        L_0x0035:
            r5 = move-exception
            goto L_0x0026
        L_0x0037:
            r6 = move-exception
            goto L_0x002d
        L_0x0039:
            r6 = move-exception
            goto L_0x0032
        L_0x003b:
            r5 = move-exception
            r3 = r4
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhenl.packer.support.PayloadReader.readAllBlocks(java.io.File):java.util.Map");
    }
}

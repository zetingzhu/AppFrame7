package com.zhenl.packer.support;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class PayloadWriter {

    /* access modifiers changed from: package-private */
    public interface ApkSigningBlockHandler {
        ApkSigningBlock handle(Map<Integer, ByteBuffer> map);
    }

    private PayloadWriter() {
    }

    public static void writeBlock(File apkFile, int id, byte[] bytes) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(bytes, 0, bytes.length);
        byteBuffer.flip();
        writeBlock(apkFile, id, byteBuffer);
    }

    public static void writeBlock(File apkFile, int id, ByteBuffer buffer) throws IOException {
        Map<Integer, ByteBuffer> idValues = new HashMap<>();
        idValues.put(Integer.valueOf(id), buffer);
        writeValues(apkFile, idValues, false);
    }

    public static void writeValues(File apkFile, final Map<Integer, ByteBuffer> idValues, boolean lowMemory) throws IOException {
        writeApkSigningBlock(apkFile, new ApkSigningBlockHandler() {
            /* class com.zhenl.packer.support.PayloadWriter.AnonymousClass1 */

            @Override // com.zhenl.packer.support.PayloadWriter.ApkSigningBlockHandler
            public ApkSigningBlock handle(Map<Integer, ByteBuffer> originIdValues) {
                if (idValues != null && !idValues.isEmpty()) {
                    originIdValues.putAll(idValues);
                }
                ApkSigningBlock apkSigningBlock = new ApkSigningBlock();
                for (Map.Entry<Integer, ByteBuffer> entry : originIdValues.entrySet()) {
                    apkSigningBlock.addPayload(new ApkSigningPayload(entry.getKey().intValue(), entry.getValue()));
                }
                return apkSigningBlock;
            }
        }, lowMemory);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x016e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void writeApkSigningBlock(java.io.File r40, com.zhenl.packer.support.PayloadWriter.ApkSigningBlockHandler r41, boolean r42) throws java.io.IOException {
        /*
        // Method dump skipped, instructions count: 507
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhenl.packer.support.PayloadWriter.writeApkSigningBlock(java.io.File, com.zhenl.packer.support.PayloadWriter$ApkSigningBlockHandler, boolean):void");
    }
}

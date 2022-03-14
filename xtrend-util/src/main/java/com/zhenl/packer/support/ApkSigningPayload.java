package com.zhenl.packer.support;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ApkSigningPayload {
    private final ByteBuffer buffer;
    private final int id;
    private final int totalSize;

    ApkSigningPayload(int id2, ByteBuffer buffer2) {
        this.id = id2;
        if (buffer2.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
        this.buffer = buffer2;
        this.totalSize = buffer2.remaining() + 12;
    }

    public int getId() {
        return this.id;
    }

    public byte[] getByteBuffer() {
        byte[] array = this.buffer.array();
        int arrayOffset = this.buffer.arrayOffset();
        return Arrays.copyOfRange(array, this.buffer.position() + arrayOffset, this.buffer.limit() + arrayOffset);
    }

    public int getTotalSize() {
        return this.totalSize;
    }
}

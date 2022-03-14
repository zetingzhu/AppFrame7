package com.zhenl.packer.support;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApkUtil {
    public static final int ANDROID_COMMON_PAGE_ALIGNMENT_BYTES = 4096;
    public static final int APK_CHANNEL_BLOCK_ID = 1903654775;
    public static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    public static final long APK_SIG_BLOCK_MAGIC_HI = 3617552046287187010L;
    public static final long APK_SIG_BLOCK_MAGIC_LO = 2334950737559900225L;
    private static final int APK_SIG_BLOCK_MIN_SIZE = 32;
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static final int UINT16_MAX_VALUE = 65535;
    public static final int VERITY_PADDING_BLOCK_ID = 1114793335;
    private static final int ZIP_EOCD_COMMENT_LENGTH_FIELD_OFFSET = 20;
    private static final int ZIP_EOCD_REC_MIN_SIZE = 22;
    private static final int ZIP_EOCD_REC_SIG = 101010256;

    private ApkUtil() {
    }

    public static long getCommentLength(FileChannel fileChannel) throws IOException {
        long archiveSize = fileChannel.size();
        if (archiveSize < 22) {
            throw new IOException("APK too small for ZIP End of Central Directory (EOCD) record");
        }
        long maxCommentLength = Math.min(archiveSize - 22, 65535L);
        long eocdWithEmptyCommentStartPosition = archiveSize - 22;
        for (int expectedCommentLength = 0; ((long) expectedCommentLength) <= maxCommentLength; expectedCommentLength++) {
            long eocdStartPos = eocdWithEmptyCommentStartPosition - ((long) expectedCommentLength);
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            fileChannel.position(eocdStartPos);
            fileChannel.read(byteBuffer);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            if (byteBuffer.getInt(0) == ZIP_EOCD_REC_SIG) {
                ByteBuffer commentLengthByteBuffer = ByteBuffer.allocate(2);
                fileChannel.position(20 + eocdStartPos);
                fileChannel.read(commentLengthByteBuffer);
                commentLengthByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                int actualCommentLength = commentLengthByteBuffer.getShort(0);
                if (actualCommentLength == expectedCommentLength) {
                    return (long) actualCommentLength;
                }
            }
        }
        throw new IOException("ZIP End of Central Directory (EOCD) record not found");
    }

    public static long findCentralDirStartOffset(FileChannel fileChannel) throws IOException {
        return findCentralDirStartOffset(fileChannel, getCommentLength(fileChannel));
    }

    public static long findCentralDirStartOffset(FileChannel fileChannel, long commentLength) throws IOException {
        ByteBuffer zipCentralDirectoryStart = ByteBuffer.allocate(4);
        zipCentralDirectoryStart.order(ByteOrder.LITTLE_ENDIAN);
        fileChannel.position((fileChannel.size() - commentLength) - 6);
        fileChannel.read(zipCentralDirectoryStart);
        return (long) zipCentralDirectoryStart.getInt(0);
    }

    public static Pair<ByteBuffer, Long> findApkSigningBlock(FileChannel fileChannel) throws IOException {
        return findApkSigningBlock(fileChannel, findCentralDirStartOffset(fileChannel));
    }

    public static Pair<ByteBuffer, Long> findApkSigningBlock(FileChannel fileChannel, long centralDirOffset) throws IOException {
        if (centralDirOffset < 32) {
            throw new IOException("APK too small for APK Signing Block. ZIP Central Directory offset: " + centralDirOffset);
        }
        fileChannel.position(centralDirOffset - 24);
        ByteBuffer footer = ByteBuffer.allocate(24);
        fileChannel.read(footer);
        footer.order(ByteOrder.LITTLE_ENDIAN);
        if (footer.getLong(8) == APK_SIG_BLOCK_MAGIC_LO && footer.getLong(16) == APK_SIG_BLOCK_MAGIC_HI) {
            long apkSigBlockSizeInFooter = footer.getLong(0);
            if (apkSigBlockSizeInFooter < ((long) footer.capacity()) || apkSigBlockSizeInFooter > 2147483639) {
                throw new IOException("APK Signing Block size out of range: " + apkSigBlockSizeInFooter);
            }
            int totalSize = (int) (8 + apkSigBlockSizeInFooter);
            long apkSigBlockOffset = centralDirOffset - ((long) totalSize);
            if (apkSigBlockOffset < 0) {
                throw new IOException("APK Signing Block offset out of range: " + apkSigBlockOffset);
            }
            fileChannel.position(apkSigBlockOffset);
            ByteBuffer apkSigBlock = ByteBuffer.allocate(totalSize);
            fileChannel.read(apkSigBlock);
            apkSigBlock.order(ByteOrder.LITTLE_ENDIAN);
            long apkSigBlockSizeInHeader = apkSigBlock.getLong(0);
            if (apkSigBlockSizeInHeader == apkSigBlockSizeInFooter) {
                return Pair.of(apkSigBlock, Long.valueOf(apkSigBlockOffset));
            }
            throw new IOException("APK Signing Block sizes in header and footer do not match: " + apkSigBlockSizeInHeader + " vs " + apkSigBlockSizeInFooter);
        }
        throw new IOException("No APK Signing Block before ZIP Central Directory");
    }

    public static Map<Integer, ByteBuffer> findIdValues(ByteBuffer apkSigningBlock) throws IOException {
        checkByteOrderLittleEndian(apkSigningBlock);
        ByteBuffer pairs = sliceFromTo(apkSigningBlock, 8, apkSigningBlock.capacity() - 24);
        Map<Integer, ByteBuffer> idValues = new LinkedHashMap<>();
        int entryCount = 0;
        while (pairs.hasRemaining()) {
            entryCount++;
            if (pairs.remaining() < 8) {
                throw new IOException("Insufficient data to read size of APK Signing Block entry #" + entryCount);
            }
            long lenLong = pairs.getLong();
            if (lenLong < 4 || lenLong > 2147483647L) {
                throw new IOException("APK Signing Block entry #" + entryCount + " size out of range: " + lenLong);
            }
            int len = (int) lenLong;
            int nextEntryPos = pairs.position() + len;
            if (len > pairs.remaining()) {
                throw new IOException("APK Signing Block entry #" + entryCount + " size out of range: " + len + ", available: " + pairs.remaining());
            }
            idValues.put(Integer.valueOf(pairs.getInt()), getByteBuffer(pairs, len - 4));
            pairs.position(nextEntryPos);
        }
        return idValues;
    }

    /* JADX INFO: finally extract failed */
    private static ByteBuffer sliceFromTo(ByteBuffer source, int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException("start: " + start);
        } else if (end < start) {
            throw new IllegalArgumentException("end < start: " + end + " < " + start);
        } else {
            int capacity = source.capacity();
            if (end > source.capacity()) {
                throw new IllegalArgumentException("end > capacity: " + end + " > " + capacity);
            }
            int originalLimit = source.limit();
            int originalPosition = source.position();
            try {
                source.position(0);
                source.limit(end);
                source.position(start);
                ByteBuffer result = source.slice();
                result.order(source.order());
                source.position(0);
                source.limit(originalLimit);
                source.position(originalPosition);
                return result;
            } catch (Throwable th) {
                source.position(0);
                source.limit(originalLimit);
                source.position(originalPosition);
                throw th;
            }
        }
    }

    private static ByteBuffer getByteBuffer(ByteBuffer source, int size) throws BufferUnderflowException {
        if (size < 0) {
            throw new IllegalArgumentException("size: " + size);
        }
        int originalLimit = source.limit();
        int position = source.position();
        int limit = position + size;
        if (limit < position || limit > originalLimit) {
            throw new BufferUnderflowException();
        }
        source.limit(limit);
        try {
            ByteBuffer result = source.slice();
            result.order(source.order());
            source.position(limit);
            return result;
        } finally {
            source.limit(originalLimit);
        }
    }

    private static void checkByteOrderLittleEndian(ByteBuffer buffer) {
        if (buffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }
}

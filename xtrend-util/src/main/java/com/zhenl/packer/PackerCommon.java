package com.zhenl.packer;

import com.zhenl.packer.support.Support;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PackerCommon {
    public static final String BLOCK_MAGIC = "Packer Ng Sig V2";
    public static final int CHANNEL_BLOCK_ID = 2054712097;
    public static final String CHANNEL_KEY = "CHANNEL";
    public static final String SEP_KV = "∘";
    public static final String SEP_LINE = "∙";
    public static final String UTF8 = "UTF-8";

    public static String readChannel(File file) throws IOException {
        return readValue(file, CHANNEL_KEY, CHANNEL_BLOCK_ID);
    }

    public static void writeChannel(File file, String channel) throws IOException {
        writeValue(file, CHANNEL_KEY, channel, CHANNEL_BLOCK_ID);
    }

    static String readValue(File file, String key, int blockId) throws IOException {
        Map<String, String> map = readValues(file, blockId);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return map.get(key);
    }

    static void writeValue(File file, String key, String value, int blockId) throws IOException {
        Map<String, String> values = new HashMap<>();
        values.put(key, value);
        writeValues(file, values, blockId);
    }

    public static Map<String, String> readValues(File file, int blockId) throws IOException {
        return mapFromString(readString(file, blockId));
    }

    public static String readString(File file, int blockId) throws IOException {
        byte[] bytes = readBytes(file, blockId);
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, "UTF-8");
    }

    public static byte[] readBytes(File file, int blockId) throws IOException {
        return readPayloadImpl(file, blockId);
    }

    public static void writeValues(File file, Map<String, String> values, int blockId) throws IOException {
        if (values != null && !values.isEmpty()) {
            Map<String, String> newValues = new HashMap<>();
            Map<String, String> oldValues = readValues(file, blockId);
            if (oldValues != null) {
                newValues.putAll(oldValues);
            }
            newValues.putAll(values);
            writeString(file, mapToString(newValues), blockId);
        }
    }

    public static void writeString(File file, String content, int blockId) throws IOException {
        writeBytes(file, content.getBytes("UTF-8"), blockId);
    }

    public static void writeBytes(File file, byte[] payload, int blockId) throws IOException {
        writePayloadImpl(file, payload, blockId);
    }

    static void writePayloadImpl(File file, byte[] payload, int blockId) throws IOException {
        Support.writeBlock(file, blockId, wrapPayload(payload));
    }

    static byte[] readPayloadImpl(File file, int blockId) throws IOException {
        int payloadLength1;
        ByteBuffer buffer = Support.readBlock(file, blockId);
        if (buffer == null) {
            return null;
        }
        byte[] magic = BLOCK_MAGIC.getBytes("UTF-8");
        byte[] actual = new byte[magic.length];
        buffer.get(actual);
        if (Arrays.equals(magic, actual) && (payloadLength1 = buffer.getInt()) > 0) {
            byte[] payload = new byte[payloadLength1];
            buffer.get(payload);
            if (buffer.getInt() == payloadLength1) {
                return payload;
            }
        }
        return null;
    }

    static ByteBuffer wrapPayload(byte[] payload) throws UnsupportedEncodingException {
        byte[] magic = BLOCK_MAGIC.getBytes("UTF-8");
        int magicLen = magic.length;
        int payloadLen = payload.length;
        ByteBuffer buffer = ByteBuffer.allocate(((magicLen + 4) * 2) + payloadLen);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(magic);
        buffer.putInt(payloadLen);
        buffer.put(payload);
        buffer.putInt(payloadLen);
        buffer.flip();
        return buffer;
    }

    public static String mapToString(Map<String, String> map) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey()).append(SEP_KV).append(entry.getValue()).append(SEP_LINE);
        }
        return builder.toString();
    }

    public static Map<String, String> mapFromString(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        for (String entry : string.split(SEP_LINE)) {
            String[] kv = entry.split(SEP_KV);
            if (kv.length == 2) {
                map.put(kv[0], kv[1]);
            }
        }
        return map;
    }
}

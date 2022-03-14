package com.zhenl.packer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test {
    public static void main(String[] args) throws Exception {
        try {
            File srcFile = new File(args[0]);
            if (args.length > 1) {
                File channelFile = new File(args[1]);
                FileInputStream channelStream = new FileInputStream(channelFile);
                byte[] channelBytes = new byte[channelStream.available()];
                channelStream.read(channelBytes);
                String[] channels = new String(channelBytes).split("\\|");
                String outDir = channelFile.getParentFile().getAbsolutePath();
                if (args.length >= 3) {
                    outDir = args[2];
                    File dir = new File(outDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }
                String name = srcFile.getName();
                String name2 = name.substring(0, name.length() - 4);
                int length = channels.length;
                for (int i = 0; i < length; i++) {
                    String channel = channels[i];
                    File file = new File(outDir, name2 + "_" + channel + ".apk");
                    FileInputStream in = new FileInputStream(srcFile);
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] b = new byte[1024];
                    while (true) {
                        int n = in.read(b);
                        if (n == -1) {
                            break;
                        }
                        out.write(b, 0, n);
                    }
                    in.close();
                    out.close();
                    PackerCommon.writeChannel(file, channel);
                    System.out.println(PackerCommon.readChannel(file));
                }
                return;
            }
            System.out.println(PackerCommon.readChannel(srcFile));
        } catch (Exception e) {
            System.out.println("Usage");
            System.out.println("java -jar packer.jar D:/Fxgo.apk(必填) D:/channel.txt(必填) D:/outputDir(可选)");
            e.printStackTrace();
        }
    }
}

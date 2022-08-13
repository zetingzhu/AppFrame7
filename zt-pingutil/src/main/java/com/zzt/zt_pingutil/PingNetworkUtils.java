package com.zzt.zt_pingutil;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author: zeting
 * @date: 2022/8/11
 * 检测是否可以连接到外网的工具类
 */
public class PingNetworkUtils {
    private static final String TAG = "PingNetworkUtils";

    /**
     * 开始ping
     */
    public void startPing(String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String pingMessage = checkNetworkByPing(10, 20, ip);
                Log.e(TAG, ">>>:" + pingMessage);
            }
        }).start();
    }

    /**
     * 检测网络是否能连接外网，通过ping方式
     *
     * @return true/false
     */
    public static String checkNetworkByPing(int count, int timeout, String ip) {
        Process process = null;
        InputStreamReader isrSuc = null;
        InputStreamReader isrErr = null;
        BufferedReader sucInput = null;
        BufferedReader errInput = null;
        StringBuilder stringBuffer = new StringBuilder();
        try {
            String pingCommand = "ping -c " + count + " -w " + timeout + " " + ip;
            stringBuffer.append("ping start : " + pingCommand + "\n");
            // ping 10次
            process = Runtime.getRuntime().exec(pingCommand);
            if (process == null) {
                stringBuffer.append("ping process is null.");
            } else {
                isrSuc = new InputStreamReader(process.getInputStream());
                isrErr = new InputStreamReader(process.getErrorStream());
                sucInput = new BufferedReader(isrSuc);
                errInput = new BufferedReader(isrErr);

                String tmpSuccess = null;
                String tmpError = null;
                while (((tmpSuccess = sucInput.readLine()) != null || (tmpError = errInput.readLine()) != null)) {
                    if (tmpSuccess != null) {
                        stringBuffer.append("> ").append(tmpSuccess).append("\n");
                    }
                    if (tmpError != null) {
                        stringBuffer.append("< ").append(tmpError).append("\n");
                    }
                }
                if (null != sucInput) {
                    sucInput.close();
                }
                if (null != errInput) {
                    errInput.close();
                }
                if (null != isrSuc) {
                    isrSuc.close();
                }
                if (null != isrErr) {
                    isrErr.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            stringBuffer.append("connected IOException :" + e.getMessage());
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
        return stringBuffer.toString();
    }
}


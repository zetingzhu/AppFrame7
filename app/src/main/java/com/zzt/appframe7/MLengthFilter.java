package com.zzt.appframe7;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

/**
 * @author: zeting
 * @date: 2022/3/18
 */
public class MLengthFilter implements InputFilter {
    private static final String TAG = MLengthFilter.class.getSimpleName();
    private final int mMax;

    public MLengthFilter(int max) {
        mMax = max;
    }

    /**
     * 这里实现字符串过滤，把你允许输入的字母添加到下面的数组即可！
     */
    protected char[] getAcceptedChars() {
        return new char[]{'0', '1', '2', '3'};
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Log.w(TAG, ">>>> " + source + " s:" + start + " e:" + end + " (d:" + dest + ") ds:" + dstart + " de:" + dend);

        int keep = mMax - (dest.length() - (dend - dstart));
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null; // keep original
        } else {
            keep += start;
            if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                --keep;
                if (keep == start) {
                    return "";
                }
            }
            return source.subSequence(start, keep);
        }
    }

    /**
     * @return the maximum length enforced by this input filter
     */
    public int getMax() {
        return mMax;
    }
}
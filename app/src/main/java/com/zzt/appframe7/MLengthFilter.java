package com.zzt.appframe7;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;

/**
 * @author: zeting
 * @date: 2022/3/18
 * 长度控制
 */
public class MLengthFilter implements InputFilter {
    public static final String TAG = MLengthFilter.class.getSimpleName();
    private final int startMax;
    private final int endMax;

    public MLengthFilter(int start, int end) {
        startMax = start;
        endMax = end;
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {



        Log.w(TAG, ">>>> (" + source + ") s:" + start + " e:" + end + " (d:" + dest + ") ds:" + dstart + " de:" + dend);
        int index = dest.toString().indexOf(".") + 1;
        if (index > 0) {
            if (dest.length() > 0 && dstart < dend) {
                CharSequence dot = dest.subSequence(dstart, dend);
                Log.e(TAG, "dot:" + dot);
                if (".".contains(dot.toString())) {
                    if (dest.length() > startMax + 1) {
                        return dot;
                    } else {
                        return null;
                    }
                }
            }
            int keep = (dest.length() - index);
            Log.e(TAG, "index:" + index + " keep:" + keep);
            if (dstart >= index) {
                if (keep >= endMax) {
                    return "";
                } else
                    return null;
            } else {
                if (index > startMax) {
                    return "";
                } else {
                    return source;
                }
            }
        } else {
            if (source.toString().contains(".")) {
                if ((dest.length() - dstart) > endMax) {
                    return "";
                }
                return null;
            }
            int keep = startMax - (dest.length() - (dend - dstart));
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
    }
}
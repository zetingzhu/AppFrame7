package com.zzt.appframe7;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;

/**
 * @author: zeting
 * @date: 2022/3/18
 * 千分位长度控制
 */
public class NumberAmountLengthFilter implements InputFilter {
    public static final String TAG = NumberAmountLengthFilter.class.getSimpleName();
    private final int startMax;// 小数点前面位数
    private final int endMax;// 小数点后面位数
    boolean thous;// 是否支支持千分位展示

    public NumberAmountLengthFilter(boolean thous, int start, int end) {
        this.startMax = start;
        this.endMax = end;
        this.thous = thous;
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Log.w(TAG, ">>>> (" + source + ") s:" + start + " e:" + end + " (d:" + dest + ") ds:" + dstart + " de:" + dend);
        int index = dest.toString().indexOf(".");
        Log.e(TAG, ">>>>>>>> index:" + index);
        if (index >= 0) {
            if (dstart <= index) {
                String replaceIndex = dest.subSequence(0, index).toString().replace(",", "");
                if (replaceIndex.length() >= startMax) {
                    return "";
                }
            }
            StringBuilder ssb = new StringBuilder();
            if (end > start && dest.length() > 0) {
                CharSequence sStr = dest.subSequence(0, dstart);
                ssb.append(sStr);
                ssb.append(source);
                ssb.append(dest.subSequence(dend, dest.length()));
                Log.w(TAG, ">>>>>>>>" + ssb.toString());
                boolean number = NumberAmountUtils.isNumberAmountInput(thous, ssb.toString(), endMax);
                if (number) {
                    return null;
                } else {
                    return "";
                }
            } else if (dest.length() > 0 && dend > dstart) {
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
        } else {
            if (source.toString().contains(".")) {
                if ((dest.length() - dstart) >= endMax) {
                    return "";
                }
                return null;
            }
            String destReplace = dest.toString().replace(",", "");
            if (destReplace.length() >= startMax) {
                return "";
            }
        }
        return null;
    }
}
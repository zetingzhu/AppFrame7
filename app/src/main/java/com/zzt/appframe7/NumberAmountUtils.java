package com.zzt.appframe7;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberAmountUtils {
    public static final String TAG = NumberAmountUtils.class.getSimpleName();

    /**
     * 设置千分位金额展示
     *
     * @param editText
     * @param startCount
     * @param endCount
     */
    public static void setAmountThousEdittext(EditText editText, boolean thous, int startCount, int endCount) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setFilters(new InputFilter[]{new NumberAmountLengthFilter(thous, startCount, endCount)});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG, "text:" + s.toString());
                if (thous && !TextUtils.isEmpty(s) && !s.toString().endsWith(".")) {
                    String moneyType = NumberAmountUtils.formatAmount(s.toString(), endCount);
                    Log.e(TAG, "text 转换:" + moneyType);
                    if (!moneyType.equals(s.toString())) {
                        editText.setText(moneyType);
                        editText.setSelection(moneyType.length());
                    }
                }
            }
        });
    }

    /**
     * 格式化千分金额
     *
     * @param string
     * @return
     */
    public static String formatAmount(String string, int endCount) {
        try {
            String replace = string.replace(",", "");
            StringBuilder sBuild = new StringBuilder("#,###");
            if (endCount > 0) {
                sBuild.append(".");
                while (endCount-- > 0) {
                    sBuild.append("#");
                }
            }
            Log.d(TAG, "拼接出来字符串：" + sBuild);
            DecimalFormat decimalFormat = new DecimalFormat(sBuild.toString());
            BigDecimal bigDecimal = new BigDecimal(replace);
            return decimalFormat.format(bigDecimal.doubleValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return string;
    }

//    private static final String AMT_REGEX = "^(([1-9][0-9]{0,14})|([0]{1})|(([0]\\.\\d{1,2}|[1-9][0-9]{0,14}\\.\\d{1,2})))$";
//    static String regex = "^(([1-9][0-9]{0,2}(,\\d{3})*)|0)(\\.\\d{1,2})?$";
//    static String regex1 = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";


    //金额验证
//    public static boolean isNumber(String str) {
//        Pattern pattern = Pattern.compile(regex); // 判断小数点后2位的数字的正则表达式
//        Matcher match = pattern.matcher(str);
//        return match.matches();
//    }

//    public static boolean isNumberThousandInput(String str, int count) {
//        /**
//         * ^(([1-9][0-9]{0,2}(,\\d{3})*(,\\d{4})*)|0|[1-9][0-9]{0,3})(\\.\\d{1,2})?$
//         */
//        String regex = "^(([1-9][0-9]{0,2}(,\\d{3})*(,\\d{4})*)|0|[1-9][0-9]{0,3})(\\.\\d{1," + count + "})?$";
//        Pattern pattern = Pattern.compile(regex); // 判断小数点后2位的数字的正则表达式
//        Matcher match = pattern.matcher(str);
//        return match.matches();
//    }

    public static boolean isNumberAmountInput(boolean thous, String str, int count) {
        /**
         *  ^(([1-9][0-9]*)|0)(\\.\\d{1,2})?$
         *  ^(([1-9][0-9]{0,2}(,\\d{3})*(,\\d{4})*)|0|[1-9][0-9]{0,3})(\\.\\d{1,2})?$
         */
        String amount = "^(([1-9][0-9]*)|0)(\\.\\d{1," + count + "})?$";
        String thousAmount = "^(([1-9][0-9]{0,2}(,\\d{3})*(,\\d{4})*)|0|[1-9][0-9]{0,3})(\\.\\d{1," + count + "})?$";
        String regex = thous ? thousAmount : amount;
        Pattern pattern = Pattern.compile(regex); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        return match.matches();
    }


//    public static String formatAmount1(String string) {
//        String replace = string.replace("(-?\\d+)(\\d{3})", "$1,");
//        String replace1 = string.replace("(\\d)(?=(\\d{3})+(?!\\d))", "$1,");
//        return replace;
//    }
}

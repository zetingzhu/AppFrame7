package com.common.lib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceActivity;


import androidx.annotation.NonNull;

import java.io.File;

/**
 * Created by fangzhu on 2014/12/16.
 */
public class PreferenceSetting {
    public static final String SETTING_FILE_NAME = "PreferenceSetting_default";// 默认存储文件

    public static final String PRE_REFERSH_TIME = "PreferenceSetting_refershtime";
    public static final String PRE_CHCHE_APP = "PreferenceSetting_CACE_APP";
    public static final String PRE_UMENG_EVENT = "PreferenceSetting_umeng_event";//记录umeng统计
    public static final String NOT_ALLOW_UNIFYPWDDLG = "not_allow_unifypwddlg";// 是否允许统一交易密码弹窗
    public static final String ISINITZIXUAN = "isInitZixuan";// 是否初始化了自选
    public static final String KEY_TO_UPDATE_VERSION_DIALOG_DISPLAY_TIME = "key_to_update_version_dialog_display_time";//更新弹框上一次显示时间
    public static final String KEY_ABOUT_YOU_CACHE = "KEY_ABOUT_YOU_CACHE";// about_you 缓存
    public static final String KEY_HTTP_URL_MODE = "key_http_url_mode";
    public static final String KEY_FIRST_SHOW_LOADING = "Key_first_show_loading";//是否展示引导
    public static final String KEY_IS_HIDE_FUND = "key_is_hide_fund"; //是否隐藏资金页面各项数字
    public static final String KEY_IS_HIDE_INVESTMENTS = "key_is_hide_investments"; //是否隐藏投资布局
    public static final String KEY_IS_HIDE_INVESTMENTS_EUR = "key_is_hide_investments_eur"; //是否隐藏投资欧元布局
    public static final String KEY_IS_HIDE_INVESTMENTS_USD = "key_is_hide_investments_usd"; //是否隐藏投资美元布局
    public static final String KEY_IS_HIDE_OPTIONALLIST = "key_is_hide_optionallist"; //是否隐藏自选布局
    public static final String KEY_IS_HIDE_HOLDLIST = "key_is_hide_holdlist"; //是否隐藏资金二级页面持仓布局
    public static final String KEY_IS_HIDE_ORDERLIS = "key_is_hide_orderlis"; //是否隐藏资金二级页面挂单布局
    public static final String KEY_IS_HIDE_ME_ACCOUNT_LIST = "key_is_hide_me_account_list";//是否隐藏我的界面资金账户页面
    public static final String KEY_OPEN_MARK_ACCOUNTLIST = "key_open_mark_accountlist";//我的界面资金账户页面 安全设置的开关
    public static final String KEY_CURRENT_CURRENCY = "key_current_currency";//资金页面当前币种
    public static final String KEY_CURRENT_SYMBOL = "key_current_symbol";//资金页面当前货币符号
    public static final String KEY_IS_NOT_FRIST_DEPOSIT = "key_is_not_frist_deposit";//是否是多次充值页面
    public static final String KEY_IS_NOT_FRIST_WITHDRAW = "key_is_not_frist_withdraw";//是否是多次提现页面
    public static final String KEY_IS_NOT_FRIST_TRANSFERACCOUNTS = "key_is_not_frist_transferaccounts";//是否是多次进入转账页面


    public static String getDownloadDir(Context app) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在

        if (sdCardExist)      //如果SD卡存在，则获取跟目录
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.toString() + "/myapp/cache/download";
        }
        return app.getExternalCacheDir() + "myapp/cache/download";
    }

    /**
     * 获取保存不同文件的 SharedPreferences
     */
    public static SharedPreferences getSPName(Context context, String spName) {
        return context.getSharedPreferences(spName, PreferenceActivity.MODE_PRIVATE);
    }

    public static void putString(SharedPreferences sp, @NonNull final String key, final String value) {
        sp.edit().putString(key, value).apply();
    }

    public static String getString(SharedPreferences sp, @NonNull final String key) {
        return sp.getString(key, "");
    }

    public static boolean getBoolean(Context context, String key) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            return sp.getBoolean(key, false);
        } else {
            return false;
        }
    }

    public static void setBoolean(Context context, String key, boolean value) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            sp.edit().putBoolean(key, value).apply();
        }
    }

    public static String getString(Context context, String key) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            return sp.getString(key, null);
        } else {
            return "";
        }
    }

    public static void setString(Context context, String key, String value) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            sp.edit().putString(key, value).apply();
        }
    }

    public static long getLong(Context context, String key) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            return sp.getLong(key, -1L);
        } else {
            return -1L;
        }
    }

    public static void setLong(Context context, String key, long value) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            sp.edit().putLong(key, value).apply();
        }
    }

    public static int getInt(Context context, String key) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            return sp.getInt(key, -1);
        } else {
            return -1;
        }
    }

    public static void setInt(Context context, String key, int value) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(
                    SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
            sp.edit().putInt(key, value).apply();
        }
    }

    public static boolean getBooleanDefaultTrue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }
}

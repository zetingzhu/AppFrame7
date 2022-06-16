package org.apache.util;

/**
 * @author: zeting
 * @date: 2022/4/1
 */
public class StringUtils {
    public static boolean isBlank(String str)
    {
        if (str == null)
        {
            return true;
        }
        int len = str.length();
        for (int i = 0; i < len; i++)
        {
            if (!Character.isWhitespace(str.codePointAt(i)))
            {
                // found a non-whitespace, we can stop searching  now
                return false;
            }
        }
        // only whitespace
        return true;
    }

}

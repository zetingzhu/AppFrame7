/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zzt.appframe7;

import static android.text.method.MetaKeyKeyListener.getMetaState;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.KeyListener;
import android.view.KeyEvent;

/**
 * For numeric text entry
 * <p></p>
 * As for all implementations of {@link KeyListener}, this class is only concerned
 * with hardware keyboards.  Software input methods have no obligation to trigger
 * the methods in this class.
 */
public class NumberFilter implements InputFilter {
    /**
     * You can say which characters you can accept.
     */
    protected char[] getAcceptedChars() {
        return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    }

    protected int lookup(KeyEvent event, Spannable content) {
        return event.getMatch(getAcceptedChars(), getMetaState(content, event));
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        char[] accept = getAcceptedChars();

        int i;
        for (i = start; i < end; i++) {
            if (!ok(accept, source.charAt(i))) {
                break;
            }
        }

        if (i == end) {
            // It was all OK.
            return null;
        }

        if (end - start == 1) {
            // It was not OK, and there is only one char, so nothing remains.
            return "";
        }

        SpannableStringBuilder filtered =
                new SpannableStringBuilder(source, start, end);
        i -= start;
        end -= start;

        // Only count down to i because the chars before that were all OK.
        for (int j = end - 1; j >= i; j--) {
            if (!ok(accept, source.charAt(j))) {
                filtered.delete(j, j + 1);
            }
        }

        return filtered;
    }

    protected static boolean ok(char[] accept, char c) {
        for (int i = accept.length - 1; i >= 0; i--) {
            if (accept[i] == c) {
                return true;
            }
        }

        return false;
    }

}

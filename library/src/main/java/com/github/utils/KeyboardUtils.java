package com.github.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * KeyboardUtils
 *
 * @author  Ivan on 2018-11-28 01:10.
 * @version v0.1
 * @since   v1.0
 */
public class KeyboardUtils {

    public static void hideSoftKeyboard(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

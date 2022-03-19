package com.material.snackbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Note: this class is here to provide backwards-compatible way for apps written before the
 * existence of the base {@link ToastBaseTransientBottomBar} class.
 */
public class ToastSnackbarLayout extends ToastSnackbarBaseLayout {
    public ToastSnackbarLayout(Context context) {
        super(context);
    }

    public ToastSnackbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Work around our backwards-compatible refactoring of Snackbar and inner content
        // being inflated against snackbar's parent (instead of against the snackbar itself).
        // Every child that is width=MATCH_PARENT is remeasured again and given the full width
        // minus the paddings.
        int childCount = getChildCount();
        int availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                child.measure(
                        View.MeasureSpec.makeMeasureSpec(availableWidth, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
            }
        }
    }
}

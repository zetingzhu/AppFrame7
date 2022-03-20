/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.help;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import java.lang.ref.WeakReference;

/**
 * @author zeting
 * @date 2022-03-20
 * 设置阴影，阴影颜色，圆角裁剪工具
 */
public class TopLayoutHelper {

    // round
    private int mRadius;
    private WeakReference<View> mOwner;

    // shadow
    private int mShadowElevation = 0;
    private float mShadowAlpha;
    private int mShadowColor = Color.BLACK;

    public TopLayoutHelper(Context context, View owner) {
        mOwner = new WeakReference<>(owner);
    }

    public int getShadowElevation() {
        return mShadowElevation;
    }


    public float getShadowAlpha() {
        return mShadowAlpha;
    }


    public int getShadowColor() {
        return mShadowColor;
    }


    public void setShadowElevation(int elevation) {
        if (mShadowElevation == elevation) {
            return;
        }
        mShadowElevation = elevation;
        invalidateOutline();
    }


    public void setShadowAlpha(float shadowAlpha) {
        if (mShadowAlpha == shadowAlpha) {
            return;
        }
        mShadowAlpha = shadowAlpha;
        invalidateOutline();
    }


    public void setShadowColor(int shadowColor) {
        if (mShadowColor == shadowColor) {
            return;
        }
        mShadowColor = shadowColor;
        setShadowColorInner(mShadowColor);
    }

    private void setShadowColorInner(int shadowColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            owner.setOutlineAmbientShadowColor(shadowColor);
            owner.setOutlineSpotShadowColor(shadowColor);
        }
    }

    private void invalidateOutline() {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            if (mShadowElevation == 0) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }
            owner.invalidateOutline();
        }
    }

    private void invalidate() {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        owner.invalidate();
    }


    public void setRadius(int radius) {
        if (mRadius != radius) {
            setRadiusAndShadow(radius, mShadowElevation, mShadowAlpha);
        }
    }

    public int getRadius() {
        return mRadius;
    }


    public void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, shadowElevation, mShadowColor, shadowAlpha);
    }


    public void setRadiusAndShadow(int radius, int shadowElevation, int shadowColor, float shadowAlpha) {
        final View owner = mOwner.get();
        if (owner == null) {
            return;
        }

        mRadius = radius;
        mShadowElevation = shadowElevation;
        mShadowAlpha = shadowAlpha;
        mShadowColor = shadowColor;
        if (useFeature()) {
            if (mShadowElevation == 0) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }
            setShadowColorInner(mShadowColor);
            owner.setOutlineProvider(new ViewOutlineProvider() {

                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    int w = view.getWidth(), h = view.getHeight();
                    if (w == 0 || h == 0) {
                        return;
                    }
                    float radius = getRealRadius();
                    int min = Math.min(w, h);
                    if (radius * 2 > min) {
                        // 解决 OnePlus 3T 8.0 上显示变形
                        radius = min / 2F;
                    }
                    int left = 0, top = 0, right = w, bottom = h;

                    float shadowAlpha = mShadowAlpha;
                    if (mShadowElevation == 0) {
                        shadowAlpha = 1f;
                    }

                    outline.setAlpha(shadowAlpha);

                    if (radius <= 0) {
                        outline.setRect(left, top,
                                right, bottom);
                    } else {
                        outline.setRoundRect(left, top,
                                right, bottom, radius);
                    }
                }
            });
            owner.setClipToOutline(mRadius > 0);
        }
        owner.invalidate();
    }


    private int getRealRadius() {
        return mRadius;
    }

    public static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }
}

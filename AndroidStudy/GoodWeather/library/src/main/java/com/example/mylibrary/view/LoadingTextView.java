package com.example.mylibrary.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @auther zj970
 * @create 2023-06-06 上午11:26
 */
public class LoadingTextView extends AppCompatTextView {
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mViewWidth = 0;
    private int mTranslate = 0;

    public LoadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                Paint mPaint = getPaint();
                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                        new int[]{0x33ffffff, 0xff3286ED, 0x33ffffff},
                        new float[]{0, 0.6f, 1}, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 10;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(20);
        }
    }
}

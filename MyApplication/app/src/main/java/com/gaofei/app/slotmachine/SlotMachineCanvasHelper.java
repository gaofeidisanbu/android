package com.gaofei.app.slotmachine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.Log;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe: 老虎机Canvas
 */
public class SlotMachineCanvasHelper {
    private static final String TAG = "SlotMachineCanvas";
    //画布大小
    public static final int SIZE_MAX = 4096;
    //透明度
    private Paint mAlphaPaint;
    //文本
    private Paint mTextPaint;
    //宽度
    private int mWidth;
    //高度
    private int mHeight;

    protected SlotMachineCanvasHelper() {

    }

    /**
     * @param width  宽度
     * @param height 高度
     * @return 成功返回0, 否则返回非0
     * @brief 初始化
     */
    public int initialize(int width, int height) {
        if (width <= 0 || height <= 0) {
            return -1;
        }
        if (width > SIZE_MAX || height > SIZE_MAX) {
            return -2;
        }
        if (!invalid()) {
            return -3;
        }
        this.mWidth = width;
        this.mHeight = height;
        mAlphaPaint = new Paint();
        mAlphaPaint.setAlpha(255);
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(36);
        mTextPaint.setColor(Color.RED);
        return 0;
    }

    /**
     * @param width  宽度
     * @param height 高度
     * @return 成功返回0, 否则返回非0
     * @brief 重置大小
     */
    public int resize(int width, int height) {
        if (width <= 0 || height <= 0) {
            return -1;
        }
        if (width > SIZE_MAX || height > SIZE_MAX) {
            return -2;
        }
        if (invalid()) {
            return -3;
        }
        this.mWidth = width;
        this.mHeight = height;
        return 0;
    }


    public int draw(Canvas canvas, Bitmap content, float x, float y, float scale, float alpha, int index) {
        if (null == content) {
            return -1;
        }
        if (scale <= 0.0f) {
            return -2;
        }
        if (alpha <= 0.0f) {
            return -3;
        }
        if (content.isRecycled()) {
            return -4;
        }
        if (invalid()) {
            return -5;
        }
        //计算图像缩放
        float scaledWidth = scale * content.getWidth();
        float scaleHeight = scale * content.getHeight();

        //锚点在内容中心
        float anchorX = scaledWidth / 2.0f;
        float anchorY = scaleHeight / 2.0f;

        //计算绘制位置
        float drawX = x - anchorX;
        float drawY = y - anchorY;
        //原始大小
        if (1.0f == scale) {
            //从中心位置开始画
            if (1.0f == alpha) {
                canvas.drawBitmap(content, drawX, drawY, null);
                Log.d(TAG, "drawX = " + drawX + " drawY = " + drawY);
            } else {
                mAlphaPaint.setAlpha((int) (alpha * 255));
                canvas.drawBitmap(content, drawX, drawY, mAlphaPaint);
            }
        } else {
            //缩放绘制
            Rect srcRect = new Rect(0, 0, content.getWidth(), content.getHeight());
            RectF dstRect = new RectF(drawX, drawY, drawX + scaledWidth, drawY + scaleHeight);
            if (1.0f == alpha) {
                canvas.drawBitmap(content, srcRect, dstRect, null);
            } else {
                mAlphaPaint.setAlpha((int) (alpha * 255));
                canvas.drawBitmap(content, drawX, drawY, mAlphaPaint);
            }
        }
        canvas.drawText(index + "", drawX, drawY, mTextPaint);
        return 0;
    }


    /**
     * @return 失败返回<0 的值
            * @ brief 获取画布宽度
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * @return 失败返回<0 的值
            * @ brief 获取画布高度
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * @return 无效返回true, 有效返回false
     * @brief 检测必要属性是否无效
     */
    public boolean invalid() {
        if (null == mAlphaPaint) {
            return true;
        }
        return false;
    }

    /**
     * @brief 释放销毁
     */
    public void release() {
        mAlphaPaint = null;
        mTextPaint = null;
        return;
    }


}

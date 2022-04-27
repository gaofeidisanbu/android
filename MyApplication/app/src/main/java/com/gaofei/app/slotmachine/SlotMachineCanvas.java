package com.gaofei.app.slotmachine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe:
 */
public class SlotMachineCanvas {
    //画布大小
    public static final int LMPRAISE_CANVAS_SIZE_MAX = 4096;
    //画布
    private Canvas mCanvas;

    //缓存
    private Bitmap mCache;
    //透明度
    private Paint mAlphaPaint;

    protected SlotMachineCanvas() {

    }

    /**
     * @brief 初始化
     * @param width 宽度
     * @param height 高度
     * @return 成功返回0, 否则返回非0
     */
    public int initialize(int width, int height) {
        if( width <= 0 || height <= 0 ) {
            return -1;
        }
        if( width > LMPRAISE_CANVAS_SIZE_MAX || height > LMPRAISE_CANVAS_SIZE_MAX) {
            return -2;
        }
        if( !invalid() ) {
            return -3;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mCache = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888, true);
            } else {
                mCache = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -10;
        } catch (Throwable e) {
            e.printStackTrace();
            return -11;
        }
        if( null == mCache ) {
            return -20;
        }
        mCanvas = new Canvas();
        mCanvas.setBitmap(mCache);

        mAlphaPaint = new Paint();
        mAlphaPaint.setAlpha(255);
        return 0;
    }

    /**
     * @brief 释放销毁
     */
    public void release() {
        if( null != mCanvas ) {
            mCanvas.setBitmap(null);
            mCanvas = null;
        }
        if( null != mCache ) {
            if( !mCache.isRecycled() ) {
                mCache.recycle();
            }
            mCache = null;
        }
        mAlphaPaint = null;
        return;
    }

    /**
     * @brief 清理画布
     * @param color 颜色值
     * @return 成功返回true, 否则返回false
     */
    public boolean clear(int color) {
        if( !invalid() ) {
            mCanvas.drawColor(color, PorterDuff.Mode.SRC);
            return true;
        }
        return false;
    }

    /**
     * @brief 检测必要属性是否无效
     * @return 无效返回true, 有效返回false
     */
    public boolean invalid() {
        if( null == mCanvas ) {
            return true;
        }
        if( null == mCache ) {
            return true;
        }
        if( mCache.isRecycled() ) {
            return true;
        }
        if( null == mAlphaPaint ) {
            return true;
        }
        return false;
    }

    public int draw(Bitmap content, float x, float y, float scale, float alpha) {
        if( null == content ) {
            return -1;
        }
        if( scale <= 0.0f ) {
            return -2;
        }
        if( alpha <= 0.0f ) {
            return -3;
        }
        if( content.isRecycled() ) {
            return -4;
        }
        if( invalid() ) {
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
        if( 1.0f == scale ) {
            //从中心位置开始画
            if( 1.0f == alpha ) {
                mCanvas.drawBitmap(content, drawX, drawY, null);
            } else {
                mAlphaPaint.setAlpha((int)(alpha * 255));
                mCanvas.drawBitmap(content, drawX, drawY, mAlphaPaint);
            }
        } else {
            //缩放绘制
            Rect srcRect = new Rect(0, 0, content.getWidth(), content.getHeight());
            RectF dstRect = new RectF(drawX, drawY, drawX + scaledWidth, drawY + scaleHeight);
            if( 1.0f == alpha ) {
                mCanvas.drawBitmap(content, srcRect, dstRect, null);
            } else {
                mAlphaPaint.setAlpha((int)(alpha * 255));
                mCanvas.drawBitmap(content, drawX, drawY, mAlphaPaint);
            }
        }
        return 0;
    }

    /**
     * @brief 重置大小
     * @param width 宽度
     * @param height 高度
     * @return 成功返回0, 否则返回非0
     */
    public int resize(int width, int height) {
        if( width <= 0 || height <= 0 ) {
            return -1;
        }
        if( width > LMPRAISE_CANVAS_SIZE_MAX || height > LMPRAISE_CANVAS_SIZE_MAX) {
            return -2;
        }
        if( invalid() ) {
            return -3;
        }
        //如果大小相同就不需要重置
        if( width == mCache.getWidth() && height == mCache.getHeight() ) {
            return 0;
        }
        //清空
        mCanvas.setBitmap(null);
        //回收
        if( !mCache.isRecycled() ) {
            mCache.recycle();
            mCache = null;
        }
        try {
            //重建
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mCache = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888, true);
            } else {
                mCache = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -10;
        } catch (Throwable e) {
            e.printStackTrace();
            return -11;
        }
        if( null == mCache ) {
            return -12;
        }
        //绑定
        mCanvas.setBitmap(mCache);
        return 0;
    }

    /**
     * @brief 绘制到目标画布
     * @param canvas 画布
     * @param isAutoScale 是否自动缩放
     * @return 成功返回0, 否则返回非0
     */
    public int bitblt(Canvas canvas, boolean isAutoScale) {
        if( invalid() ) {
            return -1;
        }

        //不自动缩放就直接绘制
        if( !isAutoScale ) {
            canvas.drawBitmap(mCache, 0, 0, null);
            return 0;
        }

        //获取目标画布大小
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        if( canvasWidth <= 0 || canvasHeight <= 0 ) {
            return -10;
        }

        //获取当前缓存大小
        int contentWidth = mCache.getWidth();
        int contentHeight = mCache.getHeight();
        if( contentWidth <= 0 || contentHeight <= 0 ) {
            return -11;
        }

        //计算缩放比例
        float wScale = (float)canvasWidth / (float)contentWidth;
        float hScale = (float)canvasHeight / (float)contentHeight;
        float scale = Math.min(wScale, hScale);

        //缩放图像内容
        float scaleWidth = contentWidth * scale;
        float scaleHeight = contentHeight * scale;

        //计算绘制位置
        float offsetX = (canvasWidth - scaleWidth) / 2.0f;
        float offsetY = (canvasHeight - scaleHeight) / 2.0f;
        RectF dstRect = new RectF(offsetX,offsetY,scaleWidth,scaleHeight);
        Rect srcRect = new Rect(0, 0, contentWidth, contentHeight);

        //绘制图像
        canvas.drawBitmap(mCache, srcRect, dstRect, null);
        return 0;
    }

    /**
     * @brief 获取画布宽度
     * @return 失败返回<0的值
     */
    public int getWidth() {
        if( null == mCache ) {
            return -1;
        }
        if( mCache.isRecycled() ) {
            return -2;
        }
        return mCache.getWidth();
    }

    /**
     * @brief 获取画布高度
     * @return 失败返回<0的值
     */
    public int getHeight() {
        if( null == mCache ) {
            return -1;
        }
        if( mCache.isRecycled() ) {
            return -2;
        }
        return mCache.getHeight();
    }

}

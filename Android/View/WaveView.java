package com.dmh.akotlin.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by QiuGang on 2017/9/20 19:22
 * Email : 1607868475@qq.com
 */
public class WaveView extends View {
    /** 直线和半圆的Paint */
    private Paint outerLinePaint;
    /** 中间文本的Paint */
    private Paint centerTextPaint;
    /** 前景波浪效果Paint */
    private Paint foregroundWavePaint;
    /** 北京波浪效果Paint */
    private Paint backgroundWavePaint;
    /** 用于绘制前景波浪 */
    private Bitmap foregroundWaveBitmap;
    /** 用于绘制背景波浪 */
    private Bitmap backgroundWaveBitmap;
    /** 绘制前景波浪的Canvas */
    private Canvas foregroundWaveCanvas;
    /** 绘制背景波浪的Canvas */
    private Canvas backgroundWaveCanvas;
    /** 绘制外部线条的颜色 */
    private int lineColor = Color.GREEN;
    /** 前景波浪的颜色 */
    private int foregroundWaveColor = Color.BLUE;
    /** 背景波浪的颜色 */
    private int backgroundWaveColor = Color.GREEN;
    /** 前景波浪左侧相对于屏幕左侧偏移量 */
    private int foregroundWaveStartXOffset = 0;
    /** 背景波浪左侧相对于屏幕左侧偏移量 */
    private int backgroundWaveStartXOffset = 0;
    /** 波浪数量 */
    private int waveCount = 3;
    /** 每个波浪所占的宽度 */
    private float perWaveWidth = 0;
    /** 当前水位高度 */
    private float waveLevel = 0.3f;
    /** 波浪最低点和最高点相对于基准线的偏移量 */
    private float foregroundWaveHeight = 0.13f;
    private float backgroundWaveHeight = 0.11f;
    /** 外部线条宽度 */
    private float lineWidth = 30f;
    /** 线条长度 */
    private float lineLength = 0.9f;
    /** 线条相对于顶部的距离 */
    private float lineMarginTop = 0.8f;
    /** 中间文字相对于顶部距离 */
    private float centerTextMarginTop = 0.5f;
    /** 外部半圆的半径 */
    private float outerHalfCircleRadius = 0.4f;
    /** 中间文本的颜色 */
    private int centerTextColor = Color.BLACK;
    /** 中间文本的文字大小 */
    private float centerTextSize = 80f;
    /** 文本内容 */
    private String centerText = "65000";
    /** 前景和背景波浪线的Path */
    private Path foregroundWavePath = new Path();
    private Path backgroundWavePath = new Path();

    private Handler animHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            foregroundWaveStartXOffset += perWaveWidth / 20;
            backgroundWaveStartXOffset += perWaveWidth / 25;
            resetWaveStartXOffset(foregroundWaveStartXOffset >= 0, backgroundWaveStartXOffset >= 0);
            invalidate();
        }
    };

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        outerLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerLinePaint.setColor(lineColor);
        outerLinePaint.setStrokeCap(Paint.Cap.ROUND);
        outerLinePaint.setStyle(Paint.Style.STROKE);
        outerLinePaint.setStrokeWidth(lineWidth);

        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setColor(centerTextColor);
        centerTextPaint.setTextSize(centerTextSize);

        foregroundWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        foregroundWavePaint.setColor(foregroundWaveColor);

        backgroundWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        backgroundWavePaint.setColor(backgroundWaveColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(foregroundWaveBitmap, 0, 0, null);
        drawInnerHalfWave(canvas);
        drawBottomLine(canvas);
        drawOuterHalfCircle(canvas);
        drawCenterText(canvas);
        animHandler.sendEmptyMessageDelayed(0, 25);
    }

    private void drawInnerHalfWave(Canvas canvas) {
        foregroundWavePath.reset();
        backgroundWavePath.reset();
        float waveY = getHeight() * (lineMarginTop - waveLevel) + lineWidth / 2;
        float foregroundWaveHeightOffset = perWaveWidth * foregroundWaveHeight;
        float backgroundWaveHeightOffset = perWaveWidth * backgroundWaveHeight;
        for (int i = 0; i < waveCount * 2; i++) {
            float foregroundWaveStartX = foregroundWaveStartXOffset + perWaveWidth * i;
            foregroundWavePath.moveTo(foregroundWaveStartX, waveY);
            foregroundWavePath.cubicTo(foregroundWaveStartX + perWaveWidth / 4, waveY - foregroundWaveHeightOffset,
                    foregroundWaveStartX + perWaveWidth / 2, waveY + foregroundWaveHeightOffset, foregroundWaveStartX +
                            perWaveWidth, waveY);

            float backgroundWaveStartX = backgroundWaveStartXOffset + perWaveWidth * i;
            backgroundWavePath.moveTo(backgroundWaveStartX, waveY);
            backgroundWavePath.cubicTo(backgroundWaveStartX + perWaveWidth / 4, waveY - backgroundWaveHeightOffset,
                    backgroundWaveStartX + perWaveWidth / 2, waveY + backgroundWaveHeightOffset, backgroundWaveStartX +
                            perWaveWidth, waveY);
        }
        foregroundWavePath.addRect(new RectF(foregroundWaveStartXOffset, waveY, (float) (getWidth() * 1.5), getHeight
                ()), Path.Direction.CW);
        backgroundWavePath.addRect(new RectF(backgroundWaveStartXOffset, waveY, (float) (getWidth() * 1.5), getHeight
                ()), Path.Direction.CW);

        foregroundWavePath.close();
        backgroundWavePath.close();

        canvas.drawPath(backgroundWavePath, backgroundWavePaint);
        canvas.drawPath(foregroundWavePath, foregroundWavePaint);
    }

    private void drawCenterText(Canvas canvas) {
        if (TextUtils.isEmpty(centerText)) {
            return;
        }
        Paint.FontMetrics fontMetrics = centerTextPaint.getFontMetrics();
        float centerTextY = fontMetrics.bottom - fontMetrics.top + getHeight() * centerTextMarginTop;
        float centerTextX = (getWidth() - centerTextPaint.measureText(centerText)) / 2;
        canvas.drawText(centerText, centerTextX, centerTextY, centerTextPaint);
    }

    private void drawOuterHalfCircle(Canvas canvas) {
        float circleCenterX = getWidth() / 2;
        float circleCenterY = getHeight() * lineMarginTop + lineWidth / 2;
        float circleRadius = getWidth() * outerHalfCircleRadius;
        canvas.drawArc(new RectF(circleCenterX - circleRadius, circleCenterY - circleRadius, circleCenterX +
                circleRadius, circleCenterY + circleRadius), -180, 180, true, outerLinePaint);
    }

    private void drawBottomLine(Canvas canvas) {
        float lineStartX = getWidth() * (1 - lineLength) / 2;
        float lineY = getHeight() * lineMarginTop + lineWidth / 2;
        float lineEndX = lineStartX + getWidth() * lineLength;
        canvas.drawLine(lineStartX, lineY, lineEndX, lineY, outerLinePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = Resources.getSystem().getDisplayMetrics().widthPixels;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = widthSize / 2;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        perWaveWidth = w / waveCount;

        if (foregroundWaveBitmap != null) {
            foregroundWaveBitmap.recycle();
        }
        foregroundWaveBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        foregroundWaveBitmap.setHasAlpha(true);
        foregroundWaveCanvas = new Canvas(foregroundWaveBitmap);
        if (backgroundWaveBitmap != null) {
            backgroundWaveBitmap.recycle();
        }
        backgroundWaveBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        backgroundWaveBitmap.setHasAlpha(true);
        backgroundWaveCanvas = new Canvas(backgroundWaveBitmap);

        float circleCenterX = getWidth() / 2;
        float circleCenterY = getHeight() * lineMarginTop + lineWidth / 2;
        float circleRadius = getWidth() * outerHalfCircleRadius;

        foregroundWaveCanvas.drawArc(new RectF(circleCenterX - circleRadius, circleCenterY - circleRadius,
                circleCenterX + circleRadius, circleCenterY + circleRadius), -180, 180, true, foregroundWavePaint);
        backgroundWaveCanvas.drawArc(new RectF(circleCenterX - circleRadius, circleCenterY - circleRadius,
                circleCenterX + circleRadius, circleCenterY + circleRadius), -180, 180, true, backgroundWavePaint);

        foregroundWavePaint.setShader(new BitmapShader(foregroundWaveBitmap, Shader.TileMode.CLAMP, Shader.TileMode
                .CLAMP));
        backgroundWavePaint.setShader(new BitmapShader(backgroundWaveBitmap, Shader.TileMode.CLAMP, Shader.TileMode
                .CLAMP));

        resetWaveStartXOffset(true, true);
    }

    private void resetWaveStartXOffset(boolean resetForeground, boolean resetBackground) {
        if (resetForeground) {
            foregroundWaveStartXOffset = -getWidth() / waveCount * 3;
        }
        if (resetBackground) {
            backgroundWaveStartXOffset = -getWidth() / waveCount * 4;
        }
    }
}

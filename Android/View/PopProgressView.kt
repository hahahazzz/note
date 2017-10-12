package com.citygreen.wanwan.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by QiuGang on 2017/10/11 11:16
 * Email : 1607868475@qq.com
 */
class PopProgressView : View {
    //线条长度 0.8F
    //线条高度 0.013
    //字体1大小 0.053
    //字体2大小 0.056
    private val screenWidth: Int = Resources.getSystem().displayMetrics.widthPixels

    private val linePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
        }
    }

    private val textPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }
    }

    private val popBorderPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = popBorderColor
        }
    }

    var lineWidth: Float = 0.8F
    var lineHeight: Float = 0.013F
    var popValueTextSize: Float = 0.038F
    var bottomMaxTextSize: Float = 0.04F
    var lineMarginTop: Float = 0.085F
    var popLineWidth: Float = 0.003F
    var popBottomCornerWidth: Float = 0.015F

    var popBorderColor: Int = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }

    var lineBackgroundColor: Int = Color.BLACK
        set(value) {
            field = value
            invalidate()
        }
    var lineForegroundColor: Int = Color.RED
        set(value) {
            field = value
            invalidate()
        }

    var currentValue: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    var maxValue: Int = 100
        set (value) {
            field = value
            invalidate()
        }

    var popTextColor = Color.WHITE
    var maxValueTextColor = Color.WHITE

    private val viewHeight: Float = 0.14F

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 进度条底色和进度颜色
        val lineBackgroundPath = Path()
        val lineForegroundPath = Path()

        val lineLeftBorderX = width * ((1.0F - lineWidth) / 2) // 进度条左边X
        val lineTopBorderY = width * (lineMarginTop - lineHeight / 2) // 进度条上边Y
        val lineRightBorderX = width * (1.0F - (1.0F - lineWidth) / 2) // 进度条右边X
        val lineBottomBorderY = width * (lineMarginTop + lineHeight / 2) // 进度条左下角Y
        val currentValueLineWidth = width * lineWidth * (currentValue.toFloat() / maxValue.toFloat())
        val currentValuePointX = lineLeftBorderX + currentValueLineWidth

        lineBackgroundPath.moveTo(currentValuePointX, lineTopBorderY)
        lineBackgroundPath.lineTo(currentValuePointX, lineBottomBorderY)
        lineBackgroundPath.lineTo(lineRightBorderX, lineBottomBorderY)
        lineBackgroundPath.lineTo(lineRightBorderX, lineTopBorderY)
        lineBackgroundPath.close()
        linePaint.color = lineBackgroundColor
        canvas?.drawPath(lineBackgroundPath, linePaint)

        lineForegroundPath.moveTo(lineLeftBorderX, lineTopBorderY)
        lineForegroundPath.lineTo(currentValuePointX, lineTopBorderY)
        lineForegroundPath.lineTo(currentValuePointX, lineBottomBorderY)
        lineForegroundPath.lineTo(lineLeftBorderX, lineBottomBorderY)
        lineForegroundPath.close()
        linePaint.color = lineForegroundColor
        canvas?.drawPath(lineForegroundPath, linePaint)

        // 进度条尾部文字
        textPaint.textSize = width * bottomMaxTextSize
        val bottomTextFontMetrics = textPaint.fontMetricsInt
        textPaint.color = maxValueTextColor
        val maxValueText = maxValue.toString()
        val maxValueTextLength = textPaint.measureText(maxValueText)
        canvas?.drawText(maxValueText, lineRightBorderX - maxValueTextLength, lineBottomBorderY - bottomTextFontMetrics.ascent, textPaint)

        // 气泡底部三角形Y坐标
        val popBottomCornerY = lineTopBorderY * 0.9F
        // 气泡底部倒三角线条偏移量
        val popBottomCornerXOffset = width * popBottomCornerWidth / 2

        // 气泡里的文字
        textPaint.color = popTextColor
        textPaint.textSize = width * popValueTextSize
        val popTextMetrics = textPaint.fontMetrics
        val currentValueText = currentValue.toString()
        val currentValueTextLength = textPaint.measureText(currentValueText)
        val popTextX = currentValuePointX - currentValueTextLength / 2
        val popTextY = (popBottomCornerY - popBottomCornerXOffset - popTextMetrics.descent) * 0.95F
        canvas?.drawText(currentValueText, popTextX, popTextY, textPaint)

        // 气泡底部倒三角
        val popPath = Path()
        // 倒三角左边的角点
        popPath.moveTo(currentValuePointX - popBottomCornerXOffset, popBottomCornerY - popBottomCornerXOffset)
        // 倒三角最下的角点
        popPath.lineTo(currentValuePointX, popBottomCornerY)
        // 倒三角右边的角点
        popPath.lineTo(currentValuePointX + popBottomCornerXOffset, popBottomCornerY - popBottomCornerXOffset)
        // 气泡左右和内部文字的间隔
        val popLeftRightPadding = 0.01F
        // 气泡左边线条X坐标
        val popRectLeftX = popTextX - popLeftRightPadding * width
        // 气泡顶部线条Y坐标
        val popRectTopY = popTextY + popTextMetrics.ascent - lineTopBorderY * 0.05F
        // 气泡右侧线条X坐标
        val popRectRightX = popTextX + currentValueTextLength + width * popLeftRightPadding
        // 气泡底部线条Y坐标
        val popRectBottomY = popBottomCornerY - popBottomCornerXOffset
        // 气泡高度,不含倒三角的高度
        val popHeight = popRectBottomY - popRectTopY
        // 气泡拐角半圆的半径
        val cornerRadius = popHeight * 0.2F
        // 连接右下角圆角之前的点
        popPath.lineTo(popRectRightX - cornerRadius, popRectBottomY)
        // 绘制右下角圆角
        popPath.quadTo(popRectRightX, popRectBottomY, popRectRightX, popRectBottomY - cornerRadius)
        // 连接右侧竖线
        popPath.lineTo(popRectRightX, popRectTopY + cornerRadius)
        // 绘制右上角半圆
        popPath.quadTo(popRectRightX, popRectTopY, popRectRightX - cornerRadius, popRectTopY)
        // 连接顶部线条
        popPath.lineTo(popRectLeftX + cornerRadius, popRectTopY)
        // 绘制左上角半圆
        popPath.quadTo(popRectLeftX, popRectTopY, popRectLeftX, popRectTopY + cornerRadius)
        // 连接左侧线条
        popPath.lineTo(popRectLeftX, popRectBottomY - cornerRadius)
        // 绘制左下角半圆
        popPath.quadTo(popRectLeftX, popRectBottomY, popRectLeftX + cornerRadius, popRectBottomY)
        // 封闭气泡
        popPath.close()
        popBorderPaint.strokeWidth = popLineWidth * width
        canvas?.drawPath(popPath, popBorderPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var parentWidth = 0
        var parentHeight = 0
        parent?.let {
            val layoutParams = (it as ViewGroup).layoutParams
            parentWidth = layoutParams.width
            parentHeight = layoutParams.height
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = Math.min(if (parentWidth == -1) screenWidth else parentWidth, screenWidth)
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = Math.min((screenWidth * viewHeight).toInt(), if (parentHeight == -1) (screenWidth * viewHeight)
                    .toInt() else parentHeight)
        }
        setMeasuredDimension(widthSize, heightSize)
    }
}
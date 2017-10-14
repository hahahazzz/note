package com.dmh.akotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator

/**
 * Created by QiuGang on 2017/10/14 11:56
 * Email : 1607868475@qq.com
 */
class HalfCircleWaveView : View {
    private val viewHeight: Float = 0.48F

    var lineWidth: Float = 0.0267F
    var arcRadius: Float = 0.348F
    var bottomLineWidth: Float = 0.82F
    var bottomLineMarginTop: Float = 0.4587F
    var arcAngle: Float = 200F
    private val innerHalfArcRadius: Float = 0.335F
    private val sin10: Float = 0.173648F
    val waveCount = 1
    private val backgroundWaveOffset: Float = 0.05F

    /** 前景波浪左侧相对于屏幕左侧偏移量  */
    private var foregroundWaveStartXOffset = 0
    /** 背景波浪左侧相对于屏幕左侧偏移量  */
    private var backgroundWaveStartXOffset = 0

    private val foregroundWavePath: Path by lazy { Path() }
    private val backgroundWavePath: Path by lazy { Path() }

    private val perWaveWidth: Float by lazy {
        width * 0.8F / waveCount
    }
    var wavePervent: Float = 0.55F
        set(value) {
            field = value
            invalidate()
        }

    private val circleX: Float by lazy {
        (width / 2).toFloat()
    }

    private val circleY: Float by lazy {
        val lineY = width * bottomLineMarginTop
        lineY - width * lineWidth / 2 - width * sin10 * innerHalfArcRadius
    }

    private val lineX: Float by lazy {
        width * (1.0F - bottomLineWidth) / 2
    }

    private val lineY: Float by lazy {
        width * bottomLineMarginTop
    }

    var foregroundWaveColor: Int = Color.parseColor("#fdd883")
        set(value) {
            field = value
            invalidate()
        }
    var backgroundWaveColor: Int = Color.parseColor("#fce7bf")
        set(value) {
            field = value
            invalidate()
        }

    private val foregroundWavePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }
    private val backgroundWavePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private lateinit var foregroundWaveBitmap: Bitmap
    private lateinit var backgroundWaveBitmap: Bitmap

    var colorBottomLine: Int = Color.parseColor("#8dc227")
        set(value) {
            field = value
            invalidate()
        }

    var colorArcStartColor: Int = Color.parseColor("#a4d147")
        set(value) {
            field = value
            invalidate()
        }

    var colorArcCenterColor: Int = Color.parseColor("#d6fc85")
        set(value) {
            field = value
            invalidate()
        }
    var colorArcEndColor: Int = Color.parseColor("#99c83a")
        set(value) {
            field = value
            invalidate()
        }

    private val bottomLinePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeCap = Paint.Cap.ROUND
            color = colorBottomLine
            strokeWidth = width * lineWidth
        }
    }

    private val outerArcPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = width * lineWidth
            shader = SweepGradient(circleX, circleY, intArrayOf(colorArcStartColor, colorArcStartColor, colorArcStartColor,
                    colorArcCenterColor, colorArcEndColor), null)
        }
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(RectF(circleX - width * arcRadius, circleY - width * arcRadius, circleX + width * arcRadius,
                circleY + width * arcRadius), -190F, arcAngle, false, outerArcPaint)
        canvas?.drawLine(lineX, lineY, lineX + width * bottomLineWidth, lineY, bottomLinePaint)
        foregroundWavePath.reset()
        backgroundWavePath.reset()
        val waveY = circleY - width * (wavePervent - 0.5F)
        for (index in -3..4) {
            val startX = index * perWaveWidth + foregroundWaveStartXOffset
            val backgroundStartX = startX + perWaveWidth * backgroundWaveOffset + backgroundWaveStartXOffset
            foregroundWavePath.moveTo(startX, waveY)
            foregroundWavePath.quadTo(startX + perWaveWidth / 4, waveY - perWaveWidth / 12, startX + perWaveWidth / 2,
                    waveY)
            foregroundWavePath.quadTo(startX + perWaveWidth / 4 * 3, waveY + perWaveWidth / 12, startX + perWaveWidth,
                    waveY)
            backgroundWavePath.moveTo(backgroundStartX, waveY)
            backgroundWavePath.quadTo(backgroundStartX + perWaveWidth / 4, waveY - perWaveWidth / 12, backgroundStartX
                    + perWaveWidth / 2, waveY)
            backgroundWavePath.quadTo(backgroundStartX + perWaveWidth / 4 * 3, waveY + perWaveWidth / 12,
                    backgroundStartX + perWaveWidth, waveY)
        }
        foregroundWavePath.rLineTo(0F, height.toFloat())
        foregroundWavePath.lineTo(-perWaveWidth, height.toFloat())
        foregroundWavePath.lineTo(-perWaveWidth, waveY)
        foregroundWavePath.close()
        backgroundWavePath.rLineTo(0F, height.toFloat())
        backgroundWavePath.lineTo(-width * backgroundWaveOffset, height.toFloat())
        backgroundWavePath.lineTo(-width * backgroundWaveOffset, waveY)
        backgroundWavePath.close()
        canvas?.drawPath(backgroundWavePath, backgroundWavePaint)
        canvas?.drawPath(foregroundWavePath, foregroundWavePaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        foregroundWaveBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).apply {
            setHasAlpha(true)
        }
        backgroundWaveBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).apply {
            setHasAlpha(true)
        }
        val foregroundCanvas = Canvas(foregroundWaveBitmap)
        val backgroundCanvas = Canvas(backgroundWaveBitmap)
        val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        arcPaint.color = foregroundWaveColor
        foregroundCanvas.drawArc(RectF(
                circleX - (w * innerHalfArcRadius).toInt(),
                circleY - (w * innerHalfArcRadius).toInt(),
                circleX + (w * innerHalfArcRadius).toInt(),
                circleY + (w * innerHalfArcRadius).toInt()
        ), -190F, arcAngle, false, arcPaint)
        arcPaint.color = backgroundWaveColor
        backgroundCanvas.drawArc(RectF(
                circleX - (w * innerHalfArcRadius).toInt(),
                circleY - (w * innerHalfArcRadius).toInt(),
                circleX + (w * innerHalfArcRadius).toInt(),
                circleY + (w * innerHalfArcRadius).toInt()
        ), -190F, arcAngle, false, arcPaint)
        foregroundWavePaint.shader = BitmapShader(foregroundWaveBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        backgroundWavePaint.shader = BitmapShader(backgroundWaveBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        foregroundWaveStartXOffset = (-perWaveWidth * 2).toInt()
        backgroundWaveStartXOffset = (-perWaveWidth * 2 + width * backgroundWaveOffset).toInt()
        startAnim()
    }


    private val foregroundAnim: ValueAnimator by lazy {
        ValueAnimator.ofInt(foregroundWaveStartXOffset, 0).apply {
            duration = 3500
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                foregroundWaveStartXOffset = it.animatedValue as Int
                invalidate()
            }
        }
    }
    private val backgroundAnim: ValueAnimator by lazy {
        ValueAnimator.ofInt(backgroundWaveStartXOffset, 0).apply {
            duration = 6500
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                backgroundWaveStartXOffset = it.animatedValue as Int
                invalidate()
            }
        }
    }

    private fun startAnim() {
        foregroundAnim.start()
        backgroundAnim.start()
    }
}
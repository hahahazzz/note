package com.citygreen.devdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet


/**
 * @Author: QiuGang
 * @Date : 2018/11/1 14:34
 * @Email : 1607868475@qq.com
 * @Record:
 *
 */
class InnerRoundCornerImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    enum class CornerDir {
        INNER,
        OUTER
    }

    var topLeft: Float = 0F
    var topRight: Float = 0F

    var bottomLeft: Float = 0F
    var bottomRight: Float = 0F

    var topLeftDir: CornerDir = CornerDir.OUTER

    var topRightDir: CornerDir = CornerDir.OUTER

    var bottomLeftDir: CornerDir = CornerDir.OUTER

    var bottomRightDir: CornerDir = CornerDir.OUTER

    override fun onDraw(canvas: Canvas?) {
        val path = Path()

        path.addRoundRect(RectF(0F, 0F, width.toFloat(), height.toFloat()),
                floatArrayOf(topLeft, topLeft,
                        topRight, topRight,
                        bottomRight, bottomRight,
                        bottomLeft, bottomLeft),
                Path.Direction.CW)
        canvas?.clipPath(path)

        if (topLeftDir == CornerDir.INNER) {
            canvas?.clipPath(Path(path).apply {
                addCircle(0F, 0F, topLeft, Path.Direction.CCW)
            })
        }

        if (topRightDir == CornerDir.INNER) {
            canvas?.clipPath(Path(path).apply {
                addCircle(width.toFloat(), 0F, topRight, Path.Direction.CCW)
            })
        }

        if (bottomLeftDir == CornerDir.INNER) {
            canvas?.clipPath(Path(path).apply {
                addCircle(0F, height.toFloat(), bottomLeft, Path.Direction.CCW)
            })
        }

        if (bottomRightDir == CornerDir.INNER) {
            canvas?.clipPath(Path(path).apply {
                addCircle(width.toFloat(), height.toFloat(), bottomRight, Path.Direction.CCW)
            })
        }
        super.onDraw(canvas)
    }
}
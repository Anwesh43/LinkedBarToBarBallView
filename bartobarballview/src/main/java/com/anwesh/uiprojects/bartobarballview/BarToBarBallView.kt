package com.anwesh.uiprojects.bartobarballview

/**
 * Created by anweshmishra on 02/09/20.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.app.Activity
import android.content.Context

val colors : Array<Int> = arrayOf(
        "#F44336",
        "#2196F3",
        "#4CAF50",
        "#3F51B5",
        "#673AB7"
).map({Color.parseColor(it)}).toTypedArray()
val circles : Int = 3
val scGap : Float = 0.02f / (circles * 2)
val delay : Long = 20
val sizeFactor : Float = 2.9f
val hFactor : Float = 5.2f
val backColor : Int = Color.parseColor("#BDBDBD")
val strokeFactor : Float = 90f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawBarToBarBall(scale : Float, w : Float, h : Float, paint : Paint) {
    val gap : Float = (w) / (circles + 2)
    val size : Float = gap / sizeFactor
    val hSize : Float = h / hFactor
    val sf : Float = scale.sinify()
    var x : Float = 0f
    var r : Float = 0f
    save()
    translate(0f, h)
    for (j in 0..circles) {
        val sfj : Float = sf.divideScale(j, circles)
        val sf1 : Float = sfj.divideScale(0, 2)
        val sf2 : Float = sfj.divideScale(1, 2)
        if (j == 0) {
            r = size * 0.5f * sf2
        } else {
            x += (gap * sf2)
        }
        save()
        translate(gap / 2 + gap * j, 0f)
        drawRect(RectF(-size / 2, -hSize * sf1, size / 2, 0f), paint)
        restore()
    }
    drawCircle(x, -hSize - size / 2, r, paint)
    restore()
}

fun Canvas.drawBTBBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawBarToBarBall(scale, w, h, paint)
}

class BarToBarBallView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}
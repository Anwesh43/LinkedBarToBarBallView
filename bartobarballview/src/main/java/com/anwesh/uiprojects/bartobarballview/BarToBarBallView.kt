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

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

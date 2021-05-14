package com.example.movieinfotest.presentation.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import com.example.movieinfotest.R

class RatingIndicator(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val container: LinearLayout
    private var drawingImages = 0
    private var value: Float = EMPTY_VALUE
    private var maxPoints: Int = 5
    private var maxValue: Float = 10f
    private var hideEmptyPoints: Boolean = false

    private lateinit var imageEmpty: Drawable
    private lateinit var imageHalf: Drawable
    private lateinit var imageFull: Drawable
    private var imageColor: Int = Color.BLACK

    private var margin: Float = 4f
    private var imageSize: Float = 32f

    init {
        inflate(context, R.layout.view_rating_indicator, this)
        container = findViewById(R.id.rating_container)

        context?.theme?.obtainStyledAttributes(attrs, R.styleable.RatingIndicator, 0, 0)
            ?.use { attrs ->
                maxPoints = attrs.getInt(R.styleable.RatingIndicator_maxPoints, 5)
                maxValue = attrs.getFloat(R.styleable.RatingIndicator_maxValue, 10f)
                value = attrs.getFloat(R.styleable.RatingIndicator_value, EMPTY_VALUE)
                hideEmptyPoints =
                    attrs.getBoolean(R.styleable.RatingIndicator_hideEmptyPoints, false)

                val empty = attrs.getResourceId(
                    R.styleable.RatingIndicator_imageEmpty,
                    R.drawable.ic_star_empty
                )
                val half = attrs.getResourceId(
                    R.styleable.RatingIndicator_imageHalf,
                    R.drawable.ic_star_half
                )
                val full = attrs.getResourceId(
                    R.styleable.RatingIndicator_imageFull,
                    R.drawable.ic_star_full
                )
                imageEmpty = ResourcesCompat.getDrawable(resources, empty, context.theme)!!
                imageHalf = ResourcesCompat.getDrawable(resources, half, context.theme)!!
                imageFull = ResourcesCompat.getDrawable(resources, full, context.theme)!!
                imageColor = attrs.getColor(R.styleable.RatingIndicator_imageColor, Color.BLACK)

                margin =
                    attrs.getDimensionPixelSize(R.styleable.RatingIndicator_marginBetweenPoints, 4)
                        .toFloat()
                imageSize =
                    attrs.getDimensionPixelSize(R.styleable.RatingIndicator_imageSize, 32).toFloat()
            }

        if (value >= EMPTY_VALUE)
            drawRating()
    }

    private fun drawRating() {
        val step = maxValue / maxPoints
        var i = step
        while (i <= value) {
            drawImage(imageFull)
            i += step
        }

        val remainder = value - (i - step)
        if (remainder >= (step / 2)) {
            drawImage(imageHalf)
        }

        if (!hideEmptyPoints)
            drawEmptyRating()
    }

    private fun drawImage(drawable: Drawable) {
        val image = ImageView(context).apply {
            setImageDrawable(drawable)
            setColorFilter(imageColor)
        }
        container.addView(image)
        drawingImages++
    }

    private fun drawEmptyRating() {
        if (!hideEmptyPoints) {
            while (drawingImages < maxPoints) {
                drawImage(imageEmpty)
            }
        }
    }

    fun setRating(value: Float) {
        container.removeAllViews()
        this.value = value
        drawRating()
    }

    fun getRating(): Float {
        return value
    }

    fun setMaxValue(maxValue: Float) {
        this.maxValue = maxValue
    }

    fun getMaxValue(): Float {
        return maxValue
    }

    fun setMaxPoints(maxPoints: Int) {
        this.maxPoints = maxPoints
    }

    fun getMaxPoints(): Int {
        return maxPoints
    }

    fun hideEmptyPoints(isHide: Boolean) {
        hideEmptyPoints = isHide
    }

    fun isHideEmptyPoints(): Boolean {
        return hideEmptyPoints
    }

    companion object {
        const val EMPTY_VALUE: Float = 0f
    }
}
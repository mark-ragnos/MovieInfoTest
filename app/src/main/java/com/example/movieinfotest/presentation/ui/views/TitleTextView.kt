package com.example.movieinfotest.presentation.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import com.example.movieinfotest.R
import com.example.movieinfotest.utils.setGone
import com.example.movieinfotest.utils.setVisible

class TitleTextView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val titleView: TextView
    private val valueView: TextView
    private var autoHide: Boolean = true

    init {
        inflate(context, R.layout.view_title_textview, this)

        titleView = this.findViewById(R.id.title)
        valueView = this.findViewById(R.id.value)
        orientation = LinearLayout.VERTICAL

        context.theme.obtainStyledAttributes(attrs, R.styleable.TitleTextView, 0, 0).use {
            titleView.text = it.getString(R.styleable.TitleTextView_ttv_title)
            valueView.text = it.getString(R.styleable.TitleTextView_ttv_value)

            autoHide = it.getBoolean(R.styleable.TitleTextView_autoHide, autoHide)

            titleView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                it.getDimensionPixelSize(
                    R.styleable.TitleTextView_ttv_title_text_size,
                    titleView.textSize.toInt()
                )
                    .toFloat()
            )
            valueView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                it.getDimensionPixelSize(
                    R.styleable.TitleTextView_ttv_value_text_size,
                    valueView.textSize.toInt()
                )
                    .toFloat()
            )

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                titleView.typeface = it.getFont(R.styleable.TitleTextView_ttv_title_text_style)
                    ?: Typeface.DEFAULT_BOLD
            } else {
                if (it.hasValue(R.styleable.TitleTextView_ttv_title_text_style)) {
                    val fontId =
                        it.getResourceId(R.styleable.TitleTextView_ttv_title_text_style, -1)
                    titleView.typeface = ResourcesCompat.getFont(context, fontId)
                }
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                valueView.typeface = it.getFont(R.styleable.TitleTextView_ttv_value_text_style)
                    ?: Typeface.DEFAULT
            } else {
                if (it.hasValue(R.styleable.TitleTextView_ttv_value_text_style)) {
                    val fontId =
                        it.getResourceId(R.styleable.TitleTextView_ttv_value_text_style, -1)
                    valueView.typeface = ResourcesCompat.getFont(context, fontId)
                }
            }

            titleView.setTextColor(
                it.getColor(
                    R.styleable.TitleTextView_ttv_title_text_color,
                    Color.BLACK
                )
            )
            valueView.setTextColor(
                it.getColor(
                    R.styleable.TitleTextView_ttv_value_text_color,
                    Color.BLACK
                )
            )
        }

        if (autoHide) {
            setVisible()
        }
    }

    private fun setVisible() {
        if (valueView.text.isNullOrEmpty()) {
            valueView.setGone()
            titleView.setGone()
        } else {
            titleView.setVisible()
            valueView.setVisible()
        }
    }

    fun setValue(value: String) {
        valueView.text = value
        setVisible()
    }

    fun getValue(): String {
        return valueView.text.toString()
    }
}

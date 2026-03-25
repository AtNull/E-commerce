package com.example.ecommerce.ui.home.common

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.example.ecommerce.R

class FavoriteImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatImageButton(context, attrs, defStyleAttr) {

    private val animationDuration: Long = 300
    private val animationScale: Float = 1.2f

    var isFavorite: Boolean = false
        set(value) {
            field = value

            updateImage()
        }

    init {
        isClickable = true
        isFocusable = true

        scaleType = ScaleType.CENTER_INSIDE

        setBackgroundResource(android.R.color.transparent)

        updateImage()
    }

    override fun performClick(): Boolean {
        isFavorite = !isFavorite

        animateTap()

        return super.performClick()
    }

    private fun updateImage() {
        val imageId = if(isFavorite) R.drawable.favorite_filled else R.drawable.favorite_outlined

        setImageResource(imageId)
    }

    private fun animateTap() {
        if (!isFavorite) {
            return
        }

        val scaleX = ObjectAnimator.ofFloat(
            this,
            "scaleX", 1f, animationScale
        ).apply {
            duration = animationDuration
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            start()
        }

        val scaleY = ObjectAnimator.ofFloat(
            this, "scaleY", 1f, animationScale
        ).apply {
            duration = animationDuration
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            start()
        }

        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            start()
        }
    }

}
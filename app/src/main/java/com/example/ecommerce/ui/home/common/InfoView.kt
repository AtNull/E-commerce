package com.example.ecommerce.ui.home.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ViewInfoBinding

class InfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewInfoBinding = ViewInfoBinding
        .inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value

            binding.titleTextView.text = value
            binding.titleTextView.isVisible = value != null
        }

    var description: String? = null
        set(value) {
            field = value

            binding.descriptionTextView.text = value
            binding.descriptionTextView.isVisible = value != null
        }

    var actionText: String? = null
        set(value) {
            field = value

            binding.actionButton.text = value
            binding.actionButton.isVisible = value != null
        }

    private var onActionClick: (() -> Unit)? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InfoView,
            0, 0
        ).apply {
            title = getString(R.styleable.InfoView_title)
            description = getString(R.styleable.InfoView_description)
            actionText = getString(R.styleable.InfoView_actionText)

            recycle()
        }

        binding.actionButton.setOnClickListener {
            onActionClick?.invoke()
        }
    }

    fun setOnActionClickListener(listener: () -> Unit) {
        onActionClick = listener
    }

}
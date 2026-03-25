package com.example.ecommerce.ui.home.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacerItemDecoration(
    private val spanCount: Int,
    private val spacingPx: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        val betweenSpacing = spacingPx / 2

        outRect.left = if (column == 0) spacingPx else betweenSpacing
        outRect.right = if (column == spanCount - 1) spacingPx else betweenSpacing
        outRect.top = 0
        outRect.bottom = 0
    }

}
package com.example.urwood.ui.main.home.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewProdukItemDecoration(private val spacing: Int, private var displayMode: Int) :
    RecyclerView.ItemDecoration() {
    companion object {
        const val HORIZONTAL: Int = 0
        const val VERTICAL: Int = 1
        const val GRID: Int = 2
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildViewHolder(view).adapterPosition
        val itemCount: Int = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(
        outRect: Rect,
        layoutManager: RecyclerView.LayoutManager?,
        position: Int,
        itemCount: Int
    ) {
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager)
        }

        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = spacing
                outRect.right = if (position == itemCount - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = spacing
            }

            VERTICAL -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = spacing
                outRect.bottom = if (position == itemCount - 1) spacing else 0
            }

            GRID -> {
                if (layoutManager is GridLayoutManager) {
                    val cols = layoutManager.spanCount
                    val rows = itemCount / cols + 1
                    outRect.left = spacing
                    outRect.right = if (position % cols == cols - 1) spacing else 0
                    outRect.top = spacing
                    outRect.bottom = if (position / cols == rows - 1) spacing else 0
                }
            }
        }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is GridLayoutManager) return GRID
        if (layoutManager!!.canScrollHorizontally()) return HORIZONTAL
        return VERTICAL
    }
}
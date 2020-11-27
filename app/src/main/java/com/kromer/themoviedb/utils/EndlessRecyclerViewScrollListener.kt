package com.kromer.themoviedb.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 5

    // Sets the starting page index
    private var startingPageIndex = 1

    // The current offset index of data you have loaded
    private var currentPage = 1

    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0
    private var mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager, startingPageIndex: Int) {
        mLayoutManager = layoutManager
        this.startingPageIndex = startingPageIndex
    }

    constructor(layoutManager: GridLayoutManager, startingPageIndex: Int) {
        mLayoutManager = layoutManager
        this.startingPageIndex = startingPageIndex
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager, startingPageIndex: Int) {
        mLayoutManager = layoutManager
        this.startingPageIndex = startingPageIndex
        visibleThreshold *= layoutManager.spanCount
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        // if trying to scroll to top and it is not reversed layout then return or continue otherwise
        if (dy <= 0 && !isReversedLayout()) return
        // if trying to scroll to bottom and it is reversed layout then return or continue otherwise
        if (dy >= 0 && isReversedLayout()) return
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount
        when (mLayoutManager) {
            is LinearLayoutManager -> {
                lastVisibleItemPosition =
                    (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
            is GridLayoutManager -> {
                lastVisibleItemPosition =
                    (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
        }

        // If it’s still loading, we check to see if the dataSet count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (isLoading && totalItemCount > previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            // Before loading new page check if the last page has data, if not, load last page again
            if (totalItemCount > previousTotalItemCount) {
                currentPage++
            }
            onLoadMore(currentPage, totalItemCount)
        }
    }

    private fun isReversedLayout(): Boolean =
        mLayoutManager is LinearLayoutManager && (mLayoutManager as LinearLayoutManager).reverseLayout

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)
    abstract val isLoading: Boolean
}
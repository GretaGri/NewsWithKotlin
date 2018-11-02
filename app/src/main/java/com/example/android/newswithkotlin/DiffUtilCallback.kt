package com.example.android.newswithkotlin

import android.support.v7.util.DiffUtil
import com.example.android.newswithkotlin.database.News
import org.jetbrains.annotations.Nullable


class DiffUtilCallback(internal var newList: ArrayList<News>?,
                       internal var oldList: ArrayList<News>?) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return if (oldList != null) oldList?.size!! else 0
    }

    override fun getNewListSize(): Int {
        return if (newList != null) newList!!.size else 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val result = newList!![newItemPosition].compareTo(oldList!![oldItemPosition])
        return result == 0
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
package defy.tech.chickenlover.adapter.util

import androidx.recyclerview.widget.DiffUtil

class ToDoItemDiffCallback constructor(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback() {
    private var oldList: List<Any>
    private var newList: List<Any>

    init {
        this.oldList = oldList
        this.newList = newList
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }
}
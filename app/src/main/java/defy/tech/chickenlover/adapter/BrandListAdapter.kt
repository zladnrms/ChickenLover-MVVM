package defy.tech.chickenlover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import defy.tech.chickenlover.adapter.util.ToDoItemDiffCallback
import defy.tech.chickenlover.databinding.ItemArticleBinding
import defy.tech.chickenlover.databinding.ItemBrandGridBinding
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.model.data.BrandSummaryItem

class BrandListAdapter(private val onClick: (BrandSummaryItem) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var brandListList : ArrayList<BrandSummaryItem>

    init {
        brandListList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val brandItem = brandListList[position]
                holder.bind(brandItem, onClick)
            }
        }
    }

    override fun getItemCount(): Int = brandListList.size

    fun setList(list: ArrayList<BrandSummaryItem>) {
        this.brandListList?.let {
            val diffUtil = ToDoItemDiffCallback(it, list)
            val result = DiffUtil.calculateDiff(diffUtil)

            it.clear()
            it.addAll(list)

            result.dispatchUpdatesTo(this@BrandListAdapter)
        } ?: let { this.brandListList = brandListList }
    }

    class ViewHolder private constructor(private val binding: ItemBrandGridBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(brandListItem: BrandSummaryItem, onClick: (BrandSummaryItem) -> Unit) {
            binding.item = brandListItem
            itemView.setOnClickListener {
                onClick(brandListItem)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBrandGridBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


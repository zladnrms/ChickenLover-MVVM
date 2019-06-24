package defy.tech.chickenlover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import defy.tech.chickenlover.adapter.util.ToDoItemDiffCallback
import defy.tech.chickenlover.databinding.ItemArticleBinding
import defy.tech.chickenlover.databinding.ItemChickenSearchBinding
import defy.tech.chickenlover.databinding.ItemRecentReviewBinding
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.model.data.ChickenReviewItem
import defy.tech.chickenlover.model.user.ChickenInfo
import kotlinx.android.synthetic.main.item_article_comment.view.*

class SearchChickenInfoListAdapter(private val onClick: (ChickenInfo) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var infoList : ArrayList<ChickenInfo>

    init {
        infoList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val infoItem = infoList[position]
                holder.bind(infoItem, onClick)
            }
        }
    }

    override fun getItemCount(): Int = infoList.size

    fun setList(list: ArrayList<ChickenInfo>) {
        this.infoList?.let {
            val diffUtil = ToDoItemDiffCallback(it, list)
            val result = DiffUtil.calculateDiff(diffUtil)

            it.clear()
            it.addAll(list)

            result.dispatchUpdatesTo(this@SearchChickenInfoListAdapter)
        } ?: let { this.infoList = infoList }
    }

    class ViewHolder private constructor(private val binding: ItemChickenSearchBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(infoItem: ChickenInfo, onClick: (ChickenInfo) -> Unit) {
            binding.item = infoItem
            itemView.setOnClickListener {
                onClick(infoItem)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChickenSearchBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


package defy.tech.chickenlover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import defy.tech.chickenlover.adapter.util.ToDoItemDiffCallback
import defy.tech.chickenlover.databinding.ItemArticleBinding
import defy.tech.chickenlover.databinding.ItemRecentReviewBinding
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.model.data.ChickenReviewItem
import kotlinx.android.synthetic.main.item_article_comment.view.*

class RecentReviewListAdapter(private val onClick: (ChickenReviewItem) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reviewList : ArrayList<ChickenReviewItem>

    init {
        reviewList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val reviewItem = reviewList[position]
                holder.bind(reviewItem, onClick)
            }
        }
    }

    override fun getItemCount(): Int = reviewList.size

    fun setList(list: ArrayList<ChickenReviewItem>) {
        this.reviewList?.let {
            val diffUtil = ToDoItemDiffCallback(it, list)
            val result = DiffUtil.calculateDiff(diffUtil)

            it.clear()
            it.addAll(list)

            result.dispatchUpdatesTo(this@RecentReviewListAdapter)
        } ?: let { this.reviewList = reviewList }
    }

    class ViewHolder private constructor(private val binding: ItemRecentReviewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(reviewItem: ChickenReviewItem, onClick: (ChickenReviewItem) -> Unit) {
            binding.item = reviewItem
            itemView.setOnClickListener {
                onClick(reviewItem)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecentReviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


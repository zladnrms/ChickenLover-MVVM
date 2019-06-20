package defy.tech.chickenlover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import defy.tech.chickenlover.adapter.util.ToDoItemDiffCallback
import defy.tech.chickenlover.databinding.ItemArticleCommentBinding
import defy.tech.chickenlover.model.data.ArticleCommentItem
import kotlinx.android.synthetic.main.item_article_comment.view.*

class ArticleCommentListAdapter(private val onClick: (ArticleCommentItem) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var articleCommentList : ArrayList<ArticleCommentItem>

    init {
        articleCommentList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val articleCommentItem = articleCommentList[position]
                holder.bind(articleCommentItem, onClick)
            }
        }
    }

    override fun getItemCount(): Int = articleCommentList.size

    fun setList(list: ArrayList<ArticleCommentItem>) {
        this.articleCommentList?.let {
            val diffUtil = ToDoItemDiffCallback(it, list)
            val result = DiffUtil.calculateDiff(diffUtil)

            it.clear()
            it.addAll(list)

            result.dispatchUpdatesTo(this@ArticleCommentListAdapter)
        } ?: let { this.articleCommentList = articleCommentList }
    }

    class ViewHolder private constructor(private val binding: ItemArticleCommentBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(articleCommentItem: ArticleCommentItem, onClick: (ArticleCommentItem) -> Unit) {
            binding.item = articleCommentItem
            itemView.tv_profile.text = articleCommentItem.name[0].toString()
            itemView.setOnClickListener {
                onClick(articleCommentItem)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemArticleCommentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


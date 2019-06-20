package defy.tech.chickenlover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import defy.tech.chickenlover.adapter.util.ToDoItemDiffCallback
import defy.tech.chickenlover.databinding.ItemArticleBinding
import defy.tech.chickenlover.model.data.ArticleListItem
import kotlinx.android.synthetic.main.item_article_comment.view.*

class ArticleListAdapter(private val onClick: (ArticleListItem) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var articleListList : ArrayList<ArticleListItem>

    init {
        articleListList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val articleItem = articleListList[position]
                holder.bind(articleItem, onClick)
            }
        }
    }

    override fun getItemCount(): Int = articleListList.size

    fun setList(list: ArrayList<ArticleListItem>) {
        this.articleListList?.let {
            val diffUtil = ToDoItemDiffCallback(it, list)
            val result = DiffUtil.calculateDiff(diffUtil)

            it.clear()
            it.addAll(list)

            result.dispatchUpdatesTo(this@ArticleListAdapter)
        } ?: let { this.articleListList = articleListList }
    }

    class ViewHolder private constructor(private val binding: ItemArticleBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(articleListItem: ArticleListItem, onClick: (ArticleListItem) -> Unit) {
            binding.item = articleListItem
            itemView.setOnClickListener {
                onClick(articleListItem)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


package defy.tech.chickenlover.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import defy.tech.chickenlover.adapter.util.ToDoItemDiffCallback
import defy.tech.chickenlover.databinding.ItemArticleBinding
import defy.tech.chickenlover.databinding.ItemWriteImageBinding
import defy.tech.chickenlover.model.data.ArticleListItem
import defy.tech.chickenlover.model.data.UploadImageData
import kotlinx.android.synthetic.main.item_write_image.view.*
import java.io.File

class WriteImageListAdapter(private val onClick: (UploadImageData) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var uploadImageList : ArrayList<UploadImageData>

    init {
        uploadImageList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val imageItem = uploadImageList[position]
                holder.bind(imageItem, onClick)
            }
        }
    }

    override fun getItemCount(): Int = uploadImageList.size

    fun setList(list: ArrayList<UploadImageData>) {
        this.uploadImageList?.let {
            val diffUtil = ToDoItemDiffCallback(it, list)
            val result = DiffUtil.calculateDiff(diffUtil)

            it.clear()
            it.addAll(list)

            result.dispatchUpdatesTo(this@WriteImageListAdapter)
        } ?: let { this.uploadImageList = uploadImageList }
    }

    class ViewHolder private constructor(private val binding: ItemWriteImageBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UploadImageData, onClick: (UploadImageData) -> Unit) {
            binding.item = item

            val file = File(item.path)
            val imageUri = Uri.fromFile(file)

            Glide.with(binding.root).load(imageUri).into(itemView.iv_image)

            itemView.setOnClickListener {
                onClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWriteImageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


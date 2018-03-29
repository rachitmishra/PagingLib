package `in`.ceeq.paginglib.view

import `in`.ceeq.paginglib.R
import `in`.ceeq.paginglib.databinding.ListItemPostBinding
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ItemViewHolder(private val viewDataBinding: ListItemPostBinding,
                     private val onItemClick: () -> Unit) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

    init {
        viewDataBinding.root.setOnClickListener {
            onItemClick()
        }
    }

    fun bindTo(postItemViewModel: PostItemViewModel) {
        viewDataBinding.viewModel = postItemViewModel.post
        viewDataBinding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup, onItemClick: () -> Unit) =
                ItemViewHolder(
                        DataBindingUtil.inflate(
                                LayoutInflater.from(parent.context),
                                R.layout.list_item_post,
                                parent,
                                false),
                        onItemClick)
    }
}

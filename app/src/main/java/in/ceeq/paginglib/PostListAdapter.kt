package `in`.ceeq.paginglib

import `in`.ceeq.paginglib.data.Post
import `in`.ceeq.paginglib.vo.NetworkState
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class PostListAdapter(private val retryCallback: () -> Unit) :
        PagedListAdapter<Post, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_item_footer ->
                FooterItemViewHolder.create(parent, retryCallback)
            R.layout.list_item_post ->
                ItemViewHolder.create(parent, onItemClick = {})
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.list_item_footer
        } else {
            R.layout.list_item_post
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.list_item_post -> (holder as ItemViewHolder).bindTo(
                    PostItemViewModel(getItem(position), false))
            R.layout.list_item_footer -> (holder as FooterItemViewHolder).bindTo(networkState)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val lead = getItem(position)
            val isDividerVisible = payloads.lastIndex != position
            val viewModel = PostItemViewModel(lead, isDividerVisible)
            (holder as ItemViewHolder).bindTo(viewModel)
        }
        onBindViewHolder(holder, position)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldLead: Post, newLead: Post) = oldLead == newLead

            override fun areContentsTheSame(oldLead: Post, newLead: Post) = oldLead == newLead
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}

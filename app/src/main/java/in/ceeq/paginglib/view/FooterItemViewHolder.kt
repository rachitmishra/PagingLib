package `in`.ceeq.paginglib.view

import `in`.ceeq.paginglib.R
import `in`.ceeq.paginglib.databinding.ListItemFooterBinding
import `in`.ceeq.paginglib.vo.NetworkState
import `in`.ceeq.paginglib.vo.Status
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FooterItemViewHolder(private val viewDataBinding: ListItemFooterBinding,
                           private val retryCallback: () -> Unit) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

    init {
        viewDataBinding.retry.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(networkState: NetworkState?) {
        viewDataBinding.progressBar.visibility = toVisibility(networkState?.status == Status.RUNNING)
        viewDataBinding.retry.visibility = toVisibility(networkState?.status == Status.FAILED)
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): FooterItemViewHolder = FooterItemViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_footer, parent, false), retryCallback)

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}

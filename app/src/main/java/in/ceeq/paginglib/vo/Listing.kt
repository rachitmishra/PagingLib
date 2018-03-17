package `in`.ceeq.paginglib.vo

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class Listing<T>(
        // the LiveData of paged lists for the UI to observe
        val pagedList: LiveData<PagedList<T>>,
        // represents the network request status to show to the user
        val networkState: LiveData<NetworkState>,
        // represents the refresh status to show to the user. Separate from listNetworkState, this
        val retry: () -> Unit)

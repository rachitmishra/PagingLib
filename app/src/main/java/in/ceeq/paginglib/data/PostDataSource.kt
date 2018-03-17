package `in`.ceeq.paginglib.data

import `in`.ceeq.paginglib.util.BaseScheduler
import `in`.ceeq.paginglib.vo.NetworkState
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource

class PostDataSource(
        private val mainApi: MainApi,
        private val baseScheduler: BaseScheduler) : PageKeyedDataSource<Int, Post>() {

    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            baseScheduler.network().execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, Post>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        networkState.postValue(NetworkState.LOADING)
        mainApi.getPosts(params.key, params.requestedLoadSize)
                .subscribeOn(baseScheduler.io())
                .observeOn(baseScheduler.ui())
                .subscribe({
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    val adjacentPageKey = if (it.isNotEmpty()) {
                        it.last().id
                    } else {
                        params.key
                    }
                    callback.onResult(it, adjacentPageKey)
                }, {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(it.message ?: "unknown err"))
                })
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Post>) {

        networkState.postValue(NetworkState.LOADING)
        mainApi.getPosts(0, params.requestedLoadSize)
                .subscribeOn(baseScheduler.io())
                .observeOn(baseScheduler.ui())
                .subscribe({
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(it, null, it.last().id)
                }, {
                    retry = {
                        loadInitial(params, callback)
                    }
                    networkState.postValue(NetworkState.error(it.message ?: "unknown error"))
                })
    }
}

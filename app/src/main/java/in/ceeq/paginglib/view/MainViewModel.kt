package `in`.ceeq.paginglib.view

import `in`.ceeq.paginglib.data.MainRepository
import `in`.ceeq.paginglib.data.entity.Post
import `in`.ceeq.paginglib.util.BaseViewManager
import `in`.ceeq.paginglib.util.BaseViewManagerImpl
import `in`.ceeq.paginglib.util.ConnectionUtils
import `in`.ceeq.paginglib.vo.Listing
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel

class MainViewModel(
        private val connectionUtils: ConnectionUtils,
        private val mainRepository: MainRepository,
        val baseViewManagerImpl: BaseViewManagerImpl) : ViewModel(),
        BaseViewManager by baseViewManagerImpl {

    init {
        baseViewManagerImpl.setRetryCallback {
            this.retry()
        }
    }

    private val repoResult = MutableLiveData<Listing<Post>>()

    val posts = switchMap(repoResult, { it.pagedList })

    val listNetworkState = switchMap(repoResult, { it.networkState })

    fun retry() {
        if (connectionUtils.isNetNotConnected && posts.value == null) {
            baseViewManagerImpl.handleError(true)
            return
        }

        if (posts.value == null) {
            showProgressView()
        }

        repoResult.value?.retry?.invoke() ?: getPosts() ?: getPosts()
    }

    fun getPosts() {
        if (connectionUtils.isNetNotConnected) {
            baseViewManagerImpl.handleError(true)
            return
        }

        showProgressView()
        repoResult.postValue(mainRepository.getPosts())
    }

    fun getComments() {
        if (connectionUtils.isNetNotConnected) {
            baseViewManagerImpl.handleError(true)
            return
        }

        showProgressView()
        mainRepository.getComments()
    }

    fun handleApiError() {
        if (posts.value == null) {
            handleError()
        }

        return
    }
}

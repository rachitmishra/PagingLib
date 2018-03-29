package `in`.ceeq.paginglib.data

import `in`.ceeq.paginglib.data.entity.Post
import `in`.ceeq.paginglib.util.BaseScheduler
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource

class PostDataSourceFactory(
        private val mainApi: MainApi,
        private val baseScheduler: BaseScheduler) : DataSource.Factory<Int, Post> {
    val sourceLiveData = MutableLiveData<PostDataSource>()

    override fun create(): DataSource<Int, Post> {
        val source = PostDataSource(mainApi, baseScheduler)
        sourceLiveData.postValue(source)
        return source
    }
}

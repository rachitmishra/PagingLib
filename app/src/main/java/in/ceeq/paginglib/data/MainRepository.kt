package `in`.ceeq.paginglib.data

import `in`.ceeq.paginglib.data.entity.Comment
import `in`.ceeq.paginglib.data.entity.Post
import `in`.ceeq.paginglib.util.BaseSchedulerImpl
import `in`.ceeq.paginglib.vo.Listing
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApi: MainApi,
                                         private val baseSchedulerImpl: BaseSchedulerImpl) {

    fun getPosts(): Listing<Post> {

        val sourceFactory = PostDataSourceFactory(mainApi, baseSchedulerImpl)

        val livePagedList = LivePagedListBuilder(sourceFactory,
                PagedList.Config.Builder().setInitialLoadSizeHint(20)
                        .setPageSize(20)
                        .build())
                .setBackgroundThreadExecutor(baseSchedulerImpl.network())
                .build()

        val networkState = Transformations.switchMap(sourceFactory.sourceLiveData, { it.networkState })

        return Listing(
                pagedList = livePagedList,
                networkState = networkState,
                retry =
                {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                }
        )
    }

    fun getComments(): LiveData<List<Comment>> {
        return LiveDataReactiveStreams.fromPublisher(
                mainApi.getComments(0, 20)
                        .subscribeOn(baseSchedulerImpl.io())
                        .observeOn(baseSchedulerImpl.ui())
                        .toFlowable()
        )
    }
}

interface MainApi {

    @GET("posts")
    fun getPosts(@Query("_start") start: Int, @Query("_limit") limit: Int): Single<List<Post>>

    @GET("comments")
    fun getComments(@Query("_start") start: Int, @Query("_limit") limit: Int): Single<List<Comment>>
}

package `in`.ceeq.paginglib.data

import `in`.ceeq.paginglib.util.BaseSchedulerImpl
import `in`.ceeq.paginglib.vo.Listing
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

data class Post(val id: Int, val userId: Int, val title: String, val body: String) {
    val idStr: String
        get() = id.toString()

    val userIdStr: String
        get() = userId.toString()
}

data class Comment(val id: Int, val postId: Int, val name: String, val email: String, val body: String) {
    val idStr: String
        get() = id.toString()

    val postIdStr: String
        get() = postId.toString()
}

object MainApiImpl {

    private val retrofit = buildRetrofit()

    fun getInstance(): MainApi = retrofit.create(MainApi::class.java)

    private fun buildRetrofit(): Retrofit {

        val okHttpClient =
                OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
                        .build()
        return Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

}

class MainRepository(private val baseSchedulerImpl: BaseSchedulerImpl) {

    fun getPosts(): Listing<Post> {

        val sourceFactory = PostDataSourceFactory(MainApiImpl.getInstance(), baseSchedulerImpl)

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
                MainApiImpl.getInstance().getComments(0, 20)
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

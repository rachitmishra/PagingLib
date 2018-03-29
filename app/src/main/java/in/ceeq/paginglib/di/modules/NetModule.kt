package `in`.ceeq.paginglib.di.modules

import `in`.ceeq.paginglib.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetModule {

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
                OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .build()

        @Singleton
        @JvmStatic
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
                Retrofit.Builder()
                        .baseUrl("http://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build()

    }
}

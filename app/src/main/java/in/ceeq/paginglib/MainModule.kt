package `in`.ceeq.paginglib

import `in`.ceeq.paginglib.data.MainApi
import `in`.ceeq.paginglib.di.modules.ActivityScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @Provides
    @ActivityScope
    fun provideMainApi(retrofit: Retrofit): MainApi = retrofit.create(MainApi::class.java)
}

package `in`.ceeq.paginglib.di.modules

import `in`.ceeq.paginglib.data.MainApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @ActivityScope
        fun provideMainApi(retrofit: Retrofit): MainApi = retrofit.create(MainApi::class.java)
    }
}

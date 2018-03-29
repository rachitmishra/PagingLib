package `in`.ceeq.paginglib.di.modules

import `in`.ceeq.paginglib.di.viewmodel.ViewModelModule
import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(includes = [
    AndroidInjectionModule::class,
    NetModule::class,
    SchedulerModule::class,
    ViewModelModule::class
])
class AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    fun provideResources(application: Application): Resources = application.resources
}

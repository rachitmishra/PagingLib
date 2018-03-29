package `in`.ceeq.paginglib.di.modules

import `in`.ceeq.paginglib.util.BaseScheduler
import `in`.ceeq.paginglib.util.BaseSchedulerImpl
import dagger.Binds
import dagger.Module

@Module
abstract class SchedulerModule {
    @Binds
    abstract fun provideSchedulerProvider(schedulerProvider: BaseSchedulerImpl): BaseScheduler
}

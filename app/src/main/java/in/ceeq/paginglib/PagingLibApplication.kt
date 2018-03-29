package `in`.ceeq.paginglib

import `in`.ceeq.paginglib.di.component.DaggerAppComponent
import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class PagingLibApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        DaggerAppComponent.builder()
                .app(this)
                .build()
                .inject(this)
    }

    override fun activityInjector() = activityInjector
}

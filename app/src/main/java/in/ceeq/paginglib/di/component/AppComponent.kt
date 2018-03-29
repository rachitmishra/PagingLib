package `in`.ceeq.paginglib.di.component

import `in`.ceeq.paginglib.PagingLibApplication
import `in`.ceeq.paginglib.di.modules.AppModule
import `in`.ceeq.paginglib.di.modules.MainBindings
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MainBindings::class])
interface AppComponent : AndroidInjector<PagingLibApplication> {
    fun inject(app: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(application: Application): Builder

        fun build(): AppComponent
    }
}

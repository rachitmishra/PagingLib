package `in`.ceeq.paginglib.di.modules

import `in`.ceeq.paginglib.MainModule
import `in`.ceeq.paginglib.di.viewmodel.ViewModelKey
import `in`.ceeq.paginglib.view.MainActivity
import `in`.ceeq.paginglib.view.MainViewModel
import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainBindings {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}

package `in`.ceeq.paginglib.view

import `in`.ceeq.paginglib.R
import `in`.ceeq.paginglib.databinding.ActivityMainBinding
import `in`.ceeq.paginglib.vo.NetworkState
import `in`.ceeq.paginglib.vo.Status
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val databinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = initViewModel()

        databinding.baseViewManagerImpl = viewModel.baseViewManagerImpl
        initAdapter()

        viewModel.getPosts()
        viewModel.getComments()
    }

    /**
     * Init the recycler adapter
     */
    private fun initAdapter() {

        val adapter = PostListAdapter {
            viewModel.retry()
        }

        recyclerView.adapter = adapter

        viewModel.posts.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.listNetworkState.observe(this, Observer {
            if (it == NetworkState.LOADED) {
                viewModel.hideProgressView()
            }

            if (it?.status == Status.FAILED) {
                viewModel.handleApiError()
            }

            adapter.setNetworkState(it)
        })
    }

    /**
     * Init the view model
     */
//    private fun initViewModel(): MainViewModel {
//        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//                return MainViewModel(ConnectionUtils(applicationContext),
//                        MainRepository(BaseSchedulerImpl()),
//                        BaseViewManagerImpl(BaseViewModel())) as T
//            }
//        })[MainViewModel::class.java]
//    }

    private fun initViewModel(): MainViewModel {
        return ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }
}

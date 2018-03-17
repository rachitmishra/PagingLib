package `in`.ceeq.paginglib.vo

import `in`.ceeq.paginglib.BR
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View

class BaseViewModel : BaseObservable() {

    var retryCallback: (() -> Unit)? = null

    @get: Bindable
    var progressViewVisible: Int = View.VISIBLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressViewVisible)
        }

    @get: Bindable
    var errorViewVisible: Int = View.GONE
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorViewVisible)
        }

    @get: Bindable
    var emptyViewVisible: Int = View.GONE
        set(value) {
            field = value
            notifyPropertyChanged(BR.emptyViewVisible)
        }

    @get: Bindable
    var errorMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessage)
        }

    @get: Bindable
    var emptyMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.emptyMessage)
        }

    @get: Bindable
    var progressMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressMessage)
        }

    fun showErrorView(message: String = "") {
        errorViewVisible = View.VISIBLE
    }

    fun hideErrorView() {
        errorViewVisible = View.GONE
    }

    fun showProgressView(message: String = "") {
        progressViewVisible = View.VISIBLE
    }

    fun hideProgressView() {
        progressViewVisible = View.GONE
    }

    fun showEmptyView(message: String = "") {
        emptyViewVisible = View.VISIBLE
    }

    fun hideEmptyView() {
        emptyViewVisible = View.GONE
    }

    fun onRetry() {
        retryCallback?.let {
            it.invoke()
        }
    }
}

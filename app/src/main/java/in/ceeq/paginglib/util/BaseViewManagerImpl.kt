package `in`.ceeq.paginglib.util

import `in`.ceeq.paginglib.vo.BaseViewModel


class BaseViewManagerImpl(val viewModel: BaseViewModel) : BaseViewManager {

    override fun setRetryCallback(retryCallback: () -> Unit) {
        viewModel.retryCallback = retryCallback
    }

    override fun handleError(noConnectionError: Boolean) {
        viewModel.hideEmptyView()
        hideProgressView()
        viewModel.showErrorView()
    }

    override fun handleEmpty() {
        viewModel.hideErrorView()
        hideProgressView()
        viewModel.showEmptyView()
    }

    override fun showProgressView() {
        viewModel.hideErrorView()
        viewModel.hideEmptyView()
        viewModel.showProgressView()
    }

    override fun hideProgressView() {
        viewModel.hideProgressView()
    }
}

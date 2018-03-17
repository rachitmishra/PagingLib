package `in`.ceeq.paginglib.util

interface BaseViewManager {
    fun handleError(noConnectionError: Boolean = false)
    fun handleEmpty()
    fun showProgressView()
    fun hideProgressView()
    fun setRetryCallback(retryCallback: () -> Unit)
}

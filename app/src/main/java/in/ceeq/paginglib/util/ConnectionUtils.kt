package `in`.ceeq.paginglib.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectionUtils(val context: Context) {

    val isNetNotConnected: Boolean
        get() = !isNetConnected

    val isNetConnected: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return !(networkInfo == null || !networkInfo.isConnectedOrConnecting)
        }
}

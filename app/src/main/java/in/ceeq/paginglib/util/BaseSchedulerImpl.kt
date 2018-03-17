package `in`.ceeq.paginglib.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BaseSchedulerImpl : BaseScheduler {

    private val networkIo = Executors.newFixedThreadPool(5)

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun network(): Executor = networkIo // Added for arch components

}

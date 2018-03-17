package `in`.ceeq.paginglib.util

import io.reactivex.Scheduler
import java.util.concurrent.Executor

interface BaseScheduler {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
    fun network(): Executor
}

package `in`.ceeq.paginglib.util

import android.arch.lifecycle.LiveData

class AbsentLiveData private constructor() : LiveData<Any>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData() as LiveData<T>
        }
    }
}

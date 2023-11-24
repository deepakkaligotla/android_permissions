package `in`.kaligotla.allpermissionsimpl.core

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import `in`.kaligotla.allpermissionsimpl.core.Resource.Status.ERROR
import `in`.kaligotla.allpermissionsimpl.core.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()

        if (responseStatus.status == SUCCESS) {
            Log.e("performGetOperation SUCCESS", "" + responseStatus.message)
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == ERROR) {
            Log.e("performGetOperation ERROR", "" + responseStatus.message)
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }
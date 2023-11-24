package `in`.kaligotla.allpermissionsimpl.data.repository.proto

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import `in`.kaligotla.allpermissionsimpl.proto.OnboardState
import java.io.IOException
import javax.inject.Inject

class OnboardStateRepositoryImpl @Inject constructor(
    context: Context, private var onboardStateDataStore: DataStore<OnboardState>
) : OnboardStateRepository {

    override suspend fun getOnboardState(): LiveData<Boolean> {
        return onboardStateDataStore.data.asLiveData()
            .switchMap { MutableLiveData(it.isOnboardedState) }
    }

    override suspend fun updateOnboardState(newOnboardState: Boolean) {
        try {
            onboardStateDataStore.updateData {
                it.toBuilder().setIsOnboardedState(newOnboardState).build()
            }
        } catch (e: IOException) {
            Log.e("updateOnboardState catch", "" + e)
        }
    }

    override suspend fun clearOnboardState() {
        onboardStateDataStore.updateData {
            it.toBuilder().clearIsOnboardedState().build()
        }
    }

    override suspend fun resetData() {
        onboardStateDataStore.updateData {
            OnboardState.getDefaultInstance()
        }
    }
}
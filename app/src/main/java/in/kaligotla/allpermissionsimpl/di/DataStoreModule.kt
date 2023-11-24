package `in`.kaligotla.allpermissionsimpl.di

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.kaligotla.allpermissionsimpl.data.repository.proto.OnboardStateRepository
import `in`.kaligotla.allpermissionsimpl.data.repository.proto.OnboardStateRepositoryImpl
import `in`.kaligotla.allpermissionsimpl.proto.OnboardState
import java.io.InputStream
import java.io.OutputStream

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Suppress("BlockingMethodInNonBlockingContext")
    object OnboardStateSerializer : Serializer<OnboardState> {
        override val defaultValue: OnboardState = OnboardState.getDefaultInstance()

        override suspend fun readFrom(input: InputStream): OnboardState {
            try {
                return OnboardState.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                throw e
            }
        }

        override suspend fun writeTo(t: OnboardState, output: OutputStream) = t.writeTo(output)
    }

    private val Context.onboardStateDataStore: DataStore<OnboardState> by dataStore(
        fileName = "user_preferences.pb",
        serializer = OnboardStateSerializer
    )

    @Provides
    @Reusable
    fun provideProtoDataStore(@ApplicationContext context: Context) =
        context.onboardStateDataStore

    @Provides
    @Reusable
    internal fun providesProtoDataStoreRepository(
        @ApplicationContext context: Context,
        onboardStateDataStore: DataStore<OnboardState>
    ): OnboardStateRepository {
        return OnboardStateRepositoryImpl(
            context,
            onboardStateDataStore
        )
    }
}
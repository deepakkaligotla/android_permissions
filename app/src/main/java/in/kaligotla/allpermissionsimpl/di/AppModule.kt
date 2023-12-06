package `in`.kaligotla.allpermissionsimpl.di

import android.app.Application
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import `in`.kaligotla.allpermissionsimpl.core.Constants.EXTRAS_RETROFIT_BUILD
import `in`.kaligotla.allpermissionsimpl.core.Constants.PERMISSION_REMOTE_DATA_SOURCE
import `in`.kaligotla.allpermissionsimpl.core.Constants.PERMISSION_SERVICE
import `in`.kaligotla.allpermissionsimpl.core.LoadDataService
import `in`.kaligotla.allpermissionsimpl.data.local.AppDatabase
import `in`.kaligotla.allpermissionsimpl.data.local.PermissionDao
import `in`.kaligotla.allpermissionsimpl.data.remote.PermissionRemoteDataSource
import `in`.kaligotla.allpermissionsimpl.data.remote.PermissionService
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepository
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepositoryImpl
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.nearbyDevices.MyNearbyDevicesViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    @Named(EXTRAS_RETROFIT_BUILD)
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("http://192.168.0.14:3000/")
        .client(
            OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS).build()
        )
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Named(PERMISSION_SERVICE)
    fun providePermissionService(
        @Named(EXTRAS_RETROFIT_BUILD)
        retrofit: Retrofit
    ): PermissionService =
        retrofit.create(PermissionService::class.java)

    @Provides
    @Named(PERMISSION_REMOTE_DATA_SOURCE)
    fun providePermissionRemoteDataSource(
        @Named(PERMISSION_SERVICE)
        permissionService: PermissionService
    ) =
        PermissionRemoteDataSource(permissionService)

    @Provides
    fun provideDatabase(appContext: Application) =
        AppDatabase.getDatabase(appContext)

    @Provides
    fun loadDataService(): Service = LoadDataService()

    @Provides
    fun providePermissionDao(db: AppDatabase) = db.dBPermissionDao()

    @RequiresApi(Build.VERSION_CODES.Q)
    @Provides
    fun provideRepository(
        loadDataService: LoadDataService,
        @Named(PERMISSION_REMOTE_DATA_SOURCE)
        remoteDataSource: PermissionRemoteDataSource,
        localDataSource: PermissionDao,
    ): PermissionRepository = PermissionRepositoryImpl(
        loadDataService, remoteDataSource, localDataSource
    )
}
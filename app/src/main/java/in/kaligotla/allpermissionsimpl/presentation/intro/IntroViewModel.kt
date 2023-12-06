package `in`.kaligotla.allpermissionsimpl.presentation.intro

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.repository.proto.AppPreferencesRepository
import `in`.kaligotla.allpermissionsimpl.data.repository.proto.OnboardStateRepository
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.proto.MyAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val onBoardStateRepository: OnboardStateRepository,
    private val appPreferencesRepository: AppPreferencesRepository
) : ViewModel(), DefaultLifecycleObserver {

    var onboardState = false

    init {
        viewModelScope.launch {
            onBoardStateRepository.getOnboardState().observeForever{
                onboardState = it
            }
        }
        getUserTheme()
    }

    private lateinit var _usertTheme: LiveData<MyAppTheme>
    var userTheme: LiveData<MyAppTheme> = _usertTheme

    fun saveUserOnboarding(onboardState: Boolean) = viewModelScope.launch {
        onBoardStateRepository.updateOnboardState(onboardState)
    }

    fun clearOnboardState() = viewModelScope.launch {
        onBoardStateRepository.clearOnboardState()
    }

    private fun getUserTheme() = viewModelScope.launch {
        _usertTheme = appPreferencesRepository.getTheme()
    }

    fun saveUserTheme(theme: AppTheme) = viewModelScope.launch {
        appPreferencesRepository.setTheme(theme)
    }

    fun resetUserTheme() = viewModelScope.launch {
        appPreferencesRepository.resetTheme()
    }
}
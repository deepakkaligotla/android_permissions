package `in`.kaligotla.allpermissionsimpl.presentation.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.repository.proto.OnboardStateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val dataRepository: OnboardStateRepository
) : ViewModel() {

    init {
        getOnboardState()
    }

    private lateinit var _onboardState: LiveData<Boolean>
    var onboardState: LiveData<Boolean> = _onboardState

    private fun getOnboardState() = viewModelScope.launch {
        _onboardState = dataRepository.getOnboardState()
    }

    fun saveUserOnboarding(onboardState: Boolean) = viewModelScope.launch {
        dataRepository.updateOnboardState(onboardState)
    }

    fun clearOnboardState() = viewModelScope.launch {
        dataRepository.clearOnboardState()
    }
}
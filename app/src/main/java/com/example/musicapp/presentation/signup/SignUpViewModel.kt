package com.example.musicapp.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.remote.data.SignupRequestDto
import com.example.musicapp.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun signUp(name: String, email: String, pass: String) {
        _state.value = SignUpState(isLoading = true)

        viewModelScope.launch {
            val request = SignupRequestDto(
                username = name,
                email = email,
                password = pass
            )

            val result = repository.signup(request)

            result.onSuccess {
                _state.value = SignUpState(isSuccess = true)
            }.onFailure { exception ->
                _state.value = SignUpState(
                    error = exception.message ?: "Error al registrarse"
                )
            }
        }
    }
}

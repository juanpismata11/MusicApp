package com.example.musicapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.local.SessionManager
import com.example.musicapp.data.remote.data.LoginRequestDto
import com.example.musicapp.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MusicRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _state.value = LoginState(error = "Por favor, llena todos los campos")
            return
        }

        _state.value = LoginState(isLoading = true)

        viewModelScope.launch {
            val request = LoginRequestDto(
                email = email,
                password = pass
            )

            val result = repository.login(request)

            result.onSuccess { userDto ->
                sessionManager.saveUserSession(userDto.id, userDto.username)

                _state.value = LoginState(isSuccess = true)
            }.onFailure { exception ->
                val errorMsg = if (exception.message?.contains("401") == true || exception.message?.contains("400") == true) {
                    "Correo o contraseña incorrectos"
                } else {
                    exception.message ?: "Error desconocido al iniciar sesión"
                }
                _state.value = LoginState(error = errorMsg)
            }
        }
    }
}

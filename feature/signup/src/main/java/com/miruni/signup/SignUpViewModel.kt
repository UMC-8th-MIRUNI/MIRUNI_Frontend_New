package com.miruni.signup

import androidx.lifecycle.ViewModel
import com.miruni.signup.model.Term
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateNickName(nickName: String) {
        _uiState.update { it.copy(nickName = nickName) }
    }

    fun updateBirth(birth: String) {
        _uiState.update { it.copy(birth = birth) }
    }

    fun updatePhone(phone: String) {
        _uiState.update { it.copy(phone = phone) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateOtp(otp: String) {
        _uiState.update { it.copy(otp = otp) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun updatePasswordCheck(passwordCheck: String) {
        _uiState.update { it.copy(passwordCheck = passwordCheck) }
    }

    fun updateOpenTerm(term: Term) {
        _uiState.update { it.copy(openTerm = term) }
    }
    fun updateSelectedTerm(term: Term?) {
        _uiState.update { it.copy(selectedTerm = term) }
    }
}

data class SignUpUiState(
    val name: String = "",
    val nickName: String = "",
    val birth: String = "",
    val phone: String = "",
    val email: String = "",
    val otp: String = "",
    val password: String = "",
    val passwordCheck: String = "",
    val openTerm : Term? = null,
    val selectedTerm : Term? = null
)

package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.repository.LocalPasswordDBStore
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginViewState(
    val checked: Boolean = false,
    val login: String = "",
    val password: String = "",
    val checkCredentials: (login: String, password: String) -> Boolean = { _, _ -> false },
    val navigateToSettings: () -> Unit = {},
    val navigateToList: (name: String) -> Unit = {},
)

@HiltViewModel
open class LoginViewModel @Inject constructor() : ViewModel() {
    var loginState by mutableStateOf(LoginViewState())
        private set

    fun onLoginChanged(login: String) {
        loginState = loginState.copy(login = login)
    }

    fun onPasswordChanged(password: String) {
        loginState = loginState.copy(password = password)
    }

    fun onCheckedChanged(checked: Boolean) {
        loginState = loginState.copy(checked = checked)
    }
}
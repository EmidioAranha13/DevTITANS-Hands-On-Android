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
    val preencher: Boolean = false,
    val login: String = "",
    val navigateToSettings: () -> Unit = {},
    val navigateToList: (name: String) -> Unit = {},
    val checkCredentials: (login: String, password: String) -> Boolean = { _, _ -> false }
)

//Utilize o passwordBDStore para obter a lista de senhas e salva-las
@HiltViewModel
open class LoginViewModel @Inject constructor (private val loginViewState: LoginViewState) : ViewModel() {
    var loginViewState by mutableStateOf(LoginViewState())
        private set

    init{
        viewModelScope.launch {
            //execute o metodo getList() do passwordDBStore e colete o resultado
        }
    }


    fun savePassword(password: PasswordInfo){

    }
}
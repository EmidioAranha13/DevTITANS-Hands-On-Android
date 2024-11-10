package com.example.plaintext.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.plaintext.ui.viewmodel.LoginViewModel

// Definindo o estado da preferências
data class PreferencesState(
    var login: String = "",
    var password: String = "",
    var preencher: Boolean = false, // Para salvar ou não as credenciais
    var isLoggedIn: Boolean = false, // Estado de login (se o login foi bem-sucedido)
    var errorMessage: String? = null // Mensagem de erro (caso as credenciais sejam inválidas)
)

@HiltViewModel
class PreferencesViewModel @Inject constructor() : ViewModel() {
    // O estado da tela, incluindo as credenciais e o estado de erro/sucesso do login
    var preferencesState by mutableStateOf(PreferencesState())
        private set

    // Função para atualizar o login
    fun onLoginChanged(login: String) {
        preferencesState = preferencesState.copy(login = login)
    }

    // Função para atualizar a senha
    fun onPasswordChanged(password: String) {
        preferencesState = preferencesState.copy(password = password)
    }

    // Função para atualizar a opção de salvar as credenciais
    fun onPreencherChange(preencher: Boolean) {
        preferencesState = preferencesState.copy(preencher = preencher)
    }

    fun onIsLoggedInChanged(isLoggedIn: Boolean) {
        preferencesState = preferencesState.copy(isLoggedIn = isLoggedIn)
    }

    fun errorMessage(errorMessage: String?) {
        preferencesState = preferencesState.copy(errorMessage = errorMessage)
    }
}

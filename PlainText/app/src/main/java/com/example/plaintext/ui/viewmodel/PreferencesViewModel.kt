package com.example.plaintext.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Definindo o estado da preferências
data class PreferencesState(
    var login: String,
    var password: String,
    var preencher: Boolean, // Para salvar ou não as credenciais
    var isLoggedIn: Boolean = false, // Estado de login (se o login foi bem-sucedido)
    var errorMessage: String? = null // Mensagem de erro (caso as credenciais sejam inválidas)
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    handle: SavedStateHandle,
) : ViewModel() {
    // O estado da tela, incluindo as credenciais e o estado de erro/sucesso do login
    var preferencesState by mutableStateOf(PreferencesState(
        login = "devtitans", // Login pré-definido
        password = "123",    // Senha pré-definida
        preencher = true     // Se a opção de salvar está marcada ou não
    ))
        private set

    // Função para atualizar o login
    fun updateLogin(login: String) {
        preferencesState = preferencesState.copy(login = login)
    }

    // Função para atualizar a senha
    fun updatePassword(password: String) {
        preferencesState = preferencesState.copy(password = password)
    }

    // Função para atualizar a opção de salvar as credenciais
    fun updatePreencher(preencher: Boolean) {
        preferencesState = preferencesState.copy(preencher = preencher)
    }

    // Função que verifica as credenciais
    fun checkCredentials(login: String, password: String): Boolean {
        // To Debug login error
        Log.d("PreferencesViewModel", "Checking credentials: input login=$login, input password=$password")
        Log.d("PreferencesViewModel", "Stored credentials: login=${preferencesState.login}, password=${preferencesState.password}")


        return (login == preferencesState.login && password == preferencesState.password)
    }
}

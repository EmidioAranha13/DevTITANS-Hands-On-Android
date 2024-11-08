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

data class ListViewState(
    var passwordList: List<PasswordInfo> = emptyList(),
    var isCollected: Boolean = false
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val passwordDBStore: PasswordDBStore
) : ViewModel() {

    var listViewState by mutableStateOf(ListViewState())
        private set

    init {
        viewModelScope.launch {
            // Executa o método getList() do passwordDBStore e coleta o resultado
            passwordDBStore.getList().collect { passwordList ->
                // Atualiza o estado da lista com as senhas coletadas
                listViewState = listViewState.copy(
                    passwordList = passwordList.map { it.toPasswordInfo() },
                    isCollected = true
                )
            }
        }
    }

    fun savePassword(password: PasswordInfo) {
        viewModelScope.launch {
            passwordDBStore.save(password) // Salva a senha no banco de dados
            // Não é necessário recarregar a lista, pois ela será atualizada automaticamente
            // pela coleta contínua de passwordDBStore.getList()
        }
    }
}

// Função de extensão para converter Password para PasswordInfo
private fun Password.toPasswordInfo(): PasswordInfo {
    return PasswordInfo(
        id = this.id,
        name = this.name,
        login = this.login,
        password = this.password,
        notes = this.notes
    )
}

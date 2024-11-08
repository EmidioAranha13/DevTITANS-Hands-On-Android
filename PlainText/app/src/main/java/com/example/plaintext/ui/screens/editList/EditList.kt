package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.TopBarComponent


data class EditListState(
    val nomeState: MutableState<String>,
    val usuarioState: MutableState<String>,
    val senhaState: MutableState<String>,
    val notasState: MutableState<String>,
)

fun isPasswordEmpty(password: PasswordInfo): Boolean {
    return password.name.isEmpty() && password.login.isEmpty() && password.password.isEmpty() && password.notes.isEmpty()
}

@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: (password: PasswordInfo) -> Unit
) {
    val editListState = EditListState (
        nomeState = rememberSaveable { mutableStateOf(args.password.name) },
        usuarioState = rememberSaveable { mutableStateOf(args.password.login) },
        senhaState = rememberSaveable { mutableStateOf(args.password.password) },
        notasState = rememberSaveable { mutableStateOf(args.password.notes) },
    )

    Scaffold (
        topBar = {
            TopBarComponent()
        },

        content = { padding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(padding)
            )
            {
                TitleScreen("Adcionar nova Senha")
                EditInput("Nome", editListState.nomeState)
                EditInput("Usuario", editListState.usuarioState)
                EditInput("Senha", editListState.senhaState)
                EditInput("Notas", editListState.notasState, textInputHeight = 240)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                )
                {
                    Button(
                        onClick = {
                            // Quando o botão for pressionado, cria um novo PasswordInfo
                            val updatedPassword = PasswordInfo(
                                id = args.password.id, // ID permanece o mesmo
                                name = editListState.nomeState.value,
                                login = editListState.usuarioState.value,
                                password = editListState.senhaState.value,
                                notes = editListState.notasState.value
                            )

                            // Chama a função savePassword passando o PasswordInfo atualizado
                            savePassword(updatedPassword)
                        },
                    ){
                        Text("Salvar")
                    }
                }
            }
        }
    )
}


@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String> = mutableStateOf(""),
    textInputHeight: Int = 60
) {
    val padding: Int = 30

    var textState by rememberSaveable { textInputState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleScreen(
    textInputLabel: String
) {
    Box(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(Color(0xFF9ACD32)) // Cor de fundo verde claro
    ) {
        Text(
            text = textInputLabel,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically).padding(10.dp)
                .align(Alignment.CenterStart) // Alinha o texto no centro da Box
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    EditList(
        Screen.EditList(PasswordInfo(1, "Nome", "Usuário", "Senha", "Notas")),
        navigateBack = {},
        savePassword = {}
    )
}


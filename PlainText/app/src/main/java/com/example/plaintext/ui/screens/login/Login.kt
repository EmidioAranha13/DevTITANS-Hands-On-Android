package com.example.plaintext.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plaintext.R
import com.example.plaintext.ui.screens.hello.ListViewModel
import com.example.plaintext.ui.screens.hello.listViewState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.ui.viewmodel.LoginViewModel
import com.example.plaintext.ui.viewmodel.PreferencesState

data class LoginViewState(
    val checked: Boolean = false,
    val login: String = "",
    val password: String = "",
    val checkCredentials: (login: String, password: String) -> Boolean = { _, _ -> false },
    val navigateToSettings: () -> Unit = {},
    val navigateToList: (name: String) -> Unit = {},
)

@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBarComponent(navigateToSettings = navigateToSettings)
        }) { innerPadding ->
        Login(
            name = "Android",
            modifier = Modifier.padding(innerPadding),
            navigateToList = navigateToList,
            navigateToSettings = navigateToSettings,

        )
    }
}

@Composable
fun Login(
    name: String,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    preferencesViewModel: PreferencesViewModel = hiltViewModel(),
    navigateToList: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val loginState = loginViewModel.loginState
    val login = loginState.login
    val pswd = loginState.password
    val checked = loginState.checked

    val context = LocalContext.current
    var yellowGreen = Color(0xFF9ACD32)


    Column(
        modifier = Modifier.fillMaxSize().padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .background(yellowGreen),
            contentAlignment = Alignment.Center,
        ) {
            Row (
                modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Imagem de exemplo",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = "\"The most secure password manager\" Bob and Alice",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .widthIn(max = 130.dp)
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 0.dp)
                )
            }
        }

        Column(
            modifier = Modifier.padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Digite suas credenciais para continuar",
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Login:  ", fontSize = 12.sp)
                OutlinedTextField(
                    value = login,
                    label = { Text("Login") },
                    onValueChange = { loginViewModel.onLoginChanged(it) },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Senha: ", fontSize = 12.sp)
                OutlinedTextField(
                    value = pswd,
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { loginViewModel.onPasswordChanged(it) },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { loginViewModel.onCheckedChanged(it) },
                )
                Text(text = "Salvar as informações de login", fontSize = 12.sp,)
            }
            Button(
                onClick = {
                    // Chama a função para verificar as credenciais
                    if (loginViewModel.checkCredentials(login, pswd)) {
                        // Login bem-sucedido
                        if(checked) {
                           //Add aqui a função que atualiza as preferencias
                        } else {
                            loginViewModel.resetState()
                        }
                        Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                       // Aqui a navegação para a tela List
                        navigateToList()
                    } else {
                        // Exibe mensagem de erro
                        Toast.makeText(context, "Login/Senha inválidos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(0.dp, Color.Gray),
                colors = ButtonDefaults.buttonColors(),
                enabled = true
            ) {
                Text("Enviar", fontSize = 12.sp)
            }


            // Exibe a mensagem de erro
//            preferencesState.errorMessage?.let {
//                Text(
//                    text = it,
//                    color = Color.Red,
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(top = 8.dp)
//                )
//            }//Toast.makeText(context, preferencesState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}



@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },

            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(
                    onClick = { shouldShowDialog.value = false }
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText") },
        actions = {
            if (navigateToSettings != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings();
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Sobre");
                        },
                        onClick = {
                            shouldShowDialog.value = true;
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )
}

@Preview(name="Modo Retrato", heightDp = 700, showBackground = true)
//@Preview(name="Modo Paisagem", widthDp = 640, showBackground = true)
@Composable
fun PreviewUI() {
    Login("Android", Modifier, viewModel(), viewModel(), {}, {})
}



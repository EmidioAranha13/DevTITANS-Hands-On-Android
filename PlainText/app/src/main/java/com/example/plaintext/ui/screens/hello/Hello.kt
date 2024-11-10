package com.example.plaintext.ui.screens.hello


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plaintext.R
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.theme.PlainTextTheme
import com.example.plaintext.ui.viewmodel.ListViewState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class dbSimulator() {
    private val datalist = mutableListOf<String>();

    init {
        for (i in 1..15) {
            datalist.add("devtitans #$i");
        }

    }

    fun getData(): Flow<List<String>> = flow {
        delay(5000)
        emit(datalist)
    }
}

data class listViewState(
    var listState: List<String> = emptyList(),
    var size: Int = 0
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dbSimulator: dbSimulator
) : ViewModel() {
    var listState by mutableStateOf(listViewState())
        private set

    init {
        viewModelScope.launch {
            collectData()
        }
    }

    fun collectData() {
        viewModelScope.launch {
            dbSimulator.getData().collect {
                listState = listState.copy(listState = it, size = it.size)
            }
        }
    }
}

@Composable
fun Hello_screen(modifier: Modifier, viewModel: ListViewModel = hiltViewModel()) {
    val listViewState: listViewState = viewModel.listState

    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        if (listViewState.listState.size == 0) {
            Text("Carregando...", fontSize = 20.sp)
        } else {
            Column() {
                Text(
                    text = "Total de itens: ${listViewState.size}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                        .padding(16.dp)
                )
                LazyColumn {
                    items(listViewState.listState.size) { index ->
                        Text(
                            text = listViewState.listState[index],
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp).fillMaxWidth()
                        )
                    }
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hello_screen(args: Screen.Hello) {
    Scaffold { padding ->
        Hello_screen(Modifier.padding(padding))
    }
}

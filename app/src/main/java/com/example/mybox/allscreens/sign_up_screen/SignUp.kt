package com.example.mybox.allscreens.sign_up_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mybox.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun SignUp(
    viewModel: SignUpModel = hiltViewModel(),
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    fnCancel: () -> Unit,
    fnNavigate: ()-> Unit
) {
    var mail by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        SignUpView(
            mail = mail,
            fnMail = { mail = it },
            name = name,
            fnName = { name = it },
            username = username,
            fnUserName = { username = it },
            password = password,
            fnPassword = { password = it },
            fnCancel = fnCancel
        ) {
            runBlocking {
                var responce = viewModel.postSigUp(
                    mail = mail,
                    name = name,
                    username = username,
                    password = password
                )
                when (responce) {
                    is Resource.Success -> {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Регестрация успешна")
                        }
                        fnNavigate.invoke()
                    }
                    is Resource.Error -> {
                        Log.e("Error: ", "${responce.message}")
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Ошибка: ${responce.message}")
                        }
                    }
                    else -> {
                        Log.e("Error: ", "Unknow error")
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Ошибка: неизвестная ошибка")
                        }
                    }
                }
            }
        }
    }
}

package com.example.mybox.allscreens.sign_in_screen

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.mybox.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response


@Composable
fun SignIn(
    viewModel: SignInModel = hiltViewModel(),
    fnCancel: () -> Unit,
    fnNavigate: ()-> Unit,
    fnToken: (String) -> Unit
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    SignInView(login = login,
        fnLogin = { login = it },
        password = password,
        fnPassword = { password = it },
        fnCancel = fnCancel) {
        runBlocking {
            var responce = viewModel.postSignIn(login, password)
            when (responce) {
                is Resource.Success -> {
                    fnToken(responce.data!!.token)
                    fnNavigate.invoke()
                }
                is Resource.Error -> {
                    Log.e("Error: ", "SignIn - ${responce.message}")
                }
            }
        }
    }
}


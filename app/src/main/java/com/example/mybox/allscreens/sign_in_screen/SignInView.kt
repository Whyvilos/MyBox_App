package com.example.mybox.allscreens.sign_in_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun SignInView(
    login: String,
    fnLogin: (String)-> Unit,
    password: String,
    fnPassword: (String) -> Unit,
    fnCancel: () -> Unit,
    fnSignIn: ()-> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Text("Вход")
        Spacer(
            modifier = Modifier
                .align(Alignment.End)
                .height(8.dp)
        )
        TextField(
            value = login,
            onValueChange = fnLogin,
            placeholder = { Text("Пароль") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0D47A1),
                backgroundColor = Color(0xFFC3DAFF),
                focusedLabelColor = Color(0xFF0D47A1),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(51.dp)

        )
        Spacer(
            modifier = Modifier
                .align(Alignment.End)
                .height(8.dp)
        )
        TextField(
            value = password,
            onValueChange = fnPassword,
            placeholder = { Text("Пароль") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0D47A1),
                backgroundColor = Color(0xFFC3DAFF),
                focusedLabelColor = Color(0xFF0D47A1),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(51.dp)

        )
        Spacer(
            modifier = Modifier
                .align(Alignment.End)
                .height(8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = fnCancel,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0D47A1),
                    contentColor = Color(0xFFC3DAFF),
                ),
                modifier = Modifier
                    .height(50.dp)
                    .width(120.dp)
            ) {
                Text(text = "Отмена")
            }
            Button(
                onClick = fnSignIn,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0D47A1),
                    contentColor = Color(0xFFC3DAFF),
                ),
                modifier = Modifier
                    .height(50.dp)
                    .width(120.dp)
            ) {
                Text(text = "Войти")
            }
        }
    }
}
package com.example.mybox.allscreens.sign_up_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SignUpView(
    mail: String,
    fnMail: (String) -> Unit,
    name: String,
    fnName: (String) -> Unit,
    username: String,
    fnUserName: (String) -> Unit,
    password: String,
    fnPassword: (String) -> Unit,
    fnCancel: () -> Unit,
    fnSignUp: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Регестарция")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = mail,
                onValueChange = fnMail,
                placeholder = { Text("Почта") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF0D47A1),
                    backgroundColor = Color(0xFFC3DAFF),
                    focusedLabelColor = Color(0xFF0D47A1),
                ),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .height(51.dp)

            )
        }
        Spacer(
            modifier = Modifier
                .align(Alignment.End)
                .height(8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = name,
                onValueChange = fnName,
                placeholder = { Text("Имя") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF0D47A1),
                    backgroundColor = Color(0xFFC3DAFF),
                    focusedLabelColor = Color(0xFF0D47A1),
                ),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .height(51.dp)

            )
        }
        Spacer(
            modifier = Modifier
                .align(Alignment.End)
                .height(8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = username,
                onValueChange = fnUserName,
                placeholder = { Text("Логин") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF0D47A1),
                    backgroundColor = Color(0xFFC3DAFF),
                    focusedLabelColor = Color(0xFF0D47A1),
                ),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .height(51.dp)

            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
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
                    .width(IntrinsicSize.Max)
                    .height(51.dp)

            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )


            Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = fnSignUp,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0D47A1),
                    contentColor = Color(0xFFC3DAFF),
                ),
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
            ) {
                Text(text = "Регистрация")
            }
        }
        Spacer(
            modifier = Modifier
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
                    .width(150.dp)
            ) {
                Text(text = "Отмена")
            }
        }
    }
}


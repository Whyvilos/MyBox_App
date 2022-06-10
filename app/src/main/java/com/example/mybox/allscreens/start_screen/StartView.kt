package com.example.mybox.allscreens.start_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Start(
    fnButtonIn: () -> Unit,
    fnButtonUp: () -> Unit,
) {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 20.dp)
    ) {

            Text(text = "Авторизация")
            Spacer(
                modifier = Modifier
                    .align(Alignment.End)
                    .height(8.dp)
            )
            Button(onClick = fnButtonIn, modifier = Modifier.width(200.dp)) {
                Text(text = "Войти")
            }
            Spacer(
                modifier = Modifier
                    .align(Alignment.End)
                    .height(8.dp)
            )
            Button(onClick = fnButtonUp, modifier = Modifier.width(200.dp)) {
                Text(text = "Зарегестрироваться")
            }
            Spacer(
                modifier = Modifier
                    .align(Alignment.End)
                    .height(8.dp)
            )
            Button(onClick = { /*TODO*/ }, modifier = Modifier.width(200.dp)) {
                Text(text = "Выйти")
            }
        }
    }


}
package com.example.mybox.allscreens.menu_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mybox.util.Resource
import kotlinx.coroutines.runBlocking

@Composable
fun Service(
    accessToken: String,
    fnButtonFavorite: () -> Unit,
    fnButtonOrders: () -> Unit,
    fnButtonNotice:() -> Unit,
    fnButtonOrdersYou: () -> Unit,
    fnButtonExit: ()-> Unit

) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = fnButtonFavorite,
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .height(55.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,

                    modifier = Modifier
                        .padding(end = 4.dp)
                        .background(Color.White)
                )
                Text(text = "Список\nизбранного")
            }

            OutlinedButton(
                onClick = fnButtonOrders,
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .height(55.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBasket,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .background(Color.White)
                )
                Text(text = "Список\nзаказов")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = fnButtonNotice,
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .height(55.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,

                    modifier = Modifier
                        .padding(end = 4.dp)
                        .background(Color.White)
                )
                Text(text = "Уведомления")
            }

            OutlinedButton(
                onClick = fnButtonOrdersYou,
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .height(55.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Shop,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .background(Color.White)
                )
                Text(text = "Мой\nмагазин")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = fnButtonFavorite,
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .height(55.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = null,

                    modifier = Modifier
                        .padding(end = 4.dp)
                        .background(Color.White)
                )
                Text(text = "Подписки")
            }

            OutlinedButton(
                onClick = fnButtonExit,
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .height(55.dp)
            ) {
//                Icon(
//                    imageVector = Icons.Default.ShoppingBasket,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(end = 4.dp)
//                        .background(Color.White)
//                )
                Text(text = "Выйти")
            }
        }

    }
}
package com.example.mybox.allscreens.util_element

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AddItem(
    title: String,
    fnTitle: (String)->Unit,
    description: String,
    price: String,
    fnPrice: (String)->Unit,
    fnDescription: (String) -> Unit,
    fnCancel: () -> Unit,
    bitmap: MutableState<Bitmap?>,
    fnOpen: () -> Unit,
    fnCreateItem: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {

        bitmap.value?.let {it ->
            Image(bitmap = it.asImageBitmap(), contentDescription = null)
        }

        Text("Новое предложение")
        TextField(
            value = title,
            onValueChange = fnTitle,
            placeholder = { Text("Наименование") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0D47A1),
                backgroundColor = Color(0xFFC3DAFF),
                focusedLabelColor = Color(0xFF0D47A1),
            ),
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(51.dp)

        )
        Spacer(
            modifier = Modifier
                .align(Alignment.End)
                .height(8.dp)
        )
        TextField(
            value = description,
            onValueChange = fnDescription,
            placeholder = { Text("Описание") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0D47A1),
                backgroundColor = Color(0xFFC3DAFF),
                focusedLabelColor = Color(0xFF0D47A1),
            ),
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(51.dp)

        )
        Spacer(
            modifier = Modifier
                .align(Alignment.End)
                .height(8.dp)
        )
        TextField(
            value = price.toString(),
            onValueChange = fnPrice,
            placeholder = { Text("Стоимость") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0D47A1),
                backgroundColor = Color(0xFFC3DAFF),
                focusedLabelColor = Color(0xFF0D47A1),
            ),
            modifier = Modifier
                .width(IntrinsicSize.Max)
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
                    .width(100.dp)
            ) {
                Text(text = "Отмена")
            }
            Button(
                onClick = fnOpen,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0D47A1),
                    contentColor = Color(0xFFC3DAFF),
                ),
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
            ) {
                Text(text = "Открыть")
            }
            Button(
                onClick = fnCreateItem,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0D47A1),
                    contentColor = Color(0xFFC3DAFF),
                ),
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
            ) {
                Text(text = "Создать")
            }
        }
    }


}
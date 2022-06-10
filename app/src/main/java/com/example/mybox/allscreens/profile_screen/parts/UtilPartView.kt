package com.example.mybox.allscreens.profile_screen.parts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.runBlocking


@Composable
fun SelectScroll(
    fnButtonPosts: ()->Unit,
    fnButtonCatalog: ()->Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = fnButtonPosts,
            border = BorderStroke(1.dp, Color.Magenta),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier.width(150.dp)
        ) {
            Text("Записи")
        }
        OutlinedButton(
            onClick = fnButtonCatalog,
            border = BorderStroke(1.dp, Color.Magenta),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier.width(150.dp)

        ) {
            Text("Каталог")
        }
    }
}

@Composable
fun AddView(
    fnButton:()-> Unit
) {

    Surface(
        shape = CircleShape,
        modifier = Modifier
            .size(45.dp)
            .background(Color.Transparent)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                clip = true
            )
            .clickable(onClick = fnButton)

    ) {
        Box(
            modifier = Modifier
                .padding()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "+", fontSize = 30.sp, color = Color(0xFF51A84B))
        }
    }
}

@Composable
fun FollowView(
    fnButton: () -> Unit,
    content: String
) {
    OutlinedButton(
        onClick = fnButton,
        border = BorderStroke(1.dp, Color.Gray),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier.width(150.dp)
    ) {
        Text(text = content)
    }
}

@Composable
fun UploadAvatar(
    bitmap: Bitmap?,
    flagViewImage: Boolean,
    fnButtonUpload: () -> Unit,
    fnButtonOpen: () -> Unit,
    fnButtonCancel: () -> Unit,

    ) {

    Dialog(onDismissRequest = {}) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(450.dp)
                .height(500.dp)
                .background(Color.White)
                .padding(bottom = 7.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if(flagViewImage){
                    bitmap?.let { it ->
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .background(Color.Black),
                        onClick = fnButtonOpen)
                    {
                        Text(text = "Открыть галерею")
                    }
                    Button(
                        modifier = Modifier
                            .background(Color.Black),
                        onClick = fnButtonCancel)
                    {
                        Text(text = "Отмена")
                    }
                }
                if(flagViewImage){
                    Button(
                        modifier = Modifier
                            .background(Color.Black),
                        onClick = fnButtonUpload)
                    {
                        Text(text = "Загрузить")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Pre1() {
    AddView({})
}

@Preview
@Composable
fun Pre2() {
    AddView({})
}

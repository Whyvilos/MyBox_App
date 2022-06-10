package com.example.mybox.allscreens.catalog_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import coil.compose.AsyncImage



@Composable
fun Catalog() {
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedImage = uri
    }

    MainContent(selectedImage) {
        launcher.launch("image/jpeg")
    }
}

@Composable
private fun MainContent(
    selectedImage: Uri? = null,
    onImageClick: () -> Unit
) {
    if (selectedImage != null) {
        Column() {


            AsyncImage(model = selectedImage, contentDescription = null)
            Text(text = selectedImage.toString())
        }
    } else
        Button(onClick = onImageClick) {
            Text(text = "Choose Image")
        }
}

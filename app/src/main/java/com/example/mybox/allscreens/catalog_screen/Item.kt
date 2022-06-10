package com.example.mybox.allscreens.catalog_screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mybox.allscreens.profile_screen.OneItemView
import com.example.mybox.allscreens.profile_screen.ProfileModel
import com.example.mybox.allscreens.profile_screen.parts.UploadAvatar
import com.example.mybox.data.remote.responses.JsonItem
import com.example.mybox.util.Constants.BASE_URL_MEDIA
import com.example.mybox.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun ItemScreen(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    userId: Int,
    youId: Int,
    itemId: Int,
    accessToken: String,
    viewModel: CatalogViewModel = hiltViewModel()
) {

    var item = remember {
        mutableStateOf<JsonItem?>(null)
    }
    val (flagCheckFavorite, setFlagCheckFavorite) = remember { mutableStateOf(false) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    runBlocking {
        when (val response = viewModel.getItem(userId, itemId, accessToken)) {
            is Resource.Success -> {
                item.value = response.data
            }
            is Resource.Error -> {
            }
        }
    }
    item.value?.let { it ->
        CreateOrderView(
            accessToken = accessToken,
            ownerId = it.id_user!!,
            itemId = itemId,
            userId = userId,
            showDialog = showDialog,
            fnShowDialog = setShowDialog,
            fnOrder = { scope.launch { scaffoldState.snackbarHostState.showSnackbar(it) } }
        )
    }
    Column() {
        item.value?.let { it ->
            OneItemView(
                fnButton = { /*TODO*/ },
                mediaUrl = it.url_media,
                status = it.status,
                title = it.title,
                description = it.description,
                price = it.price
            )
        }
        if (youId != userId) {

            runBlocking {
                val response = viewModel.getCheckFavorite(youId, itemId, accessToken)
                when (response) {
                    is Resource.Success -> {
                        setFlagCheckFavorite(response.data!!.check_flag)
                    }
                    is Resource.Error -> {
                        Log.e("Error: ", "check favorite")
                    }
                    is Resource.Loading -> {
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {

                FavoriteBatton(
                    flagCheckFavorite = flagCheckFavorite,
                    youId = youId,
                    itemId = itemId,
                    accessToken = accessToken,
                    setFlagCheckFavorite = { setFlagCheckFavorite(it) }
                )
                CreateOrder(setShowDialog)
            }


        }
    }
}

@Composable
fun CreateOrder(
    fnShowDialog: (Boolean) -> Unit,
) {
    OutlinedButton(
        onClick =
        {
            fnShowDialog(true)
        },
        border = BorderStroke(1.dp, Color.Red),
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingBasket,
            contentDescription = null,

            modifier = Modifier
                .padding(end = 4.dp)
                .background(Color.White)
        )
        Text(text = "Заказ")
    }
}

@Composable
fun FavoriteBatton(
    flagCheckFavorite: Boolean,
    youId: Int,
    itemId: Int,
    accessToken: String,
    setFlagCheckFavorite: (Boolean)->Unit,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    OutlinedButton(
        onClick =
        {
            if (flagCheckFavorite) {
                runBlocking {
                    when (viewModel.postDeleteFavorite(youId, itemId, accessToken)) {
                        is Resource.Success -> {
                            setFlagCheckFavorite(!flagCheckFavorite)
                        }
                        is Resource.Error -> {
                            Log.e("Error: ", "unfavorite")
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            } else {
                runBlocking {
                    when (viewModel.postAddFavorite(youId, itemId, accessToken)) {
                        is Resource.Success -> {
                            setFlagCheckFavorite(!flagCheckFavorite)
                        }
                        is Resource.Error -> {
                            Log.e("Error: ", "favorite")
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        },
        border = BorderStroke(1.dp, Color.Red),
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null,

            modifier = Modifier
                .padding(end = 4.dp)
                .background(if (flagCheckFavorite) Color.Red else Color.White)
        )
        Text(text = if (flagCheckFavorite) "Убрать" else "Избранное")
    }
}

@Composable
fun CreateOrderView(
    fnOrder: (String) -> Unit,
    accessToken: String,
    ownerId: Int,
    itemId: Int,
    userId: Int,
    showDialog: Boolean,
    fnShowDialog: (Boolean) -> Unit,
    viewModel: CatalogViewModel = hiltViewModel()
) {

    var description by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(onDismissRequest = {}) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(400.dp)
                    .height(250.dp)
                    .background(Color.White)
                    .padding(bottom = 7.dp)
            ) {
                Column() {

                    Text(text = "Создать заказ",fontSize = 18.sp)
                    Spacer(modifier = Modifier
                        .align(Alignment.End)
                        .height(8.dp))
                    TextField(
                        value = description,
                        onValueChange = {description = it},
                        placeholder = { Text("Описаие") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF0D47A1),
                            backgroundColor = Color(0xFFC3DAFF),
                            focusedLabelColor = Color(0xFF0D47A1),

                            ),
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .height(51.dp)

                    )
                    Spacer(modifier = Modifier
                        .height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Button(
                            modifier = Modifier
                                .background(Color.Black),
                            onClick = {fnShowDialog(false)})
                        {
                            Text(text = "Отмена")
                        }
                        Button(
                            modifier = Modifier
                                .background(Color.Black),
                            onClick = {
                                runBlocking {
                                    when(val response = viewModel.postCreateOrder(ownerId = ownerId, itemID = itemId, description = description, accessToken=accessToken)){
                                        is Resource.Success -> {
                                            Log.e("Order Create: ", "${response.data!!.id_order}")
                                            fnShowDialog(false)
                                            fnOrder("Заказ создан, ждите подтверждения")
                                        }
                                        is Resource.Error -> {
                                            Log.e("Error: ", " order create ${response.message}")
                                            fnOrder("Ошибка: ${response.message}")
                                        }
                                        is Resource.Loading -> {}
                                    }
                                }
                            })
                        {
                            Text(text = "Подтвердить")
                        }
                    }
                }
            }
        }
    }

}
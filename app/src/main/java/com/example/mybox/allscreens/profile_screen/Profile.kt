package com.example.mybox.allscreens.profile_screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mybox.allscreens.Screens
import com.example.mybox.allscreens.profile_screen.parts.AddView
import com.example.mybox.allscreens.profile_screen.parts.SelectScroll
import com.example.mybox.allscreens.profile_screen.parts.UploadAvatar
import com.example.mybox.allscreens.util_element.AddItem
import com.example.mybox.allscreens.util_element.AddPost
import com.example.mybox.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.*
import java.time.format.DateTimeFormatter






@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Profile(
    navController: NavController,
    viewModel: ProfileModel = hiltViewModel(),
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    userId: Int,
    accessToken: String
) {

    var bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val (showAddDialog, setShowAddDialog) = remember { mutableStateOf(false) }
    val (flagCatalogOrPosts, setFlagCatalogOrPosts) = remember { mutableStateOf(false) }


    var name by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var urlAvatar by remember { mutableStateOf("") }
    var flagIsYou by remember { mutableStateOf(false) }
    val (flagCreation, setFlagCreation) = remember { mutableStateOf(false) }
    val (flagCheckFollow, setFlagCheckFollow) = remember { mutableStateOf(false) }



    runBlocking {
        var responce = viewModel.getUser(userId = userId, accessToken = accessToken)
        when (responce) {
            is Resource.Success -> {
                name = responce.data!!.name
                mail = responce.data!!.mail
                username = responce.data!!.username
                urlAvatar = responce.data!!.url_avatar
                flagIsYou = responce.data!!.isyou
            }
            is Resource.Error -> {
                Log.e("Error: ", "getUser - ${responce.message!!}")
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Ошибка: не удалось загрузить профиль")
                }
            }
            else -> {}
        }
    }

    if (flagIsYou)

    else
        runBlocking {
            var response = viewModel.getCheckFollow(userId, accessToken)
            when (response) {
                is Resource.Success -> {
                    setFlagCheckFollow(response.data!!.check_flag)
                }
                is Resource.Error -> {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Ошибка: ${response.message}")
                    }
                }
                else -> {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Ошибка: Unknown error")
                    }
                }
            }
        }



    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ) {
        item() {
            UserCard(
                userId = userId,
                accessToken = accessToken,
                scope = scope,
                scaffoldState = scaffoldState,
                setShowAddDialog = { setShowAddDialog(!showAddDialog) },
                name = name,
                mail = mail,
                username = username,
                urlAvatar = urlAvatar,
                flagIsYou = flagIsYou,
                flagCreation = flagCreation,
                flagCheckFollow = flagCheckFollow,
                setFlagCheckFollow = setFlagCheckFollow
            )

            Spacer(modifier = Modifier.height(10.dp))


            SelectScroll({
                setFlagCreation(false)
                setFlagCatalogOrPosts(true)
                viewModel.changeFlag()
            },
                {
                    setFlagCreation(false)
                    setFlagCatalogOrPosts(false)
                    viewModel.changeFlag()
                })
            Spacer(modifier = Modifier.height(10.dp))
            if(flagIsYou && !flagCreation) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AddView { setFlagCreation( true) }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            if (flagCreation) {
                CreationBlock(
                    userId = userId,
                    accessToken = accessToken,
                    setFlagCreation = setFlagCreation,
                    scope = scope,
                    scaffoldState = scaffoldState,
                    flagCatalogOrPosts = flagCatalogOrPosts
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

        }
        item() {
            Box(modifier = Modifier.height(700.dp)) {
                Block(
                    userId = userId,
                    accessToken = accessToken,
                    flagCatalogOrPosts = flagCatalogOrPosts,
                    fnOpenItem = {navController.navigate(Screens.Item.withArgs(userId.toString(),it))}
                    )
            }

        }

    }

    DetailAddViewState(
        accessToken = accessToken,
        userId = userId,
        showDialog = showAddDialog,
        fnShowDialog = setShowAddDialog,
        bitmapSelected = bitmap
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreationBlock(
    userId: Int,
    accessToken: String,
    setFlagCreation: (Boolean)-> Unit,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    flagCatalogOrPosts: Boolean,
    viewModel: ProfileModel = hiltViewModel(),
) {

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("0") }



    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
        }

    var bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    imageUri?.let { it ->
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }


    if(flagCatalogOrPosts) {
        AddPost(
            bitmap = bitmap,
            description = description,
            fnDescription = { description = it },
            fnCancel = { setFlagCreation(false) },
            fnOpen = { launcher.launch("image/*") }) {
            runBlocking {
                var response = viewModel.postCreatePost(
                    userId,
                    accessToken,
                    bitmap.value,
                    description,
                    DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                )
                when (response) {
                    is Resource.Success -> {
                        setFlagCreation(false)
                        viewModel.changeFlag()
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Пост создан")
                        }
                    }
                    is Resource.Error -> {
                        Log.e("Error: ", "postPost - ${response.message!!}")
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Ошибка: ${response.message}")
                        }
                    }
                    is Resource.Loading -> {
                        Log.e("TEST: ", "add post is loading")
                    }
                }

            }
        }
    } else{
        AddItem(
            title = title,
            fnTitle = {title = it},
            description = description,
            price = price,
            fnPrice = {price = it},
            fnDescription = {description = it} ,
            fnOpen = { launcher.launch("image/*")},
            bitmap = bitmap,
            fnCancel = { setFlagCreation(false) }) {
              runBlocking {
                        var response = viewModel.postCreateItem(
                            userId = userId,
                            accessToken = accessToken,
                            title = title,
                            description = description,
                            price = price,
                            imageBitmap = bitmap.value,
                            //DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                        )
                        when (response) {
                            is Resource.Success -> {
                                setFlagCreation(false)
                                viewModel.changeFlag()
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Товар размещен")
                                }
                            }
                            is Resource.Error -> {
                                Log.e("Error: ", "postItem - ${response.message!!}")
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Ошибка: ${response.message}")
                                }
                            }
                            is Resource.Loading -> {
                                Log.e("TEST: ", "add item is loading")
                            }
                        }

                    }
        }
    }
}


@Composable
fun Block(
    userId: Int,
    accessToken: String,
    flagCatalogOrPosts: Boolean,
    viewModel: ProfileModel = hiltViewModel(),
    fnOpenItem: (String) -> Unit
) {

    var listItems by remember { viewModel.listItems }
    var listPosts by remember { viewModel.listPosts }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    var loadList by remember { viewModel.isloaded }

    if (flagCatalogOrPosts) {
        if (loadList) {
            listPosts.clear()
            runBlocking {
                viewModel.getUserPosts(userId = userId, accessToken = accessToken)
            }
            viewModel.changeFlag()
        }

        if (loadError == "") {
            if (!isLoading)
                PostsView(listPosts = listPosts)

        }
    } else{
        if (loadList) {
            listItems.clear()
            runBlocking {
                viewModel.getUserItems(userId = userId, accessToken = accessToken)
            }
            viewModel.changeFlag()
        }
        if (loadError == "") {
            if (!isLoading)
                CatalogView(listJsonItems = listItems, fnButton = {fnOpenItem(it)})

        }
    }
}


@Composable
fun UserCard(
    userId: Int,
    accessToken: String,
    scope: CoroutineScope,
    setShowAddDialog: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: ProfileModel = hiltViewModel(),
    name:String,
    mail: String,
    username: String,
    urlAvatar: String,
    flagIsYou: Boolean,
    flagCreation: Boolean,
    flagCheckFollow: Boolean,
    setFlagCheckFollow: (Boolean) -> Unit
) {
    Column(
        //modifier = Modifier.fillMaxSize()
    ) {
        ProfileView(
            name = name,
            username = username,
            mail = mail,
            urlAvatar = urlAvatar,
            flagCheckFollow = flagCheckFollow,
            fnAvatar = if (flagIsYou) setShowAddDialog else {
                {}
            },
            flagIsYou = flagIsYou,
            fnButton = {
                var response = if (flagCheckFollow) {
                    runBlocking {
                        viewModel.postUnFollow(
                            userId,
                            accessToken
                        )
                    }
                } else {
                    runBlocking {
                        viewModel.postFollow(
                            userId,
                            accessToken
                        )
                    }
                }
                when (response) {
                    is Resource.Success -> {
                        setFlagCheckFollow(!flagCheckFollow)
                    }
                    is Resource.Error -> {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Ошибка: ${response.message}")
                        }
                    }
                    is Resource.Loading -> {
                    }
                }

            },
            content = if (flagCheckFollow) "Отписаться" else "Подписаться"
        )
    }
}


@Composable
fun DetailAddViewState(
    accessToken: String,
    userId: Int,
    showDialog: Boolean,
    fnShowDialog: (Boolean) -> Unit,
    bitmapSelected: MutableState<Bitmap?>,
    viewModel: ProfileModel = hiltViewModel()
) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
        }
    if (showDialog) {
        imageUri?.let { it ->
            if (Build.VERSION.SDK_INT < 28) {
                bitmapSelected.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                bitmapSelected.value = ImageDecoder.decodeBitmap(source)
                bitmapSelected.value = ImageDecoder.decodeBitmap(source)
            }

        }

        UploadAvatar(
            bitmap = bitmapSelected.value,
            flagViewImage = bitmapSelected.value != null,
            fnButtonUpload = {
                bitmapSelected.value?.let { it ->
                    runBlocking {
                        viewModel.uploadImage(it, accessToken, userId)
                    }
                    fnShowDialog(false)
                }
            },
            fnButtonOpen = { launcher.launch("image/*") },
            fnButtonCancel = { fnShowDialog(false) }
        )
    }

}
package com.example.mybox.allscreens

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mybox.allscreens.catalog_screen.ItemScreen
import com.example.mybox.allscreens.chat_screen.Chat
import com.example.mybox.allscreens.line_screen.Line
import com.example.mybox.allscreens.menu_screen.Favorite
import com.example.mybox.allscreens.menu_screen.OrdersList
import com.example.mybox.allscreens.menu_screen.Service
import com.example.mybox.allscreens.menu_screen.YourOrder
import com.example.mybox.allscreens.notice_screen.NoticesList
import com.example.mybox.allscreens.profile_screen.Profile
import com.example.mybox.allscreens.sign_in_screen.SignIn
import com.example.mybox.allscreens.sign_up_screen.SignUp
import com.example.mybox.allscreens.start_screen.Start
import com.example.mybox.data.remote.responses.JsonUserId
import com.example.mybox.util.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    prefs: SharedPreferences,
    navController: NavHostController,
    viewModel: NavigationModel = hiltViewModel()
) {
    var accessToken by remember { mutableStateOf("") }
    var flagAuthorization by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var youId by remember {
        mutableStateOf(0)
    }

    var listNotice by remember { viewModel.listNotice }
    var newNotice by remember { viewModel.newNotice }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    var loadList by remember { viewModel.isloaded }

    var selectedScreen by remember { mutableStateOf(1) }

    accessToken = getToken(prefs)

    //saveToken("", prefs)

    Log.e(
        "TOKEN_PREFS: ",
        "token $accessToken"
    )


    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            if (accessToken != "")
                NavigationBar(
                    fnLine = {
                        selectedScreen = 1
                        navController.navigate(Screens.Line.route)
                    },
                    fnCatalog = {
                        selectedScreen = 2
                        navController.navigate(Screens.Catalog.route)
                    },
                    fnOrder = {
                        selectedScreen = 3
                        navController.navigate(Screens.Service.route)
                    },
                    fnProfile = {
                        var responce: Resource<JsonUserId>
                        runBlocking {
                            Log.e("TOOOOKEN", "df $accessToken")
                            responce = viewModel.postUserId(accessToken)
                        }

                        when (responce) {
                            is Resource.Success -> {
                                selectedScreen = 4
                                navController.navigate(Screens.Profile.withArgs(responce.data!!.id.toString()))
                            }
                            is Resource.Error -> {
                                Log.e("Error", "NavBar - ${responce.message}")
                            }
                            is Resource.Loading -> {
                            }
                        }

                    },
                    selectedScreen = selectedScreen
                )
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = Screens.Start.route
        )
        {
            composable(
                route = Screens.Start.route
            ) {
                accessToken = getToken(prefs)
                if (accessToken == "") {
                    selectedScreen = 1
                    Start(
                        fnButtonIn = { navController.navigate(Screens.SignIn.route) },
                        fnButtonUp = { navController.navigate(Screens.SignUp.route) })
                } else {
                    Column() {

                        listNotice.clear()
                        runBlocking {
                            viewModel.getNotices(accessToken = accessToken)
                        }
                        Log.e("Оповещения", "Новых оповещений $newNotice")
                        TopBar(
                            newNotice = newNotice,
                            fnNotice = { navController.navigate(Screens.Notices.route) }
                        )
                        Line(
                            accessToken = accessToken,
                            scaffoldState = scaffoldState,
                            scope = scope,
                            navController = navController
                        )
                    }
                }
            }
            composable(
                route = Screens.SignIn.route
            ) {
                SignIn(
                    fnCancel = { navController.navigate(Screens.Start.route) },
                    fnNavigate = { navController.navigate(Screens.Start.route) },
                    fnToken = { saveToken(it, prefs) })
            }
            composable(
                route = Screens.SignUp.route
            ) {
                SignUp(
                    fnCancel = { navController.navigate(Screens.Start.route) },
                    scope = scope,
                    scaffoldState = scaffoldState,
                    fnNavigate = { navController.navigate(Screens.SignIn.route) })
            }
            composable(
                route = Screens.Line.route
            ) {
                listNotice.clear()
                runBlocking {
                    viewModel.getNotices(accessToken = accessToken)
                }//TODO убрать потом
                Column() {
                    TopBar(
                        newNotice = newNotice,
                        fnNotice = { navController.navigate(Screens.Notices.route) }
                    )
                    Line(
                        accessToken = accessToken,
                        scaffoldState = scaffoldState,
                        scope = scope,
                        navController = navController
                    )
                }
            }
            composable(
                route = Screens.Catalog.route
            ) {
                Text(text = "Каталог")
                Text(text = "Заказы")
                Column() {
                    Text(text = "Профиль")
                    Button(onClick = {
                        saveToken("", prefs)
                        navController.navigate(Screens.Start.route)
                    }) {
                        Text("Выйти")
                    }
                }
            }
            composable(
                route = Screens.Service.route
            ) {
                Column(

                ) {
                    TopBar(
                        newNotice = newNotice,
                        fnNotice = { navController.navigate(Screens.Notices.route) }
                    )
                    Service(
                        accessToken = accessToken,
                        fnButtonFavorite = { navController.navigate(Screens.Favorite.route) },
                        fnButtonOrders = { navController.navigate(Screens.OrdersList.route) },
                        fnButtonNotice = { navController.navigate(Screens.Notices.route) },
                        fnButtonOrdersYou = { navController.navigate(Screens.OrdersYou.route) },
                        fnButtonExit = { saveToken("", prefs)
                            navController.navigate(Screens.Start.route) }
                    )
                }
            }
            composable(
                route = Screens.Favorite.route
            ) {
                Column() {
                    TopBar(
                        newNotice = newNotice,
                        fnNotice = { navController.navigate(Screens.Notices.route) }
                    )
                    Favorite(
                        accessToken = accessToken,
                        fnOpenItem = {
                            navController.navigate(
                                Screens.Item.withArgs(
                                    0.toString(),
                                    it
                                )
                            )
                        })
                }
            }
            composable(
                route = Screens.Profile.withArgs("{id}"),
                arguments = listOf(
                    navArgument("id")
                    {
                        type = NavType.IntType
                    })
            ) {
                val id = remember {
                    it.arguments?.getInt("id")
                }

                Profile(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    userId = id!!,
                    accessToken = accessToken,
                    navController = navController
                )

            }
            composable(
                route = Screens.Item.withArgs("{id}", "{id_item}"),
                arguments = listOf(
                    navArgument("id")
                    {
                        type = NavType.IntType
                    },
                    navArgument("id_item")
                    {
                        type = NavType.IntType
                    },
                )
            ) {
                val id = remember {
                    it.arguments?.getInt("id")
                }
                val itemId = remember {
                    it.arguments?.getInt("id_item")
                }
                runBlocking {
                    val response = viewModel.postUserId(accessToken)
                    when (response) {
                        is Resource.Success -> {
                            youId = response.data!!.id
                        }
                        is Resource.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Ошибка: ${response.message}")
                            }
                        }
                        is Resource.Loading -> {
                        }
                    }
                }

                ItemScreen(
                    userId = id!!,
                    youId = youId,
                    itemId = itemId!!,
                    accessToken = accessToken,
                    scope = scope,
                    scaffoldState = scaffoldState
                )
                //Text(text = "test $id")

            }

            composable(
                route = Screens.OrdersList.route
            ) {
                Column() {
                    TopBar(
                        newNotice = newNotice,
                        fnNotice = { navController.navigate(Screens.Notices.route) }
                    )
                    OrdersList(
                        accessToken = accessToken,
                        fnOpenItem = {
                            navController.navigate(
                                Screens.OrdersOne.withArgs(
                                    0.toString(),
                                    it
                                )
                            )
                        })
                }
            }
            composable(
                route = Screens.OrdersOne.withArgs("{id}", "{id_order}"),
                arguments = listOf(
                    navArgument("id")
                    {
                        type = NavType.IntType
                    },
                    navArgument("id_order")
                    {
                        type = NavType.IntType
                    },
                )
            ) {
                val id = remember {
                    it.arguments?.getInt("id")
                }
                val itemId = remember {
                    it.arguments?.getInt("id_order")
                }
                Text(text = "Тут детальная инфа о заказе")
            }
            composable(
                route = Screens.Notices.route
            ) {
                Column() {
                    NoticesList(
                        accessToken = accessToken,
                        listNotices = listNotice
                    )

                }
            }
            composable(
                route = Screens.OrdersYou.route
            ) {
                Column() {
                    TopBar(
                        newNotice = newNotice,
                        fnNotice = { navController.navigate(Screens.Notices.route) }
                    )
                    YourOrder(
                        accessToken = accessToken,
                        fnOpenItem = {
                            navController.navigate(
                                Screens.OrdersOne.withArgs(
                                    0.toString(),
                                    it
                                )
                            )
                        },
                        fnOpenChat = { navController.navigate(Screens.Chat.withArgs(it)) })
                }
            }
            composable(
                route = Screens.Chat.withArgs("{id}"),
                arguments = listOf(
                    navArgument("id")
                    {
                        type = NavType.IntType
                    })
            ) {

                val id = remember {
                    it.arguments?.getInt("id")
                }
                Chat(accessToken = accessToken, chatId = id!!, youId = youId)


            }
        }
    }
}



fun getToken(prefs: SharedPreferences) : String
{
    return prefs.getString("token", "")!!
}

fun saveToken(token: String, prefs: SharedPreferences)
{
    val editor = prefs.edit()
    editor.putString ("token", token).apply()
}
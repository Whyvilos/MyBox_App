package com.example.mybox.allscreens.menu_screen

import android.util.Log
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.runBlocking


@Composable
fun YourOrder(
    accessToken: String,
    fnOpenItem: (String) -> Unit,
    fnOpenChat: (String) -> Unit,
    viewModel: MenuViewModel = hiltViewModel()
) {


    var listOrders by remember { viewModel.listOrders }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    var loadList by remember { viewModel.isloaded }

    if (loadList) {
        listOrders.clear()
        runBlocking {
            viewModel.getOrdersForYou(accessToken = accessToken)
        }
        viewModel.changeFlag()
    }
    if (loadError == "") {
        if (!isLoading) {
            Log.e("your orders: ", "ex")
            OrdersShop(
                listOrders = listOrders,
                fnButton = { fnOpenItem(it) },
                accessToken = accessToken,
                fnOpenChat = {fnOpenChat(it)}
            )
        }
    }
}
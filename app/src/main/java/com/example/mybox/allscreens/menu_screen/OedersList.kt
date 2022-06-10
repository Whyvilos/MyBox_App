package com.example.mybox.allscreens.menu_screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mybox.allscreens.Screens
import com.example.mybox.allscreens.profile_screen.CatalogView
import kotlinx.coroutines.runBlocking


@Composable
fun OrdersList(
    accessToken: String,
    fnOpenItem: (String) -> Unit,
    viewModel: MenuViewModel = hiltViewModel()
) {

    var listOrders by remember { viewModel.listOrders }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    var loadList by remember { viewModel.isloaded }

    if (loadList) {
        listOrders.clear()
        runBlocking {
            viewModel.getOrders(accessToken = accessToken)
        }
        viewModel.changeFlag()
    }
    if (loadError == "") {
        if (!isLoading) {
            Log.e("orders: ", "ex")
            OrdersView(listOrders = listOrders, fnButton = { fnOpenItem(it) })
        }
    }
}

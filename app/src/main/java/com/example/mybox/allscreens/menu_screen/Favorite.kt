package com.example.mybox.allscreens.menu_screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mybox.allscreens.profile_screen.CatalogView
import kotlinx.coroutines.runBlocking


@Composable
fun Favorite(
    accessToken: String,
    fnOpenItem: (String) -> Unit,
    viewModel: MenuViewModel = hiltViewModel()
) {

    var listItems by remember { viewModel.listItems }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    var loadList by remember { viewModel.isloaded }

    if (loadList) {
        listItems.clear()
        runBlocking {
            viewModel.getFavorite(accessToken = accessToken)
        }
        viewModel.changeFlag()
    }
    if (loadError == "") {
        if (!isLoading) {
            Log.e("favorite: ", "ex")
            CatalogView(listJsonItems = listItems, fnButton = { fnOpenItem(it) })

        }
    }

}
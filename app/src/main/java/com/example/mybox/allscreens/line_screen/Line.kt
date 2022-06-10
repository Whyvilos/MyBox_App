package com.example.mybox.allscreens.line_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mybox.allscreens.Screens
import com.example.mybox.allscreens.profile_screen.PostsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking


@Composable
fun Line(
    accessToken: String,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: LineModel = hiltViewModel()
) {

    var lineList by remember {  viewModel.lineList}
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    var loadList by remember { viewModel.isloaded }

    if (loadList) {
        runBlocking {
            viewModel.getLine(accessToken = accessToken)

        }
        viewModel.changeFlag()
    }

    if(loadError==""){
        if(!isLoading)
            LineView(lineList = lineList, fnButton = {
                navController.navigate(Screens.Profile.withArgs("$it"))
            })


    }
}
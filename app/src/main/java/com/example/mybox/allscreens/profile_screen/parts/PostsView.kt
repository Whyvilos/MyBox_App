package com.example.mybox.allscreens.profile_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.mybox.allscreens.line_screen.OnePostView
import com.example.mybox.data.remote.responses.Post
import java.lang.reflect.Modifier


@Composable
fun PostsView(
    listPosts: List<Post>
) {

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(listPosts.size){
            Column() {
                OnePostView(
                    username = listPosts[it].username!!,
                    mediaUrl = listPosts[it].url_media,
                    description = listPosts[it].description,
                    time = listPosts[it].creation_time,
                    price = listPosts[it].price,
                    fnButton = {  }
                )
            }
        }
        items(1)
        {
            Spacer(modifier = androidx.compose.ui.Modifier.height(60.dp))
        }
    }
}



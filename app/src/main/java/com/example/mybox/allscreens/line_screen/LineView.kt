package com.example.mybox.allscreens.line_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mybox.data.remote.responses.Post


@Composable
fun LineView(
    lineList: List<Post>,
    fnButton: (String)->Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(lineList.size){
            Column() {
                OnePostView(username = lineList[it].username!!, mediaUrl = lineList[it].url_media,
                    description = lineList[it].description, time = lineList[it].creation_time, price = lineList[it].price, fnButton = {fnButton("${lineList[it].id_user}")})
            }
        }
        items(1)
        {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

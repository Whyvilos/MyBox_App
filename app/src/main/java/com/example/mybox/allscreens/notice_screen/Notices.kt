package com.example.mybox.allscreens.notice_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.mybox.allscreens.profile_screen.OneItemView
import com.example.mybox.data.remote.responses.JsonNotice
import com.example.mybox.util.Constants
import com.example.mybox.util.Resource
import kotlinx.coroutines.runBlocking


@Composable
fun NoticesList(
    accessToken: String,
    listNotices:  MutableList<JsonNotice>,
    viewModel: NoticeModel = hiltViewModel()
) {


    NoticesListView(listNotices)


    runBlocking {
        val response = viewModel.getCheckNotice(accessToken)
        when(response){
            is Resource.Success -> {}
            is Resource.Error -> {
                Log.e("Error: ", " check notices ${response.message}")}
            is Resource.Loading -> {}
        }
    }

}


@Composable
fun NoticesListView(
    listNotices: MutableList<JsonNotice>,
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(listNotices.size){
            NoticeView(
                fnButton = { },
                status = listNotices[it].status,
                content = listNotices[it].content
            )

        }
        items(1)
        {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun NoticeView(
    fnButton: ()->Unit,
    content: String,
    status: String

) {

    Box(
        modifier = Modifier.padding(5.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(Color.White)
                .clickable(onClick = fnButton)
        ) {

            Column(
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp
                    ),
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {



                    Text(text = content)
                    if(status == "new"){
                        Text(text = "New")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }


}
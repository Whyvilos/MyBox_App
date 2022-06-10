package com.example.mybox.allscreens.profile_screen

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
import coil.compose.AsyncImage
import com.example.mybox.data.remote.responses.JsonItem
import com.example.mybox.util.Constants


@Composable
fun CatalogView(
    fnButton: (String) -> Unit,
    listJsonItems: List<JsonItem>
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(listJsonItems.size){
            OneItemView(
                fnButton = { fnButton(listJsonItems[it].id_item.toString()) },
                mediaUrl = listJsonItems[it].url_media,
                status = listJsonItems[it].status,
                title = listJsonItems[it].title,
                description = listJsonItems[it].description,
                price = listJsonItems[it].price
            )
        }
        items(1)
        {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun OneItemView(
    fnButton: () -> Unit,
    mediaUrl: String?,
    status: String,
    title: String,
    description: String,
    price: Int?
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
                Spacer(modifier = Modifier.height(5.dp) )
                mediaUrl?.let { it ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)

                    ) {
                        AsyncImage(
                            model = Constants.BASE_URL_MEDIA + it,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )


                    }
                }
                Spacer(modifier = Modifier.height(5.dp) )
                Text(text = title)
                Spacer(modifier = Modifier.height(5.dp) )
                Text(text = description)
                Spacer(modifier = Modifier.height(5.dp) )
                Text(text = status)
                Spacer(modifier = Modifier.height(5.dp) )
                price?.let { it->
                    Text(text = "Стоимость ${it}")
                }
            }
        }
    }
}
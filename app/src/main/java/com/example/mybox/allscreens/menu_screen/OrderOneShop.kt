package com.example.mybox.allscreens.menu_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mybox.data.remote.responses.JsonOrdersItem
import com.example.mybox.util.Constants
import com.example.mybox.util.Resource
import kotlinx.coroutines.runBlocking


@Composable
fun OrdersShop(
    fnButton: (String) -> Unit,
    fnOpenChat: (String) -> Unit,
    listOrders: List<JsonOrdersItem>,
    accessToken: String,
    viewModel: MenuViewModel = hiltViewModel(),
) {

    val (flagStatusNew, setFlagStatusNew) = remember { mutableStateOf(true) }

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(listOrders.size){
            OrderOneShop(
                fnButton = { fnButton(listOrders[it].id_order.toString()) },
                mediaUrl = listOrders[it].url_media,
                status = listOrders[it].status,
                title = listOrders[it].title,
                description = listOrders[it].description,
                price = listOrders[it].price,
                flagStatusNew = flagStatusNew,
                fnCheckOrder = {
                    runBlocking {
                        viewModel.putOrderStatus(
                            accessToken = accessToken,
                            orderId = listOrders[it].id_order,
                            status = "check"
                        )
                        setFlagStatusNew(false)
                    }
                },
                    fnDropOrder = {
                        runBlocking {
                            viewModel.putOrderStatus(
                                accessToken = accessToken,
                                orderId = listOrders[it].id_order,
                                status = "drop"
                            )
                        }
                        setFlagStatusNew(false)
                    },
                fnOpenChat = {
                    runBlocking {
                        val response = viewModel.getFindChat1(
                            accessToken = accessToken,
                            orderId = listOrders[it].id_order
                        )
                        when(response){
                            is Resource.Success -> {
                                fnOpenChat(response.data!!.id.toString())
                            }
                            is Resource.Error -> {
                                Log.e("Error: ", "${response.message}")
                            }
                        }
                    }

                }

            )
        }
        items(1)
        {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}


@Composable
fun StatusControl(
    fnDropOrder: ()-> Unit,
    fnCheckOrder: ()->Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        
        Button(onClick = fnDropOrder) {
            Text(text = "Отклонить")
            
        }
        Button(onClick = fnCheckOrder) {
            Text(text = "Открыть заказ")
        }
        
    }

}

@Composable
fun OrderOneShop(
    flagStatusNew: Boolean,
    fnButton: () -> Unit,
    mediaUrl: String?,
    status: String,
    title: String,
    description: String,
    price: Int?,
    fnOpenChat: () -> Unit,
    fnDropOrder: ()->Unit,
    fnCheckOrder: ()->Unit

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
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp
                    ),
            ) {
                Spacer(modifier = Modifier.height(5.dp))
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
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = title)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = description)
                Spacer(modifier = Modifier.height(5.dp))
                price?.let { it ->
                    Text(text = "Стоимость ${it}р")
                    Spacer(modifier = Modifier.height(5.dp))
                }
                Text(
                    text = "Статус:${
                        when (status) {
                            "new" -> "новый заказ"
                            "check" -> "открыт"
                            "drop" -> "отказан"
                            "taken" -> "взят"
                            "done" -> "выполнен"
                            else -> "неизвестно"
                        }
                    }"
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (status == "check" || status == "taken" || !flagStatusNew) {
                    Button(onClick = fnOpenChat) {
                        Text(text = "Открыть чат")
                    }
                } else {
                    StatusControl(fnDropOrder = fnDropOrder, fnCheckOrder = fnCheckOrder)
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}
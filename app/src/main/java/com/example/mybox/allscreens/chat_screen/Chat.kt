package com.example.mybox.allscreens.chat_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mybox.allscreens.profile_screen.OneItemView
import com.example.mybox.data.remote.responses.JsonChat
import com.example.mybox.data.remote.responses.JsonMessage
import com.example.mybox.util.Constants
import com.example.mybox.util.Resource
import kotlinx.coroutines.runBlocking


@Composable
fun Chat(
    accessToken: String,
    chatId: Int,
    youId: Int,
    viewModel: ChatViewModel = hiltViewModel()
) {

    var chat by remember { mutableStateOf<JsonChat?>(null) }

    runBlocking {
        val response = viewModel.getMessages(accessToken = accessToken, chatId = chatId)
        when (response) {
            is Resource.Success -> {
                chat = response.data
            }
            is Resource.Error -> {
                Log.e("Error: ", "get chat ${response.message}")
            }
            is Resource.Loading -> {
            }
        }
    }

    Column() {


        chat?.let { it ->
            TopBarChat(
                name = if(it.users[0].id_user==youId) it.users[1].name else it.users[0].name,
                urlAvatar = "",
                fnProfile = {})
            ChatSpace(youId = youId, messages = it.messages)
            Spacer(modifier = Modifier.width(10.dp))

        }

    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxHeight()
    ) {


        SendSpace(
            fnSend = {
                runBlocking {
                    val response = viewModel.postSendMessage(accessToken = accessToken, chatId = chatId, content = it)
                    when(response){
                        is Resource.Success ->{
                            Log.e("Message-id: ", "${response.data!!.id}")
                            runBlocking {
                                val response = viewModel.getMessages(accessToken = accessToken, chatId = chatId)
                                when (response) {
                                    is Resource.Success -> {
                                        chat = response.data
                                    }
                                    is Resource.Error -> {
                                        Log.e("Error: ", "get chat ${response.message}")
                                    }
                                    is Resource.Loading -> {
                                    }
                                }
                            }
                        }
                        is Resource.Error ->{
                            Log.e("Message-id: ", "Ошибка ${response.message}")
                        }
                        is Resource.Loading ->{}
                    }
                }
            }
        )
    }
}


@Composable
fun ChatSpace(
    youId: Int,
    messages: List<JsonMessage>
) {
    val listState = rememberLazyListState()
    runBlocking {
        listState.scrollToItem(messages.size)
    }
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(10.dp)
    ){
        items(messages.size){
            Spacer(modifier = Modifier.height(10.dp))
            MessageOne(
                flagYourOrNot = youId==messages[it].id_user,
                message = messages[it]
            )

        }
        item{
            Spacer(modifier = Modifier.height(65.dp))
        }
    }
}


@Composable
fun MessageOne(
    flagYourOrNot: Boolean,
    message: JsonMessage
) {

    Row(
        horizontalArrangement = if (flagYourOrNot) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp)

    ) {

            Text(text = message.content)
        }
    }
}



@Composable
fun SendSpace(
    fnSend:(String)->Unit
) {
    var message by remember { mutableStateOf("") }
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Gray)
        .height(60.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { if (message == "") "Введите сообщение..." },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF0D47A1),
                    backgroundColor = Color(0xFFC3DAFF),
                    focusedLabelColor = Color(0xFF0D47A1),
                ),
                modifier = Modifier
                    .height(55.dp)
                    .weight(8f)

            )
            Button(
                onClick = {
                    fnSend(message)
                    message = ""
                },
                modifier = Modifier.weight(2f)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }

}

@Composable
fun TopBarChat(
    name: String,
    urlAvatar: String,
    fnProfile: ()->Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Gray)
        .height(60.dp)){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =  Arrangement.SpaceAround
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clickable(onClick = fnProfile)
                    .fillMaxHeight()
            ){
                Row() {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)

                    ) {
                        AsyncImage(
                            model = if (urlAvatar != "") Constants.BASE_URL_MEDIA + urlAvatar else Constants.BASE_URL_MEDIA + "default-avatar.png",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight()
                    ) {

                        Text(text = name, color = Color.White)
                    }
                }

            }
        }
    }
}

package com.example.mybox.allscreens.profile_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mybox.allscreens.profile_screen.parts.FollowView
import com.example.mybox.util.Constants.BASE_URL_MEDIA
import com.example.mybox.util.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileView(
    name: String,
    username: String,
    urlAvatar: String,
    flagCheckFollow: Boolean,
    fnAvatar: ()->Unit,
    flagIsYou: Boolean,
    fnButton: ()->Unit,
    content: String,
    mail: String
) {

    Log.e("Ima^  ", BASE_URL_MEDIA + urlAvatar)

    Box(
        modifier = Modifier.padding(5.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(Color.White)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp
                    ),
            ) {
                Spacer(modifier = Modifier.height(20.dp) )
                Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(90.dp)

                ) {
                    AsyncImage(
                        model = if (urlAvatar != "") BASE_URL_MEDIA + urlAvatar else BASE_URL_MEDIA + "default-avatar.png",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(onClick = fnAvatar)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp) )
                Text(text = name)
                Spacer(modifier = Modifier.height(5.dp) )
                Text(text = "@$username")
                Spacer(modifier = Modifier.height(5.dp) )
                Text(text = mail)
                Spacer(modifier = Modifier.height(5.dp) )
                if (!flagIsYou) FollowView(
                    fnButton = fnButton,
                    content = content
                )
                Spacer(modifier = Modifier.height(20.dp) )
            }
        }
    }
}

@Preview
@Composable
fun Test() {
    ProfileView(
        name = "СкупиШоп",
        username = "shopmega",
        urlAvatar = "http://192.168.1.65:8181/upload-2217663434.png",
        flagCheckFollow = true,
        fnAvatar = { /*TODO*/ },
        mail = "vk@vk.com",
        flagIsYou = false,
        fnButton = {},
        content = "Подписаться"
    )
}


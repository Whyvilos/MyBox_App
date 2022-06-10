package com.example.mybox.allscreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavigationBar(
    fnLine: () -> Unit,
    fnCatalog: () -> Unit,
    fnOrder: () -> Unit,
    fnProfile: () -> Unit,
    selectedScreen: Int
) {

    BottomNavigation(
        backgroundColor = Color(0xFF0D47A1),
        contentColor = Color(0xFFFFFFFF)

    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = null) },
            label = { Text("Лента") },
            selected = (selectedScreen == 1),
            onClick = fnLine
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Shop, contentDescription = null) },
            label = { Text("Каталог") },
            selected = (selectedScreen == 2),
            onClick = fnCatalog
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Backpack, contentDescription = null) },
            label = { Text("Сервис") },
            selected = (selectedScreen == 3),
            onClick = fnOrder
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Профиль") },
            selected = (selectedScreen == 4),
            onClick = fnProfile
        )
    }
}

@Composable
fun TopBar(
    newNotice: Int,
    fnNotice: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF0D47A1))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = "MyBox", color = Color.White)
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,

            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                    .clickable(onClick = fnNotice)
            ) {
                if (newNotice != 0) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier
                            .size(22.dp)
                            .background(Color.Transparent)
                            .shadow(
                                elevation = 10.dp,
                                shape = CircleShape,
                                clip = true
                            )
                        //.clickable(onClick = fnButton)

                    ) {
                        Box(
                            modifier = Modifier
                                .padding()
                                .background(Color.Magenta),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "$newNotice", fontSize = 14.sp, color = Color.White)
                        }
                    }

                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(

                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = if (newNotice == 0) Color.White else Color.White,
                            modifier = Modifier
                                .padding(end = 4.dp)

                                .size(30.dp)
                        )
                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun Prew2() {
    var x by remember { mutableStateOf(1) }
    NavigationBar(fnLine = {}, fnCatalog = { x = 2 }, fnOrder = { x = 3 }, fnProfile = {}, selectedScreen = x)
}
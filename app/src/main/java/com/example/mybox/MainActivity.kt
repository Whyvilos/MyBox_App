package com.example.mybox

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.mybox.allscreens.Navigation
import com.example.mybox.ui.theme.MyBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBoxTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                var prefs: SharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE)
                Navigation(navController = navController, prefs = prefs)
            }
        }
    }
}


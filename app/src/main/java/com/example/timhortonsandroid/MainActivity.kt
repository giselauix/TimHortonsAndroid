package com.example.timhortonsandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.timhortonsandroid.ui.HomeScreen
import com.example.timhortonsandroid.ui.theme.TimHortonsAndroidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TimHortonsAndroidTheme {
                HomeScreen()
                }
            }
        }
    }

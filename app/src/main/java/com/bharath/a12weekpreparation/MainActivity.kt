package com.bharath.a12weekpreparation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.bharath.a12weekpreparation.ui.theme._12WeekPreparationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _12WeekPreparationTheme {
                val navHostController = rememberNavController()
                MyNavHost(navHostController = navHostController)
            }
        }
    }
}




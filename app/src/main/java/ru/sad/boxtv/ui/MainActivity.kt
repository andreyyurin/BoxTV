package ru.sad.boxtv.ui

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.ExperimentalTvMaterial3Api
import ru.sad.boxtv.ui.feature.tv.TVScreen
import ru.sad.boxtv.ui.theme.BoxTVTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
private fun App(
    navController: NavHostController = rememberNavController(),
) = BoxTVTheme {
    NavHost(
        navController = navController,
        startDestination = TvScreen,
    ) {
        composable<TvScreen> {
            TVScreen(navController = navController)
        }
    }
}
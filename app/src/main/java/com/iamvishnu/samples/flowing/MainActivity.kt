package com.iamvishnu.samples.flowing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iamvishnu.samples.flowing.ui.details.DetailsScreen
import com.iamvishnu.samples.flowing.ui.home.HomeScreen
import com.iamvishnu.samples.flowing.ui.theme.FlowingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {

                        composable("home") {

                            HomeScreen(
                                onDetails = {
                                    navController.navigate("details/$it")
                                }
                            )

                        }

                        composable(
                            "details/{itemId}",
                            arguments = listOf(
                                navArgument("itemId") { type = NavType.StringType }
                            )
                        ) {

                            DetailsScreen(
                                id = it.arguments?.getString("itemId") ?: "",
                            )

                        }

                    }

                }
            }
        }
    }
}


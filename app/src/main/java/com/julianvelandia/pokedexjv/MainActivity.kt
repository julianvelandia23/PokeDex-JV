package com.julianvelandia.pokedexjv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.julianvelandia.pokedexjv.ui.theme.PokeDexJVTheme
import com.julianvelandia.presentation.NavArguments
import com.julianvelandia.presentation.composable.detail.DetailsScreen
import com.julianvelandia.presentation.composable.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeDexJVTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController, startDestination =  Route.HOME
                    ) {
                        composable(Route.HOME) {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                navigateTo = { route ->
                                    navController.navigate(Route.getDetailsRoute(route))
                                }
                            )
                        }

                        composable(
                            route = Route.DETAILS,
                            arguments = listOf(
                                navArgument(NavArguments.POKEMON_NAME) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            DetailsScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}
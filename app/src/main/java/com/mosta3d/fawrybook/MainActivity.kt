package com.mosta3d.fawrybook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mosta3d.fawrybook.auth.composable.LoginScreen
import com.mosta3d.fawrybook.auth.composable.SignupScreen
import com.mosta3d.fawrybook.chat.composables.ChatScreen
import com.mosta3d.fawrybook.partner.composable.AddPartnerScreen
import com.mosta3d.fawrybook.profile.ProfileScreen
import com.mosta3d.fawrybook.shared.state.AppState
import com.mosta3d.fawrybook.ui.theme.FawrybookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FawrybookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding

                    AppContainer()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FawrybookTheme {
        Greeting("Android")
    }
}

@Composable
fun AppContainer() {
    val authData by AppState.auth.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    var startingRoute = "login"

    LaunchedEffect(Unit) {
        startingRoute = if (authData.isLoggedIn) "chat" else "login"
    }

    LaunchedEffect(authData) {
        Log.i("sha3bolla", "authData.isLoggedIn: ${authData.isLoggedIn}")
        if (!authData.isLoggedIn) {
            navController.navigate("login") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startingRoute,
        modifier = Modifier
    ){
        composable(route = "login") {
            LoginScreen(navController = navController)
        }
        composable(route = "signup") {
            SignupScreen(navController = navController)
        }
        composable(route = "add-partner") {
            AddPartnerScreen(navController = navController)
        }
        composable(route = "profile") {
            ProfileScreen(navController = navController)
        }
        composable(route = "posts") {
            PostsScreen(navController = navController)
        }
        composable(route = "selectedPost") {
            SelectedPostScreen(navController = navController)
        }
    }
}
package com.shifthackz.joyreactor.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shifthackz.joyreactor.presentation.navigation.Route
import com.shifthackz.joyreactor.presentation.ui.screen.test.TestScreen
import com.shifthackz.joyreactor.presentation.ui.theme.JoyYouTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            JoyYouTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.TEST.value,
                    ) {
                        composable(Route.TEST.value) {
                            TestScreen(koinViewModel()).Build()
                        }
                    }
                }
            }
        }
    }
}

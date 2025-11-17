package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unlam.mobile.scaffolding.domain.classes.ShakeDetectorComposable
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.editUser.EditProfileScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.feed.FeedScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.introduction.IntroductionScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.map.MapboxScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.CreateNewsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.ProfessionalProfileScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.profile.ProfileScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.review.ReviewScreen
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import com.mapbox.common.MapboxOptions
import dagger.hilt.android.AndroidEntryPoint

const val MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiZmFja3U5NSIsImEiOiJjbWhucDNsNW0wMnp1Mmtwemg1dGNyb2Z1In0.dGrMielTiHaXoWTd38nYUQ"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapboxOptions.accessToken = MAPBOX_ACCESS_TOKEN
        setContent {
            ScaffoldingV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun ViewMainScreen() {
    MainScreen()
}

@Composable
fun MainScreen() {
    // Controller es el elemento que nos permite navegar entre pantallas. Tiene las acciones
    // para navegar como naviegate y también la información de en dónde se "encuentra" el usuario
    // a través del back stack
    val controller = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val currentRoute =
        controller
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    @Suppress("ktlint:standard:property-naming")
    val MAPA_ROUTE: String = "map"
    ShakeDetectorComposable(
        onShake = {
            controller.navigate(MAPA_ROUTE) {
                launchSingleTop = true
            }
        },
    )

    Scaffold(
        bottomBar = {
            if (currentRoute != "introduction") {
                BottomBar(controller = controller)
            }
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                // custom snackbar with the custom action button color and border
                val isError = (data.visuals as? SnackbarVisualsWithError)?.isError ?: false
                val buttonColor =
                    if (isError) {
                        ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.error,
                        )
                    } else {
                        ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.inversePrimary,
                        )
                    }

                Snackbar(
                    modifier =
                        Modifier
                            .border(2.dp, MaterialTheme.colorScheme.secondary)
                            .padding(12.dp),
                    action = {
                        TextButton(
                            onClick = { if (isError) data.dismiss() else data.performAction() },
                            colors = buttonColor,
                        ) {
                            Text(data.visuals.actionLabel ?: "")
                        }
                    },
                ) {
                    Text(data.visuals.message)
                }
            }
        },
    ) { paddingValue ->
        // NavHost es el componente que funciona como contenedor de los otros componentes que
        // podrán ser destinos de navegación.
        NavHost(navController = controller, startDestination = "feed") {
            // composable es el componente que se usa para definir un destino de navegación.
            // Por parámetro recibe la ruta que se utilizará para navegar a dicho destino.
            composable("home") {
                HomeScreen(
                    modifier = Modifier.padding(paddingValue),
                    navController = controller,
                )
            }
            composable(
                route = "professional/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""

                ProfessionalProfileScreen(
                    modifier = Modifier.padding(paddingValue),
                    viewModel = hiltViewModel(backStackEntry),
                    navController = controller,
                )
            }

            composable(
                route = "createNews/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""

                CreateNewsScreen(
                    modifier = Modifier.padding(paddingValue),
                    profileViewModel = hiltViewModel(backStackEntry),
                    onPublishSuccess = {
                        controller.navigate("feed") {
                            popUpTo("createNews/{id}") { inclusive = true }
                        }
                    },
                )
            }
            composable("feed") {
                FeedScreen(
                    modifier = Modifier.padding(paddingValue),
                    navController = controller,
                )
            }
            composable("editUser") {
                EditProfileScreen(
                    modifier = Modifier.padding(paddingValue),
                    navController = controller,
                )
            }
            composable(
                route = "review/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""

                ReviewScreen(
                    modifier = Modifier.padding(paddingValue),
                    reviewViewModel = hiltViewModel(backStackEntry),
                    navController = controller,
                )
            }

            composable("form") {
                /*
                 * FormScreen(
                    modifier = Modifier.padding(paddingValue),
                    snackbarHostState = snackBarHostState,
                )
                 * */
                ProfileScreen(
                    modifier = Modifier.padding(paddingValue),
                    navController = controller,
                )
            }
            composable("introduction") {
                IntroductionScreen(
                    { controller.navigate("feed") },
                    { controller.navigate("feed") },
                    modifier = Modifier.padding(bottom = 50.dp),
                )
            }
            composable(
                route = "user/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: "1"
                UserScreen(userId = id, modifier = Modifier.padding(paddingValue))
            }

            composable(route = "map") {
                MapboxScreen(modifier = Modifier.padding(paddingValue))
            }
        }
    }
}

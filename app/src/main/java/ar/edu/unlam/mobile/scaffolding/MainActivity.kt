package ar.edu.unlam.mobile.scaffolding

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.feed.FeedScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.introduction.IntroductionScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.ProfessionalProfileScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.profile.ProfileScreen
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingV2Theme {
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
    val context = LocalContext.current
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    var hasLocationPermission by remember {
        mutableStateOf(
            locationPermissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED },
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            hasLocationPermission = permissions.values.reduce { acc, isGranted -> acc && isGranted }
        },
    )

    if (hasLocationPermission) {
        AppContent()
    } else {
        PermissionRequestScreen(onPermissionRequested = { launcher.launch(locationPermissions) })
    }
}

@Composable
fun PermissionRequestScreen(onPermissionRequested: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Permiso de Ubicación Requerido",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Esta aplicación necesita acceso a tu ubicación para notificarte cuando estés cerca de un profesional.",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onPermissionRequested) {
            Text("Conceder Permiso")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null),
            )
            context.startActivity(intent)
        }) {
            Text(
                text = "Si el diálogo no aparece, pulsa aquí para ir a los ajustes y activarlo manualmente.",
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun AppContent() {
    val controller = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val currentRoute =
        controller
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "introduction") {
                BottomBar(controller = controller)
            }
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
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
        NavHost(navController = controller, startDestination = "introduction") {
            composable("home") {
                HomeScreen(
                    modifier = Modifier.padding(paddingValue),
                    navController = controller,
                )
            }
            composable(
                route = "professional/{dni}",
                arguments = listOf(navArgument("dni") { type = NavType.IntType }),
            ) { navBackStackEntry ->
                val dni = navBackStackEntry.arguments?.getInt("dni") ?: 0
                ProfessionalProfileScreen(
                    dni = dni,
                    modifier = Modifier.padding(paddingValue),
                )
            }
            composable("feed") {
                FeedScreen(
                    modifier = Modifier.padding(paddingValue),
                    onServiceRequest = {},
                )
            }
            composable("form") {
                ProfileScreen(45755878, modifier = Modifier.padding(paddingValue))
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
        }
    }
}

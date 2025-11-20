package ar.edu.unlam.mobile.scaffolding.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ProfileHeader
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val reviewUiState by reviewViewModel.uiState.collectAsState()
    var selectedRating by remember { mutableIntStateOf(0) }
    var writtenReview by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Reseña",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }

        HorizontalDivider()

        // Service Provider Info
        ProfileHeader(
            modifier = Modifier,
            name = reviewUiState.profileOwner?.name ?: "",
            profession = reviewUiState.profileOwner?.profession ?: "",
            rating = reviewUiState.profileOwner?.rating ?: 1.0,
            isProfileHV = false,
            imgUrl = reviewUiState.profileOwner?.imgUrl ?: "",
        )

        // Divider
        HorizontalDivider()

        Spacer(modifier = Modifier.height(20.dp))

        // Rating Section
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
        ) {
            Text(
                text = "Compartí tu experiencia...",
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Star Rating
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                for (i in 1..5) {
                    RatingStar(
                        isSelected = i <= selectedRating,
                        onClick = { selectedRating = i },
                        modifier =
                            Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Written Review Section
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
        ) {
            Text(
                text = "Agregar una reseña escrita",
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = writtenReview,
                onValueChange = { writtenReview = it },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                placeholder = {
                    Text(
                        text = "Redacta tu experiencia...",
                        color = MaterialTheme.colorScheme.outline,
                    )
                },
                shape = RoundedCornerShape(8.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Submit Button
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
        ) {
            Button(
                onClick = {
                    reviewViewModel.submitReview(
                        stars = selectedRating,
                        message = writtenReview,
                        onSuccess = {
                            navController.popBackStack()
                        },
                        onError = { error ->
                            // Error manejado automáticamente por el ViewModel
                        },
                    )
                    navController.popBackStack()
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LightPrimary),
                shape = RoundedCornerShape(12.dp),
                enabled = selectedRating > 0 && writtenReview.isNotBlank() && !reviewUiState.isSubmitting,
            ) {
                if (reviewUiState.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text(
                        text = "Enviar Reseña",
                        style =
                            MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White,
                            ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Skip Button
            TextButton(
                onClick = { navController.popBackStack() },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Saltar por ahora",
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            color = LightPrimary,
                        ),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun RatingStar(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .selectable(
                    selected = isSelected,
                    onClick = onClick,
                ),
        contentAlignment = Alignment.Center,
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Start",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(45.dp).padding(0.dp),
            )
        } else {
            Icon(
                imageVector = Icons.Default.StarBorder,
                contentDescription = "Start",
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(45.dp).padding(0.dp),
            )
        }
    }
}

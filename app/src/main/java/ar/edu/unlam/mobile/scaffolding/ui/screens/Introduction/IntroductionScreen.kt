package ar.edu.unlam.mobile.scaffolding.ui.screens.introduction // <- 'i' minúscula

// Importaciones corregidas
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int,
)

@Preview
@Composable
fun ViewIntroductionScreen() {
    IntroductionScreen({}, {})
}

@Composable
fun IntroductionScreen(
    onLoginClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentPage by remember { mutableStateOf(0) }

    val pages =
        listOf(
            OnboardingPage(
                title = "Encontrá profesionales\ncerca tuyo",
                description =
                    "Buscá y descubrí expertos en distintas áreas —" +
                        "desde servicios del hogar hasta asesorías profesionales— " +
                        "y conocé su ubicación con un solo toque en el mapa.",
                imageRes = R.drawable.onboarding_1,
            ),
            OnboardingPage(
                title = "Coordiná servicios\nde forma sencilla",
                description =
                    "Visualizá la disponibilidad de cada profesional, " +
                        "organizá tus citas según tu horario " +
                        "y contactá fácilmente sin intermediarios ni demoras.",
                imageRes = R.drawable.onboarding_2,
            ),
            OnboardingPage(
                title = "Conexiones seguras\ny confiables",
                description =
                    "Accedé a perfiles verificados y datos actualizados. " +
                        "Activá la ubicación para que el profesional sepa cómo llegar " +
                        "o encontrá vos la mejor ruta hasta su dirección.",
                imageRes = R.drawable.onboarding_3,
            ),
        )

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        // Header
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            if (currentPage < pages.size - 1) {
                TextButton(onClick = {
                    onLoginClick()
                }) {
                    Text("Skip", color = MaterialTheme.colorScheme.primary, modifier = Modifier.height(20.dp))
                }
            } else {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }

        // Contenido animado
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedContent(
                targetState = currentPage,
                transitionSpec = {
                    if (targetState > initialState) {
                        // Desplazamiento hacia izquierda (siguiente)
                        slideInHorizontally(
                            animationSpec = tween(durationMillis = 300),
                            initialOffsetX = { fullWidth -> fullWidth },
                        ) togetherWith
                            slideOutHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                targetOffsetX = { fullWidth -> -fullWidth },
                            )
                    } else {
                        // Desplazamiento hacia derecha
                        slideInHorizontally(
                            animationSpec = tween(durationMillis = 300),
                            initialOffsetX = { fullWidth -> -fullWidth },
                        ) togetherWith
                            slideOutHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                targetOffsetX = { fullWidth -> fullWidth },
                            )
                    }
                },
            ) { page ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(24.dp, top = 12.dp, 24.dp, 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = pages[page].imageRes),
                        contentDescription = "Onboarding image ${page + 1}",
                        modifier =
                            Modifier
                                .weight(0.7f)
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        contentScale = ContentScale.Fit,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = pages[page].title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = pages[page].description,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            lineHeight = 24.sp,
                        )
                    }
                }
            }
        }

        // Footer
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Indicadores
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(pages.size) { index ->
                    IndicatorDot(selected = index == currentPage)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (currentPage < pages.size - 1) {
                        currentPage++
                    } else {
                        onLoginClick()
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = if (currentPage < pages.size - 1) "Siguiente" else "Comenzar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
fun IndicatorDot(selected: Boolean) {
    val size by animateDpAsState(
        targetValue = if (selected) 32.dp else 8.dp,
        animationSpec = tween(durationMillis = 300),
    )

    Box(
        modifier =
            Modifier
                .height(8.dp)
                .width(size)
                .clip(CircleShape)
                .background(
                    if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    },
                ),
    )
}

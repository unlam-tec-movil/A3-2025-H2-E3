package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.theme.BottonColorProfessional
import ar.edu.unlam.mobile.scaffolding.ui.theme.TextBottomColorProfessional

@Preview
@Composable
fun ViewActionButtons() {
    ActionButtons({}, {}, {}, {})
}

@Composable
fun ActionButtons(
    onHowToGetThere: () -> Unit,
    onCall: () -> Unit,
    onWhatsApp: () -> Unit,
    onRegisterWork: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
    ) {
        // Primera fila de botones
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Botón Cómo llegar
            Button(
                onClick = onHowToGetThere,
                shape = RectangleShape,
                modifier =
                    Modifier
                        .size(80.dp)
                        .weight(1f)
                        .padding(end = 2.dp)
                        .clip(RoundedCornerShape(10.dp)),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = BottonColorProfessional,
                        contentColor = Color.White,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Default.Directions,
                        contentDescription = "Cómo llegar",
                        modifier = Modifier.size(30.dp),
                        tint = TextBottomColorProfessional,
                    )
                    Text(
                        "Cómo llegar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }

            // Botón Llamar
            Button(
                onClick = onCall,
                shape = RectangleShape,
                modifier =
                    Modifier
                        .size(80.dp)
                        .weight(1f)
                        .padding(start = 2.dp)
                        .clip(RoundedCornerShape(10.dp)),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = BottonColorProfessional,
                        contentColor = Color.White,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Llamar",
                        modifier = Modifier.size(30.dp),
                        tint = TextBottomColorProfessional,
                    )
                    Text(
                        "Llamar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Segunda fila de botones
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Botón WhatsApp
            Button(
                onClick = onWhatsApp,
                shape = RectangleShape,
                modifier =
                    Modifier
                        .size(80.dp)
                        .weight(1f)
                        .padding(end = 2.dp)
                        .clip(RoundedCornerShape(10.dp)),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = BottonColorProfessional,
                        contentColor = Color.White,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_whatsapp),
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(25.dp),
                        tint = TextBottomColorProfessional,
                    )
                    Text(
                        "WhatsApp",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }

            // Botón Registrar trabajo
            Button(
                onClick = onRegisterWork,
                shape = RectangleShape,
                modifier =
                    Modifier
                        .size(80.dp)
                        .weight(1f)
                        .padding(start = 2.dp)
                        .clip(RoundedCornerShape(10.dp)),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = BottonColorProfessional,
                        contentColor = Color.White,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Registrar trabajo",
                        modifier = Modifier.size(25.dp),
                        tint = TextBottomColorProfessional,
                    )
                    Text(
                        "Registrar trabajo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}

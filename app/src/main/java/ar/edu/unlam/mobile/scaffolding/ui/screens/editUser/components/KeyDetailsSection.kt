package ar.edu.unlam.mobile.scaffolding.ui.screens.editUser.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KeyDetailsSection(
    location: String,
    experience: String,
    licenseNumber: String,
    onLocationChange: (String) -> Unit,
    onExperienceChange: (String) -> Unit,
    onLicenseChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Key Details",
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        ProfileInputField(
            label = "Location",
            value = location,
            onValueChange = onLocationChange,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        ProfileInputField(
            label = "Experience",
            value = experience,
            onValueChange = onExperienceChange,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        ProfileInputField(
            label = "License #",
            value = licenseNumber,
            onValueChange = onLicenseChange,
            modifier = Modifier.padding(bottom = 5.dp),
        )
    }
}

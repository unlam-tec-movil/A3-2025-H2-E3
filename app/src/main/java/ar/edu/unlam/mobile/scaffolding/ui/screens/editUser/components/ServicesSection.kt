package ar.edu.unlam.mobile.scaffolding.ui.screens.editUser.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ServicesSection(
    services: List<String>,
    onServicesChanged: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var newServiceText by remember { mutableStateOf("") }
    var showAddServiceDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = "Services Offered",
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = 20.dp),
        ) {
            services.forEach { service ->
                RemovableServiceChip(
                    service = service,
                    onRemove = {
                        val updatedServices = services - service
                        onServicesChanged(updatedServices)
                    },
                )
            }
        }

        OutlinedButton(
            onClick = {
                // Show dialog to add new service
                showAddServiceDialog = true
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add service",
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Add a new service...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp),
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        // Simple dialog for adding new service
        if (showAddServiceDialog) {
            AddServiceDialog(
                onDismiss = { showAddServiceDialog = false },
                onAddService = { newService ->
                    if (newService.isNotBlank() && !services.contains(newService)) {
                        val updatedServices = services + newService
                        onServicesChanged(updatedServices)
                    }
                    showAddServiceDialog = false
                },
            )
        }
    }
}

@Composable
fun RemovableServiceChip(
    service: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animatedBackground by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        label = "chipBackground",
    )

    val animatedBorder by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary,
        label = "chipBorder",
    )

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(16.dp))
                .background(animatedBackground)
                .border(
                    width = 1.dp,
                    color = animatedBorder,
                    shape = RoundedCornerShape(16.dp),
                ).padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = service,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove service",
                tint = MaterialTheme.colorScheme.primary,
                modifier =
                    Modifier
                        .size(16.dp)
                        .clickable { onRemove() },
            )
        }
    }
}

@Composable
private fun AddServiceDialog(
    onDismiss: () -> Unit,
    onAddService: (String) -> Unit,
) {
    var newService by remember { mutableStateOf("") }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                .padding(16.dp),
    ) {
        Column {
            Text(
                text = "Add New Service",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp),
            )

            ProfileInputField(
                label = "Service Name",
                value = newService,
                onValueChange = { newService = it },
                modifier = Modifier.padding(bottom = 16.dp).background(Color.Transparent),
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(end = 8.dp),
                ) {
                    Text("Cancel")
                }
                androidx.compose.material3.Button(
                    onClick = { onAddService(newService) },
                ) {
                    Text("Add")
                }
            }
        }
    }
}

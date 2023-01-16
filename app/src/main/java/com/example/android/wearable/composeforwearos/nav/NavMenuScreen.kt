package com.example.android.wearable.composeforwearos.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.android.wearable.composeforwearos.theme.WearAppTheme
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScalingLazyColumnState

/**
 * NavMenuScreen use on the Home Activity, which is the main menu of the app
 */
@Composable
fun NavMenuScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    columnState: ScalingLazyColumnState,
) {
    ScalingLazyColumn(
        modifier = modifier.fillMaxSize(),
        columnState = columnState,
    ) {
        item {
            SampleChip(
                onClick = { navigateToRoute(NavScreen.List.route) },
                label = "Not scroll down"
            )
        }
        item {
            SampleChip(
                onClick = { navigateToRoute(NavScreen.Text.route) },
                label = "overlap"
            )
        }
    }
}

@Composable
fun SampleChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    content: (@Composable () -> Unit)? = null
) {
    Chip(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        label = {
            Text(modifier = Modifier.weight(1f), text = label)
            if (content != null) {
                Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                    content()
                }
            }
        }
    )
}

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    device = Devices.WEAR_OS_SQUARE, // small square
    showSystemUi = true,
    showBackground = true
)
@Composable
fun NavMenuScreenPreview() {
    WearAppTheme {
        NavMenuScreen(
            navigateToRoute = {_ -> },
            columnState = ScalingLazyColumnDefaults.belowTimeText().create(),
        )
    }
}
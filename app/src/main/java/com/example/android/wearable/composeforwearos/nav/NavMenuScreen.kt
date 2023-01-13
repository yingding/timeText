package com.example.android.wearable.composeforwearos.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.android.wearable.composeforwearos.theme.WearAppTheme
import com.google.android.horologist.compose.focus.rememberActiveFocusRequester
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.rotaryinput.rotaryWithFling

/**
 * NavMenuScreen use on the Home Activity, which is the main menu of the app
 */
@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun NavMenuScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    scrollState: ScalingLazyListState,
) {
    val focusRequester = rememberActiveFocusRequester()
    ScalingLazyColumn (
        modifier = modifier
            .fillMaxSize()
            .rotaryWithFling(focusRequester, scrollState),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally,
        autoCentering = AutoCenteringParams(itemIndex = 0)
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

    // added launchedEffect to request focus so that the bezel event can be detected.
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
        val scalingLazyListState = rememberScalingLazyListState()
        NavMenuScreen(
            navigateToRoute = {_ -> },
            scrollState = scalingLazyListState,
        )
    }
}
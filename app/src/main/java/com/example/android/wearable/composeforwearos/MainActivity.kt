package com.example.android.wearable.composeforwearos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberSwipeToDismissBoxState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.example.android.wearable.composeforwearos.nav.NavMenuScreen
import com.example.android.wearable.composeforwearos.nav.NavScreen
import com.example.android.wearable.composeforwearos.theme.WearAppTheme
import com.google.android.horologist.compose.focus.rememberActiveFocusRequester
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.NavScaffoldViewModel
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.google.android.horologist.compose.navscaffold.scrollable

class MainActivity : ComponentActivity() {
    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberSwipeDismissableNavController()
            WearAppTheme {
                WearApp(
                    navController = navController!!,
                )
            }
        }
    }
}

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun WearApp(
    navController: NavHostController,
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val swipeDismissableNavHostState = rememberSwipeDismissableNavHostState(swipeToDismissBoxState)

    WearNavScaffold(
        startDestination = NavScreen.Menu.route,
        navController = navController,
        state = swipeDismissableNavHostState
    ) {
        scrollable(
            route = NavScreen.Menu.route,
            columnStateFactory = ScalingLazyColumnDefaults.belowTimeText(firstItemIsFullWidth = true)
        ) {
            NavMenuScreen(
                navigateToRoute = { route -> navController.navigate(route) },
                columnState = it.columnState
            )
        }

        /* Time Text Abnormal1, cut off and all item elements are also not centering
         * can not be scrolled down in 3 elements with one-line Text head */
        scrollable(NavScreen.List.route) {
            it.timeTextMode = NavScaffoldViewModel.TimeTextMode.ScrollAway
            it.viewModel.vignettePosition =
                NavScaffoldViewModel.VignetteMode.On(VignettePosition.TopAndBottom)
            it.positionIndicatorMode =
                NavScaffoldViewModel.PositionIndicatorMode.On
            ThreeItemsList(columnState = it.columnState, headText = "Not Centering")
        }

        /* Time Text Abnormal2, Overlapping
         * Time Text and Head Text in 3 elements with two-line Text head */
        scrollable(NavScreen.Text.route) {
            it.timeTextMode = NavScaffoldViewModel.TimeTextMode.ScrollAway
            it.viewModel.vignettePosition =
                NavScaffoldViewModel.VignetteMode.On(VignettePosition.TopAndBottom)
            it.positionIndicatorMode =
                NavScaffoldViewModel.PositionIndicatorMode.On
            ThreeItemsList(
                columnState = it.columnState,
                headText = "Two line header very close to Time Text "
            )
        }
    } // end of wear nav scaffold
}

@Composable
fun ThreeItemsList(
    columnState: ScalingLazyColumnState,
    headText: String = "Head Text"
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        columnState = columnState,
    ) {
        item {
            Text(
                modifier = Modifier
                    // .fillMaxWidth()
                    .padding(all = 8.dp),
                text = headText,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
        }
        item {
            Box(
                modifier = Modifier
                    // .fillMaxWidth()
                    .height(140.dp)
                    .border(BorderStroke(2.dp, Color.White))
            ) {
                Text("Inside Box of 140.dp")
            }
        }
        item {
            Button(
                onClick = {},
                shape = CircleShape,
            ) {
                Icon(
                    // https://fonts.google.com/icons
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "ok button",
                    modifier = Modifier
                        .size(ButtonDefaults.DefaultIconSize)
                        .wrapContentSize(align = Alignment.Center)
                )
            }
        }
    }
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
fun ScrollablePreview() {
    val navController = rememberSwipeDismissableNavController()
    WearAppTheme {
        WearApp(
            navController = navController,
        )
    }
}
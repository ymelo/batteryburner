package com.ythat.batteryburner

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ythat.batteryburner.ui.TestToggleButton
import com.ythat.batteryburner.ui.theme.BatteryBurnerTheme

class MainActivity : ComponentActivity() {

    private val vm: DrainageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BatteryBurnerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DrainScreen("Android", vm)
                }
            }
        }
        vm.keepAwake().observe(this) { keepAwake ->
            if (keepAwake) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }
}

@Composable
fun DrainScreen(name: String, viewModel: DrainageViewModel = viewModel(), modifier: Modifier = Modifier) {
    // Whether the disclaimer has been dismissed during the current session
    var disclaimerDismissed by remember { mutableStateOf(false) }
    // How many CPU cores (TBD, feature not implemented yet) to use when draining battery
    var initialCPULoad by rememberSaveable { mutableStateOf(0f) }
    // State on whether to keep the screen awake or not
    var screenKeepAwake by rememberSaveable { mutableStateOf(false) }

    var flashlightState by rememberSaveable { mutableStateOf(false) }


    Column(modifier.padding(8.dp)) {
        if(!disclaimerDismissed) {
            DisclaimerScreen(onDisclaimerDismiss = { disclaimerDismissed = !disclaimerDismissed}, modifier)
        }

        Spacer(modifier = modifier.padding(16.dp))

        val iconId = if (flashlightState)R.drawable.flashlight_on else R.drawable.flashlight_off
        TestToggleButton(text = stringResource(R.string.flashlight), iconId, flashlightState, onCheckedChange = { checked ->
            flashlightState = checked
            viewModel.flashlight(flashlightState)
        })

        TextSlider(text = stringResource(R.string.keep_screen), screenKeepAwake, onCheckedChange = {
                screenKeepAwake = it
                viewModel.keepAwake(it)
            }
        )

        SyntheticLoad(name = "# of Threads", initialValue = initialCPULoad, minValue = 0, maxValue = viewModel.cpuCores, onValueChanged =
            {
                Log.d("cpu", "$initialCPULoad")
                initialCPULoad = it
                viewModel.makeCpuBurn(initialCPULoad.toInt())
            },
            modifier
        )
    }
}

@Composable
fun SyntheticLoad(name: String, initialValue: Float, minValue: Int, maxValue: Int, onValueChanged: (Float) -> Unit, modifier: Modifier = Modifier) {
    Surface(shape = MaterialTheme.shapes.small, modifier = modifier) {
        Column(modifier.padding(4.dp)) {
            Text(text = name, modifier.padding(end = 8.dp))
            Row (
                modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(text = minValue.toString())
                Text(text = maxValue.toString())
            }

            Slider(value = initialValue,
                onValueChange = onValueChanged,
                valueRange = 0.0f..maxValue.toFloat(),
                steps = (maxValue - 1),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BatteryBurnerTheme {
        DrainScreen("Android")
    }
}
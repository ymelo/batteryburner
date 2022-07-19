package com.ythat.batteryburner

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ythat.batteryburner.ui.theme.BatteryBurnerTheme

class MainActivity : ComponentActivity() {
    val vm: DrainageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatteryBurnerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android", vm)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, viewModel: DrainageViewModel, modifier: Modifier = Modifier) {
    var disclaimerDismissed by remember { mutableStateOf(false) }
    var initialCPULoad by rememberSaveable { mutableStateOf(0f) }
    var screenKeepAwake by rememberSaveable { mutableStateOf(false) }
    Column(modifier.padding(8.dp)) {
        if(!disclaimerDismissed) {
            DisclaimerScreen(onDisclaimerDismiss = { disclaimerDismissed = !disclaimerDismissed}, modifier)
        }
        Spacer(modifier = modifier.padding(16.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Flashlight")
            Image(
                modifier = modifier
                    .size(56.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(android.R.drawable.btn_star_big_on),
                contentDescription = null
            )
        }

        TextSlider(text = "Keep screen ON", screenKeepAwake, onCheckedChange = {
                screenKeepAwake = it
            }
        )
        TextSlider(text = "test", true, onCheckedChange = {})
        val info = HardwareStatisticsRepository().hardwareInfo()
        SyntheticLoad(name = "CPU", initialValue = initialCPULoad, minValue = 0, maxValue = 8, onValueChanged =
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
                steps = maxValue,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BatteryBurnerTheme {
        Greeting("Android", DrainageViewModel())
    }
}
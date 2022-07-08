package com.ythat.batteryburner

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatteryBurnerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var disclaimerDismissed by remember { mutableStateOf(false) }
    var initialCPULoad by remember { mutableStateOf(4) }
    var screenKeepAwake by rememberSaveable { mutableStateOf(false) }
    Column(modifier.padding(8.dp)) {
        if(!disclaimerDismissed) {
            DisclaimerScreen(onDisclaimerDismiss = { disclaimerDismissed = !disclaimerDismissed})
        }

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

        TextSlider(text = "Keep screen ON", screenKeepAwake, onCheckedChange = { screenKeepAwake = it})
        TextSlider(text = "test", true, onCheckedChange = {})
        SyntheticLoad(name = "CPU", initialValue = initialCPULoad, maxValue = 8, onValueChanged =
            {
                Log.d("cpu", "$initialCPULoad")
                initialCPULoad = it.toInt()
            }
        )

    }
}

@Composable
fun SyntheticLoad(name: String, initialValue: Int, maxValue: Int, onValueChanged: (Float) -> Unit, modifier: Modifier = Modifier) {
    Row (
        modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = name, modifier.padding(end = 8.dp))
        Text(text = initialValue.toString())
        Slider(value = initialValue.toFloat(),
            onValueChange = onValueChanged,
            valueRange = 0.0f.rangeTo(maxValue.toFloat()),
            steps = 1,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BatteryBurnerTheme {
        Greeting("Android")
    }
}
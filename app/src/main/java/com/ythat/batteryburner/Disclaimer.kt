package com.ythat.batteryburner

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ythat.batteryburner.ui.theme.BatteryBurnerTheme

@Composable
fun DisclaimerScreen(onDisclaimerDismiss: () -> Unit = {}, modifier: Modifier = Modifier) {

    Surface(shape = MaterialTheme.shapes.small, modifier = modifier) {
        Column(modifier = modifier.padding(4.dp)) {
            IconButton(onClick = onDisclaimerDismiss) {
                Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Warning", modifier = modifier.align(Alignment.CenterVertically))
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close disclaimer"
                    )
                }
            }
            TextField(value = stringResource(id = R.string.disclaimer),
                onValueChange = {},
                enabled = false,
                modifier = modifier
                    .fillMaxWidth()
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DisclaimerPreview() {
    BatteryBurnerTheme {
        DisclaimerScreen()
    }
}
package com.ythat.batteryburner.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ythat.batteryburner.R
import com.ythat.batteryburner.ui.theme.BatteryBurnerTheme

@Composable
fun TestToggleButton(text: String, iconId: Int, checked: Boolean = false, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {

    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.clip(RoundedCornerShape(32.dp))
    ) {
        val color = if (checked) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        Row(modifier = modifier.background(color)) {
            Text(text = text, color = Color.White, fontWeight = FontWeight.Bold, modifier = modifier.padding(12.dp, 2.dp, 2.dp, 2.dp).align(Alignment.CenterVertically))
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.size(56.dp).padding(2.dp, 4.dp, 4.dp, 4.dp)
            )
        }

    }
}

/**
 * Helper Composable
 * Draws a button with a rounded shape imitating (not completely)
 * the tray buttons from Android 11
 */
@Composable
fun PressableButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isSelected = remember { mutableStateOf(false) }

    Button(
        onClick = {
                  isSelected.value = !isSelected.value
        },
//        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier.height(50.dp).toggleable(value = true, interactionSource = interactionSource, indication = LocalIndication.current, enabled = true, onValueChange = {}),
    ){
        Text(
            "Button",
            color=Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PressableButtonPreview() {
    BatteryBurnerTheme {
        Column {
            TestToggleButton("test", R.drawable.flashlight_off, checked = false, onCheckedChange = { checked ->

            })
            Spacer(Modifier.height(2.dp))
            PressableButton()
        }

    }
}
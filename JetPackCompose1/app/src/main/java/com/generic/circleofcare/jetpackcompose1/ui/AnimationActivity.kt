package com.generic.circleofcare.jetpackcompose1.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.generic.circleofcare.jetpackcompose1.ui.ui.ui.theme.JetPackCompose1Theme

class AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                    Greeting4("Android")
        }
    }
}

@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    var sizeState by remember {
        mutableStateOf(200.dp)
    }
    val size by animateDpAsState(
        targetValue = sizeState,
        tween(
            durationMillis = 3000,
            1000,
            easing = LinearOutSlowInEasing
        ),
//        keyframes {
//            durationMillis= 5000
//            sizeState at 0 with LinearEasing
//            sizeState *1.5f at 1000 with FastOutLinearInEasing
//            sizeState *2f at 5000
//        },
        label = ""
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(
        modifier = modifier
            .size(size)
            .background(color),
        contentAlignment = Alignment.Center){
        Column(modifier = Modifier
            .size(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                sizeState += 50.dp
            }) {
                Text("+")

            }
            Button(modifier = Modifier.padding(0.dp, 16.dp), onClick = {
                sizeState -= 50.dp
            }) {
                Text("-")

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    JetPackCompose1Theme {
        Greeting4("Android")
    }
}
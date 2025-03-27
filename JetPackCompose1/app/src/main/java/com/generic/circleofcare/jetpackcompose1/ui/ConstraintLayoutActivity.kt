package com.generic.circleofcare.jetpackcompose1.ui

import android.graphics.drawable.PaintDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.generic.circleofcare.jetpackcompose1.R
import com.generic.circleofcare.jetpackcompose1.ui.ui.theme.JetPackCompose1Theme

class ConstraintLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackCompose1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn{//(modifier =Modifier.verticalScroll(scrollState)){
                        itemsIndexed(
                            listOf("this","is","world", "of" , "technology.", "this","is","world", "of" , "technology.", "we","are","using", "compose", "for", "application." )
                        ) { _, string ->
                            RowClient(string)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    val constrains = ConstraintSet {
        val greenBox = createRefFor("greenbox")
        val redBox = createRefFor("redbox")
        val text = createRefFor("text")
        val guidline = createGuidelineFromTop(16.dp)

        constrain(greenBox) {
            top.linkTo(guidline)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redBox) {
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            end.linkTo(parent.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(text) {
            top.linkTo(parent.top)
            start.linkTo(redBox.start)
        }
        createHorizontalChain(greenBox,redBox, chainStyle = ChainStyle.Spread)
    }
    ConstraintLayout(constraintSet = constrains, modifier = Modifier.fillMaxSize().padding(0.dp,8.dp)) {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenbox")
        )
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redbox")
        )
        Text(
            text = "Hello $name!",
            modifier = modifier
                .layoutId("text")

        )
    }

}

@Composable
fun RowClient(name: String, modifier: Modifier = Modifier) {
    val constrains = ConstraintSet {
        val image = createRefFor("image")
        val title = createRefFor("name")
        val subtitle = createRefFor("subtitle")
        val time  = createRefFor("time")

        constrain(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
           // width = Dimension.value(36.dp)
        //    height = Dimension.value(36.dp)
        }
        constrain(title) {
            top.linkTo(parent.top)
            start.linkTo(image.end)
            end.linkTo(time.start)
            width = Dimension.fillToConstraints
        }
        constrain(subtitle) {
            top.linkTo(title.bottom)
            start.linkTo(title.start)
        }
        constrain(time) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(title.bottom)
            width = Dimension.wrapContent
            }
    }
    ConstraintLayout(constraintSet = constrains, modifier = Modifier.fillMaxSize().padding(16.dp,8.dp)) {

        Image(painter = painterResource(R.drawable.ss1),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                //.background(Color.Green)
                .size(48.dp)
                .clip(CircleShape)
                // clip to the circle shape
                .border(0.4.dp, Color.Gray, CircleShape)
                .layoutId("image")
        )
        Text(
            text = name,
            fontSize = 28.sp,
            fontStyle = FontStyle.Normal,
            modifier = Modifier
                .layoutId("name")
                .padding(horizontal = 8.dp)
        )
        Text(
            text = "Android Develoiper",
            modifier = modifier
                .padding(horizontal = 8.dp)
                .layoutId("subtitle")

        )
        Text(
            text = "00:00 - 00:00",
            modifier = modifier
                .layoutId("time")

        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    JetPackCompose1Theme {
        Greeting3("Android")
    }
}
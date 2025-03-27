package com.generic.circleofcare.uidesignpractice.dropdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.generic.circleofcare.uidesignpractice.R
import com.generic.circleofcare.uidesignpractice.dropdown.data.HighLightStory
import com.generic.circleofcare.uidesignpractice.dropdown.ui.theme.UIDesignPracticeTheme

class AnimatedDropDown : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIDesignPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopBar(listOf("mihir_lakhia", "img_12"))
                        Spacer(modifier = Modifier.height(8.dp))
                        ProfileSection()
                        Spacer(modifier = Modifier.height(8.dp))

                        ProfileDescription(
                            displayName = "Mihir Lakhia",
                            description = "10 years of coding experience\n" +
                                    "Want me to make your app? Send me an email!\n" +
                                    "Subscribe to my YouTube channel!",
                            url = "www.mihirlakhia.com",
                            followedBy = listOf("Megha", "Phillip"),
                            otherCount = 18
                        )
                        Spacer(modifier = Modifier.height(25.dp))

                        ButtonSection()
                        Spacer(modifier = Modifier.height(25.dp))
                        HighlightsSection(
                            stories = listOf(
                                HighLightStory(
                                    image = painterResource(id = R.drawable.youtube),
                                    text = "YouTube"
                                ),
                                HighLightStory(
                                    image = painterResource(id = R.drawable.qa),
                                    text = "QA"
                                ),
                                HighLightStory(
                                    image = painterResource(id = R.drawable.telegram),
                                    text = "Telegram"
                                ),
                                HighLightStory(
                                    image = painterResource(id = R.drawable.discord),
                                    text = "Discord"
                                ),
                                HighLightStory(
                                    image = painterResource(id = R.drawable.youtube),
                                    text = "YouTube"
                                ),
                                HighLightStory(
                                    image = painterResource(id = R.drawable.qa),
                                    text = "Q&A"
                                ),
                                HighLightStory(
                                    image = painterResource(id = R.drawable.telegram),
                                    text = "Telegram"
                                ),
                                HighLightStory(
                                    image = painterResource(id = R.drawable.discord),
                                    text = "Discord"
                                )
                            ), modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(names: List<String>, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.Top)
            .fillMaxWidth()
            .shadow(5.dp)
            .background(color = Color.White)
            .padding(16.dp, 8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier.weight(0.5f)
        ) {
            Text(
                text = names[0],
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Icon(
                painter = painterResource(id = android.R.drawable.arrow_down_float),
                contentDescription = "More"
            )
        }
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_recent_history),
            contentDescription = "Home",
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_add),
            contentDescription = "Home",
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_info_details),
            contentDescription = "Home",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ProfileSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        CircularImage(
            image = painterResource(id = R.drawable.mihir),
            modifier = Modifier

                .size(100.dp)
                .weight(3f)

        )
        Spacer(modifier = Modifier.width(10.dp))
        StatsSection(modifier = Modifier.weight(7f))
    }
}

@Composable
fun CircularImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,// for cropping in 1:1
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatsSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        ProfileStat(number = "605", text = "Posts")
        ProfileStat(number = "100k", text = "followers")
        ProfileStat(number = "80", text = "following")
    }
}

@Composable
fun ProfileStat(
    number: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Text(text = number, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

@Composable
fun ProfileDescription(
    displayName: String,
    description: String,
    url: String,
    followedBy: List<String>,
    otherCount: Int
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = displayName,
            fontWeight = FontWeight.Bold,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
        Text(
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = url,
            color = Color(0xFF3D3D91),
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        if (followedBy.isNotEmpty()) {

            Text(text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                append("Followed by ")
                followedBy.forEachIndexed { index, name ->
                    pushStyle(boldStyle)
                    append(name)
                    pop()
                    if (index < followedBy.size - 1) {
                        append(", ")
                    }
                }
                if (otherCount > 1) {
                    append(" and ")
                    pushStyle(boldStyle)
                    append("$otherCount Others")
                }
            })
        }
    }
}

@Composable
fun ButtonSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        val minWidth = 95.dp
        val height = 30.dp
        BorderedButton(
            "Following", Icons.Default.KeyboardArrowDown,
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        BorderedButton(
            "Message", modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        BorderedButton(
            "Email", modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        BorderedButton(
            icon = Icons.Default.KeyboardArrowDown,
            modifier = Modifier
                .size(height)
        )
    }

}

@Composable
fun BorderedButton(
    text: String? = null,
    icon: ImageVector? = null,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .padding(6.dp)
    ) {
        if (text != null) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "icon")
        }
    }
}


@Composable
fun HighlightsSection(modifier: Modifier = Modifier, stories: List<HighLightStory>) {
    LazyRow(modifier = modifier) {
        items(stories) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(end = 15.dp)
            ) {
                CircularImage(image = it.image, modifier = Modifier.size(70.dp))
                Text(
                    text = it.text,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    UIDesignPracticeTheme {
        TopBar(listOf("Android", "iOS"))
    }
}
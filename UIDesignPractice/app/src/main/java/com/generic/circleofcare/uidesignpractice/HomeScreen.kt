package com.generic.circleofcare.uidesignpractice

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.generic.circleofcare.uidesignpractice.ui.theme.AquaBlue
import com.generic.circleofcare.uidesignpractice.ui.theme.Beige1
import com.generic.circleofcare.uidesignpractice.ui.theme.Beige2
import com.generic.circleofcare.uidesignpractice.ui.theme.Beige3
import com.generic.circleofcare.uidesignpractice.ui.theme.BlueViolet1
import com.generic.circleofcare.uidesignpractice.ui.theme.BlueViolet2
import com.generic.circleofcare.uidesignpractice.ui.theme.BlueViolet3
import com.generic.circleofcare.uidesignpractice.ui.theme.ButtonBlue
import com.generic.circleofcare.uidesignpractice.ui.theme.DarkerButtonBlue
import com.generic.circleofcare.uidesignpractice.ui.theme.DeepBlue
import com.generic.circleofcare.uidesignpractice.ui.theme.LightGreen1
import com.generic.circleofcare.uidesignpractice.ui.theme.LightGreen2
import com.generic.circleofcare.uidesignpractice.ui.theme.LightGreen3
import com.generic.circleofcare.uidesignpractice.ui.theme.LightRed
import com.generic.circleofcare.uidesignpractice.ui.theme.OrangeYellow1
import com.generic.circleofcare.uidesignpractice.ui.theme.OrangeYellow2
import com.generic.circleofcare.uidesignpractice.ui.theme.OrangeYellow3
import com.generic.circleofcare.uidesignpractice.ui.theme.Purple40
import com.generic.circleofcare.uidesignpractice.ui.theme.PurpleGrey40
import com.generic.circleofcare.uidesignpractice.ui.theme.TextWhite
import com.generic.circleofcare.uidesignpractice.ui.theme.UIDesignPracticeTheme

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
    ) {
        Column {
            Greeting()
            ChipSection(chips = listOf("Sweet Sleep", "Insomnia", "Depression"))
            CurrentMeditation()
            Features(
                listOf(
                    Feature(
                        "Sleep meditation",
                        R.drawable.ic_headphone,
                        OrangeYellow1,
                        OrangeYellow2,
                        OrangeYellow3
                    ),
                    Feature(
                        "Tips for sleeping",
                        R.drawable.ic_videocam,
                        BlueViolet3,
                        BlueViolet2,
                        BlueViolet1
                    ),
                    Feature(
                        "Night island",
                        R.drawable.ic_videocam,
                        Beige1,
                        Beige2,
                        Beige3
                    ),
                    Feature(
                        "Calming sounds",
                        R.drawable.ic_headphone,
                        LightGreen1,
                        LightGreen2,
                        LightGreen3
                    )
                )
            )
        }
        BottomMenuBar(
            items = listOf(
                BottomMenuItem("Home", R.drawable.ic_home),
                BottomMenuItem("Meditate", R.drawable.ic_bubble),
                BottomMenuItem("Sleep", R.drawable.ic_moon),
                BottomMenuItem("Music", R.drawable.ic_music),
                BottomMenuItem("Profile", R.drawable.ic_profile),
            ),modifier = Modifier.align(Alignment.BottomCenter)
        )
    }


}

@Composable
fun Greeting(
    name: String = "Mihir"
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Good Morning $name",
                color = TextWhite,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "We will serve you better!",
                fontSize = 18.sp,
                color = TextWhite
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ChipSection(
    chips: List<String>
) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    LazyRow {
        items(chips.size) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = it
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChipIndex == it) ButtonBlue
                        else DarkerButtonBlue
                    )
                    .padding(15.dp)
            ) {
                Text(
                    text = chips[it],
                    color = TextWhite,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun CurrentMeditation(
    color: Color = LightRed
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Daily Thought",
                color = TextWhite,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Maditation • 3-10 min",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = TextWhite
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(ButtonBlue)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

    }
}

@Composable
fun Features(feature: List<Feature>) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Features",
            modifier = Modifier.padding(15.dp),
            style = MaterialTheme.typography.headlineLarge,
            color = TextWhite
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight(),
            content = {
                items(feature.size) { index ->
                    FeatureItem(feature[index])
                }
            }
        )
    }
}

@Composable
fun FeatureItem(feature: Feature) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Medium colored path
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        val darkColoredPoint1 = Offset(0f, height * 0.5f)
        val darkColoredPoint2 = Offset(width * 0.1f, height * 0.04f)
        val darkColoredPoint3 = Offset(width * 0.3f, height * 0.35f)
        val darkColoredPoint4 = Offset(width * 0.65f, height.toFloat())
        val darkColoredPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val darkColoredPath = Path().apply {
            moveTo(darkColoredPoint1.x, darkColoredPoint1.y)
            standardQuadFromTo(darkColoredPoint1, darkColoredPoint2)
            standardQuadFromTo(darkColoredPoint2, darkColoredPoint3)
            standardQuadFromTo(darkColoredPoint3, darkColoredPoint4)
            standardQuadFromTo(darkColoredPoint4, darkColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = feature.mediumColor
            )
            drawPath(
                path = darkColoredPath,
                color = feature.lightColor
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                painter = painterResource(id = feature.iconid),
                tint = TextWhite,
                contentDescription = feature.title,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = "Start",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        // Handle the click

                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )

        }
    }
}

@Composable
fun BottomMenuBar(
    items: List<BottomMenuItem>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItemDisplay(
                menuItem = item,
                isSelected = index == selectedItemIndex,
            ) {
                selectedItemIndex = index
            }
        }
    }
}

@Composable
fun BottomMenuItemDisplay(
    menuItem: BottomMenuItem,
    isSelected: Boolean,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .clickable { onItemClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(id = menuItem.iconid), contentDescription = null,
                modifier = Modifier.padding(5.dp).size(20.dp),
                tint = if (isSelected) activeTextColor else inactiveTextColor,
            )
        }
        Text(text = menuItem.title,
            color = if (isSelected) activeTextColor else inactiveTextColor)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    UIDesignPracticeTheme {
        HomeScreen()
    }
}
package com.generic.circleofcare.runkeepertest.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.generic.circleofcare.runkeepertest.R
import com.generic.circleofcare.runkeepertest.data.Record
import com.generic.circleofcare.runkeepertest.ui.theme.HdTxtColor
import com.generic.circleofcare.runkeepertest.ui.theme.HeaderBg
import com.generic.circleofcare.runkeepertest.ui.theme.TxtColor

@ExperimentalFoundationApi
@Composable
fun Achievement() {
    Box(
        modifier = Modifier
            .background(Color.White)

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            RecordSection(
                headerText = "Personal Records",
                headerValue = "4 of 6",
                records = listOf(
                    Record(
                        title = "Longest Run",
                        subTitle = "00:00",
                        iconId = R.drawable.longest_run,
                        isActive = 1
                    ), Record(
                        title = "Highest Elevation",
                        subTitle = "2095 ft",
                        iconId = R.drawable.highest_elevation,
                        isActive = 1
                    ), Record(
                        title = "Fastest 5K",
                        subTitle = "00:00",
                        iconId = R.drawable.fastest_5k,
                        isActive = 1
                    ), Record(
                        title = "10k",
                        subTitle = "00:00:00",
                        iconId = R.drawable.fastest_10k,
                        isActive = 1
                    ), Record(
                        title = "Half Marathon",
                        subTitle = "00:00",
                        iconId = R.drawable.fastest_half_marathon,
                        isActive = 1
                    ), Record(
                        title = "Marathon",
                        subTitle = "Not Yet",
                        iconId = R.drawable.fastest_marathon,
                        isActive = 0
                    )
                ),
                headerText2 = "Virtual Races",
                headerValue2 = "",
                records2 = listOf(
                    Record(
                        title = "Virtual Half Marathon Race",
                        subTitle = "00:00",
                        iconId = R.drawable.virtual_half_marathon_race,
                        isActive = 1
                    ), Record(
                        title = "Tokyo-Hakone Ekiden 2020",
                        subTitle = "00:00:00",
                        iconId = R.drawable.tokyo_kakone_ekiden,
                        isActive = 1
                    ), Record(
                        title = "virtual 10K Race",
                        subTitle = "00:00:00",
                        iconId = R.drawable.virtual_10k_race,
                        isActive = 1
                    ), Record(
                        title = "Hakone Ekiden",
                        subTitle = "00:00:00",
                        iconId = R.drawable.hakone_ekiden,
                        isActive = 1
                    ), Record(
                        title = "Mizuno Singapore Ekiden 2015",
                        subTitle = "00:00:00",
                        iconId = R.drawable.mizuno_singapore_ekiden,
                        isActive = 1
                    ), Record(
                        title = "Virtual 5k Race",
                        subTitle = "23:07",
                        iconId = R.drawable.virtual_5k_race,
                        isActive = 0
                    )
                )
            )
//
        }

    }
}

@Composable
fun Header(
    name: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(HeaderBg)
            .fillMaxWidth()
            .padding(15.dp, 8.dp)

    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            color = HdTxtColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = HdTxtColor
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun RecordSection(
    headerText: String,
    headerValue: String,
    records: List<Record>,
    headerText2: String,
    headerValue2: String,
    records2: List<Record>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 100.dp),
            modifier = Modifier.wrapContentHeight()
        ) {
            header {
                Header(name = headerText, value = headerValue)
            }
            items(records.size) {
                RecordItem(record = records[it])
            }
            header {
                Header(name = headerText2, value = headerValue2)
            }
            items(records2.size) {
                RecordItem(record = records2[it])
            }
        }
    }
}

private fun LazyGridScope.header(content: @Composable LazyGridItemScope .() -> Unit) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
fun RecordItem(
    record: Record
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .alpha(if (record.isActive == 0) 0.5f else 1f)
            .fillMaxWidth(1f)
    ) {
        Image(
            painter = painterResource(id = record.iconId),
            contentDescription = record.title,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .size(80.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = record.title,
            color = TxtColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(5.dp)
                .clickable {
                    // Handle the click
                }
        )
        Text(
            text = record.subTitle,
            color = TxtColor,
            fontSize = 12.sp,
        )
    }
}



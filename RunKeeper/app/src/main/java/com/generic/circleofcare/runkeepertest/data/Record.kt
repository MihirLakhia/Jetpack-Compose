package com.generic.circleofcare.runkeepertest.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Record(
    val title: String,
    val subTitle: String,
    @DrawableRes val iconId: Int,
    val isActive: Int
)

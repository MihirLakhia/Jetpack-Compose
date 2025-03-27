package com.generic.circleofcare.uidesignpractice

import androidx.annotation.DrawableRes

data class Feature(
    val title:String,
    @DrawableRes val iconid: Int,
    val lightColor: androidx.compose.ui.graphics.Color,
    val mediumColor: androidx.compose.ui.graphics.Color,
    val darkColor: androidx.compose.ui.graphics.Color
)
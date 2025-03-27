package com.generic.kotlin.robinhoodtest.data

data class Asset(
var id: String,
var name: String,
var ticker: String,
var instrument_type: String,
var current_price: Double,
var previous_price: Double,
var description: String,

)
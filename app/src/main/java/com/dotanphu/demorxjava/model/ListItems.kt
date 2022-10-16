package com.dotanphu.demorxjava.model

import com.google.gson.annotations.SerializedName

data class ListItems(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    val total_count: Int
)
package com.mehyo.theproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//TodoItem that represents a single object with its attributes inside the json response
@Parcelize
data class TodoItem(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
):Parcelable



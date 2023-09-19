package me.chavdav.vacobuilt.data

import java.time.OffsetDateTime

data class Post(
    val id: Int,
    val title: String,
    val contents: String,
    val categoryId: Int,
    val timeStamp: OffsetDateTime,
)

data class PostRequest(
    val id: Int?,
    val title: String,
    val contents: String,
    val categoryId: Int,
)

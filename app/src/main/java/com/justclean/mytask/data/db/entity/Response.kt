package com.justclean.mytask.data.db.entity

class Response : ArrayList<ResponseItem>()

data class ResponseItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
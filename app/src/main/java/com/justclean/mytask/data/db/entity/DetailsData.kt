package com.justclean.mytask.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "details")
class DetailsData {
    var postId:Int=0
    @PrimaryKey
    var id:Int=0
    var name:String=""
    var email:String=""
    var body:String=""
}
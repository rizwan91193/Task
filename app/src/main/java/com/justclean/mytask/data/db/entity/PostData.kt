package com.justclean.mytask.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postData")
class PostData()
{
    var userId:Int=0
    @PrimaryKey
    var id:Int=0
    var title:String=""
    var body:String=""


}
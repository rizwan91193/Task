package com.justclean.mytask.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class FavoritesData{
    var userId:Int=0
    @PrimaryKey
    var id:Int=0
    var title:String=""
    var body:String=""
}
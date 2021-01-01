package com.justclean.mytask.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.justclean.mytask.data.db.entity.PostData
import java.util.concurrent.locks.Lock


@Database(entities = [PostData::class],version = 2,exportSchema = false)
abstract class AppDatabase:RoomDatabase(){

    abstract fun getPostDao():PostDao

    companion object
    {
        @Volatile
        private var instance:AppDatabase?=null
        private val LOCK =Any()
        operator fun invoke(context:Context) = instance?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,AppDatabase::class.java,
                "MyDatabase.db"
            ).fallbackToDestructiveMigration().build()
    }

}
package com.cenkeraydin.artbook.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Art::class], version =1, exportSchema = false)
abstract class ArtDB : RoomDatabase() {
    abstract fun artDao(): ArtDao
}
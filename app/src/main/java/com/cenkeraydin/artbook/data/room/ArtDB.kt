package com.cenkeraydin.artbook.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Art::class], version =1)
abstract class ArtDB : RoomDatabase() {
    abstract fun artDao(): ArtDao
}
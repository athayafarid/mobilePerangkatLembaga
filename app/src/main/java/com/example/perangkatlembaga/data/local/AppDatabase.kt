package com.example.perangkatlembaga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RTEntity::class, RWEntity::class, AnggotaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rtDao(): RTDao
    abstract fun rwDao(): RWDao
    abstract fun anggotaDao(): AnggotaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "perangkat_lembaga_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.haidev.tixtestnfc.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.haidev.tixtestnfc.data.local.entity.NFCEntity

@Database(entities = [NFCEntity::class], version = 1, exportSchema = false)
abstract class NFCDatabase : RoomDatabase() {

    abstract fun nfcDao(): NFCDao

    companion object {
        @Volatile
        private var instance: NFCDatabase? = null
        fun getInstance(context: Context): NFCDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NFCDatabase::class.java, "NFC.db"
                ).build()
            }
    }
}
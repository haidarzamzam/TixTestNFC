package com.haidev.tixtestnfc.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haidev.tixtestnfc.data.local.entity.NFCEntity

@Database(entities = [NFCEntity::class], version = 1, exportSchema = false)
abstract class NFCDatabase : RoomDatabase() {

    abstract fun nfcDao(): NFCDao
}
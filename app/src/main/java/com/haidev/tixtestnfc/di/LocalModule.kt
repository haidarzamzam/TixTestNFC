package com.haidev.tixtestnfc.di

import android.content.Context
import androidx.room.RoomDatabase
import com.haidev.tixtestnfc.data.local.room.NFCDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    fun provideAppDatabase(@ApplicationContext context: Context): RoomDatabase {
        return NFCDatabase.getInstance(context)
    }
}
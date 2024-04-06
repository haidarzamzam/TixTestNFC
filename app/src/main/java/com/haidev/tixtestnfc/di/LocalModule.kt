package com.haidev.tixtestnfc.di

import android.content.Context
import androidx.room.Room
import com.haidev.tixtestnfc.data.local.entity.NFCEntity
import com.haidev.tixtestnfc.data.local.room.NFCDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NFCDatabase::class.java, "NFC.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: NFCDatabase) = db.nfcDao()

    @Provides
    fun provideEntity() = NFCEntity()
}
package co.ke.paypay.di.modules

import android.content.Context
import androidx.room.Room
import co.ke.paypay.room.ApplicationDatabase
import co.ke.paypay.room.dao.ConversionDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context): ApplicationDatabase {
        return Room.databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                ApplicationDatabase.DATABASE_NAME
        )
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideConversion(applicationDatabase: ApplicationDatabase): ConversionDAO {
        return applicationDatabase.conversionDao()
    }
}

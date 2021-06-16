package co.ke.paypay.di.modules

import co.ke.paypay.network.RetrofitService
import co.ke.paypay.network.mappers.ConversionMapper
import co.ke.paypay.repo.ConversionRepository
import co.ke.paypay.room.dao.ConversionDAO
import co.ke.paypay.room.mappers.ConversionCacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
            conversionDAO: ConversionDAO,
            retrofitService: RetrofitService,
            conversionMapper: ConversionMapper,
            conversionCacheMapper: ConversionCacheMapper
    ): ConversionRepository {
        return ConversionRepository(
                conversionDAO = conversionDAO,
                retrofitService = retrofitService,
                networkMapper = conversionMapper,
                cacheMapper = conversionCacheMapper
        )
    }
}
package co.ke.paypay.network.mappers

import co.ke.paypay.network.entities.ConversionEntity
import co.ke.paypay.room.cache.ConversionCache
import co.ke.paypay.utils.DomainEntityMapper
import java.util.*
import javax.inject.Inject

class ConversionMapper
@Inject
constructor() : DomainEntityMapper<ConversionEntity, ConversionCache> {
    override fun mapToDomain(entity: ConversionEntity): ConversionCache {
        return ConversionCache(
            id = null,
            source = entity.source,
            timestamp = entity.timestamp,
            quote = entity.quotes,
            createdAt = Date().time
        )
    }

    override fun mapToEntity(domainModel: ConversionCache): ConversionEntity {
        return ConversionEntity(
            source = domainModel.source,
            timestamp = domainModel.timestamp,
            quotes = domainModel.quote,
            success = true,
            privacy = null,
            terms = null
        )
    }
}
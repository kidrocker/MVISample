package co.ke.paypay.utils

interface DomainEntityMapper<Entity, DomainModel> {
    fun  mapToDomain(entity: Entity):DomainModel
    fun mapToEntity(domainModel: DomainModel):Entity
}
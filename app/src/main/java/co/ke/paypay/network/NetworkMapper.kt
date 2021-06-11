package co.ke.paypay.network

interface NetworkMapper<Entity, DomainModel> {
    fun  mapFromNetwork(entity: Entity):DomainModel
    fun mapToNetwork(domainModel: DomainModel):Entity
}
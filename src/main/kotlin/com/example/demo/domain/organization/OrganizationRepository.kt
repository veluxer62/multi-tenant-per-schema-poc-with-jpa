package com.example.demo.domain.organization

import java.util.UUID
import javax.persistence.EntityManager
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.ClassicConfiguration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface OrganizationRepository : JpaRepository<Organization, UUID>, CustomOrganizationRepository

interface CustomOrganizationRepository {
    fun createDatabase(name: String)
}

@Repository
class CustomOrganizationRepositoryImpl(
    private val entityManager: EntityManager,
    private val flyway: Flyway,
) : CustomOrganizationRepository {
    @Transactional
    override fun createDatabase(name: String) {
        entityManager.createNativeQuery("CREATE DATABASE $name").executeUpdate()
        val config = flyway.configuration as ClassicConfiguration
        config.defaultSchema = name
        val newFlyway = Flyway(config)
        newFlyway.migrate()
    }
}

package com.example.demo.domain.organization

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrganizationService(
    private val organizationRepository: OrganizationRepository,
) {
    @Transactional
    fun create(organization: Organization): Organization {
        organizationRepository.createDatabase(organization.catalogName)
        return organizationRepository.save(organization)
    }

    fun getById(id: UUID): Organization {
        return organizationRepository.getReferenceById(id)
    }
}

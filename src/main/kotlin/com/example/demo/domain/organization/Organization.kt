package com.example.demo.domain.organization

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "organization", catalog = "poc_database")
class Organization(
    name: String,
    catalogName: String,
) {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    val name: String = name

    @Column(nullable = false, unique = true)
    val catalogName: String = catalogName
}

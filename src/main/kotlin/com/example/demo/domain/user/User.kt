package com.example.demo.domain.user

import com.example.demo.domain.organization.Organization
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user", catalog = "poc_database")
class User(
    name: String,
    password: String,
    organization: Organization,
) {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false, unique = true)
    val name: String = name

    @Column(nullable = false)
    val password: String = password

    @ManyToOne
    val organization: Organization = organization
}

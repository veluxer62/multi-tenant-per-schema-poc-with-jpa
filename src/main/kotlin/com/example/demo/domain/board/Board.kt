package com.example.demo.domain.board

import com.example.demo.domain.user.User
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "board", catalog = "###catalog_name###")
class Board(
    title: String,
    content: String,
    user: User,
) {
    @Id
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    val title: String = title

    @Column(nullable = false)
    val content: String = content

    @ManyToOne
    val user: User = user
}

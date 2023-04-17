package com.example.demo.application

import com.example.demo.domain.board.Board
import com.example.demo.domain.board.BoardService
import com.example.demo.domain.organization.Organization
import com.example.demo.domain.organization.OrganizationService
import com.example.demo.domain.user.SecurityUser
import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserService
import java.security.Principal
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    private val organizationService: OrganizationService,
    private val userService: UserService,
    private val boardService: BoardService,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostMapping("/organizations")
    @Secured("ROLE_USER")
    fun createOrganization(@RequestBody command: OrganizationCreationCommand): OrganizationDto {
        val organization = organizationService.create(command.toEntity())
        return OrganizationDto.fromEntity(organization)
    }

    @PostMapping("/users")
    @Secured("ROLE_USER")
    fun createUser(@RequestBody command: UserCreationCommand): UserDto {
        val organization = organizationService.getById(command.organizationId)
        val user = command.toEntity(organization) { passwordEncoder.encode(it) }
        return UserDto.fromEntity(userService.create(user))
    }

    @PostMapping("/boards")
    @Secured("ROLE_USER")
    fun createBoard(@RequestBody command: BoardCreationCommand): BoardDto {
        val securityUser = SecurityContextHolder.getContext().authentication.principal as SecurityUser
        val user = userService.getById(securityUser.id)
        val board = command.toEntity(user)
        return BoardDto.fromEntity(boardService.create(board))
    }

    @GetMapping("/boards")
    @Secured("ROLE_USER")
    fun getAllBoards(): List<BoardDto> {
        return boardService.getAll().map { BoardDto.fromEntity(it) }
    }

    @GetMapping("/boardsByUserName")
    @Secured("ROLE_USER")
    fun getAllBoardsByUser(principal: Principal): List<BoardDto> {
        return boardService.getAllByUserName(principal.name).map { BoardDto.fromEntity(it) }
    }
}

data class OrganizationCreationCommand(
    val name: String,
    val catalogName: String,
) {
    fun toEntity(): Organization = Organization(name, catalogName)
}

data class OrganizationDto(
    val id: UUID,
    val createdAt: LocalDateTime,
    val name: String,
) {
    companion object {
        fun fromEntity(entity: Organization): OrganizationDto = OrganizationDto(
            id = entity.id,
            createdAt = entity.createdAt,
            name = entity.name
        )
    }
}

data class UserCreationCommand(
    val name: String,
    val password: String,
    val organizationId: UUID,
) {
    fun toEntity(organization: Organization, encodedPassword: (plainPassword: String) -> String): User {
        return User(name, encodedPassword(password), organization)
    }
}

data class UserDto(
    val id: UUID,
    val createdAt: LocalDateTime,
    val name: String,
    val organization: OrganizationDto,
) {
    companion object {
        fun fromEntity(entity: User): UserDto = UserDto(
            id = entity.id,
            createdAt = entity.createdAt,
            name = entity.name,
            organization = OrganizationDto.fromEntity(entity.organization),
        )
    }
}

data class BoardCreationCommand(
    val title: String,
    val content: String,
) {
    fun toEntity(user: User): Board = Board(
        title = title,
        content = content,
        user = user,
    )
}

data class BoardDto(
    val id: UUID,
    val createdAt: LocalDateTime,
    val title: String,
    val content: String,
    val user: UserDto,
) {
    companion object {
        fun fromEntity(entity: Board): BoardDto {
            return BoardDto(
                id = entity.id,
                createdAt = entity.createdAt,
                title = entity.title,
                content = entity.content,
                user = UserDto.fromEntity(entity.user),
            )
        }
    }
}

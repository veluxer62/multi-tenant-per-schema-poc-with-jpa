package com.example.demo.domain.user

import java.util.UUID
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username).orElseThrow()
        return SecurityUser(user)
    }

    @Transactional
    fun create(user: User): User {
        return userRepository.save(user)
    }

    fun getById(id: UUID): User {
        return userRepository.getReferenceById(id)
    }
}

class SecurityUser(
    private val user: User,
) : UserDetails {
    val id: UUID = user.id
    override fun getUsername(): String = user.name
    override fun getPassword(): String = user.password
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
    }
    val catalogName: String = user.organization.catalogName
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}

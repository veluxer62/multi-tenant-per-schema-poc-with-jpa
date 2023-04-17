package com.example.demo

import com.example.demo.domain.organization.Organization
import com.example.demo.domain.user.User
import javax.persistence.EntityManager
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StartUp(
    private val entityManager: EntityManager,
    private val passwordEncoder: PasswordEncoder,
) : CommandLineRunner {
    @Transactional
    override fun run(vararg args: String?) {
        val orgIsNotExists = entityManager
            .createQuery("select o from Organization o where name = :name")
            .setParameter("name", "기본 조직")
            .resultList
            .isEmpty()

        if (orgIsNotExists) {
            val org = Organization("기본 조직", "poc_database")
            val user = User("root", passwordEncoder.encode("1"), org)
            entityManager.persist(org)
            entityManager.persist(user)
        }
    }
}

package com.example.demo.application

import com.example.demo.domain.user.SecurityUser
import org.hibernate.resource.jdbc.spi.StatementInspector
import org.springframework.security.core.context.SecurityContextHolder

class DynamicCatalogNameInterceptor : StatementInspector {
    override fun inspect(sql: String): String {
        val authentication = SecurityContextHolder.getContext().authentication

        return if (authentication != null) {
            val principal = authentication.principal as SecurityUser
            sql.replace("###catalog_name###", principal.catalogName)
        } else {
            sql
        }
    }
}

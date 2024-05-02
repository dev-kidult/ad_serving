package com.kcd.adserving.config

import com.google.common.base.CaseFormat.LOWER_CAMEL
import com.google.common.base.CaseFormat.LOWER_HYPHEN
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Collections
import java.util.Enumeration

@Configuration
class KebabToCamelFilter : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val parameters: Map<String, Array<String>> = request.parameterMap.mapKeys { LOWER_HYPHEN.to(LOWER_CAMEL, it.key) }

        filterChain.doFilter(
            object : HttpServletRequestWrapper(request) {
                override fun getParameter(name: String): String? = if (parameters.containsKey(name)) parameters[name]!![0] else null

                override fun getParameterNames(): Enumeration<String> = Collections.enumeration(parameters.keys)

                override fun getParameterValues(name: String): Array<String>? = parameters[name]

                override fun getParameterMap(): Map<String, Array<String>> = parameters
            },
            response
        )
    }
}

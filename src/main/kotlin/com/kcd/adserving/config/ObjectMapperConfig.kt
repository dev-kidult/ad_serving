package com.kcd.adserving.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.TimeZone

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper() =
        ObjectMapper().apply {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

            registerModule(JavaTimeModule())
            registerModule(
                KotlinModule.Builder()
                    .withReflectionCacheSize(512)
                    .configure(KotlinFeature.NullToEmptyCollection, false)
                    .configure(KotlinFeature.NullToEmptyMap, false)
                    .configure(KotlinFeature.NullIsSameAsDefault, true)
                    .configure(KotlinFeature.SingletonSupport, false)
                    .configure(KotlinFeature.StrictNullChecks, false)
                    .build()
            )
            configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            setDateFormat(dateFormat)
            setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")))
        }
}

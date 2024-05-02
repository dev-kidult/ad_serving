package com.kcd.adserving.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(*[IllegalArgumentException::class, BindException::class])
    @ResponseStatus(BAD_REQUEST)
    fun handleBadRequestException(request: HttpServletRequest, e: Exception): String {
        // logging
        // some error code, message, etc.
        return "BAD_REQUEST"
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleDefaultException(request: HttpServletRequest, e: Exception): String {
        // logging
        // some error code, message, etc.
        return "INTERNAL_SERVER_ERROR"
    }
}

package com.lcc.SurvivalTactics.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HandlerExceptions {

    @ExceptionHandler(TokenError::class)
    fun tokenError(ex: TokenError): ResponseEntity<*>{
        val errList = HashMap<String, String>()
        errList["Error"] = ex.message
        return ResponseEntity(errList, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EmailTakenError::class)
    fun emailTaken(ex: EmailTakenError): ResponseEntity<*>{
        val errList = HashMap<String, String>()
        errList["Error"] = ex.message
        return ResponseEntity(errList, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundError::class)
    fun notFound(ex: NotFoundError): ResponseEntity<*>{
        val errList = HashMap<String, String>()
        errList["Error"] = ex.message
        return ResponseEntity(errList, HttpStatus.BAD_REQUEST)
    }
}
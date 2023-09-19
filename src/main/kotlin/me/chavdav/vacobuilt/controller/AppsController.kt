package me.chavdav.vacobuilt.controller

import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(maxAge = 3600)
@RestController
class AppsController() {
    @GetMapping("/", produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun helloWorld(): ResponseEntity<Any?> {return ResponseEntity("Hello World", HttpStatus.OK)
    }
}
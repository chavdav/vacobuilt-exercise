package me.chavdav.vacobuilt.controller

import me.chavdav.vacobuilt.data.Person
import me.chavdav.vacobuilt.data.PersonRequest
import me.chavdav.vacobuilt.db.PersonService
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = ["/persons"], produces = [MediaType.APPLICATION_JSON_VALUE])
class PersonController(
    private val personService: PersonService
) {
    @GetMapping
    @ResponseBody
    fun getAll(): List<Person> {
        return personService.getAll()
    }

    @PostMapping
    fun addPerson(
        @RequestBody person: PersonRequest
    ): Person {
        return personService.addPerson(person.toPerson())
    }
}

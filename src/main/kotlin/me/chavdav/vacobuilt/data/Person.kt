package me.chavdav.vacobuilt.data

import java.util.*

data class Person(
    val id: UUID,
    val name: String,
    val age: Int,
)

data class PersonRequest(
    val id: UUID?,
    val name: String,
    val age: Int,
) {
    fun toPerson() = Person(
        id ?: UUID.randomUUID(),
        name,
        age
    )
}

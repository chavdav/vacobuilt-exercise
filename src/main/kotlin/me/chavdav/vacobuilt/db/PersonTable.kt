package me.chavdav.vacobuilt.db

import me.chavdav.vacobuilt.AppConfig
import me.chavdav.vacobuilt.data.Person
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.util.*

object PersonTable : Table() {
    val id = uuid("id")
    val name = text("name")
    val age = integer("age")
//    val createdAt = datetime("created_at")
//    val updatedAt = datetime("updated_at")
}

@Service
class PersonService(
    appConfig: AppConfig
) {

    fun getAll(): List<Person> = transaction {
        PersonTable.selectAll().map { toPerson(it) }
    }

    fun getPerson(id: UUID): Person? = transaction {
        PersonTable.select {
            (PersonTable.id eq id)
        }.mapNotNull { toPerson(it) }
            .singleOrNull()
    }

    fun addPerson(person: Person): Person {
        transaction {
            PersonTable.insert {
                it[id] = person.id
                it[name] = person.name
                it[age] = person.age
//                it[createdAt] = LocalDateTime.now()
//                it[updatedAt] = LocalDateTime.now()
            }
        }
        return getPerson(person.id)!!
    }

    fun updatePerson(person: Person): Person {
        val id = person.id
        return run {
            transaction {
                PersonTable.update({ PersonTable.id eq id }) {
                    it[PersonTable.id] = person.id
                    it[PersonTable.name] = person.name
                    it[PersonTable.age] = person.age
//                it[updatedAt] = LocalDateTime.now()
                }
            }
            getPerson(id)!!
        }
    }

    suspend fun deletePerson(id: UUID): Boolean = transaction {
        PersonTable.deleteWhere { PersonTable.id eq id } > 0
    }

    private fun toPerson(row: ResultRow): Person =
        Person(
            id = row[PersonTable.id],
            name = row[PersonTable.name],
            age = row[PersonTable.age],
//            createdAt = row[PersonTable.createdAt],
//            updatedAt = row[PersonTable.updatedAt],
        )
}
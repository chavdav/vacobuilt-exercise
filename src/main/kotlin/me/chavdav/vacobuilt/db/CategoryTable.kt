package me.chavdav.vacobuilt.db

import me.chavdav.vacobuilt.data.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

object CategoryTable : Table() {
    val id = integer("id")
    val name = text("name")
}

@Service
class CategoryService {

    fun getAll(): List<Category> = transaction {
        CategoryTable.selectAll().map { it.toCategory() }
    }

    fun getCategory(id: Int): Category? = transaction {
        CategoryTable.select {
            (CategoryTable.id eq id)
        }.mapNotNull { it.toCategory() }
            .singleOrNull()
    }

    fun addCategory(post: Category): Category = transaction {
        CategoryTable.insert {
            it[name] = post.name
        }.resultedValues!!.first().toCategory()
    }

    fun updateCategory(post: Category): Category {
        val id = post.id ?: throw IllegalArgumentException("Id is null")
        return run {
            transaction {
                CategoryTable.update({ CategoryTable.id eq id }) {
                    it[CategoryTable.name] = post.name
                }
                getCategory(id)!!
            }
        }
    }

    fun deleteCategory(id: Int): Boolean = transaction {
        CategoryTable.deleteWhere { CategoryTable.id eq id } > 0
    }

    private fun ResultRow.toCategory(): Category =
        Category(
            id = this[CategoryTable.id],
            name = this[CategoryTable.name],
        )
}
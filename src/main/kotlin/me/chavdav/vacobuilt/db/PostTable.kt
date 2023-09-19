package me.chavdav.vacobuilt.db

import me.chavdav.vacobuilt.AppConfig
import me.chavdav.vacobuilt.data.Post
import me.chavdav.vacobuilt.data.PostRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

object PostTable : Table() {
    val id = integer("id").autoIncrement("post_ID_seq")
    val title = text("title")
    val contents = text("contents")
    val categoryId = integer("category_id") references CategoryTable.id
    val timeStamp = timestamp("time_stamp")
}

@Service
class PostService(
    appConfig: AppConfig // configure db
) {

    fun getAll(): List<Post> = transaction {
        PostTable.selectAll().map { it.toPost() }
    }

    fun getPost(id: Int): Post? = transaction {
        PostTable.select { (PostTable.id eq id) }
            .mapNotNull { it.toPost() }
            .singleOrNull()
    }

    fun addPost(post: PostRequest): Post = transaction {
        PostTable.insert {
            it[title] = post.title
            it[contents] = post.contents
            it[categoryId] = post.categoryId
            it[timeStamp] = OffsetDateTime.now().toInstant()
        }.resultedValues!!.first().toPost()
    }

    fun updatePost(post: PostRequest): Post {
        val id = post.id ?: throw IllegalArgumentException("Id is null")
        return run {
            transaction {
                PostTable.update({ PostTable.id eq id }) {
                    it[PostTable.title] = title
                    it[PostTable.contents] = contents
                    it[PostTable.categoryId] = post.categoryId
                }
            }
            getPost(id)!!
        }
    }

    fun deletePost(id: Int): Boolean = transaction {
        PostTable.deleteWhere { PostTable.id eq id } > 0
    }

    fun deleteAll() = transaction { PostTable.deleteAll() }

    private fun ResultRow.toPost(): Post =
        Post(
            id = this[PostTable.id],
            title = this[PostTable.title],
            contents = this[PostTable.contents],
            categoryId = this[PostTable.categoryId],
            timeStamp = OffsetDateTime.ofInstant(this[PostTable.timeStamp], ZoneOffset.UTC),
        )
}
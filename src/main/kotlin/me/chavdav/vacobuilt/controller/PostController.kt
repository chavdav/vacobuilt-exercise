package me.chavdav.vacobuilt.controller

import me.chavdav.vacobuilt.data.Post
import me.chavdav.vacobuilt.data.PostRequest
import me.chavdav.vacobuilt.db.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping(path = ["/posts"], produces = [MediaType.APPLICATION_JSON_VALUE])
class PostController(
    private val postService: PostService
) {
    @GetMapping
    @ResponseBody
    fun getAll(): List<Post> {
        return postService.getAll().sortedByDescending { it.timeStamp }
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun getPost(@PathVariable("id") id: Int): Post {
        return postService.getPost(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    @ResponseBody
    fun addPost(
        @RequestBody request: PostRequest
    ): Post {
        return postService.addPost(request)
    }

    @PutMapping
    @ResponseBody
    fun updatePost(
        @RequestBody request: PostRequest
    ): Post {
        return postService.updatePost(request)
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable("id") id: Int) = postService.deletePost(id)

    // Consider not exposing this through API, or implementing soft delete flag
    @DeleteMapping
    fun deleteAll() = postService.deleteAll()

//    @GetMapping("/generateSampleData")
//    fun generateSampleData() = {
//
//        val posts = listOf(
//            Post()
//        )
//
//    }
}

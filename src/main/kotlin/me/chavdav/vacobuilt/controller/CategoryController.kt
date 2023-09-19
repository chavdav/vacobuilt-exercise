package me.chavdav.vacobuilt.controller

import me.chavdav.vacobuilt.data.Category
import me.chavdav.vacobuilt.db.CategoryService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    @ResponseBody
    fun getAll(): List<Category> {
        return categoryService.getAll()
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun getCategory(@PathVariable("id") id: Int): Category? {
        return categoryService.getCategory(id)
    }
}

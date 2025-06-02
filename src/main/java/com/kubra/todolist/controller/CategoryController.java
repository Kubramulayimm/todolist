package com.kubra.todolist.controller;

import com.kubra.todolist.request.CategoryRequest;
import com.kubra.todolist.response.CategoryResponse;
import com.kubra.todolist.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Tag(name = "Category API")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/list/{userId}")
    public List<CategoryResponse> getCategoryAll(@PathVariable String userId) {
        return categoryService.getAllCategories(userId);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryId(@PathVariable String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Void> deleteCategoryId(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok()
                .build();
    }
}

package com.kubra.todolist.service;

import com.kubra.todolist.request.CategoryRequest;
import com.kubra.todolist.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(CategoryRequest categoryRequest);

    void deleteCategory(String categoryId);

    CategoryResponse getCategory(String categoryId);

    List<CategoryResponse> getAllCategories(String userId);
}

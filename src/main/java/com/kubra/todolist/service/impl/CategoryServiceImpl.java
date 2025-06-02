package com.kubra.todolist.service.impl;

import com.kubra.todolist.exception.CategoryNotFoundException;
import com.kubra.todolist.exception.CategoryUpdateFailedException;
import com.kubra.todolist.mapper.CategoryMapper;
import com.kubra.todolist.model.Category;
import com.kubra.todolist.repository.CategoryRepository;
import com.kubra.todolist.request.CategoryRequest;
import com.kubra.todolist.response.CategoryResponse;
import com.kubra.todolist.service.CategoryService;
import com.kubra.todolist.service.TaskService;
import com.kubra.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    private final TaskService taskService;
    private final UserService userService;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        userService.getUser(categoryRequest.getUserId());

        Category category = Category.builder()
                .id(UUID.randomUUID().toString())
                .name(categoryRequest.getName())
                .userId(categoryRequest.getUserId())
                .active(true)
                .build();

        Category save = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(save);

    }

    public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryRequest.getId()).orElseThrow(CategoryNotFoundException::new);

        category.setName(categoryRequest.getName());

        Category savedCategory;
        try {
            savedCategory = categoryRepository.save(category);
        } catch (Exception e) {
            throw new CategoryUpdateFailedException();
        }

        taskService.updateCategoryNameInTasks(savedCategory);

        return categoryMapper.toCategoryResponse(category);
    }

    public void deleteCategory(String categoryId) {
        taskService.removeCategoryFromTasks(categoryId);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null) {
            throw new CategoryNotFoundException();
        }
        category.setDeleted(true);
        category.setActive(false);
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }

    public CategoryResponse getCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAllCategories(String userId) {
        List<Category> categoryAllByUserId = categoryRepository.findAllByUserId(userId);
        return categoryMapper.toCategoryResponseList(categoryAllByUserId);

    }
}

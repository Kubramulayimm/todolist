package com.kubra.todolist.service.impl;

import com.kubra.todolist.exception.CategoryNotFoundException;
import com.kubra.todolist.exception.CategoryUpdateFailedException;
import com.kubra.todolist.mapper.CategoryMapper;
import com.kubra.todolist.model.Category;
import com.kubra.todolist.repository.CategoryRepository;
import com.kubra.todolist.request.CategoryRequest;
import com.kubra.todolist.response.CategoryResponse;
import com.kubra.todolist.service.TaskService;
import com.kubra.todolist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryRequest = CategoryRequest.builder()
                .id("cat-123")
                .name("Work")
                .userId("user-123")
                .build();

        category = Category.builder()
                .id("cat-123")
                .name("Work")
                .userId("user-123")
                .active(true)
                .build();

        categoryResponse = CategoryResponse.builder()
                .id("cat-123")
                .name("Work")
                .userId("user-123")
                .build();
    }

    @Test
    void createCategory_success() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toCategoryResponse(any(Category.class))).thenReturn(categoryResponse);

        CategoryResponse response = categoryService.createCategory(categoryRequest);

        assertNotNull(response);
        assertEquals("cat-123", response.getId());
        verify(userService).getUser("user-123");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void updateCategory_success() {
        when(categoryRepository.findById("cat-123")).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toCategoryResponse(any(Category.class))).thenReturn(categoryResponse);

        CategoryResponse response = categoryService.updateCategory(categoryRequest);

        assertNotNull(response);
        verify(taskService).updateCategoryNameInTasks(category);
    }

    @Test
    void updateCategory_throwsExceptionWhenSaveFails() {
        when(categoryRepository.findById("cat-123")).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenThrow(new RuntimeException());

        assertThrows(CategoryUpdateFailedException.class, () -> categoryService.updateCategory(categoryRequest));
    }

    @Test
    void deleteCategory_success() {
        when(categoryRepository.findById("cat-123")).thenReturn(Optional.of(category));

        categoryService.deleteCategory("cat-123");

        verify(taskService).removeCategoryFromTasks("cat-123");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void deleteCategory_notFound() {
        when(categoryRepository.findById("cat-123")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory("cat-123"));
    }

    @Test
    void getCategory_success() {
        when(categoryRepository.findById("cat-123")).thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        CategoryResponse response = categoryService.getCategory("cat-123");

        assertNotNull(response);
        assertEquals("cat-123", response.getId());
    }

    @Test
    void getCategory_notFound() {
        when(categoryRepository.findById("cat-123")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategory("cat-123"));
    }

    @Test
    void getAllCategories_success() {
        when(categoryRepository.findAllByUserId("user-123")).thenReturn(List.of(category));
        when(categoryMapper.toCategoryResponseList(anyList())).thenReturn(List.of(categoryResponse));

        List<CategoryResponse> result = categoryService.getAllCategories("user-123");

        assertEquals(1, result.size());
        assertEquals("cat-123", result.get(0).getId());
    }
}
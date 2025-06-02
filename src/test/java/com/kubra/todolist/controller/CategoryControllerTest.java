package com.kubra.todolist.controller;

import org.junit.jupiter.api.Test;
import com.kubra.todolist.request.CategoryRequest;
import com.kubra.todolist.response.CategoryResponse;
import com.kubra.todolist.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        CategoryRequest request = new CategoryRequest(null, "Work", "8992e641-996f-485c-b181-7a844e523439",true);
        CategoryResponse response = new CategoryResponse("ec486951-3193-4ab8-8cc9-8b77a185fb7f", "Work", "user-123",true);

        when(categoryService.createCategory(request)).thenReturn(response);

        ResponseEntity<CategoryResponse> result = categoryController.createCategory(request);

        // Assert
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("ec486951-3193-4ab8-8cc9-8b77a185fb7f", result.getBody().getId());
        verify(categoryService).createCategory(request);
    }

    @Test
    void testUpdateCategory() {
        CategoryRequest request = new CategoryRequest(null, "Work", "8992e641-996f-485c-b181-7a844e523439",true);
        CategoryResponse response = new CategoryResponse("ec486951-3193-4ab8-8cc9-8b77a185fb7f", "Work", "8992e641-996f-485c-b181-7a844e523439",true);

        when(categoryService.updateCategory(request)).thenReturn(response);

        ResponseEntity<CategoryResponse> categoryResponseResponseEntity = categoryController.updateCategory(request);

        assertEquals("Work", categoryResponseResponseEntity.getBody().getName());
        verify(categoryService).updateCategory(request);
    }

    @Test
    void testGetAllCategories() {
        String userId = "8992e641-996f-485c-b181-7a844e523439";
        List<CategoryResponse> expected = List.of(
                new CategoryResponse("ec486951-3193-4ab8-8cc9-8b77a185fb7f", "Work", userId,true),
                new CategoryResponse("ec486951-3193-4ab8-8cc9-8b77a185fb7f", "Home", userId,true)
        );

        when(categoryService.getAllCategories(userId)).thenReturn(expected);

        List<CategoryResponse> result = categoryController.getCategoryAll(userId);

        assertEquals(2, result.size());
        verify(categoryService).getAllCategories(userId);
    }

    @Test
    void testGetCategoryById() {
        String categoryId = "ec486951-3193-4ab8-8cc9-8b77a185fb7f";
        CategoryResponse category = new CategoryResponse(categoryId, "Work", "8992e641-996f-485c-b181-7a844e523439",true);

        when(categoryService.getCategory(categoryId)).thenReturn(category);

        CategoryResponse result = categoryController.getCategoryId(categoryId);

        assertEquals("Work", result.getName());
        verify(categoryService).getCategory(categoryId);
    }

    @Test
    void testDeleteCategory() {
        String categoryId = "ec486951-3193-4ab8-8cc9-8b77a185fb7f";

        ResponseEntity<Void> response = categoryController.deleteCategoryId(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryService).deleteCategory(categoryId);
    }
}
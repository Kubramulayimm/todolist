package com.kubra.todolist.mapper;

import com.kubra.todolist.model.Category;
import com.kubra.todolist.response.CategoryResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class CategoryMapperTest {

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    void testToCategoryResponse() {
        Category category = Category.builder()
                .id("1")
                .name("Work")
                .userId("123")
                .active(true)
                .deleted(false)
                .deletedAt(null)
                .build();

        CategoryResponse response = categoryMapper.toCategoryResponse(category);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("1");
        assertThat(response.getName()).isEqualTo("Work");
        assertThat(response.getUserId()).isEqualTo("123");
    }

    @Test
    void testToCategoryResponseList() {
        Category category1 = Category.builder().id("1").name("Work").userId("u1").build();
        Category category2 = Category.builder().id("2").name("Home").userId("u2").build();

        List<Category> categoryList = List.of(category1, category2);
        List<CategoryResponse> responseList = categoryMapper.toCategoryResponseList(categoryList);

        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0).getName()).isEqualTo("Work");
        assertThat(responseList.get(1).getName()).isEqualTo("Home");
    }
}
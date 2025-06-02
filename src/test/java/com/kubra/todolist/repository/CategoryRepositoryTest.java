package com.kubra.todolist.repository;

import com.kubra.todolist.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.couchbase.DataCouchbaseTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataCouchbaseTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveAndFindById() {
        // given
        Category category = Category.builder()
                .id("test-cat-1")
                .userId("user-1")
                .name("Test Category")
                .active(true)
                .deleted(false)
                .build();

        categoryRepository.save(category);

        // when
        Optional<Category> found = categoryRepository.findById("test-cat-1");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Category");
    }

    @Test
    void testFindAllByUserId() {
        // given
        Category category1 = Category.builder().id("cat1").userId("u1").name("A").build();
        Category category2 = Category.builder().id("cat2").userId("u1").name("B").build();
        Category category3 = Category.builder().id("cat3").userId("u2").name("C").build();

        categoryRepository.saveAll(List.of(category1, category2, category3));

        // when
        List<Category> userCategories = categoryRepository.findAllByUserId("u1");

        // then
        assertThat(userCategories).hasSize(2);
        assertThat(userCategories)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("A", "B");
    }
}
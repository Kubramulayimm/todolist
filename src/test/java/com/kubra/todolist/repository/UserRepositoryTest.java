package com.kubra.todolist.repository;

import com.kubra.todolist.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.couchbase.DataCouchbaseTest;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataCouchbaseTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindById() {
        // given
        User user = User.builder()
                .id("8992e641-996f-485c-b181-7a844e523438")
                .email("merve@example.com")
                .name("merve")
                .password("123458")
                .active(true)
                .build();

        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findById("8992e641-996f-485c-b181-7a844e523438");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("merve@example.com");
    }

    @Test
    void testFindByEmail() {
        // given
        User user = User.builder()
                .id("8992e641-996f-485c-b181-7a844e523430")
                .email("pınar@example.com")
                .name("Pınar")
                .password("123456")
                .active(true)
                .build();

        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByEmail("pınar@example.com");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Pınar");
    }
}
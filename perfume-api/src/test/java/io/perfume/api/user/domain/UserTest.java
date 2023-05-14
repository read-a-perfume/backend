package io.perfume.api.user.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void signup_general_user_correct() {
        String username = "user1";
        String email = "user@user.com";
        String password = "userPw1!";
        String name = "perfumer";

        User createdGenenralUser = User
                .generalUserJoin(
                        username,
                        email,
                        password,
                        name);

        assertEquals(username, createdGenenralUser.getUsername());
        assertEquals(email, createdGenenralUser.getEmail());
        assertEquals(password, createdGenenralUser.getPassword());
        assertEquals(name, createdGenenralUser.getName());
    }
}

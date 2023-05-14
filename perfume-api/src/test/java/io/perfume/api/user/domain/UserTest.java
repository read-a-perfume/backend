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

        User createdGeneralUser = User
                .generalUserJoin(
                        username,
                        email,
                        password,
                        name,
                        true,
                        true);

        assertEquals(username, createdGeneralUser.getUsername());
        assertEquals(email, createdGeneralUser.getEmail());
        assertEquals(password, createdGeneralUser.getPassword());
        assertEquals(name, createdGeneralUser.getName());
    }
}

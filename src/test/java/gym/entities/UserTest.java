package gym.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void givenValidUser_whenSettingId_thenIdShouldBeSet() {
        user.setId(1L);
        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    void givenValidUser_whenSettingFirstName_thenFirstNameShouldBeSet() {
        user.setFirstName("Alice");
        assertThat(user.getFirstName()).isEqualTo("Alice");
    }

    @Test
    void givenValidUser_whenSettingLastName_thenLastNameShouldBeSet() {
        user.setLastName("Johnson");
        assertThat(user.getLastName()).isEqualTo("Johnson");
    }

    @Test
    void givenValidUser_whenSettingUsername_thenUsernameShouldBeSet() {
        user.setUsername("alice123");
        assertThat(user.getUsername()).isEqualTo("alice123");
    }

    @Test
    void givenValidUser_whenSettingPassword_thenPasswordShouldBeSet() {
        user.setPassword("password123");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    void givenUserWithActiveTrue_whenCheckingIsActive_thenShouldReturnTrue() {
        user.setActive(true);
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void givenUserWithActiveFalse_whenCheckingIsActive_thenShouldReturnFalse() {
        user.setActive(false);
        assertThat(user.isActive()).isFalse();
    }

    @Test
    void givenUserWithAllDetails_whenCallingToString_thenExpectedStringShouldBeGenerated() {
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Johnson");
        user.setUsername("alice123");
        user.setActive(true);

        String expectedString = "User ID: 1\n" +
                "Name: Alice Johnson\n" +
                "Username: alice123\n" +
                "Active: true";

        assertThat(user.toString()).isEqualTo(expectedString);
    }
}

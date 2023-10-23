package gym.entities;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
     void testGetAndSetId() {
        user.setId(1L);
        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    void testGetAndSetFirstName() {
        user.setFirstName("Alice");
        assertThat(user.getFirstName()).isEqualTo("Alice");
    }

    @Test
    void testGetAndSetLastName() {
        user.setLastName("Johnson");
        assertThat(user.getLastName()).isEqualTo("Johnson");
    }

    @Test
    void testGetAndSetUsername() {
        user.setUsername("alice123");
        assertThat(user.getUsername()).isEqualTo("alice123");
    }

    @Test
    void testGetAndSetPassword() {
        user.setPassword("password123");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    void testIsAndSetActive() {
        user.setActive(true);
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void testToString() {
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

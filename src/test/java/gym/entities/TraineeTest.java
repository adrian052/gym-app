package gym.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TraineeTest {

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
    }

    @Test
     void testGetAndSetId() {
        trainee.setId(1L);
        assertThat(trainee.getId()).isEqualTo(1L);
    }

    @Test
    void testGetAndSetDateOfBirth() {
        Date dateOfBirth = new Date();
        trainee.setDateOfBirth(dateOfBirth);
        assertThat(trainee.getDateOfBirth()).isEqualTo(dateOfBirth);
    }

    @Test
    void testGetAndSetAddress() {
        trainee.setAddress("123 Elm St.");
        assertThat(trainee.getAddress()).isEqualTo("123 Elm St.");
    }

    @Test
    void testGetAndSetUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Johnson");

        trainee.setUser(user);

        assertThat(trainee.getUser()).isEqualTo(user);
    }

    @Test
    void testToString() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Johnson");

        trainee.setId(1L);
        Date dateOfBirth = new Date();
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress("123 Elm St.");
        trainee.setUser(user);
        String actual = trainee.toString();
        String expectedString = "Trainee ID: 1\n" +
                "Name: Alice Johnson\n" +
                "Date of Birth: " + dateOfBirth + "\n" +
                "Address: 123 Elm St.\n" +
                "User ID: 1";

        assertThat(actual).isEqualTo(expectedString);
    }
}

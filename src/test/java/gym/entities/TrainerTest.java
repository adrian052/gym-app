package gym.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TrainerTest {

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer();
    }

    @Test
    void givenValidTrainer_whenSettingId_thenIdShouldBeSet() {
        trainer.setId(1L);
        assertThat(trainer.getId()).isEqualTo(1L);
    }

    @Test
    void givenValidTrainer_whenSettingSpecialization_thenSpecializationShouldBeSet() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Strength Training");
        trainer.setSpecialization(specialization);
        assertThat(trainer.getSpecialization()).isEqualTo(specialization);
    }

    @Test
    void givenValidTrainer_whenSettingUser_thenUserShouldBeSet() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Johnson");
        trainer.setUser(user);
        assertThat(trainer.getUser()).isEqualTo(user);
    }

    @Test
    void givenTrainerWithUser_whenCallingToString_thenExpectedStringShouldBeGenerated() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Johnson");

        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Strength Training");

        trainer.setId(1L);
        trainer.setSpecialization(specialization);
        trainer.setUser(user);
        String actual = trainer.toString();
        String expectedString = "Trainer ID: 1\n" +
                "Name: Alice Johnson\n" +
                "Specialization: Strength Training\n" +
                "User ID: 1";

        assertThat(actual).isEqualTo(expectedString);
    }

    @Test
    void givenNewTrainer_whenCheckingSpecialization_thenSpecializationShouldBeNull() {
        assertThat(trainer.getSpecialization()).isNull();
    }
}



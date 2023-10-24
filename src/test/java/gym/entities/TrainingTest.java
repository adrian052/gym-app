package gym.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingTest {

    private Training training;

    @BeforeEach
    void setUp() {
        training = new Training();
    }

    @Test
    void givenValidTraining_whenSettingId_thenIdShouldBeSet() {
        training.setId(1L);
        assertThat(training.getId()).isEqualTo(1L);
    }

    @Test
    void givenValidTraining_whenSettingTrainee_thenTraineeShouldBeSet() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        training.setTrainee(trainee);
        assertThat(training.getTrainee()).isEqualTo(trainee);
    }

    @Test
    void givenValidTraining_whenSettingTrainer_thenTrainerShouldBeSet() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        training.setTrainer(trainer);
        assertThat(training.getTrainer()).isEqualTo(trainer);
    }

    @Test
    void givenValidTraining_whenSettingTrainingName_thenTrainingNameShouldBeSet() {
        training.setTrainingName("Strength Training");
        assertThat(training.getTrainingName()).isEqualTo("Strength Training");
    }

    @Test
    void givenValidTraining_whenSettingTrainingType_thenTrainingTypeShouldBeSet() {
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Cardio");
        training.setTrainingType(trainingType);
        assertThat(training.getTrainingType()).isEqualTo(trainingType);
    }

    @Test
    void givenValidTraining_whenSettingTrainingDate_thenTrainingDateShouldBeSet() {
        Date trainingDate = new Date();
        training.setTrainingDate(trainingDate);
        assertThat(training.getTrainingDate()).isEqualTo(trainingDate);
    }

    @Test
    void givenValidTraining_whenSettingTrainingDuration_thenTrainingDurationShouldBeSet() {
        training.setTrainingDuration(60);
        assertThat(training.getTrainingDuration()).isEqualTo(60);
    }

    @Test
    void givenTrainingWithAllDetails_whenCallingToString_thenExpectedStringShouldBeGenerated() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        User traineeUser = new User();
        traineeUser.setFirstName("Alice");
        traineeUser.setLastName("Johnson");
        trainee.setUser(traineeUser);

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        User trainerUser = new User();
        trainerUser.setFirstName("Bob");
        trainerUser.setLastName("Smith");
        trainer.setUser(trainerUser);

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Cardio");

        Date trainingDate = new Date();

        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Cardio Session");
        training.setTrainingType(trainingType);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(60);

        String actual = training.toString();
        String expectedString = "Training ID: 1\n" +
                "Training Name: Cardio Session\n" +
                "Training Date: " + trainingDate + "\n" +
                "Duration: 60 minutes\n" +
                "Trainee: Alice Johnson\n" +
                "Trainer: Bob Smith\n" +
                "Training Type: Cardio";

        assertThat(actual).isEqualTo(expectedString);
    }
}

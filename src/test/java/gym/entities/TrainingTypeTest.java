package gym.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingTypeTest {

    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType();
    }

    @Test
    void givenValidTrainingType_whenSettingId_thenIdShouldBeSet() {
        trainingType.setId(1L);
        assertThat(trainingType.getId()).isEqualTo(1L);
    }

    @Test
    void givenValidTrainingType_whenSettingTrainingTypeName_thenTrainingTypeNameShouldBeSet() {
        trainingType.setTrainingTypeName("Cardio");
        assertThat(trainingType.getTrainingTypeName()).isEqualTo("Cardio");
    }

    @Test
    void givenTrainingTypeWithAllDetails_whenCallingToString_thenExpectedStringShouldBeGenerated() {
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("Cardio");

        String actual = trainingType.toString();
        String expectedString = "Type ID: 1\n" +
                "Training Type: Cardio";

        assertThat(actual).isEqualTo(expectedString);
    }
}

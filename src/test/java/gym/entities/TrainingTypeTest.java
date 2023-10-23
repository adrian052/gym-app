package gym.entities;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingTypeTest {

    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        trainingType = new TrainingType();
    }

    @Test
     void testGetAndSetId() {
        trainingType.setId(1L);
        assertThat(trainingType.getId()).isEqualTo(1L);
    }

    @Test
     void testGetAndSetTrainingTypeName() {
        trainingType.setTrainingTypeName("Cardio");
        assertThat(trainingType.getTrainingTypeName()).isEqualTo("Cardio");
    }

    @Test
     void testToString() {
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("Cardio");

        String actual = trainingType.toString();
        String expectedString = "Type ID: 1\n" +
                "Training Type: Cardio";

        assertThat(actual).isEqualTo(expectedString);
    }

}

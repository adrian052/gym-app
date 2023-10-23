package gym.entities;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingTypeTest {

    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        trainingType = new TrainingType();
    }

    @Test
    public void testGetAndSetId() {
        trainingType.setId(1L);
        assertThat(trainingType.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetAndSetTrainingTypeName() {
        trainingType.setTrainingTypeName("Cardio");
        assertThat(trainingType.getTrainingTypeName()).isEqualTo("Cardio");
    }

    @Test
    public void testToString() {
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("Cardio");

        String expectedString = "Type ID: 1\n" +
                "Training Type: Cardio";

        assertThat(trainingType.toString()).isEqualTo(expectedString);
    }

}

package gym.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import gym.dao.DataAccessObject;
import gym.entities.TrainingType;
import gym.service.TrainingTypeService;

import java.util.List;

public class TrainingTypeServiceTest {

    @InjectMocks
    private TrainingTypeService trainingTypeService;

    @Mock
    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSelect_ShouldReturnTrainingTypeById() {
        TrainingType type = new TrainingType();
        type.setId(1L);
        type.setTrainingTypeName("Type 1");

        when(trainingTypeDAO.findById(1L)).thenReturn(type);

        TrainingType result = trainingTypeService.select(1L);

        assertThat(result).isEqualTo(type);
    }


}

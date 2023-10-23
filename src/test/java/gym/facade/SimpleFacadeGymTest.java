package gym.facade;

import gym.entities.*;
import gym.facade.SimpleFacadeGym;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.Date;

import gym.service.GymService;
import org.assertj.core.api.Assertions;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleFacadeGymTest {

    @InjectMocks
    private SimpleFacadeGym facadeGym;

    @Mock
    private GymService<Trainee> traineeService;

    @Mock
    private GymService<Trainer> trainerService;

    @Mock
    private GymService<Training> trainingService;

    @Mock
    private GymService<User> userService;

    @Mock
    private GymService<TrainingType> trainingTypeService;

    @BeforeEach
    public void setUp() {
        // Inicializa la fachada con las dependencias simuladas
        MockitoAnnotations.initMocks(this);
        facadeGym = new SimpleFacadeGym(
                traineeService,
                trainerService,
                trainingService,
                userService,
                trainingTypeService
        );

    }
    @Test
    public void testDeleteTrainee() {
        traineeService.delete(1L);
        boolean result = facadeGym.deleteTrainee(1L);
        assertThat(result).isTrue();
        Mockito.verify(traineeService).delete(1L);
    }
}
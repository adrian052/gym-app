package gym.service;


import gym.dao.DataAccessObject;

import gym.entities.*;
import gym.service.simple.TrainingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TrainingServiceImplTest {
    @InjectMocks
    private TrainingService trainingService = new TrainingServiceImpl();

    @Mock
    private DataAccessObject<Trainer> trainerDAO;

    @Mock
    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Mock
    private DataAccessObject<Trainee> traineeDAO;

    @Mock
    private DataAccessObject<Training> trainingDAO;

/*
    @Test
    public void givenValidRequest_whenCreateTraining_thenShouldReturnTraining(){
        //arrange
        User mockUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra","password",true);
        TrainingType trainingType = new TrainingType(1L, "HipTrust");
        Trainee trainee = new Trainee(1L, DateUtil.date(2022,2,2),"address",mockUser);
        Trainer trainer = new Trainer(1L,trainingType, mockUser);
        when(traineeDAO.findById(1L)).thenReturn(trainee);
        when(trainerDAO.findById(1L)).thenReturn(trainer);
        when(trainingTypeDAO.findById(1L)).thenReturn(trainingType);
        when(trainingDAO.save(any(Training.class)))
                .thenReturn(new Training(1L,
                        trainee,trainer,
                        "TrainingName",
                        trainingType,DateUtil.date(2022, 2,2),
                        60));
        //act
        Training newTraining = trainingService.create(1L,1L,"TrainingName",
                1L, DateUtil.date(2022, 2,2),60);
        //assert
        assertThat(newTraining).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "TrainingName")
                .hasFieldOrPropertyWithValue("trainingDate",DateUtil.date(2022, 2,2))
                .hasFieldOrPropertyWithValue("trainingDuration", 60);

    }*/
/*
    @Test
    public void givenRequestWithNullValues_whenCreateTraining_thenShouldThrownAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> trainingService
                .create(null, null, null,null, null, 60 ));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("(traineeId, trainerId, trainingName, trainingTypeId, trainingDate) are not allowed to be null");
    }
*/
    @Test
    public void givenRequestWithNullValues_whenSelectTraining_thenShouldThrownAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> trainingService
                .select(null ));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("(id) is not allowed to be null");
    }
}

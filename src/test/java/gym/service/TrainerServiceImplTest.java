package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.Trainer;
import gym.entities.TrainingType;

import gym.entities.User;
import gym.service.simple.TrainerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainerServiceImplTest {
    @InjectMocks
    private TrainerService trainerService = new TrainerServiceImpl();
    @Mock
    private DataAccessObject<Trainer> trainerDAO;

    @Mock
    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Mock
    private DataAccessObject<User> userDAO;

    @Test
    public void givenValidRequest_whenCreateTrainer_thenTrainerShouldBeReturned() {
        //Arrange
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;

        User user = new User(1L,firstName,lastName,firstName+"."+lastName,"XSA29",true);
        TrainingType trainingType = new TrainingType(1L, "HipTrust");
        when(trainingTypeDAO.findById(1L))
                .thenReturn(trainingType);
        when(trainerDAO.save(any()))
                .thenReturn(new Trainer(1L, trainingType,user));
        //act
        Credentials trainerCredentials = trainerService.create(firstName,lastName,isActive,1L);
        //assert
        assertEquals("John.Doe", trainerCredentials.username());

    }


    @Test
    public void givenRequestWithNullValues_whenCreateTrainer_thenShouldThrowAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> trainerService.create(null,null,true,null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("firstName cannot be null");

    }


    @Test
    public void givenRequestWithNullValues_whenUpdateTrainee_thenUserShouldThrownAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> trainerService.update(new Credentials("mock", "mock"),null, null,null,true,null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id cannot be null");
    }

}

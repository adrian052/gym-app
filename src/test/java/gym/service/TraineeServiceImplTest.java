package gym.service;

import gym.dao.DataAccessObject;
import gym.dao.DateUtil;
import gym.entities.Trainee;
import gym.entities.User;
import gym.service.simple.TraineeServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TraineeServiceImplTest {
    @InjectMocks
    private TraineeService traineeService = new TraineeServiceImpl();
    @Mock
    private DataAccessObject<Trainee> traineeDAO;
    @Mock
    private DataAccessObject<User> userDAO;

    @Test
    public void givenValidRequest_whenCreateTrainee_thenTraineeShouldBeReturned() {
        //Arrange
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Date dateOfBirth = DateUtil.date(2022, 2, 2);
        String address = "Address";
        User user = new User(1L,firstName,lastName,firstName+"."+lastName,"XSA29",true);
        when(traineeDAO.save(any(Trainee.class)))
                .thenReturn(new Trainee(1L, dateOfBirth,address,user));
        //act
        Trainee trainee = traineeService.create(firstName, lastName, isActive, dateOfBirth, address);
        //assert
        assertThat(trainee).isNotNull()
                .hasFieldOrPropertyWithValue("id",1L )
                .hasFieldOrPropertyWithValue("user",user)
                .hasFieldOrPropertyWithValue("dateOfBirth",dateOfBirth)
                .hasFieldOrPropertyWithValue("address",address);
    }


    @Test
    public void givenRequestWithNullValues_whenCreateTrainee_thenShouldThrownAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> traineeService.create(null, null,
                true, DateUtil.date(2022, 2, 2), "Address"));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("(firstName, lastName) are not allowed to be null");
    }
/*
    @Test
    public void givenRequestWithNullValues_whenUpdateTrainee_thenUserShouldThrownAnException() {
        //Arrange

        //act
        Throwable thrown = catchThrowable(() -> traineeService.update(null,null, null,
                true, DateUtil.date(2022, 2, 2), "Address"));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("(id, firstName, lastName) are not allowed to be null");
    }
*/
    /*
    @Test
    public void givenValidRequest_whenUpdateTrainee_thenUserShouldBeReturned() {
        //Arrange
        User user = new User(1L,"John","Doe","John.Doe","XS29",true);
        when(traineeDAO.findById(1L))
                .thenReturn(new Trainee(1L, DateUtil.date(2022, 2, 20),"address",user));
        when(traineeDAO.save(any(Trainee.class)))
                .thenReturn(new Trainee(1L, DateUtil.date(2022, 2, 20),"address2",user));

        //act
        Trainee trainee = traineeService.update(1L, "John","Doe"
                , true, DateUtil.date(2022, 2, 20),"address2");
        //assert
        assertThat(trainee).isNotNull()
                .hasFieldOrPropertyWithValue("id",1L )
                .hasFieldOrPropertyWithValue("user",user)
                .hasFieldOrPropertyWithValue("dateOfBirth",DateUtil.date(2022, 2, 20))
                .hasFieldOrPropertyWithValue("address","address2");
    }*/
/*
    @Test
    public void givenRequestWithTraineeNotSaved_whenUpdateTrainee_thenThrowAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> traineeService.update(1L, "John","Doe"
                , true, DateUtil.date(2022, 2, 20),"address2"));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Unable to update the trainee, entity not found");
    }
*/
    /*
    @Test
    public void givenInvalidRequest_whenDelete_thenThrowAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> traineeService.delete(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("(id) is not allowed to be null");
    }
*/
    @Test
    public void givenInvalidRequest_whenSelect_thenThrowAnException() {
        //Arrange
        //act
        Throwable thrown = catchThrowable(() -> traineeService.select(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("(id) is not allowed to be null");
    }

}

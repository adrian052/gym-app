package gym.dao;

import gym.entities.Entity;
import gym.storage.GymStorage;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class DAOTest<T extends Entity> {

    @InjectMocks
    protected DataAccessObject<T> dao;

    @Mock
    protected GymStorage storage;
}
package gym.dao;

import gym.dao.DataAccessObject;
import gym.dao.TrainingTypeDAO;
import gym.entities.TrainingType;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingTypeDAOTest {

    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        trainingTypeDAO = new TrainingTypeDAO();
        ((TrainingTypeDAO) trainingTypeDAO).setStorage(storage);
    }

    @Test
    public void save_ShouldAddTrainingTypeToStorage() {
        TrainingType type = createTrainingType(1L, "Type1");
        trainingTypeDAO.save(type);
        assertThat(trainingTypeDAO.findById(1L)).isEqualTo(type);
    }

    @Test
    public void findById_ShouldReturnTrainingTypeById() {
        TrainingType type = createTrainingType(1L, "Type1");
        trainingTypeDAO.save(type);
        TrainingType result = trainingTypeDAO.findById(1L);
        assertThat(result).isEqualTo(type);
    }

    @Test
    public void findAll_ShouldReturnAllTrainingTypes() {
        TrainingType type1 = createTrainingType(1L, "Type1");
        TrainingType type2 = createTrainingType(2L, "Type2");
        trainingTypeDAO.save(type1);
        trainingTypeDAO.save(type2);
        List<TrainingType> result = trainingTypeDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(type1, type2);
    }

    @Test
    public void delete_ShouldRemoveTrainingTypeFromStorage() {
        TrainingType type = createTrainingType(1L, "Type1");
        trainingTypeDAO.save(type);
        trainingTypeDAO.delete(1L);
        assertThat(trainingTypeDAO.findById(1L)).isNull();
    }

    private TrainingType createTrainingType(Long id, String name) {
        TrainingType type = new TrainingType();
        type.setId(id);
        type.setTrainingTypeName(name);
        return type;
    }
}

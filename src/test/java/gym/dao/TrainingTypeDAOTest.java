package gym.dao;

import gym.dao.DataAccessObject;
import gym.entities.TrainingType;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingTypeDAOTest {

    private GenericDAO<TrainingType> trainingTypeDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        trainingTypeDAO = new TrainingTypeDAO();
        ((TrainingTypeDAO) trainingTypeDAO).setStorage(storage);
    }

    @Test
    public void givenTrainingTypeDAOWithInitializedData_whenSaveTrainingType_thenTrainingTypeShouldBeAddedToStorage() {
        TrainingType type = createTrainingType("Type1");
        Long newId = trainingTypeDAO.save(type);
        assertThat(trainingTypeDAO.findById(newId)).isEqualTo(type);
    }

    @Test
    public void givenTrainingTypeDAOWithInitializedData_whenFindTrainingTypeById_thenShouldReturnTrainingType() {
        TrainingType type = createTrainingType("Type1");
        Long newId = trainingTypeDAO.save(type);
        TrainingType result = trainingTypeDAO.findById(newId);
        assertThat(result).isEqualTo(type);
    }

    @Test
    public void givenTrainingTypeDAOWithInitializedData_whenFindAllTrainingTypes_thenShouldReturnAllTrainingTypes() {
        TrainingType type1 = createTrainingType("Type1");
        TrainingType type2 = createTrainingType("Type2");
        trainingTypeDAO.save(type1);
        trainingTypeDAO.save(type2);
        List<TrainingType> result = trainingTypeDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(type1, type2);
    }

    @Test
    public void givenTrainingTypeInStorage_whenSaveTrainingTypeWithoutId_thenTrainingTypeShouldBeAddedWithNewId() {
        TrainingType type1 = createTrainingType("Type1");
        Long newId = trainingTypeDAO.save(type1);
        assertThat(newId).isNotNull();
        TrainingType result = trainingTypeDAO.findById(newId);
        assertThat(result).isEqualTo(type1);
    }

    @Test
    public void givenTrainingTypeInStorage_whenDeleteNonExistentTrainingType_thenShouldReturnFalse() {
        boolean deleted = trainingTypeDAO.delete(123L); // Assuming 123L doesn't exist
        assertThat(deleted).isFalse();
    }

    private TrainingType createTrainingType(String name) {
        TrainingType type = new TrainingType();
        type.setTrainingTypeName(name);
        return type;
    }
}

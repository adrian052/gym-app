package gym.dao;

import gym.entities.TrainingType;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TrainingTypeDAOTest extends DAOTest<TrainingType>{
    @Override
    protected DataAccessObject<TrainingType> getInstance() {
        return new TrainingTypeDAO();
    }

    @Override
    protected void configureOwnMap(Map<Long, TrainingType> mockMap) {
        when(storage.getTrainingTypes()).thenReturn(mockMap);
    }

    @Override
    protected TrainingType entityWithDependencies(Long id) {
        return new TrainingType(id,"Hip Trust");
    }

    @Override
    protected TrainingType entityWithNullValues(Long id) {
        return new TrainingType(id, null);
    }

    //FindById
    @Test
    public void givenTrainingTypeMapWithTrainingTypeId1_whenFindById1_thenReturnCorrectTrainingType() {
        //arrange
        Map<Long, TrainingType> mapWithTrainingTypes = new HashMap<>();
        TrainingType expectedTrainingType = new TrainingType(1L, "Hip Trust");
        mapWithTrainingTypes.put(1L, expectedTrainingType);
        configureOwnMap(mapWithTrainingTypes);
        //act
        TrainingType actualTrainingType = dao.findById(1L);
        //assert
        assertThat(actualTrainingType).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainingTypeName", "Hip Trust");
    }
    //FindAll
    @Test
    public void givenTrainingTypeMapWithSomeTrainingTypes_whenFindAll_thenReturnListWithTheSameTrainingTypes() {
        //arrange
        TrainingType trainingType1 = new TrainingType(1L, "Run");
        TrainingType trainingType2 = new TrainingType(2L, "HipTrust");
        TrainingType trainingType3 = new TrainingType(3L, "Jump");
        configureOwnMap(new HashMap<>(Map.of(1L,trainingType1,2L, trainingType2, 3L, trainingType3)));
        //act
        List<TrainingType> trainingTypes = dao.findAll();
        //assert
        assertThat(trainingTypes).isNotNull().hasSize(3);
        assertThat(trainingTypes.get(0)).usingRecursiveComparison().isEqualTo(trainingType1);
        assertThat(trainingTypes.get(1)).usingRecursiveComparison().isEqualTo(trainingType2);
        assertThat(trainingTypes.get(2)).usingRecursiveComparison().isEqualTo(trainingType3);
    }
    //Save
    @Test
    public void givenTrainingTypeWithoutId_whenSaveTrainingType_thenReturnIdNumber1OfAValidTrainingType() {
        //arrange
        Long expectedId = 1L;
        TrainingType expectedTrainingType = new TrainingType(null, "Hip Trust");
        when(storage.getNextId(TrainingType.class)).thenReturn(expectedId);
        configureOwnMap(new HashMap<>());
        //act
        TrainingType actualTrainingType = dao.save(expectedTrainingType);
        //assert
        assertThat(actualTrainingType).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedId)
                .hasFieldOrPropertyWithValue("trainingTypeName", "Hip Trust");
    }

    @Test
    public void givenValidTrainingTypeCreatedBefore_whenSaveTrainingType_thenReturnAValidTrainingTypeUpdated() {
        //arrange
        Long expectedId = 1L;
        TrainingType trainingTypeBefore = new TrainingType(expectedId, "Hip Trust");
        TrainingType expectedTrainingType = new TrainingType(expectedId, "Run");
        configureOwnMap(new HashMap<>(Map.of(1L,trainingTypeBefore)));
        //act
        TrainingType actualTrainingType = dao.save(expectedTrainingType);
        //assert
        assertThat(actualTrainingType).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedId)
                .hasFieldOrPropertyWithValue("trainingTypeName", "Run");
    }
}
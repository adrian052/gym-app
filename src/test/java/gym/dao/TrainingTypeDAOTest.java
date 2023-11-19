package gym.dao;

import gym.entities.TrainingType;
import gym.storage.GymStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainingTypeDAOTest {

    @InjectMocks
    private DataAccessObject<TrainingType> trainingTypeDAO  = new TrainingTypeDAO();

    @Mock
    private GymStorage storage;

    // Tests for FindById
    @Test
    public void givenNullId_whenFindById_thenThrowsExceptionWithCorrectMessage() {
        //arrange

        //act
        Throwable thrown = catchThrowable(() -> trainingTypeDAO.findById(null));
        //assert
        assertThatThrownBy(() -> { throw thrown; })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to findById: Entity must not be null");
    }

    @Test
    public void givenEmptyTrainingTypeMap_whenFindByIdWithNonexistentId_thenReturnsNull() {
        //arrange
        when(storage.getTrainingTypes()).thenReturn(Collections.emptyMap());
        //act
        TrainingType actualTrainingType = trainingTypeDAO.findById(0L);
        //assert
        assertThat(actualTrainingType).isNull();
    }

    @Test
    public void givenTrainingTypeMapWithTrainingTypeId1_whenFindById1_thenReturnCorrectTrainingType() {
        //arrange
        Map<Long, TrainingType> mapWithTrainingTypes = new HashMap<>();
        TrainingType expectedTrainingType = new TrainingType(1L, "Hip Trust");
        mapWithTrainingTypes.put(1L, expectedTrainingType);
        when(storage.getTrainingTypes()).thenReturn(mapWithTrainingTypes);
        //act
        TrainingType actualTrainingType = trainingTypeDAO.findById(1L);
        //assert
        assertThat(actualTrainingType).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainingTypeName", "Hip Trust");
    }

    @Test
    public void givenEmptyTrainingTypeMap_whenFindAll_thenReturnEmptyTrainingTypeList() {
        //arrange
        when(storage.getTrainingTypes()).thenReturn(Collections.emptyMap());
        //act
        List<TrainingType> trainingTypes = trainingTypeDAO.findAll();
        //assert
        assertThat(trainingTypes).isNotNull().isEmpty();
    }

    @Test
    public void givenTrainingTypeMapWithSomeTrainingTypes_whenFindAll_thenReturnListWithTheSameTrainingTypes() {
        //arrange
        Map<Long, TrainingType> trainingTypeMap = new HashMap<>();
        TrainingType trainingType1 = new TrainingType(1L, "Run");
        TrainingType trainingType2 = new TrainingType(2L, "HipTrust");
        TrainingType trainingType3 = new TrainingType(3L, "Jump");
        trainingTypeMap.put(1L, trainingType1);
        trainingTypeMap.put(2L, trainingType2);
        trainingTypeMap.put(3L, trainingType3);
        when(storage.getTrainingTypes()).thenReturn(trainingTypeMap);
        //act
        List<TrainingType> trainingTypes = trainingTypeDAO.findAll();
        //assert
        assertThat(trainingTypes).isNotNull().hasSize(3);
        assertThat(trainingTypes.get(0)).usingRecursiveComparison().isEqualTo(trainingType1);
        assertThat(trainingTypes.get(1)).usingRecursiveComparison().isEqualTo(trainingType2);
        assertThat(trainingTypes.get(2)).usingRecursiveComparison().isEqualTo(trainingType3);
    }

    @Test
    public void givenNullId_whenDelete_thenThrowsExceptionWithCorrectMessage() {
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> trainingTypeDAO.delete(null));
        //assert
        assertThatThrownBy(() -> { throw thrown; })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to delete entity with null id");
    }

    @Test
    public void givenEmptyTrainingTypeMap_whenDelete_thenReturnFalse() {
        //arrange
        when(storage.getTrainingTypes()).thenReturn(Collections.emptyMap());
        //act
        boolean result = trainingTypeDAO.delete(1L);
        //assert
        assertThat(result).isFalse();
    }

    @Test
    public void givenTrainingTypeMapWithElementId1_whenDeleteId1_thenReturnTrue() {
        //arrange
        Map<Long, TrainingType> trainingTypeMap = new HashMap<>();
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        trainingTypeMap.put(1L, trainingType);
        when(storage.getTrainingTypes()).thenReturn(trainingTypeMap);
        //act
        assertThat(trainingTypeDAO.findById(1L)).isNotNull();
        boolean result = trainingTypeDAO.delete(1L);
        //assert
        assertThat(result).isTrue();
        assertThat(trainingTypeDAO.findById(1L)).isNull();
    }

    @Test
    public void givenNullTrainingType_whenSave_thenThrowIllegalArgumentException() {
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> trainingTypeDAO.save(null));
        //assert
        assertThatThrownBy(() -> { throw thrown; })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Entity must not be null");
    }

    @Test
    public void givenTrainingTypeWithNullFields_whenSaveTrainingType_thenThrowIllegalArgumentException() {
        //arrange
        TrainingType trainingType = new TrainingType(null, null);
        //act
        Throwable thrown = catchThrowable(() -> trainingTypeDAO.save(trainingType));
        //assert
        assertThatThrownBy(() -> { throw thrown; })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Null values are not allowed for certain attributes");
    }

    @Test
    public void givenTrainingTypeWithoutId_whenSaveTrainingType_thenReturnIdNumber1OfAValidTrainingType() {
        //arrange
        Long expectedId = 1L;
        TrainingType expectedTrainingType = new TrainingType(null, "Hip Trust");
        when(storage.getNextId(TrainingType.class)).thenReturn(expectedId);
        Map<Long, TrainingType> trainingTypeMap = new HashMap<>();
        when(storage.getTrainingTypes()).thenReturn(trainingTypeMap);
        //act
        TrainingType actualTrainingType = trainingTypeDAO.save(expectedTrainingType);
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
        Map<Long, TrainingType> trainingTypeMap = new HashMap<>();
        trainingTypeMap.put(expectedId, trainingTypeBefore);
        when(storage.getTrainingTypes()).thenReturn(trainingTypeMap);
        //act
        TrainingType actualTrainingType = trainingTypeDAO.save(expectedTrainingType);
        //assert
        assertThat(actualTrainingType).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedId)
                .hasFieldOrPropertyWithValue("trainingTypeName", "Run");
    }
}
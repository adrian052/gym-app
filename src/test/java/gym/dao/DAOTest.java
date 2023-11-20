package gym.dao;

import gym.entities.Entity;
import gym.storage.GymStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@RunWith(MockitoJUnitRunner.class)
public abstract class DAOTest<T extends Entity> {
    @InjectMocks
    protected DataAccessObject<T> dao = getInstance();
    @Mock
    protected GymStorage storage;
    protected abstract DataAccessObject<T> getInstance();
    protected abstract void configureOwnMap(Map<Long, T> mockMap);

    protected abstract T entityWithDependencies(Long id);

    protected abstract T entityWithNullValues(Long id);
    @Test
    public void givenNullId_whenFindById_thenThrowsExceptionWithCorrectMessage() {
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> dao.findById(null));
        //assert
        assertThatThrownBy(() -> {
            throw thrown;
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to findById: Entity must not be null");
    }

    @Test
    public void givenEmptyEntityMap_whenFindByIdWithNonexistentId_thenReturnsNull() {
        //arrange
        //act
        Entity actualEntity = dao.findById(0L);
        //assert
        assertThat(actualEntity).isNull();
    }

    @Test
    public void givenEmptyEntityMap_whenFindAll_thenReturnEmptyEntityList() {
        //arrange
        configureOwnMap(Collections.emptyMap());
        //act
        List<T> elements = dao.findAll();
        //assert
        assertThat(elements).isNotNull().isEmpty();
    }

    @Test
    public void givenNullId_whenDelete_thenThrowsExceptionWithCorrectMessage(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> dao.delete(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to delete entity with null id");
    }

    @Test
    public void givenEmptyEntityMap_whenDelete_thenReturnFalse(){
        //arrange
        configureOwnMap(Collections.emptyMap());
        //act
        boolean result = dao.delete(1L);
        //assert
        assertThat(result).isFalse();
    }

    @Test
    public void givenTraineeMapWithElementId1_whenDeleteId1_thenReturnTrue(){
        //arrange
        configureOwnMap(new HashMap<>(Map.of(1L, entityWithDependencies(1L))));
        //act
        assertThat(dao.findById(1L)).isNotNull();
        boolean result = dao.delete(1L);
        //assert
        assertThat(result).isTrue();
        assertThat(dao.findById(1L)).isNull();
    }

    @Test
    public void givenNullEntity_whenSave_thenThrowIllegalArgumentException(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> dao.save(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Entity must not be null");
    }

    @Test
    public void givenUserWithNullFields_whenSaveEntity_thenThrowIllegalArgumentException(){
        //arrange
        T entity = entityWithNullValues(1L);
        //act
        Throwable thrown = catchThrowable(() -> dao.save(entity));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Null values are not allowed for certain attributes");
    }

}
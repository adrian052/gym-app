package gym.dao;

import gym.entities.Identifiable;
import java.util.List;

public interface DataAccessObject<T extends Identifiable> {

    List<T> findAll();

    T findById(Long id);

    T save(T entity);

    boolean delete(Long id);

}

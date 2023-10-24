package gym.dao;

import java.util.List;

public interface DataAccessObject<T> {
    List<T> findAll();
    T findById(Long id);
    Long save(T entity);
    boolean delete(Long id);
}
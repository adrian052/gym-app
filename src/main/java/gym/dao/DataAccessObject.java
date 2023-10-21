package gym.dao;

import java.util.List;

public interface DataAccessObject<T> {
    List<T> findAll();
    T findById(Long id);
    void save(T entity);
    void delete(Long id);
}
package gym.service;
import java.util.List;

public interface GymService<T> {
    T select(Long id);
    Long create(T entity);
    void update(T entity);
    void delete(Long id);
    List<T> selectAll();
}

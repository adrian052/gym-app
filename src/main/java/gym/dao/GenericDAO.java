package gym.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public abstract class GenericDAO<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    protected abstract Map<Long, T> getEntityMap();


    public List<T> findAll() {
        return new ArrayList<>(getEntityMap().values());
    }

    public T findById(Long id) {
        if (id == null) {
            return null;
        }
        return getEntityMap().get(id);
    }

    public Long save(T entity) {
        Long entityId = getId(entity);
        if (entityId == null) {
            entityId = getNewId();
            setId(entity, entityId);
            logger.info("Saved new entity with ID: {}", entityId);
        } else {
            logger.info("Updated entity with ID: {}", entityId);
        }
        getEntityMap().put(entityId, entity);
        return entityId;
    }

    public boolean delete(Long id) {
        T removedEntity = getEntityMap().remove(id);
        if (removedEntity != null) {
            logger.info("Deleted entity with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete entity with ID: {} - Entity not found.", id);
            return false;
        }
    }

    protected abstract Long getId(T entity);

    protected abstract void setId(T entity, Long id);

    private Long getNewId() {
        return getEntityMap().size() + 1L;      }
}

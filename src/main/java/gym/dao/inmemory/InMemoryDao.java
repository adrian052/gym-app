package gym.dao.inmemory;

import gym.service.ValidationUtil;
import gym.entities.Identifiable;
import gym.dao.inmemory.storage.GymStorage;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class InMemoryDao<T extends Identifiable>{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected GymStorage storage;

    @Autowired
    public void setStorage(GymStorage storage){
        this.storage = storage;
    }

    public List<T> findAll() {
        return new ArrayList<>(getEntityMap().values());
    }

    public T findById(Long id) {
        ValidationUtil.validateNotNull(Map.of("id",id));
        return getEntityMap().get(id);
    }

    public T save(T entity) {
        ValidationUtil.validateNotNull(Map.of("entity",entity));
        if(!validateNotNull(entity)) {
            logger.error("Failed to save: Null values are not allowed for certain attributes");
            throw new IllegalArgumentException("Failed to save: Null values are not allowed for certain attributes");
        }
        if(!hasReferentialIntegrity(entity)) {
            logger.error("Failed to save: Data integrity violation while creating/updating the entity.");
            throw new DataIntegrityViolationException("Failed to save: Data integrity violation while creating/updating the entity.");
        }

        Long entityId = entity.getId();
        if (entityId == null) {
            entityId = getNewId(entity);
            entity.setId(entityId);
            logger.info("Saved new entity with ID: {}", entityId);
        } else {
            if(!getEntityMap().containsKey(entityId)){
                logger.error("Failed to update: entity with ID not found: {}", entityId);
                throw new EntityNotFoundException("Failed to update: entity with ID not found:"+ entityId);
            }
            logger.info("Updated entity with ID: {}", entityId);
        }
        getEntityMap().put(entityId, entity);
        return getEntityMap().get(entityId);
    }

    public boolean delete(Long id) {
        ValidationUtil.validateNotNull(Map.of("id",id));
        T removedEntity = getEntityMap().remove(id);
        if (removedEntity != null) {
            logger.info("Deleted entity with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete entity with ID: {} - Entity not found.", id);
            return false;
        }
    }

    protected boolean isEntityIdInMap(Identifiable identifiable, Map<Long, ? extends Identifiable> entityMap) {
        return identifiable != null && entityMap.containsKey(identifiable.getId());
    }

    private Long getNewId(T entity) {
        return storage.getNextId(entity.getClass());
    }

    protected abstract Map<Long, T> getEntityMap();

    protected abstract boolean hasReferentialIntegrity(T entity);

    protected abstract boolean validateNotNull(T entity);

}

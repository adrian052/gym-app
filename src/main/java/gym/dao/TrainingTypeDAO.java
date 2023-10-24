package gym.dao;

import gym.entities.TrainingType;
import gym.storage.GymStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingTypeDAO implements DataAccessObject<TrainingType> {
    private GymStorage storage;

    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeDAO.class);

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<TrainingType> findAll() {
        return new ArrayList<>(storage.getTrainingTypes().values());
    }

    @Override
    public TrainingType findById(Long id) {
        return storage.getTrainingTypes().get(id);
    }

    @Override
    public Long save(TrainingType trainingType) {
        if (trainingType.getId() != null) {
            storage.getTrainingTypes().put(trainingType.getId(), trainingType);
            logger.info("Updated TrainingType with ID: {}", trainingType.getId());
            return trainingType.getId();
        } else {
            Long newId = storage.getNextTrainingTypeId();
            trainingType.setId(newId);
            storage.getTrainingTypes().put(newId, trainingType);
            logger.info("Saved TrainingType with new ID: {}", newId);
            return newId;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (storage.getTrainingTypes().containsKey(id)) {
            storage.getTrainingTypes().remove(id);
            logger.info("Deleted TrainingType with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete TrainingType with ID: {} - TrainingType not found.", id);
            return false;
        }
    }
}

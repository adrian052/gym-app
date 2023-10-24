package gym.dao;

import gym.entities.Training;
import gym.storage.GymStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingDAO implements DataAccessObject<Training> {
    private GymStorage storage;

    private static final Logger logger = LoggerFactory.getLogger(TrainingDAO.class);

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(storage.getTrainings().values());
    }

    @Override
    public Training findById(Long id) {
        return storage.getTrainings().get(id);
    }

    @Override
    public Long save(Training training) {
        if (training.getId() != null) {
            storage.getTrainings().put(training.getId(), training);
            logger.info("Updated Training with ID: {}", training.getId());
            return training.getId();
        } else {
            Long newId = storage.getNextTrainingId();
            training.setId(newId);
            storage.getTrainings().put(newId, training);
            logger.info("Saved Training with new ID: {}", newId);
            return newId;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (storage.getTrainings().containsKey(id)) {
            storage.getTrainings().remove(id);
            logger.info("Deleted Training with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete Training with ID: {} - Training not found.", id);
            return false;
        }
    }
}

package gym.dao;

import gym.entities.Trainee;
import gym.storage.GymStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TraineeDAO implements DataAccessObject<Trainee> {
    private GymStorage storage;

    private static final Logger logger = LoggerFactory.getLogger(TraineeDAO.class);

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(storage.getTrainees().values());
    }

    @Override
    public Trainee findById(Long id) {
        return storage.getTrainees().get(id);
    }

    @Override
    public Long save(Trainee trainee) {
        if (trainee.getId() != null) {
            storage.getTrainees().put(trainee.getId(), trainee);
            logger.info("Updated Trainee with ID: {}", trainee.getId());
            return trainee.getId();
        } else {
            Long newId = storage.getNextTraineeId();
            trainee.setId(newId);
            storage.getTrainees().put(newId, trainee);
            logger.info("Saved new Trainee with ID: {}", newId);
            return newId;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (storage.getTrainees().containsKey(id)) {
            storage.getTrainees().remove(id);
            logger.info("Deleted Trainee with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete Trainee with ID: {} - Trainee not found.", id);
            return false;
        }
    }
}

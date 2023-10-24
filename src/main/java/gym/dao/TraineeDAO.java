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
        if(id == null){
            return null;
        }
        return storage.getTrainees().get(id);
    }

    @Override
    public Long save(Trainee trainee) {
        if (trainee.getId() == null) {
            Long newId = storage.getNextTraineeId();
            trainee.setId(newId);
            logger.info("Saved new Trainee with ID: {}", newId);
        } else {
            logger.info("Updated Trainee with ID: {}", trainee.getId());
        }
        storage.getTrainees().put(trainee.getId(), trainee);
        return trainee.getId();
    }

    @Override
    public boolean delete(Long id) {
        Trainee removedTrainee = storage.getTrainees().remove(id);
        if (removedTrainee != null) {
            logger.info("Deleted Trainee with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete Trainee with ID: {} - Trainee not found.", id);
            return false;
        }
    }
}

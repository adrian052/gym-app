package gym.dao;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.storage.GymStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainerDAO implements DataAccessObject<Trainer> {
    private GymStorage storage;

    private static final Logger logger = LoggerFactory.getLogger(TrainerDAO.class);

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(storage.getTrainers().values());
    }

    @Override
    public Trainer findById(Long id) {
        if (id == null) {
            return null;
        }
        return storage.getTrainers().get(id);
    }

    @Override
    public Long save(Trainer trainer) {
        if (trainer.getId() == null) {
            Long newId = storage.getNextTrainerId();
            trainer.setId(newId);
            logger.info("Saved new Trainer with ID: {}", newId);
        } else {
            logger.info("Updated Trainer with ID: {}", trainer.getId());
        }
        storage.getTrainers().put(trainer.getId(), trainer);
        return trainer.getId();
    }

    @Override
    public boolean delete(Long id) {
        Trainer removedTrainer = storage.getTrainers().remove(id);
        if (removedTrainer != null) {
            logger.info("Deleted Trainer with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete Trainer with ID: {} - Trainer not found.", id);
            return false;
        }
    }
}

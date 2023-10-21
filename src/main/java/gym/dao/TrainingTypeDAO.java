package gym.dao;

import gym.entities.TrainingType;
import gym.storage.GymStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingTypeDAO implements DataAccessObject<TrainingType>{
    private GymStorage storage;
    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    public List<TrainingType> findAll() {
        return new ArrayList<>(storage.getTrainingTypes().values());
    }

    public TrainingType findById(Long id) {
        return storage.getTrainingTypes().get(id);
    }

    public void save(TrainingType trainingType) {
        storage.getTrainingTypes().put(trainingType.getId(), trainingType);
    }

    public void delete(Long id) {
        storage.getTrainingTypes().remove(id);
    }
}

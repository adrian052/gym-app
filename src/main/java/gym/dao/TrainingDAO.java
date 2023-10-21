package gym.dao;

import gym.entities.Training;
import gym.storage.GymStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TrainingDAO implements DataAccessObject<Training> {
    @Autowired
    private GymStorage storage;

    public List<Training> findAll() {
        return new ArrayList<>(storage.getTrainings().values());
    }

    public Training findById(Long id) {
        return storage.getTrainings().get(id);
    }

    public void save(Training training) {
        storage.getTrainings().put(training.getId(), training);
    }

    public void delete(Long id) {
        storage.getTrainings().remove(id);
    }
}

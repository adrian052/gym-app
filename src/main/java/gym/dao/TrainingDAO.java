package gym.dao;

import gym.entities.Training;
import gym.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TrainingDAO {

    private Map<Long, Training> trainingMap;

    @Autowired
    public TrainingDAO(InMemoryStorage storage) {
        this.trainingMap = storage.getTrainings();
    }

    public List<Training> findAll() {
        return new ArrayList<>(trainingMap.values());
    }

    public Training findById(Long id) {
        return trainingMap.get(id);
    }

    public void save(Training training) {
        trainingMap.put(training.getId(), training);
    }

    public void delete(Long id) {
        trainingMap.remove(id);
    }
}

package gym.dao;

import gym.entities.TrainingType;
import gym.storage.GymStorage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TrainingTypeDAO implements DataAccessObject<TrainingType>{

    private Map<Long, TrainingType> trainingTypeMap;

    public TrainingTypeDAO(GymStorage storage) {
        this.trainingTypeMap = storage.getTrainingTypes();
    }

    public List<TrainingType> findAll() {
        return new ArrayList<>(trainingTypeMap.values());
    }

    public TrainingType findById(Long id) {
        return trainingTypeMap.get(id);
    }

    public void save(TrainingType trainingType) {
        trainingTypeMap.put(trainingType.getId(), trainingType);
    }

    public void delete(Long id) {
        trainingTypeMap.remove(id);
    }
}

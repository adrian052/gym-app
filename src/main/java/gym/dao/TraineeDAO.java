package gym.dao;

import gym.entities.Trainee;
import gym.storage.GymStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TraineeDAO implements DataAccessObject<Trainee> {

    private Map<Long, Trainee> traineeMap;

    @Autowired
    public TraineeDAO(GymStorage storage) {
        this.traineeMap = storage.getTrainees();
    }

    public List<Trainee> findAll() {
        return new ArrayList<>(traineeMap.values());
    }

    public Trainee findById(Long id) {
        return traineeMap.get(id);
    }

    public void save(Trainee trainee) {
        traineeMap.put(trainee.getId(), trainee);
    }

    public void delete(Long id) {
        traineeMap.remove(id);
    }
}

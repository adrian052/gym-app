package gym.dao;

import gym.entities.Trainee;
import gym.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TraineeDAO {

    private Map<Long, Trainee> traineeMap;

    @Autowired
    public TraineeDAO(InMemoryStorage storage) {
        this.traineeMap = storage.getTrainees();
    }

    public List<Trainee> findAll() {
        System.out.println(traineeMap.size());

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

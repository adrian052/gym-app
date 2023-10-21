package gym.dao;

import gym.entities.Trainee;
import gym.storage.GymStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TraineeDAO implements DataAccessObject<Trainee> {
    private GymStorage storage;

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    public List<Trainee> findAll() {
        return new ArrayList<>(storage.getTrainees().values());
    }

    public Trainee findById(Long id) {
        return storage.getTrainees().get(id);
    }

    public void save(Trainee trainee) {
        storage.getTrainees().put(trainee.getId(), trainee);
    }

    public void delete(Long id) {
        storage.getTrainees().remove(id);
    }
}

package gym.dao;

import gym.entities.Trainer;
import gym.storage.GymStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainerDAO implements DataAccessObject<Trainer>{

    private GymStorage storage;

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    public List<Trainer> findAll() {
        return new ArrayList<>(storage.getTrainers().values());
    }

    public Trainer findById(Long id) {
        return storage.getTrainers().get(id);
    }

    public void save(Trainer trainer) {
        storage.getTrainers().put(trainer.getId(), trainer);
    }

    public void delete(Long id) {
        storage.getTrainers().remove(id);
    }
}

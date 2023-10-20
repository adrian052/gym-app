package gym.dao;

import gym.entities.Trainer;
import gym.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TrainerDAO {

    private Map<Long, Trainer> trainerMap;

    @Autowired
    public TrainerDAO(InMemoryStorage storage) {
        this.trainerMap = storage.getTrainers();
    }

    public List<Trainer> findAll() {
        return new ArrayList<>(trainerMap.values());
    }

    public Trainer findById(Long id) {
        return trainerMap.get(id);
    }

    public void save(Trainer trainer) {
        trainerMap.put(trainer.getId(), trainer);
    }

    public void delete(Long id) {
        trainerMap.remove(id);
    }
}

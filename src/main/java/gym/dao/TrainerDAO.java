package gym.dao;

import gym.entities.Trainer;
import gym.storage.GymStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TrainerDAO extends GenericDAO<Trainer> {
    private GymStorage storage;
    @Autowired
    public void setStorage(GymStorage storage){
        this.storage = storage;
    }
    @Override
    protected Map<Long, Trainer> getEntityMap() {
        return storage.getTrainers();
    }

    @Override
    protected Long getId(Trainer entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Trainer entity, Long id) {
        entity.setId(id);
    }
}

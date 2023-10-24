package gym.dao;

import gym.entities.Trainee;
import gym.storage.GymStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Map;

@Component
public class TraineeDAO extends GenericDAO<Trainee> {
    private GymStorage storage;
    @Autowired
    public void setStorage(GymStorage storage){
        this.storage = storage;
    }
    @Override
    protected Map<Long, Trainee> getEntityMap() {
        return storage.getTrainees();
    }

    @Override
    protected Long getId(Trainee entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Trainee entity, Long id) {
        entity.setId(id);
    }
}

package gym.dao;


import gym.entities.Training;
import gym.storage.GymStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainingDAO extends GenericDAO<Training> {
    private GymStorage storage;
    @Autowired
    public void setStorage(GymStorage storage){
        this.storage = storage;
    }
    @Override
    protected Map<Long, Training> getEntityMap() {
        return storage.getTrainings();
    }

    @Override
    protected Long getId(Training entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Training entity, Long id) {
        entity.setId(id);
    }
}

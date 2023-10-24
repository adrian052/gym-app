package gym.dao;


import gym.entities.TrainingType;
import gym.storage.GymStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainingTypeDAO extends GenericDAO<TrainingType> {
    private GymStorage storage;
    @Autowired
    public void setStorage(GymStorage storage){
        this.storage = storage;
    }
    @Override
    protected Map<Long, TrainingType> getEntityMap() {
        return storage.getTrainingTypes();
    }

    @Override
    protected Long getId(TrainingType entity) {
        return entity.getId();
    }

    @Override
    protected void setId(TrainingType entity, Long id) {
        entity.setId(id);
    }
}

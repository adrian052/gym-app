package gym.dao;


import gym.entities.User;
import gym.storage.GymStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class UserDAO extends GenericDAO<User> {
    private GymStorage storage;
    @Autowired
    public void setStorage(GymStorage storage){
        this.storage = storage;
    }
    @Override
    protected Map<Long, User> getEntityMap() {
        return storage.getUsers();
    }

    @Override
    protected Long getId(User entity) {
        return entity.getId();
    }

    @Override
    protected void setId(User entity, Long id) {
        entity.setId(id);
    }
}

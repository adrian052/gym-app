package gym.dao;

import gym.entities.User;
import gym.storage.GymStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO implements DataAccessObject<User>{

    private GymStorage storage;

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    public List<User> findAll() {
        return new ArrayList<>(storage.getUsers().values());
    }

    public User findById(Long id) {
        return storage.getUsers().get(id);
    }

    public void save(User user) {
        storage.getUsers().put(user.getId(), user);
    }

    public void delete(Long id) {
        storage.getUsers().remove(id);
    }
}

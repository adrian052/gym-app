package gym.dao;

import gym.entities.User;
import gym.storage.GymStorage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO implements DataAccessObject<User>{
    private Map<Long, User> userMap;

    public UserDAO(GymStorage storage) {
        this.userMap = storage.getUsers();
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public User findById(Long id) {
        return userMap.get(id);
    }

    public void save(User user) {
        userMap.put(user.getId(), user);
    }

    public void delete(Long id) {
        userMap.remove(id);
    }
}

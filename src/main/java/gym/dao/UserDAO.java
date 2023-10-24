package gym.dao;

import gym.entities.User;
import gym.storage.GymStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO implements DataAccessObject<User> {
    private GymStorage storage;

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    public void setStorage(GymStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.getUsers().values());
    }

    @Override
    public User findById(Long id) {
        return storage.getUsers().get(id);
    }

    @Override
    public Long save(User user) {
        if (user.getId() != null) {
            storage.getUsers().put(user.getId(), user);
            logger.info("Updated User with ID: {}", user.getId());
            return user.getId();
        } else {
            Long newId = storage.getNextUserId();
            user.setId(newId);
            storage.getUsers().put(newId, user);
            logger.info("Saved User with new ID: {}", newId);
            return newId;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (storage.getUsers().containsKey(id)) {
            storage.getUsers().remove(id);
            logger.info("Deleted User with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete User with ID: {} - User not found.", id);
            return false;
        }
    }

    public boolean isUsernameTaken(String username) {
        for (User user : findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}

package gym.dao;

import gym.entities.Training;
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
        Long userId = user.getId();
        if (userId == null) {
            userId = storage.getNextUserId();
            user.setId(userId);
            logger.info("Saved new User with ID: {}", userId);
        } else {
            logger.info("Updated User with ID: {}", userId);
        }
        storage.getUsers().put(userId, user);
        return userId;
    }


    @Override
    public boolean delete(Long id) {
        User removedUser = storage.getUsers().remove(id);
        if (removedUser != null) {
            logger.info("Deleted User with ID: {}", id);
            return true;
        } else {
            logger.warn("Failed to delete User with ID: {} - User not found.", id);
            return false;
        }
    }
}

package gym.dao.db;

import gym.dao.DataAccessObject;
import gym.entities.User;

public interface UserDbDao extends DataAccessObject<User> {
    User findByUsername(String username);
}

package gym.dao;

import gym.entities.User;

public interface UserDao extends CRUD<User>{
    User findByUsername(String username);
}
